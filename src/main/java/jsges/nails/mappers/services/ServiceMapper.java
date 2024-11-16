package jsges.nails.mappers.services;

import jsges.nails.DTO.servicios.ServicioDTO;
import jsges.nails.domain.organizacion.Cliente;
import jsges.nails.domain.servicios.Servicio;

public abstract class ServiceMapper {
    public static ServicioDTO toDTO(Servicio servicio) {
        ServicioDTO servicioDTO = new ServicioDTO(servicio);
        return servicioDTO;
    }

    public static Servicio toEntity(ServicioDTO servicioDTO, Cliente cliente) {
        Servicio servicio = new Servicio();
        servicio.setId(servicioDTO.id);
        servicio.setCliente(cliente);
        servicio.setFechaRealizacion(servicioDTO.fechaDocumento);
        servicio.setTotal(servicioDTO.total);
        return servicio;
    }
}
