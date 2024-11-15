package jsges.nails.controller.articulos;

import jsges.nails.DTO.articulos.ArticuloVentaDTO;
import jsges.nails.service.articulos.IArticuloVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "${path_mapping}")
@CrossOrigin(value = "${path_cross}")
public class ArticuloVentaController {
    @Autowired
    private IArticuloVentaService modelService;

    public ArticuloVentaController() {
    }

    @GetMapping({ "/articulos" })
    public ResponseEntity<List<ArticuloVentaDTO>> getAll() {
        return modelService.listarNoEliminados();
    }

    @GetMapping({ "/articulosPageQuery" })
    public ResponseEntity<Page<ArticuloVentaDTO>> getItems(@RequestParam(defaultValue = "") String consulta,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "${max_page}") int size) {
        ResponseEntity<List<ArticuloVentaDTO>> listadoDTO = modelService.listarNoEliminados(consulta);

        return modelService.buscarPagina(PageRequest.of(page, size), listadoDTO.getBody());
    }

    @PostMapping("/articulos")
    public ResponseEntity<ArticuloVentaDTO> agregar(@RequestBody ArticuloVentaDTO model) {
        return modelService.guardar(model);
    }

    @DeleteMapping("/articuloEliminar/{id}")
    public ResponseEntity<ArticuloVentaDTO> eliminar(@PathVariable Integer id) {
        return modelService.eliminar(id);
    }

    @GetMapping("/articulos/{id}")
    public ResponseEntity<ArticuloVentaDTO> getPorId(@PathVariable Integer id) {
        return modelService.buscarPorId(id);
    }

    @PutMapping("/articulos/{id}")
    public ResponseEntity<ArticuloVentaDTO> actualizar(@PathVariable Integer id,
            @RequestBody ArticuloVentaDTO modelRecibido) {
        return modelService.actualizar(modelRecibido, id);
    }
}
