package com.FelipeMovio.banco.dto;

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
