package com.cinyema.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cinyema.app.entidades.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

	@Query("SELECT a FROM Usuario a where a.nombreDeUsuario = :nombreDeUsuario")
	public Usuario buscarPorNombreDeUsuario(@Param("nombreDeUsuario") String nombreDeUsuario);

	@Query("SELECT a FROM Usuario a WHERE a.mail = :mail")
	public Usuario buscarPorEmail(@Param("mail") String mail);

	@Query("SELECT a FROM Usuario a WHERE a.alta = :true")
	public List<Usuario> buscarUsuarioActivos(@Param("alta") Boolean alta);

	@Query("SELECT a FROM Usuario a WHERE a.alta = :false")
	public List<Usuario> buscarUsuarioInactivos(@Param("alta") Boolean alta);

	@Query("SELECT count(a) FROM Usuario a")
	public Integer cantidadUsuario();

	@Query("SELECT COUNT(a) FROM Usuario a WHERE a.alta = true")
	public Long totalAlta(@Param("alta") Boolean alta);
	
	@Query("SELECT a FROM Usuario a WHERE a.codigoVerificacion = :codigoVerificacion")
	public Usuario buscarPorCodigoVerificacion(String codigoVerificacion);
	
}