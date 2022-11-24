package br.com.gestaovotos.api.v1.openapi.controller;

import br.com.gestaovotos.api.v1.model.PautaModel;
import br.com.gestaovotos.api.v1.model.input.PautaInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.Collection;

public interface PautaControllerOpenApi {

    @Operation(summary = "Cadastra uma pauta")
    PautaModel adicionar(@RequestBody(description = "Representação de uma nova pauta", required = true) PautaInput pautaInput);

    @Operation(summary = "Lista as pautas")
    Collection<PautaModel> listar();

    @Operation(summary = "Busca uma pauta por id",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "ID da pauta inválida",
                    content = @Content(schema = @Schema)),
                    @ApiResponse(responseCode = "404", description = "Pauta não encontrado",
                    content = @Content(schema = @Schema(ref = "Problema")))
            })
    PautaModel buscar(@Parameter(description = "ID de uma pauta", example = "1", required = true) Long pautaId);

    @Operation(summary = "Exclui uma pauta por id",
            responses = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "400", description = "ID da pauta inválida",
                    content = @Content(schema = @Schema)),
                    @ApiResponse(responseCode = "404", description = "Pauta não encontrado",
                    content = @Content(schema = @Schema(ref = "Problema")))
            })
    void remover(@Parameter(description = "ID de uma pauta", example = "1", required = true) Long pautaId);
}
