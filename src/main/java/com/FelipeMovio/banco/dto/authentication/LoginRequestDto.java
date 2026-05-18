package com.FelipeMovio.banco.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class LoginRequestDto {
    @NotBlank
    private String email;

    @NotBlank
    private String senha;
}
