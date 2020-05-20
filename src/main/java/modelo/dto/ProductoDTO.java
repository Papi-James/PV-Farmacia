package modelo.dto;

import entidades.Producto;

/**
 *
 * @author papitojaime
 */
public class ProductoDTO {
    
    private Producto entidad;

    public ProductoDTO() {
        entidad = new Producto();
    }

    public ProductoDTO(Producto entidad) {
        this.entidad = entidad;
    }

    public Producto getEntidad() {
        return entidad;
    }

    public void setEntidad(Producto entidad) {
        this.entidad = entidad;
    }
    
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("idProducto: ").append(getEntidad().getIdProducto()).append("\n");
        sb.append("idCategoria: ").append(getEntidad().getIdCategoria()).append("\n");
        sb.append("nombreProducto: ").append(getEntidad().getNombre()).append("\n");
        sb.append("nombreComercial: ").append(getEntidad().getNombreComercial()).append("\n");
        sb.append("sustancia: ").append(getEntidad().getSustancia()).append("\n");
        sb.append("presentacion: ").append(getEntidad().getPresentacion()).append("\n");
        sb.append("marca: ").append(getEntidad().getMarca()).append("\n");
        sb.append("fraccion: ").append(getEntidad().getFraccion()).append("\n");
        sb.append("claveSat: ").append(getEntidad().getClaveSat()).append("\n");
        sb.append("codBarras: ").append(getEntidad().getCodBarras()).append("\n");
        sb.append("precio: ").append(getEntidad().getPrecio()).append("\n");
        sb.append("costo: ").append(getEntidad().getCosto()).append("\n");
        sb.append("iva: ").append(getEntidad().getIva()).append("\n");
        sb.append("proveedor: ").append(getEntidad().getProveedor()).append("\n");
        sb.append("existencia: ").append(getEntidad().getExistencia()).append("\n");
        return sb.toString();
    }
    
    
}
