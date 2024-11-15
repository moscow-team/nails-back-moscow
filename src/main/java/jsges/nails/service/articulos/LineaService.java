package jsges.nails.service.articulos;

import jsges.nails.DTO.articulos.LineaDTO;
import jsges.nails.domain.articulos.Linea;
import jsges.nails.mappers.LineaMapper;
import jsges.nails.repository.articulos.LineaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LineaService implements ILineaService {

    @Autowired
    private LineaRepository modelRepository;

    /**
     * Listar todas las líneas no eliminadas.
     *
     * @return Lista de líneas activas.
     */
    @Override
    public ResponseEntity<List<LineaDTO>> listarNoEliminados() {
        List<Linea> lineas = modelRepository.buscarNoEliminados();
        return ResponseEntity.ok(lineas.stream().map(LineaMapper::toDTO).toList());
    }

    /**
     * Buscar línea por ID.
     *
     * @param id ID de la línea.
     * @return Línea encontrada o respuesta 404 si no se encuentra.
     */
    @Override
    public ResponseEntity<LineaDTO> buscarPorId(Integer id) {
        Linea linea = modelRepository.findById(id).orElse(null);

        if (linea == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(LineaMapper.toDTO(linea));
    }

    /**
     * Guardar una nueva línea.
     *
     * @param modelDTO DTO de la línea a guardar.
     * @return DTO de la línea guardada.
     */
    @Override
    public ResponseEntity<LineaDTO> guardar(LineaDTO modelDTO) {
        Linea linea = LineaMapper.toEntity(modelDTO);
        return ResponseEntity.ok(LineaMapper.toDTO(modelRepository.save(linea)));
    }

    /**
     * Eliminar una línea marcándola como eliminada.
     *
     * @param id ID de la línea a eliminar.
     * @return DTO de la línea eliminada o respuesta 404 si no se encuentra.
     */
    @Override
    public ResponseEntity<LineaDTO> eliminar(Integer id) {
        Linea linea = modelRepository.findById(id).orElse(null);

        if (linea == null) {
            return ResponseEntity.notFound().build();
        }

        linea.asEliminado();
        modelRepository.save(linea);

        return ResponseEntity.ok(LineaMapper.toDTO(linea));
    }

    /**
     * Listar todas las líneas no eliminadas con consulta específica.
     *
     * @param consulta Cadena de consulta para filtrar las líneas.
     * @return Lista de líneas filtradas.
     */
    @Override
    public ResponseEntity<List<LineaDTO>> listarNoEliminados(String consulta) {
        List<Linea> lineas = modelRepository.buscarNoEliminados(consulta);
        return ResponseEntity.ok(lineas.stream().map(LineaMapper::toDTO).toList());
    }

    /**
     * Listar todas las líneas paginadas.
     *
     * @param pageable Información de paginación.
     * @return Página de líneas.
     */
    @Override
    public ResponseEntity<Page<LineaDTO>> listarPaginado(Pageable pageable) {
        Page<Linea> lineas = modelRepository.findAll(pageable);
        return ResponseEntity.ok(lineas.map(LineaMapper::toDTO));
    }

    /**
     * Buscar líneas exactas por consulta.
     *
     * @param consulta Consulta exacta para buscar líneas.
     * @return Lista de líneas encontradas.
     */
    public ResponseEntity<List<LineaDTO>> buscar(String consulta) {
        List<Linea> lineas = modelRepository.buscarExacto(consulta);
        return ResponseEntity.ok(lineas.stream().map(LineaMapper::toDTO).toList());
    }

    /**
     * Obtener una página de elementos de una lista dada.
     *
     * @param pageable Objeto Pageable que contiene la información de paginación.
     * @param lineas   Lista de elementos LineaDTO que se desean paginar.
     * @return Página de LineaDTO con la sublista correspondiente.
     */
    @Override
    public ResponseEntity<Page<LineaDTO>> buscarPagina(Pageable pageable, List<LineaDTO> lineas) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<LineaDTO> pageContent = (startItem >= lineas.size())
                ? Collections.emptyList()
                : lineas.subList(startItem, Math.min(startItem + pageSize, lineas.size()));

        return ResponseEntity.ok(new PageImpl<>(pageContent, pageable, lineas.size()));
    }

    /**
     * Actualizar una línea existente.
     *
     * @param modelDTO DTO de la línea a actualizar.
     * @param id       ID de la línea a actualizar.
     * @return DTO de la línea actualizada o respuesta 404 si no se encuentra.
     */
    @Override
    public ResponseEntity<LineaDTO> actualizar(LineaDTO modelDTO, Integer id) {
        Linea lineaExistente = modelRepository.findById(id).orElse(null);

        if (lineaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        Linea linea = LineaMapper.toEntity(modelDTO);
        return ResponseEntity.ok(LineaMapper.toDTO(modelRepository.save(linea)));
    }

    @Override
    public Linea buscarEntidadPorId(Integer id) {
        return modelRepository.findById(id).orElse(null);
    }
}
