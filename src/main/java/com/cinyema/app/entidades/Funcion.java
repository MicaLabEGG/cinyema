package com.cinyema.app.entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
public class Funcion {
	
	@Id
	private Long idFuncion = randomId();
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String fecha;
	private String horario;
	@OneToOne
	private Pelicula pelicula;
	@ManyToOne
	@JoinColumn(name = "sala_id")
	private Sala sala;
	@OneToMany(mappedBy = "funcion")
	private List<Ticket> tickets;
	

	
	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id < 0 ? -id : id;
		return id;
	}
}
