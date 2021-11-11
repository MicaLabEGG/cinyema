package com.cinyema.app.entidades;

import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import com.cinyema.app.enumeraciones.Pais;

@Entity
public class Actor {
	
	
	@Id
	private Long idActor = randomId();
	@Column(unique = true)
	private String nombreCompleto;
	@Enumerated(EnumType.STRING)
	private Pais pais;
	@ManyToMany(mappedBy = "actores")
    private List<Pelicula> peliculas;
	
	
	public Actor(Long idActor, String nombreCompleto, Pais pais) {
		super();
		this.idActor = idActor;
		this.nombreCompleto = nombreCompleto;
		this.pais = pais;
	}

	public Actor() {
		super();
	}
	

	public List<Pelicula> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(List<Pelicula> peliculas) {
		this.peliculas = peliculas;
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
	
	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id<0 ? -id:id;
		return id;
	}

	@Override
	public String toString() {
		return "Actor [idActor=" + idActor + ", nombreCompleto=" + nombreCompleto + ", pais=" + pais + "]";
	}

}