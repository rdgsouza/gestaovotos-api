package br.com.gestaovotos.api.v1.openapi.controller;

import br.com.gestaovotos.api.v1.model.AssociadoModel;
import br.com.gestaovotos.api.v1.model.input.AssociadoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.Collection;

public interface AssociadoControllerOpenApi {

    @Operation(summary = "Cadastra um associado")
    AssociadoModel adicionar(@RequestBody(description = "Representação de um novo associado", required = true) AssociadoInput associadoInput);

    @Operation(summary = "Lista os associados")
    Collection<AssociadoModel> listar();

    @Operation(summary = "Busca um associado por id",
            responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do associado inválido",
            content = @Content(schema = @Schema(ref = "Problema"))),
            @ApiResponse(responseCode = "404", description = "Associado não encontrado",
            content = @Content(schema = @Schema(ref = "Problema")))
    })
    AssociadoModel buscar(@Parameter(description = "ID de um associado", example = "1", required = true) Long associadoId);

    @Operation(summary = "Exclui um associado por id",
            responses = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400", description = "ID do associado inválido",
            content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "404", description = "Associado não encontrado",
            content = @Content(schema = @Schema(ref = "Problema")))
            })
    void remover(@Parameter(description = "ID de um associado", example = "1", required = true) Long associadoId);
}
