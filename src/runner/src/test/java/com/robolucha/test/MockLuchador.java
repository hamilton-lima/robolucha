package com.robolucha.test;

import com.github.javafaker.Faker;
import com.robolucha.models.Code;
import com.robolucha.models.Luchador;
import com.robolucha.models.LuchadorMatchState;
import com.robolucha.runner.luchador.MethodNames;

import java.util.List;

public class MockLuchador {

	static Faker faker = new Faker();

	public static Luchador build() {
		Luchador a = new Luchador();
		a.setName(faker.name().username()); // TODO: should have 30 chars??
		return a;
	}

	public static Luchador build(long id) {
		Luchador a = build();
		a.setId(id);
		return a;
	}

	public static Luchador build(long id, String event, String code) {
		Luchador a = build(id);

		Code c2 = new Code();
		c2.setEvent( event);
		c2.setScript(code );
		a.getCodes().add(c2);

		return a;
	}

//	public static NPC buildNPC() {
//		NPC a = new NPC();
//		a.setName(DataGenerator.getRandomString(30));
//		a.setCodePackage(new CodePackage());
//		return a;
//	}

	public static Luchador createWithRepeatCode(String repeatCode) {

		Luchador a = MockLuchador.build();
		a.setName(faker.name().username());

		Code c = new Code();
		c.setEvent(MethodNames.REPEAT);
		c.setScript(repeatCode);

		a.getCodes().add(c);
		return a;
	}

	public static Code getRepeatCode(List<Code> codes) {
		return getCode(codes, MethodNames.REPEAT);
	}

	public static Code getCode(List<Code> codes, String event) {
		for (Code code : codes) {
			if (code.getEvent().equals(event)) {
				return code;
			}
		}

		return null;
	}

	public static LuchadorMatchState buildLuchadorMatchState(long id) {
		LuchadorMatchState result = new LuchadorMatchState();
		result.setId(id);
		result.setName(faker.name().username());
		return result;
	}
}
