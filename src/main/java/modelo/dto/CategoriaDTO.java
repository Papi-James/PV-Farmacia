package modelo.dto;

import entidades.Categoria;

/**
 *
 * @author papitojaime
 */
public class CategoriaDTO {
    
    private Categoria entidad;

    public CategoriaDTO() {
        entidad = new Categoria();
    }

    public CategoriaDTO(Categoria entidad) {
        this.entidad = entidad;
    }

    public Categoria getEntidad() {
        return entidad;
    }

    public void setEntidad(Categoria entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("idCategoria: ").append(getEntidad().getIdCategoria()).append("\n");
        sb.append("nombreCategoria: ").append(getEntidad().getNombre()).append("\n");
        sb.append("descripcionCategoria: ").append(getEntidad().getDescripcion()).append("\n");
        return sb.toString();
    }
    
    
}
