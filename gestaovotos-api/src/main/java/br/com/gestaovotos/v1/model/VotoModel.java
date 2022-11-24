package br.com.gestaovotos.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VotoModel {


    @Schema(example = "1")
    private Long id;

    @Schema(example = "true ")
    private boolean voto;

    @Schema(example = "56177539084")
    private String cpf;


    @Schema(example = "1")
    private PautaIdModel pauta;


    @Schema(example = "1")
    private AssociadoIdModel associado;

}
