package com.robolucha.game;

// TODO: create for Lua implementation
// @deprecated
public class JavascriptSecurityTest {

//	private static Logger logger = Logger.getLogger(JavascriptSecurityTest.class);
//
//	@Before
//	public void setUp() throws Exception {
//		matchRunGen.setup();
//	}
//
//	BeanMockDataGenerator matchRunGen = new BeanMockDataGenerator() {
//		public Class getClassToTest() {
//			return MatchRun.class;
//		}
//	};
//
//
//	/**
//	 * verifica a existencia da variavel "me" do tipo LutchadorMatchState no
//	 * contexto do codigo de start
//	 *
//	 * @throws ScriptException
//	 * @throws NoSuchMethodException
//	 * @throws IllegalAccessException
//	 * @throws InstantiationException
//	 */
//	@Test
//	public void testVariableME() throws NoSuchMethodException, ScriptException, InstantiationException, IllegalAccessException {
//
//		String invalid[] = {
//				"f = new java.io.File('test.txt')",
//				"fo = new com.robolucha.game.LutchadorPublicState();o = fo.getClass().forName('java.io.File')",
//				"o = new com.robolucha.game.LutchadorPublicState(); j = o.getClass().forName('java.lang.Thread').newInstance(); j.start();" };
//
//		MatchRun match = (MatchRun) matchRunGen.mock();
//		MatchRunner runner = new MatchRunner(match);
//
//
//		for (int i = 0; i < invalid.length; i++) {
//			Luchador l1 = MockLuchador.build();
//			l1.setId(1L);
//			List<Code> codes = new ArrayList<Code>();
//
//			Code code = new Code();
//			code.setEvent("start");
//			code.setScript(invalid[i]);
//			codes.add(code);
//			l1.getCodePackage().setCodes(codes);
//
//			LuchadorRunner one = new LuchadorRunner(l1,runner,null);
//			assertTrue(!one.isActive());
//		}
//	}

}
