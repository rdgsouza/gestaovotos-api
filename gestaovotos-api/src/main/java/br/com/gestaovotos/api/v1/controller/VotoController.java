package br.com.gestaovotos.api.v1.controller;

import br.com.gestaovotos.domain.model.Voto;
import br.com.gestaovotos.domain.service.VotoService;
import br.com.gestaovotos.api.v1.ResourceUriHelper;
import br.com.gestaovotos.api.v1.assembler.VotoInputDisassembler;
import br.com.gestaovotos.api.v1.assembler.VotoModelAssembler;
import br.com.gestaovotos.api.v1.model.VotoModel;
import br.com.gestaovotos.api.v1.model.input.VotoInput;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Tag(name = "Votos")
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class VotoController {

    @Autowired
    private VotoService votoService;
    @Autowired
    private VotoInputDisassembler votoInputDisassembler;
    @Autowired
    private VotoModelAssembler votoModelAssembler;


    @PostMapping("v1/pautas/{pautaId}/sessoes/{sessaoId}/votos")
    @ResponseStatus(HttpStatus.CREATED)
    public VotoModel enviarVoto(@PathVariable Long pautaId, @PathVariable Long sessaoId,
                                @RequestBody @Valid VotoInput votoInput) {

        Voto votoAtual = votoInputDisassembler
                .toDomainObject(votoInput);

        VotoModel votoModel = votoModelAssembler.
                toModel(votoService.enviarVoto(pautaId, sessaoId, votoAtual));

        ResourceUriHelper.addUriInResponseHeader(votoModel.getId());

        return votoModel;
    }

    @GetMapping("v1/pautas/{pautaId}/votos")
    @ResponseStatus(HttpStatus.OK)
    public Collection<VotoModel> listarVotosPorPautaId(@PathVariable Long pautaId) {

        return votoModelAssembler.
                toCollectionModel(votoService.buscarVotosPorPautaId(pautaId));
    }

    @DeleteMapping("v1/votos/{votoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long votoId) {

        votoService.excluirVoto(votoId);
    }

}