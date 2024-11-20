package jsges.nails;

import jsges.nails.domain.servicios.ItemServicio;
import jsges.nails.repository.servicios.ItemServicioRepository;
import jsges.nails.service.servicios.ItemServicioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemServicioServiceTest {

    @Mock
    private ItemServicioRepository modelRepository;

    @InjectMocks
    private ItemServicioService itemServicioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListar() {
        List<ItemServicio> items = List.of(new ItemServicio());
        when(modelRepository.findAll()).thenReturn(items);

        List<ItemServicio> result = itemServicioService.listar();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(modelRepository, times(1)).findAll();
    }

    @Test
    public void testGuardar() {
        ItemServicio itemServicio = new ItemServicio();
        when(modelRepository.save(any(ItemServicio.class))).thenReturn(itemServicio);

        ItemServicio result = itemServicioService.guardar(itemServicio);

        assertNotNull(result);
        verify(modelRepository, times(1)).save(any(ItemServicio.class));
    }

    @Test
    public void testBuscarPorServicio() {
        Integer idServicio = 1;
        List<ItemServicio> items = List.of(new ItemServicio());
        when(modelRepository.buscarPorServicio(idServicio)).thenReturn(items);

        List<ItemServicio> result = itemServicioService.buscarPorServicio(idServicio);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(modelRepository, times(1)).buscarPorServicio(idServicio);
    }
}
