package com.cinyema.app.entidades;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cinyema.app.enumeraciones.Calificacion;
import com.cinyema.app.enumeraciones.Formato;
import com.cinyema.app.enumeraciones.Genero;
import com.cinyema.app.enumeraciones.Idioma;
import com.cinyema.app.enumeraciones.Pais;
import com.cinyema.app.enumeraciones.Subtitulo;

import lombok.Data;

@Data
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
	@Enumerated(EnumType.STRING)
	private Formato formato;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String imagen;
	private Boolean alta;
	@OneToOne
	private Director director;
	@ManyToMany
	@JoinTable(name = "peliculaActor", joinColumns = @JoinColumn(name = "idPelicula"), inverseJoinColumns = @JoinColumn(name = "idActor"))
	private List<Actor> actores;
	private Calificacion calificacion;
	private Double voto;
	private String trailer;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pelicula")
	private List<Funcion>funciones;

	public Pelicula() {
	}

	public Pelicula(Long idPelicula, String titulo, String anio, String descripcion, String duracion, Genero genero,
			Pais pais, Idioma idioma, Subtitulo subtitulo, String imagen, Boolean alta, Director director,
			List<Actor> actores, Calificacion calificacion, Double voto, String trailer, List<Funcion>funciones) {
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
		this.calificacion = calificacion;
		this.voto = voto;
		this.trailer = trailer;
		this.funciones = funciones;
	}

	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id < 0 ? -id : id;
		return id;
	}

}
