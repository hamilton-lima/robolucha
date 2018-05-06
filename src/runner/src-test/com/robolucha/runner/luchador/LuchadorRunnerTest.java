package com.robolucha.runner.luchador;

import com.robolucha.game.vo.MessageVO;
import com.robolucha.models.Code;
import com.robolucha.models.Luchador;
import com.robolucha.runner.MatchRunner;
import com.robolucha.test.MockLuchador;
import com.robolucha.test.MockMatchRunner;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LuchadorRunnerTest {

    private static Logger logger = Logger.getLogger(LuchadorRunnerTest.class);

    @Test
    public void testEmptyCode() throws Exception {

        MatchRunner runner = MockMatchRunner.build();

        Luchador l1 = MockLuchador.build();
        l1.setId(1L);
        List<Code> codes = new ArrayList<Code>();

        Code code = new Code();
        code.setEvent("start");
        code.setScript(" "); // EMPTY CODE!!
        l1.getCodes().add(code);

        LuchadorRunner one = new LuchadorRunner(l1, runner, null);

        // TODO: add observable
        one.run("start");

        // assertTrue(!one.isActive());
        assertTrue("verificar se codigo NAO possui erro apos tentar atualizar codigo vazio",
                code.getException() == null);

        MessageVO message = one.getMessage();
        Assert.assertNull("no message is expected when the code is empty", message);

    }

    @Test
    public void testInvalidCode() throws Exception {

        MatchRunner runner = MockMatchRunner.build();

        Luchador l1 = MockLuchador.build();
        l1.setId(1L);

        Code code = new Code();
        code.setEvent("start");
        code.setScript("this is an invalid code");
        l1.getCodes().add(code);

        LuchadorRunner one = new LuchadorRunner(l1, runner, null);
        one.run("start");

        while (one.getCurrentRunner() != null) {
            // wait for the runner to complete
            Thread.sleep(50);
        }

        // assertTrue(!one.isActive());
        assertTrue("verificar se codigo com erro fica com excecao apos tentar atualizar codigo com defeito",
                code.getException() != null);

        MessageVO message = one.getMessage();
        Assert.assertEquals(MessageVO.DANGER, message.type);
    }

    @Test
    public void testNOCODE() throws Exception {

        String[] methods = {"repeat", "onHitWall", "onHitOther", "onFound", "onGotDamage", "onListen"};
        MatchRunner runner = MockMatchRunner.build();

        for (int i = 0; i < methods.length; i++) {
            Luchador l1 = MockLuchador.build();
            l1.setId(1L);

            LuchadorRunner one = new LuchadorRunner(l1, runner, null);
            one.run(methods[i]);
        }

        Luchador l1 = MockLuchador.build();
        l1.setId(1L);
        LuchadorRunner one = new LuchadorRunner(l1, runner, null);
        one.run("invalidMethod");

        while (one.getCurrentRunner() != null) {
            // wait for the runner to complete
            Thread.sleep(50);
        }

        MessageVO message = one.getMessage();
        Assert.assertNull("no error when call an not existing or empty function", message);
    }

    /**
     * verifica a existencia dos metodos padrao existentes na classe
     * JavascriptFacade na implementacao javascript do LutchadorRunner
     *
     * @throws ScriptException
     * @throws NoSuchMethodException
     */
    @Test
    public void testFacadeMethodsCall() throws Exception, ScriptException {
        Luchador l1 = MockLuchador.build();
        l1.setId(1L);

        Code c2 = new Code();
        c2.setEvent("start");
        c2.setScript("");
        l1.getCodes().add(c2);

        LuchadorRunner one = new LuchadorRunner(l1, MockMatchRunner.build(), null);

        c2.setScript("move(10);");
        one.updateCodeEngine();
        one.run("start");
        assertEquals("move(10)", one.facade.getLastCall());

        c2.setScript("stop();");
        one.updateCodeEngine();
        one.run("start");
        assertEquals("stop()", one.facade.getLastCall());

        c2.setScript("reset();");
        one.updateCodeEngine();
        one.run("start");
        assertEquals("reset()", one.facade.getLastCall());

        c2.setScript("turn(11);");
        one.updateCodeEngine();
        one.run("start");
        assertEquals("turn(11)", one.facade.getLastCall());

        c2.setScript("turnGun(45);");
        one.updateCodeEngine();
        one.run("start");
        assertEquals("turnGun(45)", one.facade.getLastCall());

        c2.setScript("fire(3);");
        one.updateCodeEngine();
        one.run("start");
        assertEquals("fire(3)", one.facade.getLastCall());

        c2.setScript("punch();");
        one.updateCodeEngine();
        one.run("start");
        assertEquals("punch()", one.facade.getLastCall());

    }

    /**
     * verifica a existencia da variavel "me" do tipo LutchadorMatchState no
     * contexto do codigo de start
     *
     * @throws ScriptException
     * @throws NoSuchMethodException
     */
    @Test
    public void testVariableME() throws Exception, ScriptException {

        Luchador l1 = MockLuchador.build();
        l1.setId(1L);
        List<Code> codes = new ArrayList<Code>();

        Code code = new Code();
        code.setEvent("start");
        code.setScript("function getLife(){debug(me); return me.life;}");
        l1.getCodes().add(code);

        MatchRunner runner = MockMatchRunner.build();
        LuchadorRunner one = new LuchadorRunner(l1, runner, null);

        String result = one.getString("getLife()");
        logger.debug(">>>> #1 call result = " + result);
        String expected = Integer.toString(l1.getLife());
        assertTrue(expected.equals(result));

        one.damage(3);
        one.run("repeat");

        while (one.currentRunner != null) {
            // wait for the runner to complete
            Thread.sleep(50);
        }

        result = one.getString("getLife()");
        logger.debug(">>>> #2 call result = " + result);
        assertEquals("17", result);
    }

    @Test
    public void testMultipleLutchadores() throws Exception, ScriptException {

        MatchRunner runner = MockMatchRunner.build();
        Luchador l1 = MockLuchador.build();
        l1.setId(1L);

        Code code = new Code();
        code.setEvent("start");
        code.setScript("var counter = 3; function count(a){counter += a; return counter;}");
        l1.getCodes().add(code);

        LuchadorRunner one = new LuchadorRunner(l1, runner, null);

        Luchador l2 = MockLuchador.build(2L,
                "start",
                "var counter = 10; function count(a){counter += a; debug('updated counter=' + counter); return counter;}");

        LuchadorRunner two = new LuchadorRunner(l2, runner, null);
        two.run("count", 42);

        while (one.currentRunner != null) {
            // wait for the runner to complete
            Thread.sleep(50);
        }

        String resultOne = (String) one.getString("counter");
        String resultTwo = (String) two.getString("counter");

        logger.debug(">>>> call resultOne = " + resultOne);
        logger.debug(">>>> call resultTwo = " + resultTwo);

        assertEquals("3", resultOne);
        assertEquals("52", resultTwo);
    }

    @Test
    public void testStart() throws Exception, ScriptException {
        Luchador l1 = MockLuchador.build(1L,
                "start",
                "var counter = 3; function count(a){counter += a; return counter;}");

        MatchRunner runner = MockMatchRunner.build();
        LuchadorRunner one = new LuchadorRunner(l1, runner, null);
        one.run("count", 42);

        while (one.currentRunner != null) {
            // wait for the runner to complete
            Thread.sleep(50);
        }

        Double result = Double.parseDouble(one.getString("counter"));
        logger.debug("count call result = " + result);
        Double expected = new Double(45);
        assertTrue(expected.equals(result));
    }


    @Test
    public void testMethodsWithoutParameter() throws Exception, ScriptException {

        String[] methods = {"repeat", "onHitWall"};
        MatchRunner runner = MockMatchRunner.build();

        for (int i = 0; i < methods.length; i++) {
            Luchador l1 = MockLuchador.build();
            l1.setId(1L);
            List<Code> codes = new ArrayList<Code>();

            Code code = new Code();
            code.setEvent(methods[i]);
            code.setScript("counter ++; return counter;");
            codes.add(code);

            Code c2 = new Code();
            c2.setEvent("start");
            c2.setScript("var counter = 3;");
            codes.add(c2);

            l1.getCodes().addAll(codes);

            LuchadorRunner one = new LuchadorRunner(l1, runner, null);
            one.run(methods[i]);

            String result = one.getString("counter");
            while (one.currentRunner != null) {
                // wait for the runner to complete
                Thread.sleep(50);
            }

            logger.debug("===== method=" + methods[i] + ", count call result = " + result);
            Double expected = new Double(4);
            assertTrue(expected.equals(result));

        }

    }

}
