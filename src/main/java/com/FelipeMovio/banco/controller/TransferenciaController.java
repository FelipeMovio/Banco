package com.FelipeMovio.banco.controller;


import com.FelipeMovio.banco.database.model.UsuarioEntity;
import com.FelipeMovio.banco.dto.MinhasTransacoesResponseDto;
import com.FelipeMovio.banco.dto.TransacaoPixRequestDto;
import com.FelipeMovio.banco.dto.TransacaoRequestDto;
import com.FelipeMovio.banco.dto.TransacaoResponseDto;
import com.FelipeMovio.banco.service.TransferenciaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transferencia")
@RequiredArgsConstructor
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransacaoResponseDto fazerTransacao(@Valid @RequestBody TransacaoRequestDto dto,
                                               @AuthenticationPrincipal UsuarioEntity usuario){

        return transferenciaService.transferirValores(dto, usuario);

    }

    @PostMapping("/pix")
    @ResponseStatus(HttpStatus.CREATED)
    public TransacaoResponseDto fazerTransacaoPix(@Valid @RequestBody TransacaoPixRequestDto dto,
                                               @AuthenticationPrincipal UsuarioEntity usuario){

        return transferenciaService.transferenciaPix(dto, usuario);

    }

    @GetMapping("/me")
    public MinhasTransacoesResponseDto minhasTransacoes(
            @AuthenticationPrincipal UsuarioEntity usuario){

        return transferenciaService.minhasTransacoes(usuario);
    }
}
