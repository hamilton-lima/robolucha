package com.robolucha.runner.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.athanazio.saramago.client.Bean;
import com.athanazio.saramago.server.dao.SaramagoColumn;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@JsonRootName("translation")
@SaramagoColumn(description = "Traduções dos textos")
public class Translation extends Bean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = false, nullable = false)
	private String name;
	
	@ManyToOne(optional = false)
	private I18NLang i18nLang;

	@Column(unique = false, nullable = true)
	private String translation;

	@Column(unique = false, nullable = true)
	private String translationStatus;

	@Override
	public String toString() {
		return "Translation [id=" + id + ", name=" + name + ", i18nLang=" + i18nLang + ", translation=" + translation
				+ ", translationStatus=" + translationStatus + "]";
	}
	
	public String getTranslationStatus() {
		return translationStatus;
	}

	public void setTranslationStatus(String translationStatus) {
		this.translationStatus = translationStatus;
	}

	public I18NLang getI18nLang() {
		return i18nLang;
	}

	public void setI18nLang(I18NLang i18nLang) {
		this.i18nLang = i18nLang;
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
