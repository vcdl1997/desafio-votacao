package br.tec.db.desafio_votacao.domain.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.tec.db.desafio_votacao.domain.entities.Pauta;
import br.tec.db.desafio_votacao.domain.vo.FiltroPautaVO;

public interface PautaRepository {
	
	public Page<Pauta> listar(FiltroPautaVO filtro, Pageable pageable);
	
	public Pauta salvar(Pauta voto);
	
}
