package br.tec.db.desafio_votacao.application.mappers;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.tec.db.desafio_votacao.application.dto.associado.request.CadastroAssociadoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.associado.request.FiltroAssociadoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.associado.response.AssociadoResponseDTO;
import br.tec.db.desafio_votacao.domain.entities.Associado;
import br.tec.db.desafio_votacao.domain.vo.FiltroAssociadoVO;

@Mapper(componentModel = "spring")
public interface AssociadoMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "cpf", target = "cpf", qualifiedByName = "stringParaLong")
	@Mapping(target = "versao", ignore = true)
	@Mapping(target = "dataHoraInclusao", ignore = true)
	Associado cadastroAssociadoRequestDTOParaAssociado(CadastroAssociadoRequestDTO dto);
	
	AssociadoResponseDTO associadoParaAssociadoResponseDTO(Associado dto);
	
	@Mapping(source = "id", target = "id", qualifiedByName = "stringParaLong")
	@Mapping(source = "cpf", target = "cpf", qualifiedByName = "stringParaLong")
	FiltroAssociadoVO filtroAssociadoDTOParaFiltroAssociadoVO(FiltroAssociadoRequestDTO dto); 
	
    @Named("stringParaLong")
    default Long stringParaLong(String cpf) {
        return StringUtils.isNotEmpty(cpf) ? Long.valueOf(cpf) : null;
    }
	
}
