package br.com.gestaovotos.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class PautaInput {

    @Schema(example = "Ref. acordo coletivo no aumento de 50% do salário dos programadores")
    @NotBlank
    private String descricao;

}
