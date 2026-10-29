package br.com.fatecads.fatecads.Security;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fatecads.fatecads.entity.Usuario;
import br.com.fatecads.fatecads.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        String loginNormalizado = login == null ? "" : login.trim().toLowerCase(Locale.ROOT);

        Usuario usuario = usuarioRepository.findByLoginUsuario(loginNormalizado)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + login));

        return new UserDetailsImpl(usuario);
    }
}