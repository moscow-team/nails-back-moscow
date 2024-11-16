package jsges.nails.domain.servicios;

import jakarta.persistence.*;
import jsges.nails.domain.ObjetoEliminable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@ToString
public class ItemServicio extends ObjetoEliminable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    String observacion;

    // Eliminamos el cascade para evitar la eliminación en cascada. Esto podría llevar a la creación o eliminación accidental de tipos de servicio.
    @ManyToOne()
    private TipoServicio tipoServicio;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Servicio servicio;

    @Column(nullable = false)
    private double total;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ItemServicio that = (ItemServicio) o;
        return this.getEstado() == that.getEstado() && Objects.equals(id, that.id)
                && Objects.equals(observacion, that.observacion) && Objects.equals(tipoServicio, that.tipoServicio)
                && Objects.equals(servicio, that.servicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, this.getEstado(), observacion, tipoServicio, servicio);
    }

    public ItemServicio() {

    }

    public ItemServicio(Servicio servicio, TipoServicio tipo, Double precio, String observacion) {
        super(0);
        this.servicio = servicio;
        this.tipoServicio = tipo;
        setTotal(precio);
        this.observacion = observacion;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        if (total <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor que cero.");
        }
        this.total = total;
    }
}
