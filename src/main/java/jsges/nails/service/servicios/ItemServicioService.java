package jsges.nails.service.servicios;

import jsges.nails.domain.servicios.ItemServicio;
import jsges.nails.repository.servicios.ItemServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServicioService implements IItemServicioService {
    @Autowired
    private ItemServicioRepository modelRepository;

    @Override
    public List<ItemServicio> listar() {
        List<ItemServicio> items = modelRepository.findAll();
        return items;
    }

    @Override
    public ItemServicio guardar(ItemServicio model) {
        return modelRepository.save(model);
    }

    @Override
    public List<ItemServicio> buscarPorServicio(Integer idServicio) {
        return modelRepository.buscarPorServicio(idServicio);
    };
}
