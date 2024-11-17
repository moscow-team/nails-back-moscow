package jsges.nails.service.servicios;
import jsges.nails.DTO.servicios.ServicioDTO;
import jsges.nails.domain.organizacion.Cliente;
import jsges.nails.domain.servicios.Servicio;
import jsges.nails.mappers.ClienteMapper;
import jsges.nails.mappers.services.ServiceMapper;
import jsges.nails.repository.servicios.ServicioRepository;
import jsges.nails.service.organizacion.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioService implements IServicioService {

    @Autowired
    private ServicioRepository modelRepository;
    private IClienteService clienteService;

    @Override
    public ResponseEntity<List<ServicioDTO>> listarNoEliminados() {
        List<Servicio> servicios = modelRepository.buscarNoEliminados();

        if (servicios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(servicios.stream().map(ServiceMapper::toDTO).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<ServicioDTO> buscarPorId(Integer id) {
        Servicio servicio = modelRepository.findById(id).orElse(null);

        if (servicio == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ServiceMapper.toDTO(servicio));
    }

    @Override
    public ResponseEntity<ServicioDTO> guardar(ServicioDTO model) {
        Cliente cliente = ClienteMapper.toEntity(clienteService.buscarPorId(model.cliente).getBody());

        if (cliente == null) {
            return ResponseEntity.badRequest().build();
        }

        Servicio servicio = modelRepository.save(ServiceMapper.toEntity(model, cliente));

        return ResponseEntity.ok(ServiceMapper.toDTO(servicio));    
    }


    @Override
    public ResponseEntity<Page<ServicioDTO>> listarServicios(Pageable pageable) {
        Page<Servicio> servicios = modelRepository.findAll(pageable);

        if (servicios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(servicios.map(ServiceMapper::toDTO));
    }



    @Override
    public ResponseEntity<Page<ServicioDTO>> buscarPagina(Pageable pageable, List<ServicioDTO> listado) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ServicioDTO> list;
        if (listado.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, listado.size());
            list = listado.subList(startItem, toIndex);
        }

        Page<ServicioDTO> bookPage
                = new PageImpl<ServicioDTO>(list, PageRequest.of(currentPage, pageSize), listado.size());

        return ResponseEntity.ok(bookPage);
    }

    @Override
    public ResponseEntity<List<ServicioDTO>> listarNoEliminados(String consulta) {
        List<Servicio> servicios = modelRepository.buscarNoEliminados(consulta);

        if (servicios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(servicios.stream().map(ServiceMapper::toDTO).collect(Collectors.toList()));
    }
}
