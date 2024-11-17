package jsges.nails.controller.organizacion;

import jsges.nails.DTO.Organizacion.ClienteDTO;
import jsges.nails.service.organizacion.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value="${path_mapping}")
@CrossOrigin(value="${path_cross}")

public class ClienteControlador {

    @Autowired
    private IClienteService clienteServicio;


    public ClienteControlador() {
    }

    @GetMapping({"/clientes"})
    public ResponseEntity<List<ClienteDTO>> listar() {
        return this.clienteServicio.listar();
    }

    @GetMapping({"/clientesPageQuery"})
    public ResponseEntity<Page<ClienteDTO>> getItems(@RequestParam(defaultValue = "") String consulta,@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "${max_page}") int size) {
        ResponseEntity<List<ClienteDTO>> listado = clienteServicio.listar(consulta);

        return clienteServicio.buscarPaginado(PageRequest.of(page, size),listado.getBody());
    }


    @PostMapping("/clientes")
    public ResponseEntity<ClienteDTO> agregar(@RequestBody ClienteDTO cliente){
        return clienteServicio.guardar(cliente);
    }


    @PutMapping("/clienteEliminar/{id}")
    public ResponseEntity<ClienteDTO> eliminar(@PathVariable Integer id){
        return clienteServicio.eliminar(id);
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Integer id){
        return clienteServicio.buscarPorId(id);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<ClienteDTO> actualizar(@PathVariable Integer id,
                                              @RequestBody ClienteDTO modelRecibido){
        return clienteServicio.actualizar(id, modelRecibido);
    }

}
