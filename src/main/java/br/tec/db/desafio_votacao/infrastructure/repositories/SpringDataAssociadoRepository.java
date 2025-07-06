package br.tec.db.desafio_votacao.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.tec.db.desafio_votacao.domain.entities.Associado;

public interface SpringDataAssociadoRepository extends JpaRepository<Associado, Long>, JpaSpecificationExecutor<Associado>{

}
