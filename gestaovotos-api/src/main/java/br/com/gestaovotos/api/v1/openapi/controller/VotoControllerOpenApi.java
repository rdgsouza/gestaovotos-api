package br.com.gestaovotos.api.v1.openapi.controller;

import br.com.gestaovotos.api.v1.model.VotoModel;
import br.com.gestaovotos.api.v1.model.input.VotoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.Collection;

public interface VotoControllerOpenApi {

    @Operation(summary = "Envia o voto referente a uma pauta e sessão",
            description = "Para enviar uma voto é necessário informar o id de uma pauta e o id de uma sessão")
    VotoModel enviarVoto(@Parameter(description = "ID de uma pauta", example = "1", required = true) Long pautaId,
                         @Parameter(description = "ID de uma sessão", example = "1", required = true) Long sessaoId,
                         @RequestBody(description = "Representação de um novo voto", required = true) VotoInput votoInput);

    @Operation(summary = "Lista os votos por id da pauta",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "ID da pauta inválido",
                    content = @Content(schema = @Schema)),
                    @ApiResponse(responseCode = "404", description = "Pauta não encontrada",
                    content = @Content(schema = @Schema(ref = "Problema")))
            })
    Collection<VotoModel> listarVotosPorPautaId(@Parameter(description = "ID de uma pauta", example = "1", required = true) Long pautaId);

    @Operation(summary = "Exclui um voto por id",
            responses = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "400", description = "ID do voto inválido",
                    content = @Content(schema = @Schema)),
                    @ApiResponse(responseCode = "404", description = "Voto não encontrada",
                    content = @Content(schema = @Schema(ref = "Problema")))
            })
    void remover(@Parameter(description = "ID de um voto", example = "1", required = true) Long votoId);
}