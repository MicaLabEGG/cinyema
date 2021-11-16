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
	@OneToOne
	private Pelicula pelicula;
	@ManyToOne
	@JoinColumn(name = "FK_TICKUSER", nullable = false, updatable = false)
	private Usuario usuario;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String fecha;
	private Double precio;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "asiento_idAsiento", referencedColumnName = "idAsiento")	
	private Asiento asiento;
	
	public Long getIdTicket() {
		return idTicket;
	}
	
	//constructor para hacer la compra de ticket
	public Ticket( Pelicula pelicula, Usuario usuario, String fecha, Double precio, Asiento asiento) {
		super();
		this.pelicula = pelicula;
		this.usuario = usuario;
		this.fecha = fecha;
		this.precio = precio;
		this.asiento = asiento;
	}
	
	public Ticket() {
		super();
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Asiento getAsiento() {
		return asiento;
	}

	public void setAsiento(Asiento asiento) {
		this.asiento = asiento;
	}

	public void setIdTicket(Long idTicket) {
		this.idTicket = idTicket;
	}

	@Override
	public String toString() {
		return "Ticket [idTicket=" + idTicket + ", fecha=" + fecha + ", precio=" + precio + "]";
	}

	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id<0 ? -id:id;
		return id;
	}

}