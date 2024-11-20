package jsges.nails;

import jsges.nails.DTO.Organizacion.ClienteDTO;
import jsges.nails.DTO.servicios.ServicioDTO;
import jsges.nails.domain.organizacion.Cliente;
import jsges.nails.domain.servicios.Servicio;
import jsges.nails.repository.servicios.ServicioRepository;
import jsges.nails.service.organizacion.IClienteService;
import jsges.nails.service.servicios.ServicioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioServiceTest {

    @Mock
    private ServicioRepository modelRepository;

    @Mock
    private IClienteService clienteService;

    @InjectMocks
    private ServicioService servicioService;

    private ServicioDTO servicioDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarNoEliminados() {

        Cliente cliente = new Cliente();
        cliente.setId(1);

        Servicio servicio = new Servicio();
        servicio.setId(1);
        servicio.setCliente(cliente);

        List<Servicio> servicios = List.of(servicio);
        when(modelRepository.buscarNoEliminados()).thenReturn(servicios);

        ResponseEntity<List<ServicioDTO>> response = servicioService.listarNoEliminados();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(modelRepository, times(1)).buscarNoEliminados();
    }

    @Test
    public void testBuscarPorId() {

        Cliente cliente = new Cliente();
        cliente.setId(1);

        Servicio servicio = new Servicio();
        servicio.setId(1);
        servicio.setCliente(cliente);

        when(modelRepository.findById(1)).thenReturn(java.util.Optional.of(servicio));

        ResponseEntity<ServicioDTO> response = servicioService.buscarPorId(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getId());
        verify(modelRepository, times(1)).findById(1);
    }

    @Test
    public void testGuardar() {
        Cliente cliente = new Cliente();
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1);

        when(clienteService.buscarPorId(1)).thenReturn(ResponseEntity.ok(clienteDTO));

        ServicioDTO servicioDTO = new ServicioDTO();
        servicioDTO.setFechaDocumento(LocalDateTime.now());

        Servicio servicio = new Servicio();
        servicio.setId(1);

        when(modelRepository.save(any(Servicio.class))).thenReturn(servicio);

        ResponseEntity<ServicioDTO> response = servicioService.guardar(servicioDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getId());

        verify(clienteService, times(1)).buscarPorId(1);
        verify(modelRepository, times(1)).save(any(Servicio.class));
    }

    @Test
    public void testGuardarBadRequest() {
        when(clienteService.buscarPorId(1)).thenReturn(ResponseEntity.notFound().build());

        ResponseEntity<ServicioDTO> response = servicioService.guardar(servicioDTO);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
        verify(clienteService, times(1)).buscarPorId(1);
    }

    @Test
    public void testListarServicios() {

        Cliente cliente = new Cliente();
        cliente.setId(1);

        Servicio servicio = new Servicio();
        servicio.setId(1);
        servicio.setCliente(cliente);

        Page<Servicio> servicioPage = new PageImpl<>(List.of(servicio));
        when(modelRepository.findAll(PageRequest.of(0, 10))).thenReturn(servicioPage);

        ResponseEntity<Page<ServicioDTO>> response = servicioService.listarServicios(PageRequest.of(0, 10));

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getContent().size());
        verify(modelRepository, times(1)).findAll(PageRequest.of(0, 10));
    }

    @Test
    public void testBuscarPagina() {

        servicioDTO = new ServicioDTO();
        servicioDTO.setId(1);
        servicioDTO.setFechaDocumento(LocalDateTime.now());

        List<ServicioDTO> servicios = List.of(servicioDTO);
        ResponseEntity<Page<ServicioDTO>> response = servicioService.buscarPagina(PageRequest.of(0, 10), servicios);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    public void testListarNoEliminadosConConsulta() {

        Cliente cliente = new Cliente();
        cliente.setId(1);

        Servicio servicio = new Servicio();
        servicio.setId(1);
        servicio.setCliente(cliente);

        List<Servicio> servicios = List.of(servicio);
        when(modelRepository.buscarNoEliminados("consulta")).thenReturn(servicios);

        ResponseEntity<List<ServicioDTO>> response = servicioService.listarNoEliminados("consulta");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(modelRepository, times(1)).buscarNoEliminados("consulta");
    }

    @Test
    public void testListarNoEliminadosEmpty() {
        when(modelRepository.buscarNoEliminados()).thenReturn(Collections.emptyList());

        ResponseEntity<List<ServicioDTO>> response = servicioService.listarNoEliminados();

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testBuscarPorIdNotFound() {
        when(modelRepository.findById(1)).thenReturn(java.util.Optional.empty());

        ResponseEntity<ServicioDTO> response = servicioService.buscarPorId(1);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
}
