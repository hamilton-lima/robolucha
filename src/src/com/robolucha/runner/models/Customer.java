package com.robolucha.runner.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.athanazio.saramago.client.Bean;
import com.athanazio.saramago.server.dao.SaramagoColumn;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@JsonRootName("customer")
@SaramagoColumn(description = "clientes autorizados a acessar o jogo")
public class Customer extends Bean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = false, nullable = false)
	private String name;

	@Column(unique = false, nullable = true)
	private String email;

	@Column(unique = false, nullable = true)
	private String locale;

	@Column(unique = true, nullable = true)
	private String facebookID;
	
	@Column(unique = false, nullable = true)
	private String profilePic;

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email
				+ ", locale=" + locale + ", facebookID=" + facebookID
				+ ", profilePic=" + profilePic + "]";
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebookID() {
		return facebookID;
	}

	public void setFacebookID(String facebookID) {
		this.facebookID = facebookID;
	}

}
