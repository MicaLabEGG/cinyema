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


@Entity
public class Ticket {

	@Id
	private Long idTicket = randomId();
	@ManyToOne
	@JoinColumn(name = "FK_TICKUSER", nullable = false, updatable = false)
	private Usuario usuario;
	@ManyToOne
	@JoinColumn(name = "FK_TICKFUNC", nullable = false, updatable = false)
	private Funcion funcion;
	@ManyToOne
	@JoinColumn(name = "FK_TICKASIEN", nullable = false, updatable = false)
	private Asiento asiento;

	public Ticket(Long idTicket, Usuario usuario, Funcion funcion, Asiento asiento) {
		super();
		this.idTicket = idTicket;
		this.usuario = usuario;
		this.funcion = funcion;
		this.asiento = asiento;
	}

	public Ticket() {
		super();
	}

	public Long getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(Long idTicket) {
		this.idTicket = idTicket;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Funcion getFuncion() {
		return funcion;
	}

	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}

	public Asiento getAsiento() {
		return asiento;
	}

	public void setAsiento(Asiento asiento) {
		this.asiento = asiento;
	}

	@Override
	public String toString() {
		return "Ticket [idTicket=" + idTicket + ", usuario=" + usuario + "]";
	}

	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id<0 ? -id:id;
		return id;
	}

}