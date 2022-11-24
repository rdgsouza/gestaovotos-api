package br.com.gestaovotos.v1.controller;

import br.com.gereciamentovotacao.api.ResourceUriHelper;
import br.com.gereciamentovotacao.api.v1.assembler.PautaInputDisassembler;
import br.com.gereciamentovotacao.api.v1.assembler.PautaModelAssembler;
import br.com.gereciamentovotacao.api.v1.model.PautaModel;
import br.com.gereciamentovotacao.api.v1.model.input.PautaInput;
import br.com.gereciamentovotacao.api.v1.openapi.controller.PautaControllerOpenApi;
import br.com.gereciamentovotacao.domain.model.Pauta;
import br.com.gereciamentovotacao.domain.repository.PautaRepository;
import br.com.gereciamentovotacao.domain.service.PautaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Tag(name = "Pautas")
@RestController
@RequestMapping(path ="/v1/pautas", produces = MediaType.APPLICATION_JSON_VALUE)
public class PautaController implements PautaControllerOpenApi {

	@Autowired
	private PautaRepository pautaRepository;

	@Autowired
	private PautaService pautaService;

	@Autowired
	private PautaInputDisassembler pautaInputDisassembler;

	@Autowired
	private PautaModelAssembler pautaModelAssembler;

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PautaModel adicionar(@RequestBody @Valid PautaInput pautaInput) {

		Pauta pautaAtual = pautaInputDisassembler
				.toDomainObject(pautaInput);

		PautaModel pautaModel = pautaModelAssembler.
				toModel(pautaService.salvar(pautaAtual));

		ResourceUriHelper.addUriInResponseHeader(pautaModel.getId());

		return pautaModel;
	}


	@Override
	@GetMapping
	public Collection<PautaModel> listar() {

		List<Pauta> todasPautas = pautaRepository.findAll();

		return pautaModelAssembler.toCollectionModel(todasPautas);
	}

	@Override
	@GetMapping("/{pautaId}")
	public PautaModel buscar(@PathVariable Long pautaId) {
		Pauta pauta = pautaService.buscarOuFalhar(pautaId);

		return pautaModelAssembler.toModel(pauta);
	}

	@Override
	@DeleteMapping("/{pautaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long pautaId) {

		pautaService.excluir(pautaId);
	}

}
