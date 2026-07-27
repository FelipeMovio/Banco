package com.FelipeMovio.banco.controller;

import com.FelipeMovio.banco.database.model.PixKeyEntity;
import com.FelipeMovio.banco.database.model.UsuarioEntity;
import com.FelipeMovio.banco.dto.CompletarPerfilDto;
import com.FelipeMovio.banco.dto.PixKeyRequestDto;
import com.FelipeMovio.banco.dto.PixKeyResponseDto;
import com.FelipeMovio.banco.dto.UsuarioMeResponseDto;
import com.FelipeMovio.banco.service.ContaService;
import com.FelipeMovio.banco.service.PixService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/conta/me")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService contaService;
    private final PixService pixService;

    @PostMapping("/completar-perfil")
    public ResponseEntity<String> completarPerfil(
            @Valid
            @RequestBody CompletarPerfilDto dto,
            @AuthenticationPrincipal UsuarioEntity usuario
    ) {

        contaService.completarPerfil(dto, usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body("Perfil completado com sucesso");
    }

    @GetMapping
    public ResponseEntity<UsuarioMeResponseDto> me(
            @AuthenticationPrincipal UsuarioEntity usuario
    ) {

        return ResponseEntity.ok(contaService.buscarDadosUsuario(usuario));
    }


    @PostMapping("/create/pix")
    public ResponseEntity<String> cadastrarChavePix(@RequestBody @Valid PixKeyRequestDto dto,
                                                  @AuthenticationPrincipal UsuarioEntity usuario){
        pixService.cadastrarChave(dto,usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body("Chave pix cadastrada");
    }
    @GetMapping("/pix/chaves")
    public ResponseEntity<List<PixKeyResponseDto>> verChavesPix( @AuthenticationPrincipal UsuarioEntity usuario){
        List<PixKeyResponseDto> keys = pixService.listarChaves(usuario);

        if (keys.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(keys);
    }



}

