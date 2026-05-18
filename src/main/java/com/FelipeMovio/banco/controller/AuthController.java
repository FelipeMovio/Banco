package com.FelipeMovio.banco.controller;

import com.FelipeMovio.banco.dto.authentication.LoginRequestDto;
import com.FelipeMovio.banco.dto.authentication.RegisterRequestDto;
import com.FelipeMovio.banco.dto.authentication.TokenResponseDto;
import com.FelipeMovio.banco.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterRequestDto dto) throws BadRequestException {
        authenticationService.register(dto);

    }

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody @Valid LoginRequestDto dto) throws BadRequestException {
        return authenticationService.login(dto);
    }
}
