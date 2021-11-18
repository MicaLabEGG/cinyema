package com.cinyema.app.entidades;

import java.util.List;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "sala")
public class Sala {

	@Id
	private Long idSala = randomId();
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "sa_fid", referencedColumnName = "idSala" )
	private List<Asiento> asientos;
	private Integer cantidadAsientos;
	private String nombreSala;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sala")
	private List<Funcion> funciones;
	
	@ManyToOne
	@JoinTable(name = "cine_salas",
	          joinColumns = {@JoinColumn(name = "sala_FK", insertable = false,
	                  updatable = false, referencedColumnName = "idSala")},
	          inverseJoinColumns = {@JoinColumn(name = "cine_FK", insertable = false,
	                  updatable = false, referencedColumnName = "idCine")})
	private Cine cine;
	
	public Sala(Long idSala, List<Asiento> asientos, Integer cantidadAsientos, String nombreSala,
			List<Funcion> funciones, Cine cine) {
		super();
		this.idSala = idSala;
		this.asientos = asientos;
		this.cantidadAsientos = cantidadAsientos;
		this.nombreSala = nombreSala;
		this.funciones = funciones;
		this.cine = cine;
	}

	public Sala() {
		super();
	}

	public Long getIdSala() {
		return idSala;
	}

	public void setIdSala(Long idSala) {
		this.idSala = idSala;
	}

	public List<Asiento> getAsientos() {
		return asientos;
	}

	public void setAsientos(List<Asiento> asientos) {
		this.asientos = asientos;
	}

	public Integer getCantidadAsientos() {
		return cantidadAsientos;
	}

	public void setCantidadAsientos(Integer cantidadAsientos) {
		this.cantidadAsientos = cantidadAsientos;
	}

	public String getNombreSala() {
		return nombreSala;
	}

	public void setNombreSala(String nombreSala) {
		this.nombreSala = nombreSala;
	}

	public List<Funcion> getFunciones() {
		return funciones;
	}

	public void setFunciones(List<Funcion> funciones) {
		this.funciones = funciones;
	}

	public Cine getCine() {
		return cine;
	}

	public void setCine(Cine cine) {
		this.cine = cine;
	}

	@Override
	public String toString() {
		return "Sala [idSala=" + idSala + ", asientos=" + asientos + ", cantidadAsientos=" + cantidadAsientos
				+ ", nombreSala=" + nombreSala + ", funciones=" + funciones + "]";
	}

	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id < 0 ? -id : id;
		return id;
	}

}
