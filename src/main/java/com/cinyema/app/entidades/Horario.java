package com.cinyema.app.entidades;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "horario")
public class Horario {

	@Id
	private Long idHorario = randomId();
	
	private String horario;
	
	@OneToMany
	private List<Asiento> asientos;
	
	@ManyToOne
	private Sala sala;

	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id < 0 ? -id : id;
		return id;
	}

}
