package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelo.dao.MedicoDAO;
import modelo.dto.MedicoDTO;
/**
 *
 * @author papitojaime
 */
@Named(value = "medicoMB")
@SessionScoped
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicoMB extends BaseBean implements Serializable{

    private MedicoDAO dao = new MedicoDAO();
    private MedicoDTO dto;
    private List<MedicoDTO> listaDeMedicos;
    
    @PostConstruct
    public void init(){
        listaDeMedicos = new ArrayList<>();
        listaDeMedicos = dao.readAll();
        listaDeMedicos.remove(0);
    }
    
    public String prepareAdd(){
        dto= new MedicoDTO();
        setAccion(ACC_CREAR);
        return "/medico/medicoForm?faces-redirect=true";
    }
    
    public String prepareUpdate(){
        setAccion(ACC_ACTUALIZAR);
        return "/medico/medicoForm?faces-redirect=true";
    }
    
    public String prepareIndex(){
        init();
        return "/medico/listadoMedicos?faces-redirect=true";
    }
    
    public String back(){
        return prepareIndex();
    }
    
    public boolean validate(){
        boolean valido = true;
        return valido;
    }
    
    public String add()
    {
        Boolean valido = validate();
        if(valido){
            dao.create(dto);
            if(valido)
            {
                return prepareIndex();
            }
            else
                return prepareAdd();
        }
        return prepareAdd();
    }
    
    public String update()
    {
        Boolean valido = validate();
        if(valido){
            dao.update(dto);
            if(valido)
            {
                return prepareIndex();
            }
            else
                return prepareUpdate();
        }
        return prepareUpdate();
    }
    
    public String delete()
    {
        dao.delete(dto);      
        return prepareIndex();
    }
    
    public void seleccionarMedico(){
        String claveSel = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new MedicoDTO();
        dto.getEntidad().setIdMedico(Integer.parseInt(claveSel));
        
        try{
            dto = dao.read(dto);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
   
}
