package br.com.gestaovotos.v1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class SessaoModel {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Sessão de Votação - Acordo coletivo entre associados para aumento de salário dos programadores")
    private String descricao;

    private Integer tempoAbertura;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime dataInicio;

    private PautaIdModel pauta;
}
