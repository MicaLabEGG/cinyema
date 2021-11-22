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
	
	@Query("SELECT f FROM Funcion f WHERE f.pelicula.idPelicula = :idPelicula")
	public List<Funcion> obtenerFuncionesPorPeliculaId(@Param("idPelicula") Long idPelicula);
	
	@Query("SELECT f FROM Funcion f WHERE f.pelicula.idPelicula = :idPelicula AND f.fecha = :fecha")
	public List<Funcion> obtenerFuncionesPorPeliculaAndFecha(@Param("idPelicula") Long idPelicula, @Param("fecha") String fecha);

	@Query("SELECT f FROM Funcion f WHERE f.pelicula.idPelicula = :idPelicula AND f.fecha = :fecha AND f.horario = :horario")
	public Funcion obtenerFuncionesPorPeliculaAndFechaAndHorario(@Param("idPelicula") Long idPelicula, @Param("fecha") String fecha, @Param("horario") String horario);

}
