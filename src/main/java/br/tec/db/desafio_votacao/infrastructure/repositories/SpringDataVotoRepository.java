package br.tec.db.desafio_votacao.infrastructure.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.tec.db.desafio_votacao.domain.entities.Voto;
import br.tec.db.desafio_votacao.domain.entities.VotoId;

public interface SpringDataVotoRepository extends JpaRepository<Voto, VotoId>{

	public List<Voto> findById_SessaoId(Long idSessao);
	
}
