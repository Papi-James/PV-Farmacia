package bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelo.dao.UsuarioDAO;
import modelo.dto.UsuarioDTO;
import utilerias.EnvioCorreo;

/**
 *
 * @author papitojaime
 */
@Named(value = "usuarioMB")
@SessionScoped
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioMB extends BaseBean implements Serializable {
    
    private UsuarioDAO dao = new UsuarioDAO();
    private UsuarioDTO dto;
    private List<UsuarioDTO> listaDeUsuarios;
    
    @PostConstruct
    public void init(){
        listaDeUsuarios = new ArrayList<>();
        listaDeUsuarios = dao.readAll();
    }
    
    public String prepareAdd(){
        dto= new UsuarioDTO();
        setAccion(ACC_CREAR);
        return "/usuario/usuarioForm?faces-redirect=true";
    }
    
    public String prepareUpdate(){
        setAccion(ACC_ACTUALIZAR);
        return "/usuario/usuarioForm?faces-redirect=true";
    }
    
    public String prepareIndex(){
        init();
        return "/usuario/listadoUsuarios?faces-redirect=true";
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
        if (valido) {
            dao.create(dto);
            EnvioCorreo c = new EnvioCorreo();

            String correo = dto.getEntidad().getEmail();
            String asunto = "Registro prueba";
            String conTexto = "El registro del usuario fue realizado exitosamente";
            try {
                c.enviarEmail(correo, asunto, conTexto);
            } catch (MessagingException ex) {
                Logger.getLogger(EnvioCorreo.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    
    
    public void seleccionarUsuario(){
        String claveSel = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new UsuarioDTO();
        dto.getEntidad().setIdUsuario(Integer.parseInt(claveSel));
        
        try{
            dto = dao.read(dto);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    
}
