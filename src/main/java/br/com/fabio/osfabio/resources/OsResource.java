package br.com.fabio.osfabio.resources;

import br.com.fabio.osfabio.dtos.OsDTO;
import br.com.fabio.osfabio.services.OsService;
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
@RequestMapping(value = "/os")
public class OsResource {

    @Autowired
    private OsService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<OsDTO> findById(@PathVariable Integer id) {
        OsDTO obj = new OsDTO(service.findById(id));
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<List<OsDTO>> findAll() {
        List<OsDTO> list = service.findAll().stream().map(obj -> new OsDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<OsDTO> create(@Valid @RequestBody OsDTO obj) {
        obj = new OsDTO(service.create(obj));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping
    public ResponseEntity<OsDTO> update(@Valid @RequestBody OsDTO obj) {
        obj = new OsDTO(service.update(obj));
        return ResponseEntity.ok().body(obj);
    }
}

