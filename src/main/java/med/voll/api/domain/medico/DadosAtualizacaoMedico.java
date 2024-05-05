package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(

        @NotNull
        Long id,

        String nome,
        @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "Insira um número de telefone válido no formato (xx) xxxxx-xxxx!")
        String telefone,
        @Valid
        DadosEndereco endereco) {

}
