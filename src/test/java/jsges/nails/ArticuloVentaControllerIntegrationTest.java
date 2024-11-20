package jsges.nails;

import com.fasterxml.jackson.databind.ObjectMapper;
import jsges.nails.DTO.articulos.ArticuloVentaDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
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

        // Simular una solicitud DELETE
        mockMvc.perform(delete("/nails/articuloEliminar/{id}", idArticulo)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idArticulo));
    }

    // Test para actualizar un artículo
    @Test
    void testActualizar() throws Exception {
        int idArticulo = 1;

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

    // Test para agregar un articulo
    @Test
    void testAgregar() throws Exception {
        // Paso 1: Preparar objeto a enviar
        ArticuloVentaDTO nuevoArticulo = new ArticuloVentaDTO();
        nuevoArticulo.setDenominacion("Articulo 3");
        nuevoArticulo.setLinea(3);

        // Paso 2: Ejecutar POST con el JSON
        mockMvc.perform(post("/nails/articulos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoArticulo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.denominacion").value("Articulo 3"))
                .andExpect(jsonPath("$.linea").value(3));
    }


}
