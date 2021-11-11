package com.cinyema.app.entidades;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "asiento")
public class Asiento {
	
	@Id
	private Long idAsiento = randomId();
	private String numeroDeAsiento;
	private Boolean libre;
	@ManyToOne
	private Sala sala;
	@OneToOne
	private Ticket ticket;
	
	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id<0 ? -id:id;
		return id;
	}
	
}
