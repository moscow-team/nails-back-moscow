package jsges.nails;

import jsges.nails.domain.articulos.Linea;
import jsges.nails.repository.articulos.LineaRepository;
import jsges.nails.DTO.articulos.LineaDTO;
import jsges.nails.service.articulos.LineaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LineaServiceTest {

    @InjectMocks
    private LineaService lineaService;

    @Mock
    private LineaRepository lineaRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public  void testGuardar() {
        LineaDTO lineaDTO = new LineaDTO();
        lineaDTO.setId(1);
        lineaDTO.setDenominacion("Linea 1");

        Linea linea = new Linea();
        linea.setId(1);
        linea.setDenominacion("Linea 1");

        when(lineaRepository.save(any(Linea.class))).thenReturn(linea);

        LineaDTO result = lineaService.guardar(lineaDTO).getBody();

        assertNotNull(result);
        assertEquals("Linea 1", result.getDenominacion());
    }

    @Test
    public void testBuscarPorId() {
        Linea linea = new Linea();
        linea.setId(1);
        linea.setDenominacion("Linea 1");

        when(lineaRepository.findById(1)).thenReturn(Optional.of(linea));

        LineaDTO result = lineaService.buscarPorId(1).getBody();

        assertNotNull(result);
        assertEquals("Linea 1", result.getDenominacion());
    }

    @Test
    public void testEliminar() {
        Linea linea = new Linea();
        linea.setId(1);
        linea.setDenominacion("Linea 1");

        when(lineaRepository.findById(1)).thenReturn(Optional.of(linea));
        doNothing().when(lineaRepository).deleteById(1);

        ResponseEntity<LineaDTO> response= lineaService.eliminar(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}
