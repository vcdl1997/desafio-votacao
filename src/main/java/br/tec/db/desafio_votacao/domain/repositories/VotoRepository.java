package br.tec.db.desafio_votacao.domain.repositories;

import java.util.List;

import br.tec.db.desafio_votacao.domain.entities.Voto;
import br.tec.db.desafio_votacao.domain.entities.VotoId;
import br.tec.db.desafio_votacao.domain.vo.FiltroVotoVO;

public interface VotoRepository {
	
	List<Voto> listarVotosPorSessaoVotacao(final FiltroVotoVO vo);
	
	Voto salvar(Voto voto);
	
	boolean associadoJaVotouNessaSessaoVotacao(VotoId id);
	
}
