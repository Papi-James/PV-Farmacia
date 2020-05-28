package modelo.dto;

import entidades.Entrada;

/**
 *
 * @author papitojaime
 */
public class EntradaDTO {
    
    private Entrada entidad;

    public EntradaDTO() {
        entidad = new Entrada();
    }

    public EntradaDTO(Entrada entidad) {
        this.entidad = entidad;
    }

    public Entrada getEntidad() {
        return entidad;
    }

    public void setEntidad(Entrada entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("idEntrada: ").append(getEntidad().getIdEntrada()).append("\n");
        sb.append("idUsuario: ").append(getEntidad().getIdUsuario()).append("\n");
        sb.append("proveedor: ").append(getEntidad().getProveedor()).append("\n");
        sb.append("numFactura: ").append(getEntidad().getNumFactura()).append("\n");
        sb.append("total: ").append(getEntidad().getTotal()).append("\n");
        sb.append("fecha: ").append(getEntidad().getFecha()).append("\n");
        return sb.toString();
    }
}
