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
	private String lugar;
	private Double precio;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "asiento_idAsiento", referencedColumnName = "idAsiento")	
	private Asiento asiento;
	
	public Long getIdTicket() {
		return idTicket;
	}
	
	//constructor para hacer la compra de ticket
	public Ticket( Pelicula pelicula, Usuario usuario, String fecha, String lugar, double precio, Asiento asiento) {
		super();
		this.pelicula = pelicula;
		this.usuario = usuario;
		this.fecha = fecha;
		this.lugar = lugar;
		this.precio = precio;
		this.asiento = asiento;
	}
	
	public Ticket() {
		super();
	}
	
	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id<0 ? -id:id;
		return id;
	}

}
