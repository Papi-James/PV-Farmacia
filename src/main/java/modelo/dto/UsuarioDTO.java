package modelo.dto;

import entidades.Usuario;

/**
 *
 * @author papitojaime
 */
public class UsuarioDTO {
    
    private Usuario entidad;

    public UsuarioDTO() {
        entidad = new Usuario();
    }

    public UsuarioDTO(Usuario entidad) {
        this.entidad = entidad;
    }

    public Usuario getEntidad() {
        return entidad;
    }

    public void setEntidad(Usuario entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("idUsuario: ").append(getEntidad().getIdUsuario()).append("\n");
        sb.append("nombreUsuario: ").append(getEntidad().getNombreUsuario()).append("\n");
        sb.append("nombreReal: ").append(getEntidad().getNombre()).append("\n");
        sb.append("paternoUsuario: ").append(getEntidad().getPaterno()).append("\n");
        sb.append("maternoUsuario: ").append(getEntidad().getMaterno()).append("\n");
        sb.append("correoUsuario: ").append(getEntidad().getCorreo()).append("\n");
        sb.append("contraUsuario: ").append(getEntidad().getContrasenia()).append("\n");
        sb.append("tipoUsuario: ").append(getEntidad().getTipoUsuario()).append("\n");
        return sb.toString();
    }
    
    
}
