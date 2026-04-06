package com.delivery.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PedidoDTO {

    @NotBlank(message = "O nome do cliente é obrigatório")
    private String nomeCliente;

    private String telefoneCliente;

    private String enderecoEntrega;

    @NotEmpty(message = "O pedido deve ter ao menos um item")
    @Valid
    private List<ItemPedidoDTO> itens;

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public String getTelefoneCliente() { return telefoneCliente; }
    public void setTelefoneCliente(String telefoneCliente) { this.telefoneCliente = telefoneCliente; }

    public String getEnderecoEntrega() { return enderecoEntrega; }
    public void setEnderecoEntrega(String enderecoEntrega) { this.enderecoEntrega = enderecoEntrega; }

    public List<ItemPedidoDTO> getItens() { return itens; }
    public void setItens(List<ItemPedidoDTO> itens) { this.itens = itens; }

    public static class ItemPedidoDTO {

        @NotNull(message = "O ID do prato é obrigatório")
        private Long pratoId;

        @NotNull(message = "A quantidade é obrigatória")
        @Min(value = 1, message = "A quantidade mínima é 1")
        private Integer quantidade;

        public Long getPratoId() { return pratoId; }
        public void setPratoId(Long pratoId) { this.pratoId = pratoId; }

        public Integer getQuantidade() { return quantidade; }
        public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    }
}
