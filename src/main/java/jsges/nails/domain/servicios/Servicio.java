package jsges.nails.domain.servicios;

import jakarta.persistence.*;
import jsges.nails.domain.ObjetoEliminable;
import jsges.nails.domain.organizacion.Cliente;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Servicio extends ObjetoEliminable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(nullable = false)
    private LocalDateTime fechaRealizacion;

    @Column(nullable = false)
    private double total;

    // Constructores
    public Servicio() {
        super(0);
        this.fechaRegistro = LocalDateTime.now(); // Fecha de creación por defecto
    }

    public Servicio(Cliente cliente, LocalDateTime fechaRealizacion, double total) {
        this();
        this.cliente = cliente;
        this.fechaRealizacion = fechaRealizacion;
        this.total = total;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public LocalDateTime getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(LocalDateTime fechaRealizacion) {
        if (fechaRealizacion.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de realización debe ser futura.");
        }
        this.fechaRealizacion = fechaRealizacion;
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

    @Override
    public String toString() {
        return "Servicio{" +
                "id=" + id +
                ", estado=" + getEstadoString() +
                ", cliente=" + cliente.getId() + // Evitar cargar toda la entidad
                ", fechaRegistro=" + fechaRegistro +
                ", fechaRealizacion=" + fechaRealizacion +
                ", total=" + total +
                '}';
    }
}
