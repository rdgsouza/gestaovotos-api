package br.com.gestaovotos.v1.assembler;

import br.com.gestaovotos.domain.model.Sessao;
import br.com.gestaovotos.v1.model.input.SessaoInput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessaoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Sessao toDomainObject(SessaoInput sessaoInput) {
        return modelMapper.map(sessaoInput, Sessao.class);
    }

    public void copyToDomainObject(SessaoInput sessaoInput, Sessao sessao) {

        modelMapper.map(sessaoInput, sessao);
    }
}
