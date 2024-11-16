package jsges.nails.service.servicios;

import jsges.nails.DTO.servicios.TipoServicioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITipoServicioService {

    public ResponseEntity<List<TipoServicioDTO>> listarNoEliminados();

    public ResponseEntity<TipoServicioDTO> buscarPorId(Integer id);

    public ResponseEntity<TipoServicioDTO> guardar(TipoServicioDTO model);

    public ResponseEntity<TipoServicioDTO> actualizar(TipoServicioDTO model);

    public ResponseEntity<TipoServicioDTO> eliminar(Integer id);

    public ResponseEntity<List<TipoServicioDTO>> listarNoEliminados(String consulta);

    public ResponseEntity<Page<TipoServicioDTO>> getTiposServicios(Pageable pageable);

    public ResponseEntity<Page<TipoServicioDTO>> buscarPagina(Pageable pageable, List<TipoServicioDTO> tipoServicios);
}
