package jsges.nails;

import jsges.nails.DTO.Organizacion.ClienteDTO;
import jsges.nails.domain.organizacion.Cliente;
import jsges.nails.repository.organizacion.ClienteRepository;
import jsges.nails.service.organizacion.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListar() {
        List<Cliente> clientes = List.of(new Cliente());
        when(clienteRepository.buscarNoEliminados()).thenReturn(clientes);

        ResponseEntity<List<ClienteDTO>> response = clienteService.listar();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(clienteRepository, times(1)).buscarNoEliminados();
    }

    @Test
    public void testListarConConsulta() {
        String consulta = "some query";
        List<Cliente> clientes = List.of(new Cliente());
        when(clienteRepository.buscarNoEliminados(consulta)).thenReturn(clientes);

        ResponseEntity<List<ClienteDTO>> response = clienteService.listar(consulta);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(clienteRepository, times(1)).buscarNoEliminados(consulta);
    }

    @Test
    public void testBuscarPorIdClienteEncontrado() {
        Integer id = 1;
        Cliente cliente = new Cliente();
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        ResponseEntity<ClienteDTO> response = clienteService.buscarPorId(id);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(clienteRepository, times(1)).findById(id);
    }

    @Test
    public void testBuscarPorIdClienteNoEncontrado() {
        Integer id = 1;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<ClienteDTO> response = clienteService.buscarPorId(id);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(clienteRepository, times(1)).findById(id);
    }

    @Test
    public void testGuardar() {
        ClienteDTO clienteDTO = new ClienteDTO();
        Cliente cliente = new Cliente();
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ResponseEntity<ClienteDTO> response = clienteService.guardar(clienteDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    public void testListarClientesPaginado() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Cliente> clientes = new PageImpl<>(List.of(new Cliente()));
        when(clienteRepository.findAll(pageable)).thenReturn(clientes);

        ResponseEntity<Page<ClienteDTO>> response = clienteService.listarClientes(pageable);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getContent().size());
        verify(clienteRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testEliminarCliente() {
        Integer id = 1;
        Cliente cliente = new Cliente();
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ResponseEntity<ClienteDTO> response = clienteService.eliminar(id);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    public void testEliminarClienteNoEncontrado() {
        Integer id = 1;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<ClienteDTO> response = clienteService.eliminar(id);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(clienteRepository, times(1)).findById(id);
    }

    @Test
    public void testBuscarPaginado() {
        Pageable pageable = PageRequest.of(0, 10);
        List<ClienteDTO> clientes = List.of(new ClienteDTO());
        ResponseEntity<Page<ClienteDTO>> response = clienteService.buscarPaginado(pageable, clientes);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    public void testActualizarCliente() {
        Integer id = 1;
        ClienteDTO clienteDTO = new ClienteDTO();
        Cliente clienteExistente = new Cliente();
        Cliente clienteActualizado = new Cliente();

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteActualizado);

        ResponseEntity<ClienteDTO> response = clienteService.actualizar(id, clienteDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    public void testActualizarClienteNoEncontrado() {
        Integer id = 1;
        ClienteDTO clienteDTO = new ClienteDTO();
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<ClienteDTO> response = clienteService.actualizar(id, clienteDTO);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(clienteRepository, times(1)).findById(id);
    }
}
