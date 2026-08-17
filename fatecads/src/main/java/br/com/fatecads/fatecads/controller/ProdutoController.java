package br.com.fatecads.fatecads.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.fatecads.fatecads.entity.Produto;
import br.com.fatecads.fatecads.service.ProdutoService;

@Controller
@RequestMapping("/produto")
public class ProdutoController {
    
    @Autowired
    private ProdutoService produtoService;

    //Metodo para listar todos os produtos
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Produto> produtos = produtoService.findAll();
        model.addAttribute("produtos", produtos);
        return "produto/listarProdutos";
    }

    //Metodo para abrir o formulario de criação de produtos
    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("produto", new Produto());
        return "produto/formularioProduto";
    }
    
    //Metodo para salvar o produto
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto) {
        produtoService.save(produto);
        return "redirect:/produto/listar";
    }

    //Metodo para abrir o formulario de edição de produtos
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable("id") Integer id, Model model) {
        Produto produto = produtoService.findById(id);
        model.addAttribute("produto", produto);
        return "produto/formularioProduto";
    }

    //Metodo para excluir um produto pela ID
    @GetMapping("/excluir/{id}")
    public String excluirForm(@PathVariable("id") Integer id, Model model) {
        Produto produto = produtoService.findById(id);
        produtoService.deleteById(id);
        return "redirect:/produto/listar";
    }

}
