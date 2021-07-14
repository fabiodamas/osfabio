package br.com.fabio.osfabio.services;

import br.com.fabio.osfabio.domain.Pessoa;
import br.com.fabio.osfabio.domain.Tecnico;
import br.com.fabio.osfabio.dtos.TecnicoDTO;
import br.com.fabio.osfabio.repositories.PessoaRepository;
import br.com.fabio.osfabio.repositories.TecnicoRepository;
import br.com.fabio.osfabio.services.exceptions.DataIntegratyViolationException;
import br.com.fabio.osfabio.services.exceptions.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@Service
public class TecnicoService {
    /*
     * Busca Tecnico pelo ID
     */

    Logger log = LoggerFactory.getLogger(TecnicoService.class);

    @Autowired
    private TecnicoRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;    

	@Autowired
	private BCryptPasswordEncoder encoder;    

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

	public Tecnico create(TecnicoDTO objDTO) {
		log.info("SERVICE - CRIANDO NOVO TÉCNICO");
		if (findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}

		Tecnico newTec = new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone(),
				encoder.encode(objDTO.getSenha()));

		// if (objDTO.getPerfis().contains(Perfil.ADMIN)) {
		//	newTec.addPerfil(Perfil.ADMIN);
		// }

		return repository.save(newTec);
	}    

	/*
	 * Busca Tecnico pelo CPF
	 */
	private Pessoa findByCPF(TecnicoDTO objDTO) {
		log.info("SERVICE - ANALIZANDO SE O CPF ESTÁ CADASTRADO NO BANCO");
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());

		if (obj != null) {
			return obj;
		}
		return null;
	}    

	/*
	 * Atualiza um Tecnico
	 */
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		log.info("SERVICE - ATUALIZANDO TÉCNICO");
		Tecnico oldObj = findById(id);

		if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}

		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		// if (objDTO.getPerfis().contains(Perfil.ADMIN)) {
		// 	oldObj.addPerfil(Perfil.ADMIN);
		// }
		return repository.save(oldObj);
	}

	/*
	 * Deleta um Tecnico pelo ID
	 */
	public void delete(Integer id) {
		log.info("SERVICE - DELETANDO TÉCNICO");
		Tecnico obj = findById(id);

		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Técnico possui Ordens de Serviço, não pode ser deletado!");
		}

		repository.deleteById(id);
	}	
}
