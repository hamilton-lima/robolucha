package com.robolucha.runner.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.athanazio.saramago.client.Bean;
import com.athanazio.saramago.server.bean.SystemRole;
import com.athanazio.saramago.server.dao.SaramagoColumn;

@Entity
@SaramagoColumn(label = "Arquivo", description = "Representa link para arquivo armazenado no AWS/S3")
public class File extends Bean {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true, nullable = false)
	@SaramagoColumn(label = "nome", description = "nome do arquivo")
	private String name;

	@Column(unique = false, nullable = true)
	@SaramagoColumn(label = "url", description = "caminho da internet para o arquivo")
	private String url;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	@Override
	public String toString() {
		return "File [id=" + id + ", name=" + name + ", url=" + url + "]";
	}

	
}
