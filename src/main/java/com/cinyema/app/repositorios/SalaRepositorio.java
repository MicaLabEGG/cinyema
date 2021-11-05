package com.cinyema.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cinyema.app.entidades.Pelicula;
import com.cinyema.app.entidades.Sala;

@Repository
public interface SalaRepositorio extends JpaRepository<Sala, Long> {
	
	//@Query("SELECT s FROM sala s WHERE s.pelicula = :pelicula")
	//public List<Sala> buscarSalaPorPelicula(@Param("pelicula") Pelicula pelicula);

}
