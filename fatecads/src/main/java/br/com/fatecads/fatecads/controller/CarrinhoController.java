package br.com.fatecads.fatecads.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.fatecads.fatecads.dto.CarrinhoItem;
import br.com.fatecads.fatecads.entity.Produto;
import br.com.fatecads.fatecads.service.ProdutoService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {
    private static final String CARRINHO_SESSION = "carrinhoProdutos";

    private final ProdutoService produtoService;

    public CarrinhoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping("/adicionar/{id}")
    public String adicionar(@PathVariable Integer id,
            @RequestParam(defaultValue = "1") Integer quantidade,
            HttpSession session) {
        Produto produto = produtoService.findById(id);
        if (produto != null && quantidade != null && quantidade > 0) {
            Map<Integer, Integer> carrinho = obterCarrinho(session);
            carrinho.merge(id, quantidade, Integer::sum);
            session.setAttribute(CARRINHO_SESSION, carrinho);
        }
        return "redirect:/produto/listar";
    }

    @GetMapping
    public String visualizar(HttpSession session, Model model) {
        List<CarrinhoItem> itens = montarItens(session);
        double total = itens.stream().mapToDouble(CarrinhoItem::getSubtotal).sum();
        model.addAttribute("itens", itens);
        model.addAttribute("total", total);
        return "carrinho/carrinho";
    }

    @PostMapping("/remover/{id}")
    public String remover(@PathVariable Integer id, HttpSession session) {
        Map<Integer, Integer> carrinho = obterCarrinho(session);
        carrinho.remove(id);
        session.setAttribute(CARRINHO_SESSION, carrinho);
        return "redirect:/carrinho";
    }

    @PostMapping("/limpar")
    public String limpar(HttpSession session) {
        session.removeAttribute(CARRINHO_SESSION);
        return "redirect:/carrinho";
    }

    @SuppressWarnings("unchecked")
    private Map<Integer, Integer> obterCarrinho(HttpSession session) {
        Object atributo = session.getAttribute(CARRINHO_SESSION);
        if (atributo instanceof Map<?, ?>) {
            return (Map<Integer, Integer>) atributo;
        }
        return new HashMap<>();
    }

    private List<CarrinhoItem> montarItens(HttpSession session) {
        List<CarrinhoItem> itens = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entrada : obterCarrinho(session).entrySet()) {
            Produto produto = produtoService.findById(entrada.getKey());
            if (produto != null) {
                itens.add(new CarrinhoItem(produto, entrada.getValue()));
            }
        }
        return itens;
    }
}