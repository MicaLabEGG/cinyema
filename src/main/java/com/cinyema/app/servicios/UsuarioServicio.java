package com.cinyema.app.servicios;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.cinyema.app.entidades.Usuario;
import com.cinyema.app.enumeraciones.Rol;
import com.cinyema.app.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio implements UserDetailsService, ServicioBase<Usuario> {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Usuario registrar(Usuario usuario) throws Exception {
		validar(usuario);
//		validarMayoriaEdad(usuario);
		usuario.setAlta(true);
		usuario.setRol(Rol.USUARIO);
		BCryptPasswordEncoder encriptada = new BCryptPasswordEncoder();
		usuario.setContrasenia(encriptada.encode(usuario.getContrasenia()));
		return usuarioRepositorio.save(usuario);
	}

	@Transactional
	public Usuario registrarVacio() {
		return new Usuario();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Usuario editar(Usuario usuario) throws Exception {
		validar(usuario);
//		validarMayoriaEdad(usuario);
		BCryptPasswordEncoder encriptada = new BCryptPasswordEncoder();
		usuario.setContrasenia(encriptada.encode(usuario.getContrasenia()));
		return usuarioRepositorio.save(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> listar() {
		return usuarioRepositorio.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Usuario obtenerPorId(Long id) throws Exception {
		Optional<Usuario> result = usuarioRepositorio.findById(id);
		if (result.isEmpty()) {
			throw new Exception("No se encontró");
		} else {
			Usuario usuario = result.get();
			return usuario;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Usuario obtenerUsuarioPorNombre(String nombreDeUsuario) throws Exception {
		Usuario result = usuarioRepositorio.buscarPorNombreDeUsuario(nombreDeUsuario);
		if (result == null) {
			throw new Exception("No se encontró");
		} else {
			return result;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminar(Long idUsuario) {
		usuarioRepositorio.deleteById(idUsuario);
	}

	@Transactional(readOnly = true)
	public List<Usuario> usuariosActivos() {

		return usuarioRepositorio.buscarUsuarioActivos();
	}

	@Transactional(readOnly = true)
	public List<Usuario> usuariosInactivos() {

		return usuarioRepositorio.buscarUsuarioInactivos();
	}

	@Transactional(readOnly = true)
	public Integer cantidadDeUsuario() {

		return usuarioRepositorio.cantidadUsuario();
	}

	@Transactional(readOnly = true)
	public Double porcentajeUsuariosActivos() {

		return (double) (usuarioRepositorio.cantidadUsuario() / usuarioRepositorio.buscarUsuarioActivos().size());
	}

	@Transactional(readOnly = true)
	public Double porcentajeUsuariosInactivos() {

		return (double) (usuarioRepositorio.cantidadUsuario() / usuarioRepositorio.buscarUsuarioInactivos().size());
	}

	public void validar(Usuario usuario) throws Exception {
		Date hoy = new Date();

		if (usuario.getNombre() == null || StringUtils.isBlank(usuario.getNombre())) {
			throw new Exception("Nombre de usuario inválido");
		}

		// codigo de validacion de mail sacado de internet :D
		if (usuario.getMail() == null || usuario.getMail().isEmpty() || !usuario.getMail().contains("@")) {
			throw new Error("E-mail de usuario inválido");
		}

		if (usuario.getNombreDeUsuario() == null || StringUtils.isBlank(usuario.getNombreDeUsuario())) {
			throw new Exception("Nombre de usuario inválido");
		}

		if (usuario.getContrasenia() == null || usuario.getContrasenia().isEmpty()
				|| usuario.getContrasenia().contains("  ") || usuario.getContrasenia().length() <= 6) {
			throw new Exception("Contraseña inválida");
		}

//		if (usuario.getFechaNacimiento() == null || usuario.getFechaNacimiento().after(hoy)) {
//			throw new Exception("Fecha de nacimiento inválida");
//		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public UserDetails loadUserByUsername(String nombreDeUsuario) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepositorio.buscarPorNombreDeUsuario(nombreDeUsuario);
		User user = null;
		if (usuario != null) {
			List<GrantedAuthority> permisos = new ArrayList<>();
			GrantedAuthority permiso = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
			permisos.add(permiso);
			// Se extraen atributos de contexto del navegador
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			// Se crea la sesion y se agrega el cliente a la misma
			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("usuariosession", usuario);
			user = new User(nombreDeUsuario, usuario.getContrasenia(), permisos);
		} else {
			throw new UsernameNotFoundException("El ususario no se encontro");
		}
		return user;
	}

//	public Boolean validarMayoriaEdad(Usuario usuario) {
//		DateTimeFormatter farmateo = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		LocalDate fechaDeNacimiento = LocalDate.parse(usuario.getFechaNacimiento(), farmateo);
//		Period edad = Period.between(fechaDeNacimiento, LocalDate.now());
//		if (edad.getYears() < 18) {
//			return false;
//		} else {
//			return true;
//		}
//	}
}
