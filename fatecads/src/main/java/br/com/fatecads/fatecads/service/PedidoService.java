package br.com.fatecads.fatecads.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fatecads.fatecads.entity.ItemDoPedido;
import br.com.fatecads.fatecads.entity.Pedido;
import br.com.fatecads.fatecads.entity.Produto;
import br.com.fatecads.fatecads.repository.Pedidorepository;
import br.com.fatecads.fatecads.repository.ProdutoRepository;

@Service
public class PedidoService {
    @Autowired
    private Pedidorepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    //Método para criar um pedido
    public Pedido salvarPedido(Pedido pedido){
        pedido.setDataPedido(LocalDate.now());
        List<ItemDoPedido> itens = pedido.getItens() == null
                ? Collections.emptyList()
                : pedido.getItens();

        for(ItemDoPedido item : itens){
            if (item.getProduto() == null || item.getProduto().getIdProduto() == null
                    || item.getQuantidade() == null || item.getQuantidade() <= 0) {
                throw new IllegalArgumentException("Item do pedido inválido.");
            }

            Produto produto = produtoRepository.findById(item.getProduto().getIdProduto())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
            item.setProduto(produto);
            item.setPreco(produto.getValorProduto());
            item.atualizarSubtotal();
            item.setPedido(pedido);
        }
        pedido.setItens(itens);
        pedido.atualizarTotal();
        return pedidoRepository.save(pedido);
    }
}
