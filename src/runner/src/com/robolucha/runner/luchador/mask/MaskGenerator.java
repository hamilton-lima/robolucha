package com.robolucha.runner.luchador.mask;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.robolucha.models.MaskConfig;

public class MaskGenerator {

	public static final String[] background = { "background.png" };

	public static final String[] background2 = { "background2_0001.png", "background2_0002.png", "background2_0003.png",
			"background2_0004.png", "background2_0005.png", "background2_0006.png", "background2_0007.png",
			"background2_0008.png", "background2_0009.png", "background2_0010.png", "background2_0011.png",
			"background2_0012.png", "background2_0013.png", "background2_0014.png", "background2_0015.png" };

	public static final String[] ornamentTop = { "ornamento_cima0001.png", "ornamento_cima0002.png",
			"ornamento_cima0003.png", "ornamento_cima0004.png", "ornamento_cima0005.png", "ornamento_cima0006.png",
			"ornamento_cima0007.png", "ornamento_cima0008.png", "ornamento_cima0009.png", "ornamento_cima0010.png",
			"ornamento_cima0011.png" };

	public static final String[] ornamentBottom = { "ornamento_baixo0001.png", "ornamento_baixo0002.png",
			"ornamento_baixo0003.png", "ornamento_baixo0004.png", "ornamento_baixo0005.png", "ornamento_baixo0006.png",
			"ornamento_baixo0007.png", "ornamento_baixo0008.png", "ornamento_baixo0009.png",
			"ornamento_baixo0010.png" };

	public static final String[] face = { "rosto0001.png", "rosto0002.png", "rosto0003.png", "rosto0004.png",
			"rosto0005.png", "rosto0006.png", "rosto0007.png", "rosto0008.png", "rosto0009.png", "rosto0010.png" };

	public static final String[] mouth = { "boca0001.png", "boca0002.png", "boca0003.png", "boca0004.png",
			"boca0005.png", "boca0006.png", "boca0007.png", "boca0008.png", "boca0009.png", "boca0010.png",
			"boca0011.png", "boca0012.png", "boca0013.png", "boca0014.png", "boca0015.png", "boca0016.png",
			"boca0017.png", "boca0018.png", "boca0019.png", "boca0020.png" };

	public static final String[] eye = { "olho0001.png" };

	private static MaskGenerator instance;
	private LinkedHashMap<String, String[]> maskElements;

	private MaskGenerator() {
		maskElements = new LinkedHashMap<String, String[]>();
		maskElements.put("background", background);
		maskElements.put("background2", background2);
		maskElements.put("ornamentTop", ornamentTop);
		maskElements.put("ornamentBottom", ornamentBottom);
		maskElements.put("face", face);
		maskElements.put("mouth", mouth);
		maskElements.put("eye", eye);
	}

	public LinkedHashMap<String, String[]> getMaskElements() {
		return maskElements;
	}

	public static MaskGenerator getInstance() {
		if (null == instance) {
			instance = new MaskGenerator();
		}
		return instance;
	}

	public MaskConfig random() throws Exception {

		MaskConfig result = new MaskConfig();
		Iterator iterator = maskElements.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String[]> entry = (Entry<String, String[]>) iterator.next();
			String key = entry.getKey();
			String[] values = entry.getValue();

			String value;
			// randomize if String[] has more than one
			if (values.length > 1) {
				value = values[random(values.length)];
			} else {
				value = values[0];
			}

			String color = randomColor();

			// usa beanutil para atribuir valores dos campos
			setValue(result, key, value);
			setValue(result, key + "Color", color);
		}

		return result;
	}

	private void setValue(MaskConfig result, String fieldName, String value) throws Exception {
		Field field = result.getClass().getField(fieldName);
		field.set(result, value);
	}

	public int random(int n) {
		return (int) Math.floor((Math.random() * n));
	}

	public String randomColor() {
		int random = random(NMSColor.colors.length);
		return NMSColor.colors[random];
	}

}
