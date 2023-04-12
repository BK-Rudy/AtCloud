package br.edu.infnet.cloud.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.cloud.model.domain.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Integer> {
	
}
