package com.delivery.repository;

import com.delivery.model.Prato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PratoRepository extends JpaRepository<Prato, Long> {
    List<Prato> findByCategoriaId(Long categoriaId);
    List<Prato> findByDisponivelTrue();
}
