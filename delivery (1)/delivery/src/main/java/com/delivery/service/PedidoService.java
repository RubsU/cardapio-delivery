package com.delivery.service;

import com.delivery.dto.PedidoDTO;
import com.delivery.exception.BusinessException;
import com.delivery.exception.ResourceNotFoundException;
import com.delivery.model.ItemPedido;
import com.delivery.model.Pedido;
import com.delivery.model.Prato;
import com.delivery.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PratoService pratoService;

    public PedidoService(PedidoRepository pedidoRepository, PratoService pratoService) {
        this.pedidoRepository = pedidoRepository;
        this.pratoService = pratoService;
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public List<Pedido> listarPorStatus(Pedido.StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + id));
    }

    @Transactional
    public Pedido criar(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setNomeCliente(dto.getNomeCliente());
        pedido.setTelefoneCliente(dto.getTelefoneCliente());
        pedido.setEnderecoEntrega(dto.getEnderecoEntrega());

        List<ItemPedido> itens = new ArrayList<>();

        // Regra de negócio: só aceita pratos disponíveis
        for (PedidoDTO.ItemPedidoDTO itemDTO : dto.getItens()) {
            Prato prato = pratoService.buscarPorId(itemDTO.getPratoId());

            if (!prato.getDisponivel()) {
                throw new BusinessException(
                        "O prato '" + prato.getNome() + "' não está disponível no momento.");
            }

            ItemPedido item = new ItemPedido();
            item.setPrato(prato);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPedido(pedido);
            item.calcularSubtotal();
            itens.add(item);
        }

        pedido.setItens(itens);
        pedido.calcularValorTotal(); // Regra de negócio: valor total calculado automaticamente
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido atualizarStatus(Long id, Pedido.StatusPedido novoStatus) {
        Pedido pedido = buscarPorId(id);

        if (pedido.getStatus() == Pedido.StatusPedido.CANCELADO) {
            throw new BusinessException("Não é possível alterar o status de um pedido cancelado.");
        }
        if (pedido.getStatus() == Pedido.StatusPedido.ENTREGUE) {
            throw new BusinessException("Não é possível alterar o status de um pedido já entregue.");
        }

        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelar(Long id) {
        atualizarStatus(id, Pedido.StatusPedido.CANCELADO);
    }
}
