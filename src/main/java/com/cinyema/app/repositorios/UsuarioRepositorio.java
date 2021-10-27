package com.cinyema.app.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cinyema.app.entidades.Actor;
import com.cinyema.app.entidades.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{

	@Query("SELECT a FROM Usuario a where a.nombreDeUsuario = :nombreDeUsuario")
	public Optional<Actor> buscarPorNombreDeUsuario(@Param("nombreDeUsuario") String nombreDeUsuario);
	
}
