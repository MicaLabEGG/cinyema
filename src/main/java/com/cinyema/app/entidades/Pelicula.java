package com.cinyema.app.entidades;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cinyema.app.enumeraciones.Genero;
import com.cinyema.app.enumeraciones.Idioma;
import com.cinyema.app.enumeraciones.Pais;
import com.cinyema.app.enumeraciones.Subtitulo;

@Entity
@Table(name = "pelicula")
public class Pelicula {
	
	@Id
	private Long idPelicula = randomId();
	private String titulo;
	private String anio;
	private String descripcion;
	private String duracion;
	@Enumerated(EnumType.STRING)
	private Genero genero;
	@Enumerated(EnumType.STRING)
	private Pais pais;
	@Enumerated(EnumType.STRING)
	private Idioma idioma;
	@Enumerated(EnumType.STRING)
	private Subtitulo subtitulo;
	private Imagen imagen;
	private Boolean alta;
	@OneToOne
	private Director director;
	@OneToMany
	private Actor actores;
	
	public Pelicula() {
		
	}
	
	public Pelicula(Long idPelicula, String titulo, String anio, String descripcion, String duracion, Genero genero,
			Pais pais, Idioma idioma, Subtitulo subtitulo, Imagen imagen, Boolean alta, Director director, Actor actores) {
		super();
		this.idPelicula = idPelicula;
		this.titulo = titulo;
		this.anio = anio;
		this.descripcion = descripcion;
		this.duracion = duracion;
		this.genero = genero;
		this.pais = pais;
		this.idioma = idioma;
		this.subtitulo = subtitulo;
		this.imagen = imagen;
		this.alta = alta;
		this.director = director;
		this.actores = actores;
	}
	
	


	public Long getIdPelicula() {
		return idPelicula;
	}

	public void setIdPelicula(Long idPelicula) {
		this.idPelicula = idPelicula;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

	public Subtitulo getSubtitulo() {
		return subtitulo;
	}

	public void setSubtitulo(Subtitulo subtitulo) {
		this.subtitulo = subtitulo;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public Boolean getAlta() {
		return alta;
	}

	public void setAlta(Boolean alta) {
		this.alta = alta;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}

	public Actor getActores() {
		return actores;
	}

	public void setActores(Actor actores) {
		this.actores = actores;
	}

	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id<0 ? -id:id;
		return id;
	}

}
