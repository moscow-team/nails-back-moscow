package jsges.nails.service.organizacion;

import jsges.nails.DTO.Organizacion.ClienteDTO;
import jsges.nails.domain.organizacion.Cliente;
import jsges.nails.mappers.ClienteMapper;
import jsges.nails.repository.organizacion.ClienteRepository;

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
public class ClienteService implements IClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public ResponseEntity<List<ClienteDTO>> listar() {
        List<Cliente> clientesActivos = clienteRepository.buscarNoEliminados();
        List<ClienteDTO> clienteDTOs = clientesActivos.stream()
            .map(ClienteMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(clienteDTOs);
    }

    @Override
    public ResponseEntity<List<ClienteDTO>> listar(String consulta) {
         List<Cliente> clientesActivos = clienteRepository.buscarNoEliminados(consulta);
         List<ClienteDTO> clienteDTOs = clientesActivos.stream()
             .map(ClienteMapper::toDTO)
             .collect(Collectors.toList());
         return ResponseEntity.ok(clienteDTOs);
    }

    @Override
    public ResponseEntity<ClienteDTO> buscarPorId(Integer id) {
        Cliente model = clienteRepository.findById(id).orElse(null);
        if (model == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ClienteMapper.toDTO(model));
    }

    @Override
    public ResponseEntity<ClienteDTO> guardar(ClienteDTO cliente) {
        Cliente clienteEntity = ClienteMapper.toEntity(cliente);
        Cliente clienteGuardado = clienteRepository.save(clienteEntity);
        return ResponseEntity.ok(ClienteMapper.toDTO(clienteGuardado));
    }

    @Override
    public ResponseEntity<Page<ClienteDTO>> listarClientes(Pageable pageable) {
        Page<Cliente> clientes = clienteRepository.findAll(pageable);
        List<ClienteDTO> clienteDTOs = clientes.stream()
            .map(ClienteMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(new PageImpl<>(clienteDTOs, pageable, clientes.getTotalElements()));
    }

    @Override
    public ResponseEntity<ClienteDTO> eliminar(Integer id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        cliente.setEstado(1);
        clienteRepository.save(cliente);
        return ResponseEntity.ok(ClienteMapper.toDTO(cliente));
    }

    @Override
    public ResponseEntity<Page<ClienteDTO>> buscarPaginado(Pageable pageable, List<ClienteDTO> clientes) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ClienteDTO> list;
        if (clientes.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, clientes.size());
            list = clientes.subList(startItem, toIndex);
        }

        Page<ClienteDTO> bookPage
                = new PageImpl<ClienteDTO>(list, PageRequest.of(currentPage, pageSize), clientes.size());

        return ResponseEntity.ok(bookPage);
    }

    @Override
    public ResponseEntity<ClienteDTO> actualizar(Integer id, ClienteDTO modelRecibido){
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        Cliente clienteActualizado = ClienteMapper.toEntity(modelRecibido);
        clienteActualizado.setId(id);
        Cliente clienteGuardado = clienteRepository.save(clienteActualizado);
        return ResponseEntity.ok(ClienteMapper.toDTO(clienteGuardado));
    }
}
