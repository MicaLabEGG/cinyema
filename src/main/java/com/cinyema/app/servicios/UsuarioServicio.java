package com.cinyema.app.servicios;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinyema.app.entidades.Usuario;
import com.cinyema.app.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	
	@Transactional
	public Usuario registroUsuario(String nombre, String mail, String nombreDeUsuario, String contrasenia, Boolean alta) throws Exception{
		
		Usuario usuario = new Usuario();
		usuario.setNombre(nombre);
		usuario.setMail(mail);
		usuario.setNombreDeUsuario(nombreDeUsuario);
		usuario.setContrasenia(contrasenia);
		usuario.setAlta(alta);
		return usuarioRepositorio.save(usuario);
	}
	
}
