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

@Entity
public class Funcion {
	
	@Id
	private Long idFuncion = randomId();
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String fecha;
	private String horario;
	@ManyToOne
	@JoinColumn(name = "pelicula_id")
	private Pelicula pelicula;
	@ManyToOne
	@JoinColumn(name = "sala_id")
	private Sala sala;
	@OneToMany(mappedBy = "funcion")
	private List<Ticket> tickets;
	
	public Funcion(Long idFuncion, String fecha, String horario, Pelicula pelicula, Sala sala, List<Ticket> tickets) {
		super();
		this.idFuncion = idFuncion;
		this.fecha = fecha;
		this.horario = horario;
		this.pelicula = pelicula;
		this.sala = sala;
		this.tickets = tickets;
	}

	public Funcion() {
		super();
	}

	public Long getIdFuncion() {
		return idFuncion;
	}

	public void setIdFuncion(Long idFuncion) {
		this.idFuncion = idFuncion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	@Override
	public String toString() {
		return "Funcion [idFuncion=" + idFuncion + ", fecha=" + fecha + ", horario=" + horario + ", tickets=" + tickets + "]";
	}

	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id < 0 ? -id : id;
		return id;
	}
}
