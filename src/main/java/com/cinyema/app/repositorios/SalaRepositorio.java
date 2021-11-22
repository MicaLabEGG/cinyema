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
	
//	@Query("SELECT s FROM Sala s WHERE s.pelicula.titulo = :titulo")
//	public Sala buscarSalaPorPelicula(@Param("titulo") String titulo);
	
	@Query("SELECT s FROM Sala s JOIN s.funciones f WHERE f.pelicula.idPelicula = :idPelicula")
	public List<Sala> buscarSalaPorFuncionidPelicula(@Param("idPelicula") Long idPelicula);

	@Query("SELECT s FROM Sala s JOIN s.funciones f WHERE f.idFuncion = :idFuncion")
	public Sala buscarSalaPorIdFuncion(@Param("idFuncion") Long idFuncion);
}
