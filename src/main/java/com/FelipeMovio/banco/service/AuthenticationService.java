package com.FelipeMovio.banco.service;


import com.FelipeMovio.banco.config.TokenProvider;
import com.FelipeMovio.banco.database.model.RoleEntity;
import com.FelipeMovio.banco.database.model.UsuarioEntity;
import com.FelipeMovio.banco.database.repository.RoleRepository;
import com.FelipeMovio.banco.database.repository.UsuarioRepository;
import com.FelipeMovio.banco.dto.authentication.LoginRequestDto;
import com.FelipeMovio.banco.dto.authentication.RegisterRequestDto;
import com.FelipeMovio.banco.dto.authentication.TokenResponseDto;
import com.FelipeMovio.banco.enums.RoleTypeEnum;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor

public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    public void register(RegisterRequestDto dto) throws BadRequestException {
        UsuarioEntity usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElse(null);

        if (usuario != null) {
            throw new BadRequestException("Aluno já cadastrado com este email");
        }

        RoleEntity role = roleRepository.findByNome(RoleTypeEnum.ROLE_USER.name())
                .orElseGet(() -> roleRepository.save(RoleEntity.builder()
                        .nome(RoleTypeEnum.ROLE_USER.name())
                        .build()));

        usuarioRepository.save(UsuarioEntity.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .role(Set.of(role))
                .senha(passwordEncoder.encode(dto.getSenha()))
                .build());
    }

    public TokenResponseDto login( LoginRequestDto dto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));

            // authentication provider -> userdetailsservice -> passwordEnconder.matches() -> autenticado

            String token = tokenProvider.generateToken(authentication);

            return new TokenResponseDto(token);



        }catch (BadCredentialsException e){
            throw new BadCredentialsException("credenciais invalidas");
        }catch (Exception e){
            throw e;
        }
    }
}
