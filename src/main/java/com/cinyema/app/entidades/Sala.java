package com.cinyema.app.entidades;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sala")
public class Sala {

	@Id
	private Long idSala = randomId();
	
	/*
	 * chequear la relacion
	 */
	@OneToMany
	private List<Asiento> asientos;
	
	public Sala() {
	}
	
	
	
	public Sala(Long idSala, List<Asiento> asientos) {
		super();
		this.idSala = idSala;
		this.asientos = asientos;
	}

	

	public Long getIdSala() {
		return idSala;
	}



	public void setIdSala(Long idSala) {
		this.idSala = idSala;
	}



	public List<Asiento> getAsientos() {
		return asientos;
	}



	public void setAsientos(List<Asiento> asientos) {
		this.asientos = asientos;
	}



	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id<0 ? -id:id;
		return id;
	}



	@Override
	public String toString() {
		return "Sala [idSala=" + idSala + ", asientos=" + asientos + "]";
	}
	
	
}
