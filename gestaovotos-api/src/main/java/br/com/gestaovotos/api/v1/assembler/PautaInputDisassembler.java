package br.com.gestaovotos.api.v1.assembler;

import br.com.gestaovotos.domain.model.Pauta;
import br.com.gestaovotos.api.v1.model.input.PautaInput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PautaInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Pauta toDomainObject(PautaInput pautaInput) {
        return modelMapper.map(pautaInput, Pauta.class);
    }

    public void copyToDomainObject(PautaInput pautaInput, Pauta pauta) {

        modelMapper.map(pautaInput, pauta);
    }
}
