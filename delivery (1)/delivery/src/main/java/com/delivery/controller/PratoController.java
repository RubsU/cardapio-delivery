package com.delivery.controller;

import com.delivery.dto.PratoDTO;
import com.delivery.model.Prato;
import com.delivery.service.PratoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pratos")
public class PratoController {

    private final PratoService pratoService;

    public PratoController(PratoService pratoService) {
        this.pratoService = pratoService;
    }

    @GetMapping
    public ResponseEntity<List<Prato>> listarTodos() {
        return ResponseEntity.ok(pratoService.listarTodos());
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<Prato>> listarDisponiveis() {
        return ResponseEntity.ok(pratoService.listarDisponiveis());
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Prato>> listarPorCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(pratoService.listarPorCategoria(categoriaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prato> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pratoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Prato> criar(@Valid @RequestBody PratoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pratoService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prato> atualizar(@PathVariable Long id,
                                            @Valid @RequestBody PratoDTO dto) {
        return ResponseEntity.ok(pratoService.atualizar(id, dto));
    }

    @PatchMapping("/{id}/disponibilidade")
    public ResponseEntity<Prato> alterarDisponibilidade(@PathVariable Long id,
                                                         @RequestBody Map<String, Boolean> body) {
        Boolean disponivel = body.get("disponivel");
        if (disponivel == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(pratoService.alterarDisponibilidade(id, disponivel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pratoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
