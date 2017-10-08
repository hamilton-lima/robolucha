package com.robolucha.runner.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.athanazio.saramago.client.Bean;
import com.athanazio.saramago.server.dao.SaramagoColumn;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@JsonRootName("code")
@SaramagoColumn(description = "definicoes do codigo de um lutchador para um evento")
public class Code extends Bean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = false, nullable = false)
	private String event;

	@Column(unique = false, nullable = false)
	private Long version = 1L;

	@Column(unique = false, nullable = true, length = 90000)
	private String script;

	@SaramagoColumn(description = "texto do erro de compilacao se houver algum")
	@Column(unique = false, nullable = true)
	private String exception;

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Code [id=" + id + ", event=" + event + ", version=" + version + ", script=" + script + ", exception="
				+ exception + "]";
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	@JsonIgnore
	public String getName() {
		return event + ":" + version;
	}

	@JsonIgnore
	public void setName(String name) {

	}

//	public CodePackage getCodePackage() {
//		return codePackage;
//	}
//
//	public void setCodePackage(CodePackage codePackage) {
//		this.codePackage = codePackage;
//	}

}
