package com.cinyema.app.Entidad;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cinyema.app.enumeracion.Rol;

@Entity
@Table(name = "Usuario")
public class Usuario {
	
	@Id
	private long id = randomId();
	private String nombre;
	private String mail;
	private String nombreDeUsuario;
	private String contraseña;
	private Boolean alta;
	private Rol rol;
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public Usuario(long id, String nombre, String mail, String nombreDeUsuario, String contraseña, Boolean alta,
			Rol rol) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.mail = mail;
		this.nombreDeUsuario = nombreDeUsuario;
		this.contraseña = contraseña;
		this.alta = alta;
		this.rol = rol;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public Boolean getAlta() {
		return alta;
	}

	public void setAlta(Boolean alta) {
		this.alta = alta;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id<0 ? -id:id;
		return id;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", mail=" + mail + ", nombreDeUsuario=" + nombreDeUsuario
				+ ", contraseña=" + contraseña + ", alta=" + alta + ", rol=" + rol + "]";
	}
	
	
	
}
