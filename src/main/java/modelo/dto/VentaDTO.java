package modelo.dto;

import entidades.Venta;

/**
 *
 * @author papitojaime
 */
public class VentaDTO {
    
    private Venta entidad;

    public VentaDTO() {
        entidad = new Venta();
    }

    public VentaDTO(Venta entidad) {
        this.entidad = entidad;
    }

    public Venta getEntidad() {
        return entidad;
    }

    public void setEntidad(Venta entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("idVenta: ").append(getEntidad().getIdVenta()).append("\n");
        sb.append("idUsuario: ").append(getEntidad().getIdUsuario()).append("\n");
        sb.append("fecha: ").append(getEntidad().getFecha()).append("\n");
        sb.append("total: ").append(getEntidad().getTotal()).append("\n");
        return sb.toString();
    }
}
