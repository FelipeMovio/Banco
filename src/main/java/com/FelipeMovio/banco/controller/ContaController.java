package com.FelipeMovio.banco.controller;

import com.FelipeMovio.banco.database.model.UsuarioEntity;
import com.FelipeMovio.banco.dto.CompletarPerfilDto;
import com.FelipeMovio.banco.dto.UsuarioMeResponseDto;
import com.FelipeMovio.banco.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/conta")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService contaService;

    @PostMapping("/completar-perfil")
    public ResponseEntity<String> completarPerfil(
            @RequestBody CompletarPerfilDto dto,
            @AuthenticationPrincipal UsuarioEntity usuario
    ) {

        contaService.completarPerfil(dto, usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body("Perfil completado com sucesso");
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioMeResponseDto> me(
            @AuthenticationPrincipal UsuarioEntity usuario
    ) {

        return ResponseEntity.ok(contaService.buscarDadosUsuario(usuario));
    }
}

