package com.cinyema.app.entidades;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.cinyema.app.enumeraciones.Rol;

@Entity
@Table(name = "usuario")
public class Usuario {
	
	@Id
	private Long idUsuario = randomId();
	
	private String nombre;
	private String mail;
	private String nombreDeUsuario;
	private String contrasenia;
	private Boolean alta;
	@Column(updatable = false)
	private String codigoVerificacion;
		
	@DateTimeFormat(pattern="dd-MM-yyyy")
	private String fechaNacimiento;
	
	@Enumerated(EnumType.STRING)
	private Rol rol;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
	private List<Ticket> ticket;
	
	public Usuario() {
	}

	public Usuario(Long idUsuario, String nombre, String mail, String nombreDeUsuario, String contrasenia, Boolean alta,
			String codigoVerificacion, String fechaNacimiento, Rol rol, List<Ticket> ticket) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.mail = mail;
		this.nombreDeUsuario = nombreDeUsuario;
		this.contrasenia = contrasenia;
		this.alta = alta;
		this.codigoVerificacion = codigoVerificacion;
		this.fechaNacimiento = fechaNacimiento;
		this.rol = rol;
		this.ticket = ticket;
	}


	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNombreDeUsuario() {
		return nombreDeUsuario;
	}

	public void setNombreDeUsuario(String nombreDeUsuario) {
		this.nombreDeUsuario = nombreDeUsuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public Boolean getAlta() {
		return alta;
	}

	public void setAlta(Boolean alta) {
		this.alta = alta;
	}

	public String getCodigoVerificacion() {
		return codigoVerificacion;
	}

	public void setCodigoVerificacion(String codigoVerificacion) {
		this.codigoVerificacion = codigoVerificacion;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public List<Ticket> getTicket() {
		return ticket;
	}

	public void setTicket(List<Ticket> ticket) {
		this.ticket = ticket;
	}
	
	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id<0 ? -id:id;
		return id;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nombre=" + nombre + ", mail=" + mail + ", nombreDeUsuario="
				+ nombreDeUsuario + ", contrasenia=" + contrasenia + ", alta=" + alta + ", codigoVerificacion="
				+ codigoVerificacion + ", fechaNacimiento=" + fechaNacimiento + ", rol=" + rol + "]";
	}

}
