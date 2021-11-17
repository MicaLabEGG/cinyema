package com.cinyema.app.entidades;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
public class Ticket {

	@Id
	private Long idTicket = randomId();
	@OneToOne
	private Pelicula pelicula;
	@ManyToOne
	@JoinColumn(name = "FK_TICKUSER", nullable = false, updatable = false)
	private Usuario usuario;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String fecha;
	private Double precio;
	@ManyToOne
	@JoinColumn(name = "FK_TICKFUNC", nullable = false, updatable = false)
	private Funcion funcion;
	@OneToOne
	private Asiento asiento;

	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id<0 ? -id:id;
		return id;
	}

}