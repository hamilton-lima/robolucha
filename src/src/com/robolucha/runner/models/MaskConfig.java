package com.robolucha.runner.models;

public class MaskConfig {

	private Long id;
	private GameComponent gameComponent;
	private String name;
	private String background;
	private String backgroundColor;
	private String background2;
	private String background2Color;
	private String ornamentTop;
	private String ornamentTopColor;
	private String ornamentBottom;
	private String ornamentBottomColor;
	private String face;
	private String faceColor;
	private String mouth;
	private String mouthColor;
	private String eye;
	private String eyeColor;

	@Override
	public String toString() {
		return "MaskConfig [id=" + id + ", gameComponent=" + gameComponent + ", name=" + name + ", background="
				+ background + ", backgroundColor=" + backgroundColor + ", background2=" + background2
				+ ", background2Color=" + background2Color + ", ornamentTop=" + ornamentTop + ", ornamentTopColor="
				+ ornamentTopColor + ", ornamentBottom=" + ornamentBottom + ", ornamentBottomColor="
				+ ornamentBottomColor + ", face=" + face + ", faceColor=" + faceColor + ", mouth=" + mouth
				+ ", mouthColor=" + mouthColor + ", eye=" + eye + ", eyeColor=" + eyeColor + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GameComponent getGameComponent() {
		return gameComponent;
	}

	public void setGameComponent(GameComponent gameComponent) {
		this.gameComponent = gameComponent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getBackground2() {
		return background2;
	}

	public void setBackground2(String background2) {
		this.background2 = background2;
	}

	public String getBackground2Color() {
		return background2Color;
	}

	public void setBackground2Color(String background2Color) {
		this.background2Color = background2Color;
	}

	public String getOrnamentTop() {
		return ornamentTop;
	}

	public void setOrnamentTop(String ornamentTop) {
		this.ornamentTop = ornamentTop;
	}

	public String getOrnamentTopColor() {
		return ornamentTopColor;
	}

	public void setOrnamentTopColor(String ornamentTopColor) {
		this.ornamentTopColor = ornamentTopColor;
	}

	public String getOrnamentBottom() {
		return ornamentBottom;
	}

	public void setOrnamentBottom(String ornamentBottom) {
		this.ornamentBottom = ornamentBottom;
	}

	public String getOrnamentBottomColor() {
		return ornamentBottomColor;
	}

	public void setOrnamentBottomColor(String ornamentBottomColor) {
		this.ornamentBottomColor = ornamentBottomColor;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getFaceColor() {
		return faceColor;
	}

	public void setFaceColor(String faceColor) {
		this.faceColor = faceColor;
	}

	public String getMouth() {
		return mouth;
	}

	public void setMouth(String mouth) {
		this.mouth = mouth;
	}

	public String getMouthColor() {
		return mouthColor;
	}

	public void setMouthColor(String mouthColor) {
		this.mouthColor = mouthColor;
	}

	public String getEye() {
		return eye;
	}

	public void setEye(String eye) {
		this.eye = eye;
	}

	public String getEyeColor() {
		return eyeColor;
	}

	public void setEyeColor(String eyeColor) {
		this.eyeColor = eyeColor;
	}

}
