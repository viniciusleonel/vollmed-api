package med.voll.api.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuario(

        @NotBlank(message = "Insira um e-mail!")
        @Email(message = "Insira um e-mail válido!")
        String login,
        @NotBlank(message = "Insira uma senha!")
        String senha) {
}
