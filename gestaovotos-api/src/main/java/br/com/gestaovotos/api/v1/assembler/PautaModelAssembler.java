package br.com.gestaovotos.api.v1.assembler;

import br.com.gestaovotos.domain.model.Pauta;
import br.com.gestaovotos.api.v1.model.PautaModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PautaModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PautaModel toModel(Pauta pauta) {

        return modelMapper.map(pauta, PautaModel.class);
    }

    public List<PautaModel> toCollectionModel(List<Pauta> pautas) {
        return pautas.stream()
                .map(pauta -> toModel(pauta))
                .collect(Collectors.toList());
    }
}
