package jsges.nails.domain.articulos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticuloVenta {
        final int ESTADO_ACTIVO = 0;
        final int ESTADO_ELIMINADO = 1;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(columnDefinition = "TEXT")
        String denominacion;

        @Column(columnDefinition = "INTEGER", nullable = false)
        private int estado = 0;

        @Column(columnDefinition = "TEXT")
        String observacion;

        @ManyToOne(cascade = CascadeType.ALL)
        private Linea linea;

        public void asEliminado() {
                this.setEstado(ESTADO_ELIMINADO);
        }

        public void setEstado(int estado) {
                if (estado != ESTADO_ACTIVO && estado != ESTADO_ELIMINADO) {
                        throw new IllegalArgumentException("El estado solo puede ser 0 (activo) o 1 (eliminado).");
                }
                this.estado = estado;
        }
}
