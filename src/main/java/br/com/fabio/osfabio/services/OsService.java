package br.com.fabio.osfabio.services;

import br.com.fabio.osfabio.domain.Cliente;
import br.com.fabio.osfabio.domain.OS;
import br.com.fabio.osfabio.domain.Tecnico;
import br.com.fabio.osfabio.domain.enuns.Prioridade;
import br.com.fabio.osfabio.domain.enuns.Status;
import br.com.fabio.osfabio.dtos.OsDTO;
import br.com.fabio.osfabio.repositories.OSRepository;
import br.com.fabio.osfabio.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OsService {

    @Autowired
    private OSRepository repository;

    @Autowired
    private TecnicoService tecnicoService;

    @Autowired
    private ClienteService clienteService;

    public OS findById(Integer id) {
        Optional<OS> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + OS.class.getName()));
    }

    public List<OS> findAll() {
        return repository.findAll();
    }

    public OS create(@Valid OsDTO obj) {
        return fromDTO(obj);
    }

    public OS update(@Valid OsDTO obj) {
        findById(obj.getId());
        return fromDTO(obj);
    }

    private OS fromDTO(OsDTO obj) {
        OS newObj = new OS();
        newObj.setId(obj.getId());
        newObj.setObservacoes(obj.getObservacoes());
        newObj.setPrioridade(Prioridade.toEnum(obj.getPrioridade().getCod()));
        newObj.setStatus(Status.toEnum(obj.getStatus().getCod()));

        Tecnico tec = tecnicoService.findById(obj.getTecnico());
        Cliente cli = clienteService.findById(obj.getCliente());

        newObj.setTecnico(tec);
        newObj.setCliente(cli);

        if(newObj.getStatus().getCod().equals(2)) {
            newObj.setDataFechamento(LocalDateTime.now());
        }

        return repository.save(newObj);
    }





}
