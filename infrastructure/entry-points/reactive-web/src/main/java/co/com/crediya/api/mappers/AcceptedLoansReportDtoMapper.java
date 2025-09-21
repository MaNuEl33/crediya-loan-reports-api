package co.com.crediya.api.mappers;

import co.com.crediya.api.dtos.AcceptedLoansReportDto;
import co.com.crediya.model.acceptedloansreport.AcceptedLoansReport;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AcceptedLoansReportDtoMapper {
    AcceptedLoansReportDto toResponseDto(AcceptedLoansReport acceptedLoansReport);
}
