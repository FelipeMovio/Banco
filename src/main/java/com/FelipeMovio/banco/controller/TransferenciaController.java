package com.FelipeMovio.banco.controller;

import com.FelipeMovio.banco.database.model.TransacoesEntity;
import com.FelipeMovio.banco.dto.TransacaoRequestDto;
import com.FelipeMovio.banco.service.TransferenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transferencia")
@RequiredArgsConstructor
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransacoesEntity fazerTransacao(@RequestBody TransacaoRequestDto dto){

        return transferenciaService.transferirValores(dto);

    }
}
