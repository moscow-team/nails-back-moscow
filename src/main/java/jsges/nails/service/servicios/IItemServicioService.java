package jsges.nails.service.servicios;

import jsges.nails.domain.servicios.ItemServicio;

import java.util.List;

public interface IItemServicioService {
    public List<ItemServicio> listar();

    public ItemServicio guardar(ItemServicio model);

    public List<ItemServicio> buscarPorServicio(Integer idServicio);
}
