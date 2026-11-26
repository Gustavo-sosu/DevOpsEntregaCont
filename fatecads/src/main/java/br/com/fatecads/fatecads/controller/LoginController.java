package br.com.fatecads.fatecads.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import br.com.fatecads.fatecads.entity.Usuario;
import br.com.fatecads.fatecads.service.UsuarioService;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/acesso-negado")
    public String acessoNegado() {
        return "acessoNegado";
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastroUsuario";
    }

    @PostMapping("/cadastro/salvar")
    public String salvarCadastro(@ModelAttribute Usuario usuario) {
        usuarioService.register(usuario);
        return "redirect:/login?cadastro=sucesso";
    }

    @GetMapping("/home")
    public String home() {
        return "Home";
    }
}

