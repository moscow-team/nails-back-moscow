package jsges.nails;

import jsges.nails.DTO.articulos.ArticuloVentaDTO;
import jsges.nails.domain.articulos.ArticuloVenta;
import jsges.nails.domain.articulos.Linea;
import jsges.nails.repository.articulos.ArticuloVentaRepository;
import jsges.nails.service.articulos.ArticuloVentaService;
import jsges.nails.service.articulos.ILineaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ArticuloVentaServiceTest {

    @Mock
    private ArticuloVentaRepository modelRepository;

    @Mock
    private ILineaService lineaService;

    @InjectMocks
    private ArticuloVentaService articuloVentaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarNoEliminados() {

        Linea linea = new Linea();
        linea.setId(1);

        ArticuloVenta articulo = new ArticuloVenta();
        articulo.setLinea(linea);

        List<ArticuloVenta> articuloList = List.of(articulo);
        when(modelRepository.buscarNoEliminados()).thenReturn(articuloList);

        ResponseEntity<List<ArticuloVentaDTO>> response = articuloVentaService.listarNoEliminados();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(modelRepository, times(1)).buscarNoEliminados();
    }

    @Test
    public void testBuscarPorIdFound() {
        Integer id = 1;

        Linea linea = new Linea();
        linea.setId(1);

        ArticuloVenta articulo = new ArticuloVenta();
        articulo.setLinea(linea);

        when(modelRepository.findById(id)).thenReturn(Optional.of(articulo));

        ResponseEntity<ArticuloVentaDTO> response = articuloVentaService.buscarPorId(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(modelRepository, times(1)).findById(id);
    }

    @Test
    public void testBuscarPorIdNotFound() {
        Integer id = 1;
        when(modelRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<ArticuloVentaDTO> response = articuloVentaService.buscarPorId(id);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(modelRepository, times(1)).findById(id);
    }

    @Test
    public void testGuardar() {
        ArticuloVentaDTO articuloDTO = new ArticuloVentaDTO();
        articuloDTO.setLinea(1);

        Linea linea = new Linea();
        linea.setId(1);

        ArticuloVenta articuloVenta = new ArticuloVenta();
        articuloVenta.setLinea(linea);

        when(lineaService.buscarEntidadPorId(articuloDTO.getLinea())).thenReturn(linea);
        when(modelRepository.save(any(ArticuloVenta.class))).thenReturn(articuloVenta);

        ResponseEntity<ArticuloVentaDTO> response = articuloVentaService.guardar(articuloDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(lineaService, times(1)).buscarEntidadPorId(articuloDTO.getLinea());
        verify(modelRepository, times(1)).save(any(ArticuloVenta.class));
    }

    @Test
    public void testGuardarLineaNotFound() {
        ArticuloVentaDTO articuloDTO = new ArticuloVentaDTO();
        articuloDTO.setLinea(1);

        when(lineaService.buscarEntidadPorId(articuloDTO.getLinea())).thenReturn(null);

        ResponseEntity<ArticuloVentaDTO> response = articuloVentaService.guardar(articuloDTO);

        assertNotNull(response);
        assertEquals(404, response.getStatusCode());
        verify(lineaService, times(1)).buscarEntidadPorId(articuloDTO.getLinea());
        verify(modelRepository, times(0)).save(any(ArticuloVenta.class));
    }

    @Test
    public void testEliminar() {
        Integer id = 1;

        Linea linea = new Linea();
        linea.setId(1);

        ArticuloVenta articulo = new ArticuloVenta();
        articulo.setLinea(linea);

        when(modelRepository.findById(id)).thenReturn(Optional.of(articulo));

        ResponseEntity<ArticuloVentaDTO> response = articuloVentaService.eliminar(id);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        verify(modelRepository, times(1)).findById(id);
        verify(modelRepository, times(1)).save(articulo);
    }

    @Test
    public void testEliminarNotFound() {
        Integer id = 1;
        when(modelRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<ArticuloVentaDTO> response = articuloVentaService.eliminar(id);

        assertNotNull(response);
        assertEquals(404, response.getStatusCode());
        verify(modelRepository, times(1)).findById(id);
        verify(modelRepository, times(0)).save(any(ArticuloVenta.class));
    }

    @Test
    public void testActualizarArticuloExistente() {
        Integer id = 1;
        ArticuloVentaDTO articuloDTO = new ArticuloVentaDTO();
        articuloDTO.setLinea(1);

        Linea linea = new Linea();
        linea.setId(1);

        ArticuloVenta articuloExistente = new ArticuloVenta();
        articuloExistente.setLinea(linea);

        // Simulamos que el articulo ya existe en la BD
        when(modelRepository.findById(id)).thenReturn(Optional.of(articuloExistente));
        when(lineaService.buscarEntidadPorId(articuloDTO.getLinea())).thenReturn(linea);
        when(modelRepository.save(any(ArticuloVenta.class))).thenReturn(articuloExistente);

        ResponseEntity<ArticuloVentaDTO> response = articuloVentaService.actualizar(articuloDTO, id);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        verify(modelRepository, times(1)).findById(id);
        verify(lineaService, times(1)).buscarEntidadPorId(articuloDTO.getLinea());
        verify(modelRepository, times(1)).save(any(ArticuloVenta.class));
    }

    @Test
    void testActualizarArticuloNoExistente() {
        Integer id = 1;
        ArticuloVentaDTO articuloDTO = new ArticuloVentaDTO();
        articuloDTO.setLinea(1);  // Aseguramos que el DTO tenga un valor válido para la línea

        // Simulamos que el artículo no existe en la base de datos
        when(modelRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<ArticuloVentaDTO> response = articuloVentaService.actualizar(articuloDTO, id);

        assertNotNull(response);
        assertEquals(404, response.getStatusCode());  // Debería devolver un 404 si el artículo no existe
        verify(modelRepository, times(1)).findById(id);  // Verificamos que se haya buscado el artículo
        verify(lineaService, times(0)).buscarEntidadPorId(anyInt());  // No debe llamar a buscarEntidadPorId si el artículo no existe
        verify(modelRepository, times(0)).save(any(ArticuloVenta.class));  // No debe intentar guardar nada si el artículo no existe
    }

    @Test
    public void testListarPaginado() {
        Pageable pageable = PageRequest.of(0, 10);

        Linea linea = new Linea();
        linea.setId(1);

        ArticuloVenta articulo = new ArticuloVenta();
        articulo.setLinea(linea);

        Page<ArticuloVenta> page = new PageImpl<>(List.of(articulo));

        when(modelRepository.findAll(pageable)).thenReturn(page);

        ResponseEntity<Page<ArticuloVentaDTO>> response = articuloVentaService.listarPaginado(pageable);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        assertEquals(1, response.getBody().getContent().size());
        verify(modelRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testBuscarPagina() {
        Pageable pageable = PageRequest.of(0,  2);
        List<ArticuloVentaDTO> listado = List.of(
                new ArticuloVentaDTO(),
                new ArticuloVentaDTO(),
                new ArticuloVentaDTO()
        );

        ResponseEntity<Page<ArticuloVentaDTO>> response = articuloVentaService.buscarPagina(pageable, listado);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        assertEquals(2, response.getBody().getContent().size()); // 2 elementos en la primer pagina
        assertEquals(3, response.getBody().getTotalElements()); // 3 elementos en total
        assertEquals(2, response.getBody().getTotalPages()); // 2 paginas en total
    }

}
