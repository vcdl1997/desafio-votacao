package br.tec.db.desafio_votacao.application.mappers;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.tec.db.desafio_votacao.application.dto.pauta.request.CadastroPautaRequestDTO;
import br.tec.db.desafio_votacao.application.dto.pauta.request.FiltroPautaRequestDTO;
import br.tec.db.desafio_votacao.application.dto.pauta.response.PautaResponseDTO;
import br.tec.db.desafio_votacao.domain.entities.Pauta;
import br.tec.db.desafio_votacao.domain.vo.FiltroPautaVO;

@Mapper(componentModel = "spring")
public interface PautaMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "dataHoraInclusao", ignore = true)
	Pauta cadastroPautaRequestDTOParaPauta(CadastroPautaRequestDTO dto); 

	@Mapping(source = "id", target = "id", qualifiedByName = "stringParaLong")
	FiltroPautaVO filtroPautaRequestDTOParaFiltroPautaVO(FiltroPautaRequestDTO dto); 
	
	PautaResponseDTO pautaParaPautaResponseDTO(Pauta pauta); 
	
    @Named("stringParaLong")
    default Long stringParaLong(String cpf) {
        return StringUtils.isNotEmpty(cpf) ? Long.valueOf(cpf) : null;
    }
	
}
