package com.cinyema.app.entidades;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "asiento")
public class Asiento {
	

	@Id
	private Long idAsiento = randomId();
	
	private String numeroDeAsiento;
	private Boolean libre;
	
	@ManyToOne
	private Sala sala;
	
	
	public Asiento() {
	}
	
	


	

	


	public Asiento(Long idAsiento, String numeroDeAsiento, Boolean libre, Sala sala) {
		super();
		this.idAsiento = idAsiento;
		this.numeroDeAsiento = numeroDeAsiento;
		this.libre = libre;
		this.sala = sala;
	}

	



	public Long getIdAsiento() {
		return idAsiento;
	}









	public void setIdAsiento(Long idAsiento) {
		this.idAsiento = idAsiento;
	}









	public String getNumeroDeAsiento() {
		return numeroDeAsiento;
	}









	public void setNumeroDeAsiento(String numeroDeAsiento) {
		this.numeroDeAsiento = numeroDeAsiento;
	}









	public Boolean getLibre() {
		return libre;
	}









	public void setLibre(Boolean libre) {
		this.libre = libre;
	}









	public Sala getSala() {
		return sala;
	}









	public void setSala(Sala sala) {
		this.sala = sala;
	}


	
	
	@Override
	public String toString() {
		return "Asiento [idAsiento=" + idAsiento + ", numeroDeAsiento=" + numeroDeAsiento + ", libre=" + libre
				+ ", sala=" + sala + "]";
	}









	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id<0 ? -id:id;
		return id;
	}
	
}
