package br.com.gestaovotos.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AssociadoIdInput {

    @Schema(example = "1")
    @NotNull
    private Long id;
}
