package jsges.nails.service.articulos;

import jsges.nails.DTO.articulos.ArticuloVentaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IArticuloVentaService {

    public ResponseEntity<List<ArticuloVentaDTO>> listarNoEliminados();

    public ResponseEntity<ArticuloVentaDTO> buscarPorId(Integer id);

    public ResponseEntity<ArticuloVentaDTO> guardar(ArticuloVentaDTO model);

    public ResponseEntity<ArticuloVentaDTO> eliminar(int id);

    public ResponseEntity<List<ArticuloVentaDTO>> listarNoEliminados(String consulta);

    public ResponseEntity<Page<ArticuloVentaDTO>> listarPaginado(Pageable pageable);

    public ResponseEntity<Page<ArticuloVentaDTO>> buscarPagina(Pageable pageable, List<ArticuloVentaDTO> list);

    public ResponseEntity<ArticuloVentaDTO> actualizar(ArticuloVentaDTO model, Integer id);
}
