package br.com.gestaovotos.api.v1.assembler;

import br.com.gestaovotos.api.v1.model.input.AssociadoInput;
import br.com.gestaovotos.domain.model.Associado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssociadoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Associado toDomainObject(AssociadoInput associadoInput)
    {
        return modelMapper.map(associadoInput, Associado.class);
    }

    public void copyToDomainObject(AssociadoInput associadoInput, Associado associado) {

        modelMapper.map(associadoInput, associado);
    }
}
