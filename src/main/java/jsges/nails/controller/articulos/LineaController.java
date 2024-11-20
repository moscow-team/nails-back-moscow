package jsges.nails.controller.articulos;

import jsges.nails.DTO.articulos.LineaDTO;
import jsges.nails.service.articulos.ILineaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "${path_mapping}")
@CrossOrigin(value = "${path_cross}")
public class LineaController {
    @Autowired
    private ILineaService modelService;

    public LineaController() {
    }

    @GetMapping({ "/lineas" })
    public ResponseEntity<List<LineaDTO>> getAll() {
        return modelService.listarNoEliminados();
    }

    @GetMapping({ "/lineasPageQuery" })
    public ResponseEntity<Page<LineaDTO>> getItems(@RequestParam(defaultValue = "") String consulta,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "${max_page}") int size) {
        ResponseEntity<List<LineaDTO>> listado = modelService.listarNoEliminados(consulta);

        ResponseEntity<Page<LineaDTO>> bookPage = modelService.buscarPagina(PageRequest.of(page, size),
                listado.getBody());
        return bookPage;
    }

    @PostMapping("/linea")
    public ResponseEntity<LineaDTO> agregar(@RequestBody LineaDTO model) {
        return modelService.guardar(model);
    }

    @DeleteMapping("/lineaEliminar/{id}")
    public ResponseEntity<LineaDTO> eliminar(@PathVariable Integer id) {
        return modelService.eliminar(id);
    }

    @GetMapping("/linea/{id}")
    public ResponseEntity<LineaDTO> getPorId(@PathVariable Integer id) {
        return modelService.buscarPorId(id);
    }

    @PutMapping("/linea/{id}")
    public ResponseEntity<LineaDTO> actualizar(@PathVariable Integer id,
            @RequestBody LineaDTO modelRecibido) {
        return modelService.actualizar(modelRecibido, id);
    }
}
