package modelo.dto;

import entidades.Medico;

/**
 *
 * @author papitojaime
 */
public class MedicoDTO {
    
    
    private Medico entidad;

    public MedicoDTO() {
        entidad = new Medico();
    }

    public MedicoDTO(Medico entidad) {
        this.entidad = entidad;
    }

    public Medico getEntidad() {
        return entidad;
    }

    public void setEntidad(Medico entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("idMedico: ").append(getEntidad().getIdMedico()).append("\n");
        sb.append("cedula: ").append(getEntidad().getCedula()).append("\n");
        sb.append("nombre: ").append(getEntidad().getNombre()).append("\n");
        sb.append("paterno: ").append(getEntidad().getPaterno()).append("\n");
        sb.append("materno: ").append(getEntidad().getMaterno()).append("\n");
        sb.append("direccion: ").append(getEntidad().getDireccion()).append("\n");
        return sb.toString();
    }
}
