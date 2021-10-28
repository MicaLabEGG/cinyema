package com.cinyema.app.servicios;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinyema.app.entidades.Usuario;
import com.cinyema.app.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio {

	@Autowired
	UsuarioRepositorio usuarioRepositorio;
	
	@Transactional
	public Usuario crearUsuario() {
		
	}
}
