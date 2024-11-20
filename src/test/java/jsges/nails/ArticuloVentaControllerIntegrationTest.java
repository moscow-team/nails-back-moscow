package jsges.nails;

import com.fasterxml.jackson.databind.ObjectMapper;
import jsges.nails.DTO.articulos.ArticuloVentaDTO;
import jsges.nails.domain.articulos.ArticuloVenta;
import jsges.nails.domain.articulos.Linea;
import jsges.nails.repository.articulos.ArticuloVentaRepository;
import jsges.nails.repository.articulos.LineaRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ArticuloVentaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ArticuloVentaRepository articuloVentaRepository;

    @Autowired
    private LineaRepository lineaRepository;

    // Test para obtener todos los articulos
    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/nails/articulos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // Test para eliminar un artículo por ID
    @Test
    void testEliminar() throws Exception {
        int idArticulo = 1;
        this.setUp(idArticulo);

        // Simular una solicitud DELETE
        mockMvc.perform(delete("/nails/articuloEliminar/{id}", idArticulo)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idArticulo));
    }

    @Test
    void testEliminarNotFound() throws Exception {
        int idArticulo = 9999; // Articulo con ID que no existe

        // Simular una solicitud DELETE
        mockMvc.perform(delete("/nails/articuloEliminar/{id}", idArticulo)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // Test para actualizar un artículo
    @Test
    void testActualizar() throws Exception {
        int idArticulo = 1;

        this.setUp(idArticulo);
        // Crear el DTO con los datos actualizados
        ArticuloVentaDTO articuloActualizado = new ArticuloVentaDTO();
        articuloActualizado.setDenominacion("Articulo Actualizado");
        articuloActualizado.setLinea(2);

        // Simular una solicitud PUT
        mockMvc.perform(put("/nails/articulos/{id}", idArticulo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articuloActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.denominacion").value("Articulo Actualizado"))
                .andExpect(jsonPath("$.linea").value(2));
    }

    @Test
    void testActualizarBadRequestLinea() throws Exception {
        int idArticulo = 1;

        this.setUp(idArticulo);
        // Crear el DTO con los datos actualizados
        ArticuloVentaDTO articuloActualizado = new ArticuloVentaDTO();
        articuloActualizado.setDenominacion("Articulo Actualizado");
        articuloActualizado.setLinea(3);

        // Simular una solicitud PUT
        mockMvc.perform(put("/nails/articulos/{id}", idArticulo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articuloActualizado)))
                .andExpect(status().isBadRequest());
    }

    // Test para agregar un articulo
    @Test
    void testAgregar() throws Exception {
        // Paso 1: Preparar objeto a enviar
        this.setUp(1);
        ArticuloVentaDTO nuevoArticulo = new ArticuloVentaDTO();
        nuevoArticulo.setDenominacion("Articulo 3");
        nuevoArticulo.setLinea(1);

        // Paso 2: Ejecutar POST con el JSON
        mockMvc.perform(post("/nails/articulos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoArticulo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.denominacion").value("Articulo 3"))
                .andExpect(jsonPath("$.linea").value(1));
    }

    @Test
    void testAgregarBadRequest() throws Exception {
        // Paso 1: Preparar objeto a enviar
        this.setUp(1);
        ArticuloVentaDTO nuevoArticulo = new ArticuloVentaDTO();
        nuevoArticulo.setDenominacion("Articulo 3");
        nuevoArticulo.setLinea(3);

        // Paso 2: Ejecutar POST con el JSON
        mockMvc.perform(post("/nails/articulos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoArticulo)))
                .andExpect(status().isBadRequest());
    }

    private void setUp(int idArticulo) {
        Linea linea = new Linea();
        linea.setId(1);
        linea.setDenominacion("Linea 1");
        lineaRepository.save(linea);

        Linea linea2 = new Linea();
        linea2.setId(2);
        linea2.setDenominacion("Linea 2");
        lineaRepository.save(linea2);
        
        ArticuloVenta articuloVenta = new ArticuloVenta();
        articuloVenta.setId(idArticulo);
        articuloVenta.setDenominacion("Articulo 1");
        articuloVenta.setLinea(linea);

        articuloVentaRepository.save(articuloVenta);
    }
}
