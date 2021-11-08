package com.cinyema.app.repositorios;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cinyema.app.entidades.Actor;

@Repository
public interface ActorRepositorio extends JpaRepository<Actor, Long>{
	
	@Query("SELECT a FROM Actor a where a.nombreCompleto = :nombreCompleto")
	public Optional<Actor> buscarPorNombre(@Param("nombreCompleto") String nombreCompleto);
	
	@Query("SELECT COUNT(a) FROM Actor a")
	public Long buscarCantidadActores();
	
}
