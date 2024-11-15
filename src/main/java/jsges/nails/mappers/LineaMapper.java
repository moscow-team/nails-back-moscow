package jsges.nails.mappers;

import jsges.nails.DTO.articulos.LineaDTO;
import jsges.nails.domain.articulos.Linea;

public abstract class LineaMapper {
    public static LineaDTO toDTO(Linea linea) {
        LineaDTO lineaDTO = new LineaDTO();
        lineaDTO.id = linea.getId();
        lineaDTO.denominacion = linea.getDenominacion();
        return lineaDTO;
    }

    public static Linea toEntity(LineaDTO lineaDTO) {
        Linea linea = new Linea();
        linea.setId(lineaDTO.id);
        linea.setDenominacion(lineaDTO.denominacion);
        return linea;
    }
}
