package jsges.nails.service.servicios;
import jsges.nails.DTO.servicios.TipoServicioDTO;
import jsges.nails.domain.servicios.TipoServicio;
import jsges.nails.mappers.services.TipoServicioMapper;
import jsges.nails.repository.servicios.TipoServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoServicioService implements ITipoServicioService {

    @Autowired
    private TipoServicioRepository modelRepository;

    @Override
    public ResponseEntity<List<TipoServicioDTO>> listarNoEliminados() {
        List<TipoServicio> tipos = modelRepository.buscarNoEliminados();

        if (tipos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tipos.stream().map(TipoServicioMapper::toDTO).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<TipoServicioDTO> buscarPorId(Integer id) {
        TipoServicio tipoServicio = modelRepository.findById(id).orElse(null);

        if (tipoServicio == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(TipoServicioMapper.toDTO(tipoServicio));
    }



    @Override
    public ResponseEntity<TipoServicioDTO> guardar(TipoServicioDTO model) {
        TipoServicio tipoServicio = modelRepository.save(TipoServicioMapper.toEntity(model));

        return ResponseEntity.ok(TipoServicioMapper.toDTO(tipoServicio));
    }


    @Override
    public ResponseEntity<TipoServicioDTO> eliminar(Integer id) {
        TipoServicio tipoServicio = modelRepository.findById(id).orElse(null);

        if (tipoServicio == null) {
            return ResponseEntity.notFound().build();
        }

        tipoServicio.asEliminado();
        modelRepository.save(tipoServicio);

        return ResponseEntity.ok(TipoServicioMapper.toDTO(tipoServicio));
    }

    @Override
    public ResponseEntity<List<TipoServicioDTO>> listarNoEliminados(String consulta) {
        List<TipoServicio> tipoServicios = modelRepository.buscarNoEliminados(consulta);

        if (tipoServicios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tipoServicios.stream().map(TipoServicioMapper::toDTO).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<Page<TipoServicioDTO>> getTiposServicios(Pageable pageable) {
        Page<TipoServicio> tiposServicios = modelRepository.findAll(pageable);

        if (tiposServicios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tiposServicios.map(TipoServicioMapper::toDTO));
    }

    @Override
    public ResponseEntity<Page<TipoServicioDTO>> buscarPagina(Pageable pageable, List<TipoServicioDTO>lineas) {
        List<TipoServicio> tiposServicios = lineas.stream().map(TipoServicioMapper::toEntity).collect(Collectors.toList());
        Page<TipoServicio> tiposServiciosPage = new PageImpl<>(tiposServicios, pageable, tiposServicios.size());

        return ResponseEntity.ok(tiposServiciosPage.map(TipoServicioMapper::toDTO));        
    }

    @Override
    public ResponseEntity<TipoServicioDTO> actualizar(TipoServicioDTO model) {
        TipoServicio tipoServicio = modelRepository.findById(model.id).orElse(null);

        if (tipoServicio == null) {
            return ResponseEntity.notFound().build();
        }

        tipoServicio.setDenominacion(model.denominacion);

        modelRepository.save(tipoServicio);

        return ResponseEntity.ok(TipoServicioMapper.toDTO(tipoServicio));
    }
}
