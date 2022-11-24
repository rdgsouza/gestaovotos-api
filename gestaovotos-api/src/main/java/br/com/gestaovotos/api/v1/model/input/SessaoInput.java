package br.com.gestaovotos.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
public class SessaoInput {

    @Schema(example = "Sessão de Votação - Acordo coletivo entre associados para aumento de salário dos programadores")
    @NotBlank
    private String descricao;

    @Schema(example = "60")
    private Integer tempoAbertura = 1;

    @Schema(example = "2022-11-23T14:00:00")
    private LocalDateTime dataInicio;

    @Schema(example = "1")
    @NotNull
    private PautaIdInput pauta;
}
