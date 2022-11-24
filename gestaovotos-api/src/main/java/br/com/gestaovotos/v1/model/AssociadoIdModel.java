package br.com.gestaovotos.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssociadoIdModel {

    @Schema(example = "1")
    private Long id;

}
