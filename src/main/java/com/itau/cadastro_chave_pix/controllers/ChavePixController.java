package com.itau.cadastro_chave_pix.controllers;

import com.itau.cadastro_chave_pix.domains.ChavePix;
import com.itau.cadastro_chave_pix.services.ChavePixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chaves-pix")
public class ChavePixController {

    @Autowired
    private ChavePixService chavePixService;

    @PostMapping
    public ResponseEntity<ChavePix> incluir(@RequestBody ChavePix chavePix) {
        ChavePix novaChave = chavePixService.incluirChavePix(chavePix);
        return ResponseEntity.status(200).body(novaChave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChavePix> atualizarChave(@PathVariable UUID id, @RequestBody ChavePix chaveAtualizada) {
        ChavePix chavePixAtualizada = chavePixService.atualizarChave(id, chaveAtualizada);
        return ResponseEntity.ok(chavePixAtualizada);
    }

    @PutMapping("/{id}/inativar")
    public ResponseEntity<ChavePix> inativar(@PathVariable UUID id) {
        ChavePix chaveInativa = chavePixService.inativarChave(id);
        return ResponseEntity.status(200).body(chaveInativa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChavePix> buscarPorId(@PathVariable UUID id) {
        return chavePixService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/conta")
    public ResponseEntity<List<ChavePix>> listarPorConta(
            @RequestParam Integer numeroAgencia,
            @RequestParam Integer numeroConta) {

        List<ChavePix> chaves = chavePixService.listarPorConta(numeroAgencia, numeroConta);
        return ResponseEntity.ok(chaves);
    }

    @GetMapping("/nome")
    public ResponseEntity<List<ChavePix>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(chavePixService.buscarPorNomeCorrentista(nome));
    }
}
