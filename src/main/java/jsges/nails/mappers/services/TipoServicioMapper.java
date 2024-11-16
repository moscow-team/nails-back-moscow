package jsges.nails.mappers.services;

import jsges.nails.DTO.servicios.TipoServicioDTO;
import jsges.nails.domain.servicios.TipoServicio;

public abstract class TipoServicioMapper {
    public static TipoServicioDTO toDTO(TipoServicio tipoServicio) {
        TipoServicioDTO tipoServicioDTO = new TipoServicioDTO();
        tipoServicioDTO.id = tipoServicio.getId();
        tipoServicioDTO.denominacion = tipoServicio.getDenominacion();
        
        return tipoServicioDTO;
    }

    public static TipoServicio toEntity(TipoServicioDTO tipoServicioDTO) {
        TipoServicio tipoServicio = new TipoServicio();
        tipoServicio.setId(tipoServicioDTO.id);
        tipoServicio.setDenominacion(tipoServicioDTO.denominacion);
        return tipoServicio;
    }
}
