package br.tec.db.desafio_votacao.domain.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.tec.db.desafio_votacao.domain.entities.Pauta;
import br.tec.db.desafio_votacao.domain.vo.FiltroPautaVO;

public interface PautaRepository {
	
	Optional<Pauta> obterPorId(Long id);
	
	Page<Pauta> listar(FiltroPautaVO filtro, Pageable pageable);
	
	Pauta salvar(Pauta voto);
	
}
