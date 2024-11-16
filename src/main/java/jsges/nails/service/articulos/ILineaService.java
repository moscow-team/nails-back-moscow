package jsges.nails.service.articulos;

import jsges.nails.DTO.articulos.LineaDTO;
import jsges.nails.domain.articulos.Linea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ILineaService {

    public ResponseEntity<List<LineaDTO>> listarNoEliminados();

    public ResponseEntity<LineaDTO> buscarPorId(Integer id);

    public Linea buscarEntidadPorId(Integer id);

    public ResponseEntity<LineaDTO> guardar(LineaDTO model);

    public ResponseEntity<LineaDTO> eliminar(Integer id);

    public ResponseEntity<List<LineaDTO>> listarNoEliminados(String consulta);

    public ResponseEntity<Page<LineaDTO>> listarPaginado(Pageable pageable);

    public ResponseEntity<Page<LineaDTO>> buscarPagina(Pageable pageable, List<LineaDTO> lineas);

    public ResponseEntity<List<LineaDTO>> buscar(String consulta);

    public ResponseEntity<LineaDTO> actualizar(LineaDTO model, Integer id);
}
