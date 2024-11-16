package jsges.nails.controller.services;

import jsges.nails.DTO.servicios.ServicioDTO;
import jsges.nails.service.servicios.IServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "${path_mapping}")
@CrossOrigin(value = "${path_cross}")
public class ServicioController {
    @Autowired
    private IServicioService modelService;

    public ServicioController() {

    }

    @GetMapping({ "/servicios" })
    public ResponseEntity<List<ServicioDTO>> getAll() {
        return modelService.listarNoEliminados();
    }

    @GetMapping("/servicio/{id}")
    public ResponseEntity<ServicioDTO> getPorId(@PathVariable Integer id) {
        return modelService.buscarPorId(id);
    }

    @GetMapping({ "/serviciosPageQuery" })
    public ResponseEntity<Page<ServicioDTO>> getItems(@RequestParam(defaultValue = "") String consulta,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "${max_page}") int size) {
        ResponseEntity<List<ServicioDTO>> listado = modelService.listarNoEliminados(consulta);

        return modelService.buscarPagina(PageRequest.of(page, size), listado.getBody());
    }

    @PostMapping("/servicios")
    public ResponseEntity<ServicioDTO> agregar(@RequestBody ServicioDTO model) {
        return modelService.guardar(model);
    }
}
