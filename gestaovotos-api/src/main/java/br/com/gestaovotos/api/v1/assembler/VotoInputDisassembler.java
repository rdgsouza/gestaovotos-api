package br.com.gestaovotos.api.v1.assembler;

import br.com.gestaovotos.domain.model.Voto;
import br.com.gestaovotos.api.v1.model.input.VotoInput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VotoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Voto toDomainObject(VotoInput votoInput) {

        return modelMapper.map(votoInput, Voto.class);
    }

    public void copyToDomainObject(VotoInput votoInput, Voto voto) {

        modelMapper.map(votoInput, voto);
    }
}
