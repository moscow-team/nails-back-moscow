package jsges.nails;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import jsges.nails.DTO.servicios.TipoServicioDTO;
import jsges.nails.domain.servicios.TipoServicio;
import jsges.nails.mappers.services.TipoServicioMapper;
import jsges.nails.repository.servicios.TipoServicioRepository;
import jsges.nails.service.servicios.TipoServicioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class TipoServicioServiceTest {

    @Mock
    private TipoServicioRepository modelRepository;

    @InjectMocks
    private TipoServicioService tipoServicioService;

    private TipoServicio tipoServicio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        tipoServicio = new TipoServicio();
        tipoServicio.setId(1);
        tipoServicio.setDenominacion("Servicio 1");
    }

    // Test para listarNoEliminados
    @Test
    public void testListarNoEliminados() {
        when(modelRepository.buscarNoEliminados()).thenReturn(List.of(tipoServicio));

        ResponseEntity<List<TipoServicioDTO>> response = tipoServicioService.listarNoEliminados();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(modelRepository, times(1)).buscarNoEliminados();
    }

    @Test
    public void testListarNoEliminadosEmpty() {
        when(modelRepository.buscarNoEliminados()).thenReturn(List.of());

        ResponseEntity<List<TipoServicioDTO>> response = tipoServicioService.listarNoEliminados();

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    // Test para buscarPorId
    @Test
    public void testBuscarPorId() {
        when(modelRepository.findById(1)).thenReturn(java.util.Optional.of(tipoServicio));

        ResponseEntity<TipoServicioDTO> response = tipoServicioService.buscarPorId(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getId());
        verify(modelRepository, times(1)).findById(1);
    }

    @Test
    public void testBuscarPorIdNotFound() {
        when(modelRepository.findById(1)).thenReturn(java.util.Optional.empty());

        ResponseEntity<TipoServicioDTO> response = tipoServicioService.buscarPorId(1);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    // Test para guardar
    @Test
    public void testGuardar() {
        TipoServicioDTO tipoServicioDTO = new TipoServicioDTO();
        tipoServicioDTO.setId(1);
        tipoServicioDTO.setDenominacion("Nuevo Servicio");

        when(modelRepository.save(any(TipoServicio.class))).thenReturn(tipoServicio);

        ResponseEntity<TipoServicioDTO> response = tipoServicioService.guardar(tipoServicioDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getId());
        verify(modelRepository, times(1)).save(any(TipoServicio.class));
    }

    // Test para eliminar
    @Test
    public void testEliminar() {
        when(modelRepository.findById(1)).thenReturn(java.util.Optional.of(tipoServicio));

        ResponseEntity<TipoServicioDTO> response = tipoServicioService.eliminar(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(modelRepository, times(1)).findById(1);
        verify(modelRepository, times(1)).save(tipoServicio);
    }

    @Test
    public void testEliminarNotFound() {
        when(modelRepository.findById(1)).thenReturn(java.util.Optional.empty());

        ResponseEntity<TipoServicioDTO> response = tipoServicioService.eliminar(1);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    // Test para listarNoEliminados con consulta
    @Test
    public void testListarNoEliminadosConConsulta() {
        when(modelRepository.buscarNoEliminados("consulta")).thenReturn(List.of(tipoServicio));

        ResponseEntity<List<TipoServicioDTO>> response = tipoServicioService.listarNoEliminados("consulta");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(modelRepository, times(1)).buscarNoEliminados("consulta");
    }

    @Test
    public void testListarNoEliminadosConConsultaEmpty() {
        when(modelRepository.buscarNoEliminados("consulta")).thenReturn(List.of());

        ResponseEntity<List<TipoServicioDTO>> response = tipoServicioService.listarNoEliminados("consulta");

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    // Test para getTiposServicios (paginación)
    @Test
    public void testGetTiposServicios() {
        Page<TipoServicio> tipoServiciosPage = new PageImpl<>(List.of(tipoServicio));

        when(modelRepository.findAll(any(Pageable.class))).thenReturn(tipoServiciosPage);

        ResponseEntity<Page<TipoServicioDTO>> response = tipoServicioService.getTiposServicios(PageRequest.of(0, 10));

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getContent().size());
        verify(modelRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void testGetTiposServiciosEmpty() {
        Page<TipoServicio> tipoServiciosPage = Page.empty();

        when(modelRepository.findAll(any(Pageable.class))).thenReturn(tipoServiciosPage);

        ResponseEntity<Page<TipoServicioDTO>> response = tipoServicioService.getTiposServicios(PageRequest.of(0, 10));

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    // Test para actualizar
    @Test
    public void testActualizar() {
        TipoServicioDTO tipoServicioDTO = new TipoServicioDTO();
        tipoServicioDTO.setId(1);
        tipoServicioDTO.setDenominacion("Servicio Actualizado");

        when(modelRepository.findById(1)).thenReturn(java.util.Optional.of(tipoServicio));
        when(modelRepository.save(any(TipoServicio.class))).thenReturn(tipoServicio);

        ResponseEntity<TipoServicioDTO> response = tipoServicioService.actualizar(tipoServicioDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Servicio Actualizado", response.getBody().getDenominacion());
        verify(modelRepository, times(1)).findById(1);
        verify(modelRepository, times(1)).save(any(TipoServicio.class));
    }

    @Test
    public void testActualizarNotFound() {
        TipoServicioDTO tipoServicioDTO = new TipoServicioDTO();
        tipoServicioDTO.setId(1);
        tipoServicioDTO.setDenominacion("Servicio Actualizado");

        when(modelRepository.findById(1)).thenReturn(java.util.Optional.empty());

        ResponseEntity<TipoServicioDTO> response = tipoServicioService.actualizar(tipoServicioDTO);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
}
