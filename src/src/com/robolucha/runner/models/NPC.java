package com.robolucha.runner.models;

import java.io.Serializable;

import javax.persistence.Entity;

import com.athanazio.saramago.server.dao.SaramagoColumn;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@JsonRootName("npc")
@SaramagoColumn(description = "definicoes do NPC")
public class NPC extends GameComponent implements Serializable {


}
