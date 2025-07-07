package br.tec.db.desafio_votacao.application.mappers;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.tec.db.desafio_votacao.application.dto.sessaovotacao.request.CadastroSessaoVotacaoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.sessaovotacao.request.FiltroSessaoVotacaoRequestDTO;
import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
import br.tec.db.desafio_votacao.domain.vo.FiltroSessaoVotacaoVO;
import br.tec.db.desafio_votacao.shared.utils.DateUtils;

@Mapper(componentModel = "spring")
public interface SessaoVotacaoMapper {
	
	FiltroSessaoVotacaoVO filtroSessaoVotacaoRequestDTOParaFiltroSessaoVotacaoVO(FiltroSessaoVotacaoRequestDTO dto);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "pauta", ignore = true)
	@Mapping(target = "dataHoraInicio", source = "dataHoraInicio", qualifiedByName = "stringParaLocalDateTime")
	@Mapping(target = "dataHoraEncerramento", source = "dataHoraEncerramento", qualifiedByName = "stringParaLocalDateTime")
	@Mapping(target = "dataHoraInclusao", ignore = true)
	SessaoVotacao cadastroSessaoVotacaoRequestDTOParaSessaoVotacao(CadastroSessaoVotacaoRequestDTO dto);
	
	@Named("stringParaLocalDateTime")
    default LocalDateTime stringParaLocalDateTime(String localDateTimeString) {
        return DateUtils.converteStringParaLocalDateTime(localDateTimeString);
    }
	
}
