package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(

        @NotBlank (message = "Insira seu nome!")
        String nome,

        @NotBlank (message = "Insira um e-mail!")
        @Email (message = "Insira um e-mail válido!")
        String email,

        @NotBlank (message = "Insira seu telefone!")
        @Pattern(regexp = "\\(\\d{2}\\) \\d{7}-\\d{4}", message = "Insira um número de telefone válido no formato (xx) xxxxxx-xxxx!")
        String telefone,

        @NotBlank (message = "Insira seu CRM!")
        @Pattern(regexp = "\\d{4,6}", message = "Insira um CRM válido!")
        String crm,

        @NotNull (message = "Selecione uma especialidade!")
        Especialidade especialidade,

        @NotNull (message = "Insira um endereço!")
        @Valid
        DadosEndereco endereco
) {
}
