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

@Entity
@Data
@Table(name = "sala")
public class Sala {

	@Id
	private Long idSala = randomId();
	@OneToOne
	private Pelicula pelicula;
	@OneToMany
	@JoinColumn(name = "idAsiento")
	private List<Asiento> asientos;
	
	private Integer cantidadAsientos;

	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id < 0 ? -id : id;
		return id;
	}

}
