package br.com.gestaovotos.api.v1.controller;

import br.com.gestaovotos.api.v1.model.input.AssociadoInput;
import br.com.gestaovotos.domain.model.Associado;
import br.com.gestaovotos.domain.repository.AssociadoRepository;
import br.com.gestaovotos.domain.service.AssociadoService;
import br.com.gestaovotos.api.v1.ResourceUriHelper;
import br.com.gestaovotos.api.v1.assembler.AssociadoInputDisassembler;
import br.com.gestaovotos.api.v1.assembler.AssociadoModelAssembler;
import br.com.gestaovotos.api.v1.model.AssociadoModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Tag(name = "Associados")
@RestController
@RequestMapping(path ="/v1/associados", produces = MediaType.APPLICATION_JSON_VALUE)
public class AssociadoController {

	@Autowired
	private AssociadoRepository associdoRepository;

	@Autowired
	private AssociadoService associadoService;

	@Autowired
	private AssociadoInputDisassembler associadoInputDisassembler;

	@Autowired
	private AssociadoModelAssembler associadoModelAssembler;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AssociadoModel adicionar(@RequestBody @Valid AssociadoInput associadoInput) {

		Associado associadoAtual = associadoInputDisassembler
				.toDomainObject(associadoInput);

		AssociadoModel associadoModel = associadoModelAssembler.
				toModel(associadoService.salvar(associadoAtual));

		ResourceUriHelper.addUriInResponseHeader(associadoModel.getId());

		return associadoModel;
	}


	@GetMapping
	public Collection<AssociadoModel> listar() {

		List<Associado> todosAssociados = associdoRepository.findAll();

		return associadoModelAssembler.toCollectionModel(todosAssociados);
	}

	@GetMapping("/{associadoId}")
	public AssociadoModel buscar(@PathVariable Long associadoId) {
		Associado associado = associadoService.buscarOuFalhar(associadoId);

		return associadoModelAssembler.toModel(associado);
	}

	@DeleteMapping("/{associadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long associadoId) {

		associadoService.excluir(associadoId);
	}

}
