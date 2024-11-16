package jsges.nails.controller.services;


import jsges.nails.DTO.servicios.TipoServicioDTO;
import jsges.nails.service.servicios.ITipoServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="${path_mapping}")
@CrossOrigin(value="${path_cross}")
public class TipoServicioController {
    @Autowired
    private ITipoServicioService modelService;

    public TipoServicioController() {

    }

    @GetMapping({"/tiposServicios"})
    public ResponseEntity<List<TipoServicioDTO>> getAll() {
        return this.modelService.listarNoEliminados();
    }

    @GetMapping({"/tiposServiciosPageQuery"})
    public ResponseEntity<Page<TipoServicioDTO>> getItems(@RequestParam(defaultValue = "") String consulta, @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "${max_page}") int size) {
        ResponseEntity<List<TipoServicioDTO>> listado = modelService.listarNoEliminados(consulta);

        return modelService.buscarPagina(PageRequest.of(page, size), listado.getBody());
    }


    @PostMapping("/tiposServicios")
    public  ResponseEntity<TipoServicioDTO>  agregar(@RequestBody TipoServicioDTO model){
        return modelService.guardar(model);
    }


    @PutMapping("/tipoServicioEliminar/{id}")
    public ResponseEntity<TipoServicioDTO> eliminar(@PathVariable Integer id){
        return modelService.eliminar(id);
    }

    @GetMapping("/tiposServicios/{id}")
    public ResponseEntity<TipoServicioDTO> getPorId(@PathVariable Integer id){
        return modelService.buscarPorId(id);
    }

    @PutMapping("/tiposServicios/{id}")
    public ResponseEntity<TipoServicioDTO> actualizar(@PathVariable Integer id,
                                                   @RequestBody TipoServicioDTO modelRecibido){
        modelRecibido.id = id;
        return modelService.actualizar(modelRecibido);
    }

}

