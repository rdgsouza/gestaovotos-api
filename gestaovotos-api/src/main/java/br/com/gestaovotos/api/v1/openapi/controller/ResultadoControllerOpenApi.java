package br.com.gestaovotos.api.v1.openapi.controller;

import br.com.gestaovotos.api.v1.dto.ResultadoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface ResultadoControllerOpenApi {

    @Operation(summary = "Lista o resultado da votação pelo id da pauta",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "ID da pauta inválida",
                    content = @Content(schema = @Schema)),
                    @ApiResponse(responseCode = "404", description = "Pauta não encontrado",
                    content = @Content(schema = @Schema(ref = "Problema")))
            })
    ResultadoDto findVotosByPautaId(@Parameter(description = "ID de uma pauta", example = "1", required = true) Long pautaId);
}
