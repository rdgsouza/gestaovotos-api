package br.com.gestaovotos.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class AssociadoInput {

    @Schema(example = "José da Silva")
    @NotBlank
    private String nome;

    @Schema(example = "56177539084")
    @NotBlank
    private String cpf;

    @Schema(example = "jose.silva@gmail.com")
    private String email;

}
