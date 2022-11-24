package br.com.gestaovotos.api.v1.openapi.controller;

import br.com.gestaovotos.api.v1.model.SessaoModel;
import br.com.gestaovotos.api.v1.model.input.SessaoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.Collection;

public interface SessaoControllerOpenApi {

    @Operation(summary = "Inicia uma sessão para votação", description = "Para iniciar uma sessão é necessário informar o ID de uma pauta")
    SessaoModel iniciarSessaoVotacao(@RequestBody(description = "Representação de uma nova sessão", required = true) SessaoInput sessaoInput);

    @Operation(summary = "Lista as sessões")
    Collection<SessaoModel> listar();

    @Operation(summary = "Busca uma sessão por id",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "ID da sessão inválido",
                    content = @Content(schema = @Schema)),
                    @ApiResponse(responseCode = "404", description = "Sessão não encontrada",
                    content = @Content(schema = @Schema(ref = "Problema")))
            })
    SessaoModel buscar(@Parameter(description = "ID de uma sessão", example = "1", required = true) Long sessaoId);

    @Operation(summary = "Exclui uma sessão por id",
            responses = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "400", description = "ID da sessão inválido",
                    content = @Content(schema = @Schema)),
                    @ApiResponse(responseCode = "404", description = "Sessão não encontrada",
                    content = @Content(schema = @Schema(ref = "Problema")))
            })
    void remover(@Parameter(description = "ID de uma sessão", example = "1", required = true) Long sessaoId);

}
