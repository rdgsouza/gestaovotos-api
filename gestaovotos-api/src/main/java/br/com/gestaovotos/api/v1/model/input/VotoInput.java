package br.com.gestaovotos.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class VotoInput {

    @Schema(example = "true - Para votar sim ou false - Para votar não")
    @NotNull
    private boolean opcaoVoto;

    @Schema(example = "56177539084")
    @NotBlank
    private String cpf;

    @Schema(example = "1")
    @NotNull
    private AssociadoIdInput associado;
}
