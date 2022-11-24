package br.com.gestaovotos.v1.assembler;

import br.com.gestaovotos.domain.model.Sessao;
import br.com.gestaovotos.v1.model.SessaoModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SessaoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public SessaoModel toModel(Sessao sessao) {
        return modelMapper.map(sessao, SessaoModel.class);
    }

    public List<SessaoModel> toCollectionModel(List<Sessao> sessoes) {
        return sessoes.stream()
                .map(sessao -> toModel(sessao))
                .collect(Collectors.toList());
    }
}
