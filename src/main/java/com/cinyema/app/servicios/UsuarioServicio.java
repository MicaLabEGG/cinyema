package com.cinyema.app.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cinyema.app.entidades.Usuario;
import com.cinyema.app.enumeraciones.Rol;
import com.cinyema.app.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Transactional
	public Usuario registroUsuario(String nombre, String mail, String nombreDeUsuario, String contrasenia,
			Date fechaNacimiento) throws Exception {

		validar(nombre, mail, nombreDeUsuario, contrasenia, fechaNacimiento);

		validarMayoriaEdad(fechaNacimiento);

		Usuario usuario = new Usuario();
		BCryptPasswordEncoder encriptada = new BCryptPasswordEncoder();
		usuario.setNombre(nombre);
		usuario.setMail(mail);
		usuario.setNombreDeUsuario(nombreDeUsuario);
		usuario.setContrasenia(encriptada.encode(contrasenia));
		usuario.setAlta(true);
		usuario.setFechaNacimiento(fechaNacimiento);
		usuario.setRol(Rol.USUARIO);
		return usuarioRepositorio.save(usuario);
	}

	@Transactional
	public Usuario modificarUsuario(Long id, String nombre, String mail, String nombreDeUsuario, String contrasenia,
			Date fechaNacimiento) throws Exception {

		validar(nombre, mail, nombreDeUsuario, contrasenia, fechaNacimiento);

		Usuario usuario = obtenerUsuario(id); // Crear metodo obtenerUsuario

		BCryptPasswordEncoder encriptada = new BCryptPasswordEncoder();

		usuario.setNombre(nombre);
		usuario.setMail(mail);
		usuario.setNombreDeUsuario(nombreDeUsuario);
		usuario.setContrasenia(encriptada.encode(contrasenia));
		usuario.setAlta(true);
		usuario.setFechaNacimiento(fechaNacimiento);
		usuario.setRol(Rol.USUARIO);
		return usuarioRepositorio.save(usuario);

	}

	@Transactional
	public List<Usuario> buscarUsuarios() {
		return usuarioRepositorio.findAll();
	}

	@Transactional
	public Usuario obtenerUsuario(Long id) throws Exception {
		Optional<Usuario> result = usuarioRepositorio.findById(id);

		if (result.isEmpty()) {
			throw new Exception("No se encontro");
		} else {
			Usuario usuario = result.get();
			return usuario;
		}
	}

	@Transactional
	public Usuario obtenerUsuarioPorNombreDeUsuario(String nombreDeUsuario) throws Exception {
		Optional<Usuario> result = usuarioRepositorio.buscarPorNombreDeUsuario(nombreDeUsuario);

		if (result.isEmpty()) {
			throw new Exception("No se encontro");
		} else {
			Usuario usuario = result.get();
			return usuario;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminarUsuario(Long idUsuario) {
		usuarioRepositorio.deleteById(idUsuario);
	}

	public void validar(String nombre, String mail, String nombreDeUsuario, String contrasenia, Date fechaDeNacimiento)
			throws Exception {

		Date hoy = new Date();

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

		if (contrasenia == null || contrasenia.isEmpty() || contrasenia.contains("  ") || contrasenia.length() <= 6) {
			throw new Exception();
		}

		if (fechaDeNacimiento == null || fechaDeNacimiento.after(hoy)) {
			throw new Exception();
		}

	}

	@Override
	public UserDetails loadUserByUsername(String nombreDeUsuario) throws UsernameNotFoundException {

		Usuario usuario =  usuarioRepositorio.buscarPorNombreDeUsuario(nombreDeUsuario);
		User user = null;

		if (usuario != null) {
			List<GrantedAuthority> permisos = new ArrayList<>();
			GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
			permisos.add(p);

			user = new User(nombreDeUsuario, usuario.getContrasenia(), permisos);
		} else {
			throw new UsernameNotFoundException("El ususario no se encontro");
		}

		return user;

	}

	public Boolean validarMayoriaEdad(Date fechaNacimiento) {

		DateFormat formateoFechaAString = new SimpleDateFormat("dd/MM/yyyy");
		String fechaNacimientoEnString = formateoFechaAString.format(fechaNacimiento);
		DateTimeFormatter farmateo = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaDeNacimiento = LocalDate.parse(fechaNacimientoEnString, farmateo);
		Period edad = Period.between(fechaDeNacimiento, LocalDate.now());

		if (edad.getYears() < 18) {
			return false;
		} else {
			return true;
		}

	}
}
