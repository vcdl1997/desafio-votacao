package br.tec.db.desafio_votacao.domain.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.tec.db.desafio_votacao.domain.entities.Associado;
import br.tec.db.desafio_votacao.domain.vo.FiltroAssociadoVO;

public interface AssociadoRepository {
	
	Page<Associado> listar(FiltroAssociadoVO filtro, Pageable pageable);
	
	Optional<Associado> obterPorId(Long id);
	
	Associado salvar(Associado associado);

	boolean existemUsuariosComEsteCpf(Long cpf);
	
}
