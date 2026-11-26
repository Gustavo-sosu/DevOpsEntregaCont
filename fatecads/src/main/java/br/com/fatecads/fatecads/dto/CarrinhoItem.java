package br.com.fatecads.fatecads.dto;

import br.com.fatecads.fatecads.entity.Produto;

public class CarrinhoItem {
    private final Produto produto;
    private final Integer quantidade;

    public CarrinhoItem(Produto produto, Integer quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Double getSubtotal() {
        return produto.getValorProduto() * quantidade;
    }
}