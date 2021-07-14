package br.com.fabio.osfabio.resources;

import br.com.fabio.osfabio.domain.Tecnico;
import br.com.fabio.osfabio.dtos.TecnicoDTO;
import br.com.fabio.osfabio.services.TecnicoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
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

	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO) {
		log.info("RESOURCE - CRIANDO NOVO TÉCNICO");
		Tecnico newObj = service.create(objDTO);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();

		log.info("RESOURCE - RETORNANDO RESPOSTA PARA REQUISIÇÃO");
		return ResponseEntity.created(uri).build();
	}    

	/*
	 * Atualiza um Tecnico
	 */
	// @PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO) {
		log.info("RESOURCE - ATUALIZANDO TÉCNICO");
		TecnicoDTO newObj = new TecnicoDTO(service.update(id, objDTO));
		log.info("RESOURCE - RETORNANDO RESPOSTA PARA REQUISIÇÃO");
		return ResponseEntity.ok().body(newObj);
	}

	/*
	 * Delete Tecnico
	 */
	// @PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		log.info("RESOURCE - DELETANDO TÉCNICO");
		service.delete(id);
		log.info("RESOURCE - RETORNANDO RESPOSTA PARA REQUISIÇÃO");
		return ResponseEntity.noContent().build();
	}    

}
