package br.com.gestaovotos.v1.assembler;

import br.com.gestaovotos.domain.model.Associado;
import br.com.gestaovotos.v1.model.AssociadoModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssociadoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public AssociadoModel toModel(Associado associado) {

        return modelMapper.map(associado, AssociadoModel.class);
    }

    public List<AssociadoModel> toCollectionModel(List<Associado> associados) {
        return associados.stream()
                .map(associado -> toModel(associado))
                .collect(Collectors.toList());
    }
}
