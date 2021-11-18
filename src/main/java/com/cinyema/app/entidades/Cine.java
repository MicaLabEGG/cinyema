package com.cinyema.app.entidades;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "cines")
public class Cine {

	@Id
	private Long idCine = randomId();
	@OneToMany
	@JoinTable(name = "cine_salas", joinColumns = 
             {@JoinColumn(name ="cine_FK", referencedColumnName = "idCine")},
             inverseJoinColumns = {@JoinColumn(name = "sala_FK", referencedColumnName = "idSala")})
	private List<Sala> salas;
	private String nombre;
	private String direccion;
	private String mail;
	private String telefono;
	private Double precio;

	public Cine(Long idCine, List<Sala> salas, String nombre, String direccion, String mail, String telefono, Double precio) {
		super();
		this.idCine = idCine;
		this.salas = salas;
		this.nombre = nombre;
		this.direccion = direccion;
		this.mail = mail;
		this.telefono = telefono;
		this.precio = precio;
	}

	public Cine() {
		super();
	}
	

	public Long getIdCine() {
		return idCine;
	}

	public void setIdCine(Long idCine) {
		this.idCine = idCine;
	}

	public List<Sala> getSalas() {
		return salas;
	}

	public void setSalas(List<Sala> salas) {
		this.salas = salas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	
	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id<0 ? -id:id;
		return id;
	}

	@Override
	public String toString() {
		return "Cine [idCine=" + idCine + ", salas=" + salas + ", nombre=" + nombre + ", direccion=" + direccion
				+ ", mail=" + mail + ", telefono=" + telefono + ", precio=" + precio + "]";
	}
	
}