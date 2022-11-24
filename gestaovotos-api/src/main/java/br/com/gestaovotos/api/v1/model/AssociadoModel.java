package br.com.gestaovotos.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssociadoModel {


    @Schema(example = "1")
    private Long id;

    @Schema(example = "José da Silva")
    private String nome;

    @Schema(example = "56177539084")
    private String cpf;

    @Schema(example = "jose.silva@gmail.com")
    private String email;

}
