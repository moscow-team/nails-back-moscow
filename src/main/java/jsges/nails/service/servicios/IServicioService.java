package jsges.nails.service.servicios;

import jsges.nails.DTO.servicios.ServicioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IServicioService {
    public ResponseEntity<List<ServicioDTO>> listarNoEliminados();

    public ResponseEntity<ServicioDTO> buscarPorId(Integer id);

    public ResponseEntity<ServicioDTO> guardar(ServicioDTO model);

    public ResponseEntity<Page<ServicioDTO>> buscarPagina(Pageable pageable, List<ServicioDTO> servicios);

    public ResponseEntity<Page<ServicioDTO>> listarServicios(Pageable pageable);

    public ResponseEntity<List<ServicioDTO>> listarNoEliminados(String consulta);
}
