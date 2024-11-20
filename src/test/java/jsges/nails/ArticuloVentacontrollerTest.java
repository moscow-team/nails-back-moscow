package jsges.nails;

import jsges.nails.DTO.articulos.ArticuloVentaDTO;
import jsges.nails.controller.articulos.ArticuloVentaController;
import jsges.nails.service.articulos.IArticuloVentaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArticuloVentacontrollerTest {

    @InjectMocks
    private ArticuloVentaController articuloVentaController;

    @Mock
    private IArticuloVentaService articuloVentaService;

    @Test
    void testGetAll() {
        // Paso 1: Preparar datos de prueba
        ArticuloVentaDTO articulo1 = new ArticuloVentaDTO();
        articulo1.setId(1);
        articulo1.setDenominacion("Articulo 1");
        articulo1.setLinea(1);

        ArticuloVentaDTO articulo2 = new ArticuloVentaDTO();
        articulo2.setId(2);
        articulo2.setDenominacion("Articulo 2");
        articulo2.setLinea(2);

        List<ArticuloVentaDTO> mockList = List.of(articulo1, articulo2);

        // Paso 2: Configurar comportamiento del mock
        when(articuloVentaService.listarNoEliminados()).thenReturn(ResponseEntity.ok(mockList));

        // Paso 3: Ejecutar el metodo del controlador
        ResponseEntity<List<ArticuloVentaDTO>> response = articuloVentaController.getAll();

        // Paso 4: Verificar el resultado
        assertNotNull(response, "La respuesta no debe ser nula");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "El código de estado debe ser 200");
        assertEquals(mockList, response.getBody(), "El cuerpo de la respuesta debe contener la lista simulada.");

        // Paso 5: Verificar que el servicio fue invocado una vez
        verify(articuloVentaService, times(1)).listarNoEliminados();
    }

    @Test
    void testAgregar() {
        // Paso 1: Preparar datos de prueba
        ArticuloVentaDTO nuevoArticulo = new ArticuloVentaDTO();
        nuevoArticulo.setDenominacion("Producto Test");
        nuevoArticulo.setLinea(1);

        // Configurar respuesta simulada
        when(articuloVentaService.guardar(nuevoArticulo)).thenReturn(ResponseEntity.ok(nuevoArticulo));

        // Paso 2: Llamar al metodo del controlador
        ResponseEntity<ArticuloVentaDTO> response = articuloVentaController.agregar(nuevoArticulo);

        // Paso 3: Verificar resultados
        assertNotNull(response, "La respuesta no debe ser nula");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "El código de estado debe ser 200");
        assertEquals(nuevoArticulo, response.getBody(), "El cuerpo de la respuesta debe contener el artículo agregado");

        // Paso 4: Verificar que el servicio fue invocado correctamente
        verify(articuloVentaService, times(1)).guardar(nuevoArticulo);
    }

    @Test
    void testEliminar() {
        // Paso 1: Configurar comportamiento del mock
        Integer idArticulo = 1;
        ArticuloVentaDTO articuloEliminado = new ArticuloVentaDTO();
        articuloEliminado.setId(idArticulo);
        articuloEliminado.setDenominacion("Producto Eliminado");

        when(articuloVentaService.eliminar(idArticulo)).thenReturn(ResponseEntity.ok(articuloEliminado));

        // Paso 2: Llamar al metodo del controlador
        ResponseEntity<ArticuloVentaDTO> response = articuloVentaController.eliminar(idArticulo);

        // Paso 3: Verificar resultados
        assertNotNull(response, "La respuesta no debe ser nula");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "El código de estado debe ser 200");
        assertEquals(articuloEliminado, response.getBody(), "El cuerpo de la respuesta debe contener el artículo eliminado");

        // Paso 4: Verificar interacción con el mock
        verify(articuloVentaService, times(1)).eliminar(idArticulo);
    }

    @Test
    void testActualizar() {
        // Paso 1: Preparar datos de entrada y salida simulados
        Integer idArticulo = 1;
        ArticuloVentaDTO articuloActualizado = new ArticuloVentaDTO();
        articuloActualizado.setId(idArticulo);
        articuloActualizado.setDenominacion("Producto Actualizado");
        articuloActualizado.setLinea(4);

        // Simular la respuesta del servicio al actualizar
        when(articuloVentaService.actualizar(articuloActualizado, idArticulo)).thenReturn(ResponseEntity.ok(articuloActualizado));

        // Paso 2: Ejecutar el método del controlador
        ResponseEntity<ArticuloVentaDTO> response = articuloVentaController.actualizar(idArticulo, articuloActualizado);

        // Paso 3: Verificar resultados
        assertNotNull(response, "La respuesta no debe ser nula");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "El código de estado debe ser 200");
        assertEquals(articuloActualizado, response.getBody(), "El cuerpo de la respuesta debe contener el artículo actualizado");

        // Paso 4: Verificar interacción con el mock
        verify(articuloVentaService, times(1)).actualizar(articuloActualizado, idArticulo);
    }

}
