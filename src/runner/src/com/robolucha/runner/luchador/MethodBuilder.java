package com.robolucha.runner.luchador;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.Context;

import org.apache.log4j.Logger;

import com.robolucha.models.Code;

/**
 * Gera assinatura de metodos javascript padrao, combinando com codigo fornecido
 * pelos programadores
 * 
 * @author hamiltonlima
 *
 */
public class MethodBuilder {

	private static MethodBuilder instance;

	private static Logger logger = Logger.getLogger(MethodBuilder.class);

	//TODO: create default code in lua
	private MethodBuilder() {
		methods = new HashMap<String, MethodDefinition>();
		add(MethodNames.START, "", "");
		add(MethodNames.REPEAT, "function repeat(){ //vazio\n", "\n}");
		add(MethodNames.ON_HIT_WALL, "function onHitWall(){ //vazio\n", "\n}");
		add(MethodNames.ON_HIT_OTHER, "function onHitOther(other){ //vazio\n", "\n}");
		add(MethodNames.ON_FOUND, "function onFound(other,chance){ //vazio\n", "\n}");
		add(MethodNames.ON_GOT_DAMAGE, "function onGotDamage(other,amount){ //vazio \n", "\n}");
		add(MethodNames.ON_LISTEN, "function onListen(other,message){ //vazio \n", "\n}");
	}

	private void add(String name, String start, String end) {
		methods.put(name, new MethodDefinition(name, start, end));
	}

	public static MethodBuilder getInstance() {
		if (instance == null) {
			instance = new MethodBuilder();
		}
		return instance;
	}

	private Map<String, MethodDefinition> methods;

	public void buildAll(LuchadorRunner runner, List<Code> codes) {

		HashMap<String, Code> local = populateLocalHash(codes);
		String script = "";
		Long codeId = 0L;
		String key = null;

		Iterator iterator = methods.keySet().iterator();
		while (iterator.hasNext()) {
			StringBuffer buffer = new StringBuffer();

			key = (String) iterator.next();
			script = "";
			codeId = 0L;
			
			Code code = null;

			if (local.containsKey(key)) {
				code = local.get(key);
				script = code.getScript();
				codeId = code.getId();
			}

			logger.debug("definindo codigo : key=" + key + ", script=" + script);
			MethodDefinition definition = methods.get(key);

			buffer.append(definition.getStart());
			buffer.append(script);
			buffer.append(definition.getEnd());

			String js = buffer.toString();
			if (logger.isDebugEnabled()) {
				logger.debug(js);
			}

			try {
				runner.eval(key, js);
			} catch (Exception e) {
				String message = "erro interpretando codigo code.id=" + codeId + " gamecomponent.id="
						+ runner.getGameComponent().getId();

				if( logger.isDebugEnabled()){
					logger.debug(">>>> code=" + code );
					logger.debug(">>>> erro=" + e.getMessage());
				}

				if( code != null ){
					runner.saveExceptionToCode(code.getEvent(), e.getMessage());
				}

				if (logger.isDebugEnabled()) {
					logger.error(message, e);
				} else {
					logger.error(message);
				}

				if (key != null) {
					local.get(key).setException(e.getMessage());
				}
			}
		}

	}

	private HashMap<String, Code> populateLocalHash(List<Code> codes) {
		HashMap<String, Code> result = new HashMap<String, Code>();
		if (codes != null) {
			Iterator iterator = codes.iterator();
			while (iterator.hasNext()) {
				Code code = (Code) iterator.next();
				result.put(code.getEvent(), code);
			}
		}
		return result;
	}

	public void build(LuchadorRunner runner, Code code) {

		logger.debug("definindo codigo : " + code);
		if (code == null) {
			return;
		}

		String script = code.getScript();
		Long codeId = code.getId();
		String key = code.getEvent();

		MethodDefinition definition = methods.get(key);
		StringBuffer buffer = new StringBuffer();
		buffer.append(definition.getStart());
		buffer.append(script);
		buffer.append(definition.getEnd());

		String js = buffer.toString();
		logger.debug(js);

		try {
			Context cx = Context.enter();
			cx.setClassShutter(new RhinoWhiteList());

			runner.eval(key, js);
		} catch (Exception e) {
			logger.error("erro interpretando codigo " + codeId);
			if (key != null) {
				code.setException(e.getMessage());
			}
		} finally {
			Context.exit();
		}

	}

}
