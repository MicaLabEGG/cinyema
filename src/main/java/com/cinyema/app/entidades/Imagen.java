package com.cinyema.app.entidades;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Imagen implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6583952797146191132L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idImagen;
	
	private String nombre;
	/*mime asigna formato 
	 * de archivo de imagen
	 */
	private String mime;
	
	/*lob es que el dato es pesado, y que 
	 * lo va a cargar cuando sea necesario
	 */
	@Lob @Basic(fetch = FetchType.LAZY)
	private byte[] contenido;

	public Imagen(Long idImagen, String nombre, String mime, byte[] contenido) {
		super();
		this.idImagen = idImagen;
		this.nombre = nombre;
		this.mime = mime;
		this.contenido = contenido;
	}

	public Imagen() {
		super();
	}

	public Long getIdImagen() {
		return idImagen;
	}

	public void setIdImagen(Long idImagen) {
		this.idImagen = idImagen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public byte[] getContenido() {
		return contenido;
	}

	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}