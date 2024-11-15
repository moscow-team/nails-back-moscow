package jsges.nails.mappers;

import jsges.nails.DTO.articulos.ArticuloVentaDTO;
import jsges.nails.domain.articulos.ArticuloVenta;
import jsges.nails.domain.articulos.Linea;

public abstract class ArticuloMapper {

    public static ArticuloVentaDTO toDTO(ArticuloVenta articuloVenta) {
        ArticuloVentaDTO articuloVentaDTO = new ArticuloVentaDTO(articuloVenta);
        return articuloVentaDTO;
    }

    public static ArticuloVenta toEntity(ArticuloVentaDTO articuloVentaDTO, Linea linea) {
        ArticuloVenta articuloVenta = new ArticuloVenta();
        articuloVenta.setId(articuloVentaDTO.id);
        articuloVenta.setDenominacion(articuloVentaDTO.denominacion);
        articuloVenta.setLinea(linea);
        return articuloVenta;
    }
}