package com.cinyema.app.entidades;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sala")
public class Cine {

	@Id
	private Long idCine = randomId();
	
	/*
	 * chequear la relacion
	 */
	@OneToMany
	private List<Sala> salas;
	
	public Cine() {
	}
	
	
	
	public Cine(Long idCine, List<Sala> salas) {
		super();
		this.idCine = idCine;
		this.salas = salas;
	}

	

	public Long getIdCine() {
		return idCine;
	}



	public void setIdCine(Long idCine) {
		this.idCine = idCine;
	}



	public List<Sala> getSalas() {
		return salas;
	}



	public void setSalas(List<Sala> salas) {
		this.salas = salas;
	}



	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id<0 ? -id:id;
		return id;
	}



	@Override
	public String toString() {
		return "Cine [idCine=" + idCine + ", salas=" + salas + "]";
	}
	
	
}
