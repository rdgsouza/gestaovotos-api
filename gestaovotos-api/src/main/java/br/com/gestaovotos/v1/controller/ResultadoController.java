package br.com.gestaovotos.v1.controller;

import br.com.gestaovotos.domain.service.ResultadoService;
import br.com.gestaovotos.v1.dto.ResultadoDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Resultados")
@RestController
public class ResultadoController {

    @Autowired
    private ResultadoService resultadoService;

    @GetMapping("v1/pautas/{pautaId}/resultado")
    @ResponseStatus(code = HttpStatus.OK)
    public ResultadoDto findVotosByPautaId(@PathVariable Long pautaId) {
        return resultadoService.getResultadoVotacao(pautaId);
    }
}
