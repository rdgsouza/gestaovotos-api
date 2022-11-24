package br.com.gestaovotos.v1.dto;

import br.com.gestaovotos.domain.model.Pauta;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ResultadoDto {

    private Pauta pauta;
    @Schema(example = "10")
    private Integer totalSim;
    @Schema(example = "5")
    private Integer totalNao;
    @Schema(example = "15")
    private Integer totalVotos;
    @Schema(example = "1")
    private Integer totalSessoes;
}
