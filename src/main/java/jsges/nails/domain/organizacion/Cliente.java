package jsges.nails.domain.organizacion;

import jakarta.persistence.*;
import jsges.nails.domain.ObjetoEliminable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.io.Serializable;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
public class Cliente extends ObjetoEliminable implements Serializable {
        
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(columnDefinition = "TEXT")
        String razonSocial;

        @Column(columnDefinition = "TEXT")
        String letra;

        @Column(columnDefinition = "TEXT")
        String contacto;

        @Column(columnDefinition = "TEXT")
        String celular;

        @Column(columnDefinition = "TEXT")
        String mail;

        @Column(nullable = false)
        Date fechaInicio;

        @Column(nullable = false)
        Date fechaNacimiento;


}
