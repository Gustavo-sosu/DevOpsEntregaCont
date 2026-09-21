package br.com.fatecads.fatecads.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fatecads.fatecads.entity.Usuario;
import br.com.fatecads.fatecads.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario save(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo.");
        }

        if (usuario.getLoginUsuario() != null) {
            usuario.setLoginUsuario(usuario.getLoginUsuario().trim().toLowerCase(Locale.ROOT));
        }

        if (usuario.getRole() == null || usuario.getRole().isBlank()) {
            usuario.setRole("ROLE_USER");
        }

        if (usuario.getSenhaUsuario() != null && !usuario.getSenhaUsuario().isBlank()) {
            usuario.setSenhaUsuario(passwordEncoder.encode(usuario.getSenhaUsuario()));
        }

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public void deleteById(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }
}
