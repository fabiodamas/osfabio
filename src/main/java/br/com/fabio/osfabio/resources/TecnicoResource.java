package br.com.fabio.osfabio.resources;

import br.com.fabio.osfabio.dtos.TecnicoDTO;
import br.com.fabio.osfabio.services.TecnicoService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {
    Logger log = LoggerFactory.getLogger(TecnicoService.class);

    @Autowired
    private TecnicoService service;

    /*
     * Busca pelo ID
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
        log.info("RESOURCE - BUSCANDO TÉCNICO POR ID");
        TecnicoDTO objDTO = new TecnicoDTO(service.findById(id));
        log.info("RESOURCE - RETORNANDO RESPOSTA PARA REQUISIÇÃO");
        return ResponseEntity.ok().body(objDTO);
    }

    /*
     * Lista todos objetos do tipo Tecnico na base
     */
    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll() {
        log.info("RESOURCE - BUSCANDO TODOS OD TÉCNICOS");
        List<TecnicoDTO> listDTO = service.findAll().stream().map(obj -> new TecnicoDTO(obj))
                .collect(Collectors.toList());

        log.info("RESOURCE - RETORNANDO RESPOSTA PARA REQUISIÇÃO");
        return ResponseEntity.ok().body(listDTO);
    }

}
