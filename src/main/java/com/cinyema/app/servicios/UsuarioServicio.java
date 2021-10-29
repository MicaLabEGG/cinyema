package com.cinyema.app.servicios;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinyema.app.entidades.Usuario;
import com.cinyema.app.enumeraciones.Rol;
import com.cinyema.app.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	
	@Transactional
	public Usuario registroUsuario(String nombre, String mail, String nombreDeUsuario, String contrasenia, Boolean alta, Date fechaNacimiento, Rol rol) throws Exception{
		

		validar(nombre, mail, nombreDeUsuario, contrasenia, alta, fechaNacimiento, rol);
		
	//	validarMayoriaEdad();
		
		Usuario usuario = new Usuario();
		usuario.setNombre(nombre);
		usuario.setMail(mail);
		usuario.setNombreDeUsuario(nombreDeUsuario);
		usuario.setContrasenia(contrasenia);
		usuario.setAlta(alta);
		usuario.setFechaNacimiento(fechaNacimiento);
		usuario.setRol(rol);
		return usuarioRepositorio.save(usuario);
	}
	
	@Transactional
	public Usuario modificarUsuario(Long id, String nombre, String mail, String nombreDeUsuario, String contrasenia, Boolean alta, Date fechaNacimiento, Rol rol) throws Exception{
		

		validar(nombre, mail, nombreDeUsuario, contrasenia, alta, fechaNacimiento, rol);

		
		Usuario usuario = obtenerUsuario(id);  //Crear metodo obtenerUsuario
		usuario.setNombre(nombre);
		usuario.setMail(mail);
		usuario.setNombreDeUsuario(nombreDeUsuario);
		usuario.setContrasenia(contrasenia);
		usuario.setAlta(alta);
		usuario.setFechaNacimiento(fechaNacimiento);
		usuario.setRol(rol);
		return usuarioRepositorio.save(usuario);
		
	}
	
	@Transactional
	public List<Usuario> buscarUsuarios(){
		return usuarioRepositorio.findAll();
	}
	
	@Transactional
	public Usuario obtenerUsuario(Long id) throws Exception{
		Optional<Usuario> result = usuarioRepositorio.findById(id);
		
		if(result.isEmpty()) {
			throw new Exception("No se encontro");
		}
		else {
			Usuario usuario= result.get();
			return usuario;
		}	
	}
	
	@Transactional
	public Usuario obtenerUsuarioPorNombreDeUsuario(String nombreDeUsuario) throws Exception{
		Optional<Usuario> result = usuarioRepositorio.buscarPorNombreDeUsuario(nombreDeUsuario);
	 
		if(result.isEmpty()) {
			throw new Exception("No se encontro");
		}
		else {
			Usuario usuario = result.get();
			return usuario;
		}
	}
	
	public void validar(String nombre, String mail, String nombreDeUsuario, String contrasenia, Boolean alta, Date fechaDeNacimiento, Rol rol) throws Exception {
		
		if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
			throw new Exception();
		}
		
		/*
		 * codigo de validacion de mail sacado de internet :D
		 */
		if (mail == null || mail.isEmpty() || !mail.contains("@")) {
            throw new Error("Debe ser un mail correcto.");
        }

	
		if (nombreDeUsuario == null || nombreDeUsuario.isEmpty() || nombreDeUsuario.contains("  ")) {
			throw new Exception();
		}
		
		if (contrasenia == null || contrasenia.isEmpty() || contrasenia.contains("  ")) {
			throw new Exception();
		}
		
		
		if(alta == null) {
			throw new Exception();
		}
	
		/*
		 * Completar validacion de fechaDeNacimiento que la fecha no sea en el futuro
		 */
		if(fechaDeNacimiento == null) {
			throw new Exception();
		}
	
		
		if(rol == null) {
			throw new Exception();
		}
	}
}
