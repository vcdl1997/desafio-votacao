package br.tec.db.desafio_votacao.domain.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.tec.db.desafio_votacao.domain.entities.Associado;
import br.tec.db.desafio_votacao.domain.vo.FiltroAssociadoVO;

public interface AssociadoRepository {
	
	public Page<Associado> listar(FiltroAssociadoVO filtro, Pageable pageable);
	
	public Optional<Associado> obterPorId(Long id);
	
	public Associado salvar(Associado associado);
	
	public void deletar(Associado associado);
	
}
