package br.com.gestaovotos.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PautaModel {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Ref. acordo coletivo no aumento de 50% do salário dos programadores")
    private String descricao;

}
