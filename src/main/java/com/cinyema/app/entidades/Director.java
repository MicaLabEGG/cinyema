package com.cinyema.app.entidades;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import com.cinyema.app.enumeraciones.Pais;

@Entity
public class Director {
	

	@Id
	private Long idDirector = randomId();
	
	@Column(unique = true)
	private String nombre;

	@Enumerated(EnumType.STRING)
	private Pais pais;
	
	
	public Director() {
		super();
	}

	public Director(Long idDirector, String nombre, Pais pais) {
		this.idDirector = idDirector;
		this.nombre = nombre;
		this.pais = pais;
	}
	
	public Director(String nombre) {
		this.nombre = nombre;
	}

	public Long getIdDirector() {
		return idDirector;
	}

	public void setIdDirector(Long idDirector) {
		this.idDirector = idDirector;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}
	
	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id<0 ? -id:id;
		return id;
	}
	
	@Override
	public String toString() {
		return "Director [idDirector=" + idDirector + ", nombre=" + nombre + ", pais=" + pais + "]";
	}

}
