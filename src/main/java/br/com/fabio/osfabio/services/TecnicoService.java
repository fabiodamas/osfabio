package br.com.fabio.osfabio.services;

import br.com.fabio.osfabio.domain.Tecnico;
import br.com.fabio.osfabio.repositories.TecnicoRepository;
import br.com.fabio.osfabio.services.exceptions.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {
    /*
     * Busca Tecnico pelo ID
     */

    Logger log = LoggerFactory.getLogger(TecnicoService.class);

    @Autowired
    private TecnicoRepository repository;

    public Tecnico findById(Integer id) {
        log.info("SERVICE - BUSCANDO TÉCNICO POR ID");
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));
    }

    /*
     * Busca todos os Tecnicos da base de dados
     */
    public List<Tecnico> findAll() {
        log.info("SERVICE - BUSCANDO TODOS OD TÉCNICOS");
        return repository.findAll();
    }

}
