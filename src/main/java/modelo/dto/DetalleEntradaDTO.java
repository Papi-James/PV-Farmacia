package modelo.dto;

import entidades.DetalleEntrada;

/**
 *
 * @author papitojaime
 */
public class DetalleEntradaDTO {
  
   private DetalleEntrada entidad;

    public DetalleEntradaDTO() {
        entidad = new DetalleEntrada();
    }

    public DetalleEntradaDTO(DetalleEntrada entidad) {
        this.entidad = entidad;
    }

    public DetalleEntrada getEntidad() {
        return entidad;
    }

    public void setEntidad(DetalleEntrada entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("idDetalleEntrada: ").append(getEntidad().getIdDEntrada()).append("\n");
        sb.append("idProducto: ").append(getEntidad().getIdProducto()).append("\n");
        sb.append("idEntrada: ").append(getEntidad().getIdEntrada()).append("\n");
        sb.append("cantidad: ").append(getEntidad().getCantidad()).append("\n");
        sb.append("costo: ").append(getEntidad().getCosto()).append("\n");
        return sb.toString();
    }
}
