package com.cinyema.app.entidades;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Autowired;

import com.cinyema.app.enumeraciones.Pais;
import com.cinyema.app.funciones.RandomId;

@Entity
public class Actor {
	
	@Autowired
	private RandomId randomId;
	
	@Id
	private Long idActor = randomId.randomId();
	@Column(unique = true)
	private String nombreCompleto;
	@Enumerated(EnumType.STRING)
	private Pais pais;
	@ManyToOne
    @JoinColumn(name = "FK_PELIACT", nullable = false, updatable = false)
    private Pelicula pelicula;
	
	
	public Actor(Long idActor, String nombreCompleto, Pais pais) {
		super();
		this.idActor = idActor;
		this.nombreCompleto = nombreCompleto;
		this.pais = pais;
	}

	public Actor() {
		super();
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	public Long getIdActor() {
		return idActor;
	}

	public void setIdActor(Long idActor) {
		this.idActor = idActor;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	@Override
	public String toString() {
		return "Actor [idActor=" + idActor + ", nombreCompleto=" + nombreCompleto + ", pais=" + pais + "]";
	}

}