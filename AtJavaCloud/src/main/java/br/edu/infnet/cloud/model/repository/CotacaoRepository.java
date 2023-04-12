package br.edu.infnet.cloud.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.cloud.model.domain.Cotacao;

@Repository
public interface CotacaoRepository extends JpaRepository<Cotacao, Integer> {
	
}
