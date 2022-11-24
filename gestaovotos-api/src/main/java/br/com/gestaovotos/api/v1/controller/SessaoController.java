package br.com.gestaovotos.api.v1.controller;

import br.com.gestaovotos.api.v1.ResourceUriHelper;
import br.com.gestaovotos.api.v1.model.SessaoModel;
import br.com.gestaovotos.api.v1.model.input.SessaoInput;
import br.com.gestaovotos.domain.model.Sessao;
import br.com.gestaovotos.domain.repository.SessaoRepository;
import br.com.gestaovotos.domain.service.SessaoService;
import br.com.gestaovotos.api.v1.assembler.SessaoInputDisassembler;
import br.com.gestaovotos.api.v1.assembler.SessaoModelAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Tag(name = "Sessões")
@RestController
@RequestMapping(path ="/v1/sessoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class SessaoController {

    private SessaoRepository sessaoRepository;

    private SessaoService sessaoService;

    private SessaoInputDisassembler sessaoInputDisassembler;

    private SessaoModelAssembler sessaoModelAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessaoModel iniciarSessaoVotacao(@RequestBody @Valid SessaoInput sessaoInput) {

        Sessao sessaoAtual = sessaoInputDisassembler
                .toDomainObject(sessaoInput);

        SessaoModel sessaoModel = sessaoModelAssembler.
                toModel(sessaoService.iniciarSessaoVotacao(sessaoAtual));

        ResourceUriHelper.addUriInResponseHeader(sessaoModel.getId());

        return sessaoModel;
    }

    @GetMapping
    public Collection<SessaoModel> listar() {

        List<Sessao> todasSessoes = sessaoRepository.findAll();

        return sessaoModelAssembler.toCollectionModel(todasSessoes);
    }

    @GetMapping("/{sessaoId}")
    public SessaoModel buscar(@PathVariable Long sessaoId) {
        Sessao sessao = sessaoService.buscarOuFalhar(sessaoId);

        return sessaoModelAssembler.toModel(sessao);
    }

    @DeleteMapping("/{sessaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long sessaoId) {

        sessaoService.excluir(sessaoId);
    }

}
