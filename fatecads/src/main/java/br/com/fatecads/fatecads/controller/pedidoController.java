package br.com.fatecads.fatecads.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;

import br.com.fatecads.fatecads.entity.Pedido;
import br.com.fatecads.fatecads.entity.Produto;
import br.com.fatecads.fatecads.service.UsuarioService;
import br.com.fatecads.fatecads.service.PedidoService;
import br.com.fatecads.fatecads.service.ProdutoService;

@Controller
@RequestMapping("/pedido")
public class pedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("pedidos", pedidoService.findAll());
        return "pedido/listarPedidos";
    }

    @GetMapping("/consultar/{id}")
    public String consultar(@PathVariable Integer id, Model model) {
        Optional<Pedido> pedido = pedidoService.findDetalhadoById(id);
        model.addAttribute("pedido", pedido.orElse(null));
        return "pedido/consultarPedido";
    }

    @PostMapping({"", "/salvar"})
    @ResponseBody
    public ResponseEntity<Void> salvarPedido(@RequestBody Pedido pedido) {
        pedidoService.salvarPedido(pedido);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("usuarios", usuarioService.findAll());
        List<Produto> produtos = produtoService.findAll();
        model.addAttribute("produtos", produtos);
        return "pedido/formularioPedido";
    }
}
