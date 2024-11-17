package jsges.nails.service.organizacion;

import jsges.nails.DTO.Organizacion.ClienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IClienteService {
    public ResponseEntity<List<ClienteDTO>> listar();

    public ResponseEntity<ClienteDTO> buscarPorId(Integer id);

    public ResponseEntity<ClienteDTO> guardar(ClienteDTO cliente);

    public ResponseEntity<ClienteDTO> eliminar(Integer id);

    public ResponseEntity<List<ClienteDTO>> listar(String consulta);

    public ResponseEntity<Page<ClienteDTO>> listarClientes(Pageable pageable);

    public ResponseEntity<Page<ClienteDTO>> buscarPaginado(Pageable pageable, List<ClienteDTO> clientes);

    public ResponseEntity<ClienteDTO> actualizar(Integer id, ClienteDTO modelRecibido);
  }
