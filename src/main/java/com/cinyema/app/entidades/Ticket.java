package com.cinyema.app.entidades;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Ticket {

	@Id
	private Long idTicket = randomId();
	@OneToOne
	private Pelicula pelicula;
	@ManyToOne
	@JoinColumn(name = "FK_TICKUSER", nullable = false, updatable = false)
	private Usuario usuario;
	@Temporal(TemporalType.DATE)
	private Date fecha;
	private String lugar;
	private Double precio;
	
	public Long getIdTicket() {
		return idTicket;
	}
	public void setIdTicket(Long idTicket) {
		this.idTicket = idTicket;
	}
	public Pelicula getPelicula() {
		return pelicula;
	}
	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Ticket(Long idTicket, Pelicula pelicula, Usuario usuario, Date fecha, String lugar, Double precio) {
		super();
		this.idTicket = idTicket;
		this.pelicula = pelicula;
		this.usuario = usuario;
		this.fecha = fecha;
		this.lugar = lugar;
		this.precio = precio;
	}
	public Ticket() {
		super();
	}
	
	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long)uuid.hashCode();
		id = id<0 ? -id:id;
		return id;
	}

}
