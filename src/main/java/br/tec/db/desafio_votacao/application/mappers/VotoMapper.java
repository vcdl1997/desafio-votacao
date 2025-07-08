package br.tec.db.desafio_votacao.application.mappers;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.tec.db.desafio_votacao.application.dto.voto.request.FiltroVotoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.voto.request.VotoRequestDTO;
import br.tec.db.desafio_votacao.domain.entities.Voto;
import br.tec.db.desafio_votacao.domain.enums.RespostaVotoEnum;
import br.tec.db.desafio_votacao.domain.vo.FiltroVotoVO;

@Mapper(componentModel = "spring")
public interface VotoMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "respostaVoto", target = "resposta", qualifiedByName = "stringToRespostaVotoEnum")
	@Mapping(target = "dataHoraInclusao", ignore = true)
	Voto votoRequestDTOParaVoto(VotoRequestDTO dto);
	
	@Mapping(source = "idSessaoVotacao", target = "idSessaoVotacao", qualifiedByName = "stringToLong")
	FiltroVotoVO filtroVotoRequestDTOParaFiltroVotoVO(FiltroVotoRequestDTO dto);
	
	@Named("stringToRespostaVotoEnum")
	default RespostaVotoEnum stringToRespostaVotoEnum(String respostaVoto) {
		return RespostaVotoEnum.from(respostaVoto);
	}
	
	@Named("stringToLong")
	default Long stringToLong(String idSessaoVotacao) {
		return StringUtils.isNumeric(idSessaoVotacao) ? Long.valueOf(idSessaoVotacao) : null;
		
	}

}
