package com.cinyema.app.entidades;

import java.util.List;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name = "sala")
public class Sala {

	@Id
	private Long idSala = randomId();
//	@OneToOne
//	private Pelicula pelicula;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "sa_fid", referencedColumnName = "idSala" )
	private List<Asiento> asientos;
	
	private Integer cantidadAsientos;

	private String nombreSala;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sala")
	private List<Funcion> funciones;

	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id < 0 ? -id : id;
		return id;
	}

}
