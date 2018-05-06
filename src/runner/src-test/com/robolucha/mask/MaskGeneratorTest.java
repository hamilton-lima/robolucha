package com.robolucha.mask;

import com.robolucha.models.MaskConfig;
import com.robolucha.runner.luchador.mask.MaskGenerator;
import com.robolucha.runner.luchador.mask.NMSColor;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MaskGeneratorTest {

	@Test
	public void testRandom() {
		for (int i = 0; i < 10000; i++) {

			MaskConfig random = MaskGenerator.getInstance().random();
			assertNotNull(random.background);
			assertNotNull(random.backgroundColor);
			assertNotNull(random.background2);
			assertNotNull(random.background2Color);
			assertNotNull(random.ornamentTop);
			assertNotNull(random.ornamentTopColor);
			assertNotNull(random.ornamentBottom);
			assertNotNull(random.ornamentBottomColor);
			assertNotNull(random.face);
			assertNotNull(random.faceColor);
			assertNotNull(random.mouth);
			assertNotNull(random.mouthColor);
			assertNotNull(random.eye);
			assertNotNull(random.eyeColor);

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
