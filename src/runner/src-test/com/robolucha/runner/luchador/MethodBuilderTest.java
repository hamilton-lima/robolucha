package com.robolucha.runner.luchador;

import com.robolucha.models.Luchador;
import com.robolucha.models.MaskConfigVO;
import com.robolucha.runner.MatchRunner;
import com.robolucha.test.MockLuchador;
import com.robolucha.test.MockMatchRunner;
import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MethodBuilderTest {

    private static Logger logger = Logger.getLogger(MethodBuilderTest.class);

    // TODO: add test MethodNames.REPEAT, "var a = 1; var b = 2; if( a => b ) b = a;");
    // TODO: add test MethodNames.REPEAT, "move(10);");
    // TODO: add test MethodNames.REPEAT, "move(10);nheco;"); ==> expect error

    @Test
    public void buildAllWithLuaFunctionInCode() {
        MatchRunner match = MockMatchRunner.build();
        Luchador luchador = MockLuchador.build(1L, MethodNames.REPEAT, "print('1');");
        LuchadorRunner runner = new LuchadorRunner(luchador, match, new MaskConfigVO());
        MethodBuilder.getInstance().buildAll(runner, luchador.getCodes());

        long errors = luchador.getCodes().stream().filter(code -> code.getException() != null).map(
                code -> {
                    logger.debug("code exception: >>> " + code.getException());
                    return code;
                }).count();

        assertEquals(0, errors);
    }

    @Test
    public void buildAll() {
        MatchRunner match = MockMatchRunner.build();
        Luchador luchador = MockLuchador.build(1L, MethodNames.REPEAT, "fire(1)");
        LuchadorRunner runner = new LuchadorRunner(luchador, match, new MaskConfigVO());
        MethodBuilder.getInstance().buildAll(runner, luchador.getCodes());

        long errors = luchador.getCodes().stream().filter(code -> code.getException() != null).map(
                code -> {
                    logger.debug("code exception: >>> " + code.getException());
                    return code;
                }).count();

        assertEquals(0, errors);
    }

    @Test
    public void build() {
    }
}