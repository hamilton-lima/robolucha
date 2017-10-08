package com.robolucha.runner.models;

import javax.persistence.Column;

import com.athanazio.saramago.client.Bean;
import com.athanazio.saramago.server.dao.SaramagoColumn;

public class MapTileset extends Bean {

	@SaramagoColumn(description = "Altura dos tiles")
	@Column(unique = false, nullable = false)
	private Integer tileHeight;
	
	@SaramagoColumn(description = "Largura dos tiles")
	@Column(unique = false, nullable = false)	
	private Integer tileWidth;
	
	@SaramagoColumn(description = "ID do primeiro Tile")
	@Column(unique = false, nullable = false)
	private Integer firstgid;
	
	@SaramagoColumn(description = "Numero de tiles no Tileset")
	@Column(unique = false, nullable = false)
	private Integer tilecount;
	
	@SaramagoColumn(description = "Numero de colunas")
	@Column(unique = false, nullable = false)
	private Integer columns;
	
	@SaramagoColumn(description = "Espaçamento, em pixels, entre tiles")
	@Column(unique = false, nullable = true)
	private Integer spacing;
	
	@SaramagoColumn(description = "Margem em volta dos tiles")
	@Column(unique = false, nullable = true)
	private Integer margin;
	
	@SaramagoColumn(description = "Arquivo de imagem do Tileset")
	@Column(unique = false, nullable = true)
	private File imgFile;
	
	@Column(unique = false, nullable = false)
	private GameDefinition map;
	
	public GameDefinition getMap() {
		return map;
	}

	public void setMap(GameDefinition map) {
		this.map = map;
	}

	public File getImgFile() {
		return imgFile;
	}

	public Integer getFirstgid() {
		return firstgid;
	}

	public void setFirstgid(Integer firstgid) {
		this.firstgid = firstgid;
	}

	public Integer getTilecount() {
		return tilecount;
	}

	public void setTilecount(Integer tilecount) {
		this.tilecount = tilecount;
	}

	public Integer getColumns() {
		return columns;
	}

	public void setColumns(Integer columns) {
		this.columns = columns;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(Integer tileHeight) {
		this.tileHeight = tileHeight;
	}

	public Integer getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(Integer tileWidth) {
		this.tileWidth = tileWidth;
	}

	public Integer getSpacing() {
		return spacing;
	}

	public void setSpacing(Integer spacing) {
		this.spacing = spacing;
	}

	public Integer getMargin() {
		return margin;
	}

	public void setMargin(Integer margin) {
		this.margin = margin;
	}

	
	@Override
	public String toString() {
		return "MapTileset [tileHeight=" + tileHeight + ", tileWidth="
				+ tileWidth + ", firstgid=" + firstgid + ", tilecount="
				+ tilecount + ", columns=" + columns + ", spacing=" + spacing
				+ ", margin=" + margin + ", imgFile=" + imgFile + ", map="
				+ map + "]";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@Override
	public void setId(Long arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setName(String arg0) {
		// TODO Auto-generated method stub

	}

}
