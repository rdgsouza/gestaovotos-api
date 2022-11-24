package br.com.gestaovotos.v1.assembler;

import br.com.gestaovotos.domain.model.Voto;
import br.com.gestaovotos.v1.model.VotoModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VotoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public VotoModel toModel(Voto voto) {

        return modelMapper.map(voto, VotoModel.class);
    }

    public List<VotoModel> toCollectionModel(List<Voto> votos) {
        return votos.stream()
                .map(voto -> toModel(voto))
                .collect(Collectors.toList());
    }
}
