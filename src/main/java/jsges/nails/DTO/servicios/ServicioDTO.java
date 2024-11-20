package jsges.nails.DTO.servicios;

import jsges.nails.domain.servicios.ItemServicio;
import jsges.nails.domain.servicios.Servicio;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class ServicioDTO{
    public Integer id;
    public Integer cliente;
    public LocalDateTime fechaDocumento;
    public Set<ItemServicioDTO> listaItems = new HashSet<>();
    public Double total;
    public String clienteRazonSocial;
    public ServicioDTO() {}

    public ServicioDTO(Servicio elemento, List<ItemServicio>list) {
        this.id = elemento.getId();
        this.cliente = elemento.getCliente().getId();
        this.clienteRazonSocial = elemento.getCliente().getRazonSocial();
        this.fechaDocumento = elemento.getFechaRealizacion();
        this.total= elemento.getTotal();

        list.forEach((model) -> {
           listaItems.add(new ItemServicioDTO(model));
        });
    }

    public ServicioDTO(Servicio elemento){
        this.id = elemento.getId();
        this.cliente = elemento.getCliente().getId();
        this.clienteRazonSocial = elemento.getCliente().getRazonSocial();
        this.fechaDocumento = elemento.getFechaRealizacion();
        this.total= elemento.getTotal();
    }
}
