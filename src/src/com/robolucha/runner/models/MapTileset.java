package com.robolucha.runner.models;

public class MapTileset {

	private int tileHeight;
	private int tileWidth;
	private int firstgid;
	private int tilecount;
	private int columns;
	private int spacing;
	private int margin;
	// TODO: review file storage location
	private String imgFile;
	private GameDefinition map;

	@Override
	public String toString() {
		return "MapTileset [tileHeight=" + tileHeight + ", tileWidth=" + tileWidth + ", firstgid=" + firstgid
				+ ", tilecount=" + tilecount + ", columns=" + columns + ", spacing=" + spacing + ", margin=" + margin
				+ ", imgFile=" + imgFile + ", map=" + map + "]";
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public int getFirstgid() {
		return firstgid;
	}

	public void setFirstgid(int firstgid) {
		this.firstgid = firstgid;
	}

	public int getTilecount() {
		return tilecount;
	}

	public void setTilecount(int tilecount) {
		this.tilecount = tilecount;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getSpacing() {
		return spacing;
	}

	public void setSpacing(int spacing) {
		this.spacing = spacing;
	}

	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

	public String getImgFile() {
		return imgFile;
	}

	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}

	public GameDefinition getMap() {
		return map;
	}

	public void setMap(GameDefinition map) {
		this.map = map;
	}

}
