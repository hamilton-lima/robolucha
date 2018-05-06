package com.robolucha.mask;

import com.robolucha.bean.MaskConfig;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MaskGeneratorTest {

	@Test
	public void testRandom() {
		for (int i = 0; i < 10000; i++) {

			MaskConfig random = MaskGenerator.getInstance().random();
			assertNotNull(random.getBackground());
			assertNotNull(random.getBackgroundColor());
			assertNotNull(random.getBackground2());
			assertNotNull(random.getBackground2Color());
			assertNotNull(random.getOrnamentTop());
			assertNotNull(random.getOrnamentTopColor());
			assertNotNull(random.getOrnamentBottom());
			assertNotNull(random.getOrnamentBottomColor());
			assertNotNull(random.getFace());
			assertNotNull(random.getFaceColor());
			assertNotNull(random.getMouth());
			assertNotNull(random.getMouthColor());
			assertNotNull(random.getEye());
			assertNotNull(random.getEyeColor());

		}
	}

	@Test
	public void testRandomInt() {
		int max = 10;
		int r = -1;
		for (int i = 0; i < 10000; i++) {
			r = MaskGenerator.getInstance().random(max);
			assertTrue("sorteio nao pode ser superior ao maximo", r <= max);
			assertTrue("sorteio nao pode ser menor que zero", r >= 0);
		}
	}

	@Test
	public void testRandomColor() {
		String randomColor;
		for (int i = 0; i < 10000; i++) {
			randomColor = MaskGenerator.getInstance().randomColor();
			boolean found = false;
			for (int j = 0; j < NMSColor.colors.length; j++) {
				if (randomColor.equals(NMSColor.colors[j])) {
					found = true;
					break;
				}
			}
			assertTrue("cor sorteada precisa estar na lista de cores possiveis", found);

		}
	}

}
