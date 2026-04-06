package com.delivery.service;

import com.delivery.dto.CategoriaDTO;
import com.delivery.exception.BusinessException;
import com.delivery.exception.ResourceNotFoundException;
import com.delivery.model.Categoria;
import com.delivery.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com id: " + id));
    }

    @Transactional
    public Categoria criar(CategoriaDTO dto) {
        if (categoriaRepository.existsByNomeIgnoreCase(dto.getNome())) {
            throw new BusinessException("Já existe uma categoria com o nome: " + dto.getNome());
        }
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria atualizar(Long id, CategoriaDTO dto) {
        Categoria categoria = buscarPorId(id);
        if (!categoria.getNome().equalsIgnoreCase(dto.getNome())
                && categoriaRepository.existsByNomeIgnoreCase(dto.getNome())) {
            throw new BusinessException("Já existe uma categoria com o nome: " + dto.getNome());
        }
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void deletar(Long id) {
        Categoria categoria = buscarPorId(id);
        if (!categoria.getPratos().isEmpty()) {
            throw new BusinessException("Não é possível excluir uma categoria que possui pratos cadastrados.");
        }
        categoriaRepository.delete(categoria);
    }
}
