package jsges.nails.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class TipoObjeto implements Serializable {
        final int ESTADO_ACTIVO = 0;
        final int ESTADO_ELIMINADO = 1;

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_objeto_id_seq")
        @SequenceGenerator(name = "tipo_objeto_id_seq", sequenceName = "tipo_objeto_id_seq", allocationSize = 1)
        private Integer id;

        private int codigo;

        @Column(columnDefinition = "TEXT")
        private String denominacion;
        
        @Column(columnDefinition = "INTEGER", nullable = false)
        private int estado = 0;

        @Column(columnDefinition = "TEXT")
        private String detalle;


        public void asEliminado() {
            this.setEstado(ESTADO_ELIMINADO);
        }
        
        public void setEstado(int estado) {
                if (estado != ESTADO_ACTIVO && estado != ESTADO_ELIMINADO) {
                        throw new IllegalArgumentException("El estado solo puede ser 0 (activo) o 1 (eliminado).");
                }
                this.estado = estado;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final TipoObjeto other = (TipoObjeto) obj;
            return Objects.equals(this.id, other.id);
        }
}
