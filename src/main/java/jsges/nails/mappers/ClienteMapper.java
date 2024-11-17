package jsges.nails.mappers;

import jsges.nails.DTO.Organizacion.ClienteDTO;
import jsges.nails.domain.organizacion.Cliente;

public class ClienteMapper {
    public static Cliente toEntity(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setRazonSocial(dto.getRazonSocial());
        cliente.setLetra(dto.getLetra());
        cliente.setContacto(dto.getContacto());
        cliente.setCelular(dto.getCelular());
        cliente.setMail(dto.getMail());
        cliente.setFechaInicio(dto.getFechaInicio());
        cliente.setFechaNacimiento(dto.getFechaNacimiento());
        // Map other fields as necessary

        return cliente;
    }

    public static ClienteDTO toDTO(Cliente entity) {
        if (entity == null) {
            return null;
        }

        ClienteDTO dto = new ClienteDTO();
        dto.setId(entity.getId());
        dto.setRazonSocial(entity.getRazonSocial());
        dto.setLetra(entity.getLetra());
        dto.setContacto(entity.getContacto());
        dto.setCelular(entity.getCelular());
        dto.setMail(entity.getMail());
        dto.setFechaInicio(entity.getFechaInicio());
        dto.setFechaNacimiento(entity.getFechaNacimiento());
        // Map other fields as necessary

        return dto;
    }
}
