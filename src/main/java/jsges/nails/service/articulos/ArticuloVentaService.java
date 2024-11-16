package jsges.nails.service.articulos;

import jsges.nails.DTO.articulos.ArticuloVentaDTO;
import jsges.nails.domain.articulos.ArticuloVenta;
import jsges.nails.domain.articulos.Linea;
import jsges.nails.mappers.ArticuloMapper;
import jsges.nails.repository.articulos.ArticuloVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class ArticuloVentaService implements IArticuloVentaService {
    @Autowired
    private ArticuloVentaRepository modelRepository;

    @Autowired
    private ILineaService lineaService;

    @Override
    public ResponseEntity<List<ArticuloVentaDTO>> listarNoEliminados() {
        List<ArticuloVenta> articulos = modelRepository.buscarNoEliminados();

        return ResponseEntity.ok(articulos.stream().map(ArticuloMapper::toDTO).toList());
    }

    @Override
    public ResponseEntity<ArticuloVentaDTO> buscarPorId(Integer id) {
        ArticuloVenta model = modelRepository.findById(id).orElse(null);

        if (model == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ArticuloMapper.toDTO(model));
    }

    @Override
    public ResponseEntity<ArticuloVentaDTO> guardar(ArticuloVentaDTO model) {
        Linea linea = lineaService.buscarEntidadPorId(model.linea);

        if (linea == null) {
            return ResponseEntity.notFound().build();
        }

        ArticuloVenta newModel = ArticuloMapper.toEntity(model, linea);
        return ResponseEntity.ok(ArticuloMapper.toDTO(modelRepository.save(newModel)));
    }

    @Override
    public ResponseEntity<ArticuloVentaDTO> eliminar(int id) {
        ArticuloVenta model = modelRepository.findById(id).orElse(null);

        if (model == null) {
            return ResponseEntity.notFound().build();
        }

        model.asEliminado();
        modelRepository.save(model);

        return ResponseEntity.ok(ArticuloMapper.toDTO(model));
    }

    @Override
    public ResponseEntity<List<ArticuloVentaDTO>> listarNoEliminados(String consulta) {
        List<ArticuloVenta> articulos = modelRepository.buscarNoEliminados(consulta);

        return ResponseEntity.ok(articulos.stream().map(ArticuloMapper::toDTO).toList());
    }

    @Override
    public ResponseEntity<Page<ArticuloVentaDTO>> listarPaginado(Pageable pageable) {
        Page<ArticuloVenta> articulos = modelRepository.findAll(pageable);

        return ResponseEntity.ok(articulos.map(ArticuloMapper::toDTO));
    }

    /**
     * Método para obtener una página de elementos de una lista dada.
     *
     * @param pageable Objeto Pageable que contiene la información de paginación,
     *                 como el número de página actual y el tamaño de página
     *                 deseado.
     * @param listado  Lista de elementos ArticuloVentaDTO que se desean paginar.
     * @return Una instancia de Page<ArticuloVentaDTO> que contiene la sublista de
     *         elementos correspondiente
     *         a la página solicitada, según el tamaño de página y el número de
     *         página especificados en pageable.
     */
    @Override
    public ResponseEntity<Page<ArticuloVentaDTO>> buscarPagina(Pageable pageable, List<ArticuloVentaDTO> listado) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<ArticuloVentaDTO> pageContent = (startItem >= listado.size())
                ? Collections.emptyList()
                : listado.subList(startItem, Math.min(startItem + pageSize, listado.size()));

        return ResponseEntity.ok(new PageImpl<>(pageContent, pageable, listado.size()));
    }

    @Override
    public ResponseEntity<ArticuloVentaDTO> actualizar(ArticuloVentaDTO model, Integer id) {
        ArticuloVenta articuloExistente = modelRepository.findById(id).orElse(null);

        if (articuloExistente == null) {
            return ResponseEntity.notFound().build();
        }
        Linea linea = lineaService.buscarEntidadPorId(model.linea);

        if (linea == null) {
            return ResponseEntity.notFound().build();
        }

        ArticuloVenta modelParaActualizar = ArticuloMapper.toEntity(model, linea);

        return ResponseEntity.ok(ArticuloMapper.toDTO(modelRepository.save(modelParaActualizar)));
    }
}
