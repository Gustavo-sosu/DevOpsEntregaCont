package br.com.fatecads.fatecads.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;

import br.com.fatecads.fatecads.entity.Aluno;
import br.com.fatecads.fatecads.entity.Pedido;
import br.com.fatecads.fatecads.entity.Produto;
import br.com.fatecads.fatecads.service.AlunoService;
import br.com.fatecads.fatecads.service.PedidoService;
import br.com.fatecads.fatecads.service.ProdutoService;

@Controller
@RequestMapping("/pedido")
public class pedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("pedidos", pedidoService.findAll());
        return "pedido/listarPedidos";
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
        List<Aluno> alunos = alunoService.findAll();
        model.addAttribute("alunos", alunos);
        List<Produto> produtos = produtoService.findAll();
        model.addAttribute("produtos", produtos);
        return "pedido/formularioPedido";
    }
}
