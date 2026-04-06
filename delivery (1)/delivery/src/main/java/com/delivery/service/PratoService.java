package com.delivery.service;

import com.delivery.dto.PratoDTO;
import com.delivery.exception.ResourceNotFoundException;
import com.delivery.model.Categoria;
import com.delivery.model.Prato;
import com.delivery.repository.PratoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PratoService {

    private final PratoRepository pratoRepository;
    private final CategoriaService categoriaService;

    public PratoService(PratoRepository pratoRepository, CategoriaService categoriaService) {
        this.pratoRepository = pratoRepository;
        this.categoriaService = categoriaService;
    }

    public List<Prato> listarTodos() {
        return pratoRepository.findAll();
    }

    public List<Prato> listarDisponiveis() {
        return pratoRepository.findByDisponivelTrue();
    }

    public List<Prato> listarPorCategoria(Long categoriaId) {
        categoriaService.buscarPorId(categoriaId);
        return pratoRepository.findByCategoriaId(categoriaId);
    }

    public Prato buscarPorId(Long id) {
        return pratoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prato não encontrado com id: " + id));
    }

    @Transactional
    public Prato criar(PratoDTO dto) {
        Categoria categoria = categoriaService.buscarPorId(dto.getCategoriaId());
        Prato prato = new Prato();
        prato.setNome(dto.getNome());
        prato.setDescricao(dto.getDescricao());
        prato.setPreco(dto.getPreco());
        prato.setDisponivel(dto.getDisponivel() != null ? dto.getDisponivel() : true);
        prato.setCategoria(categoria);
        return pratoRepository.save(prato);
    }

    @Transactional
    public Prato atualizar(Long id, PratoDTO dto) {
        Prato prato = buscarPorId(id);
        Categoria categoria = categoriaService.buscarPorId(dto.getCategoriaId());
        prato.setNome(dto.getNome());
        prato.setDescricao(dto.getDescricao());
        prato.setPreco(dto.getPreco());
        prato.setDisponivel(dto.getDisponivel() != null ? dto.getDisponivel() : prato.getDisponivel());
        prato.setCategoria(categoria);
        return pratoRepository.save(prato);
    }

    @Transactional
    public Prato alterarDisponibilidade(Long id, Boolean disponivel) {
        Prato prato = buscarPorId(id);
        prato.setDisponivel(disponivel);
        return pratoRepository.save(prato);
    }

    @Transactional
    public void deletar(Long id) {
        Prato prato = buscarPorId(id);
        pratoRepository.delete(prato);
    }
}
