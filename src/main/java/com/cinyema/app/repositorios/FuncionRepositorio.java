package com.cinyema.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cinyema.app.entidades.Funcion;
import com.cinyema.app.entidades.Ticket;

@Repository
public interface FuncionRepositorio extends JpaRepository<Funcion, Long>{
	
	@Query("SELECT f FROM Funcion f WHERE f.sala.idSala = :idSala")
	public List<Funcion> obtenerFuncionesPorSalaId(@Param("idSala") Long idSala);

}
