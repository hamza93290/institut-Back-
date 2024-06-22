package com.institut.institut.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {

    private Long id;
    private String username;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?_\\-])[A-Za-z\\d@$!%*?_\\-]{6,}$", message = "Format du mot de passe invalide")
    @NotEmpty(message = "Le mot de passe ne doit pas être vide")
    private String password;

    public AdminDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
