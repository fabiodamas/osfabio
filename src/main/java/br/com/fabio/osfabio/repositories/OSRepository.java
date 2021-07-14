package br.com.fabio.osfabio.repositories;

import br.com.fabio.osfabio.domain.OS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OSRepository extends JpaRepository<OS, Integer>{

}
