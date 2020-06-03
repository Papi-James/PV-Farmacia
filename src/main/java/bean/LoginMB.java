/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entidades.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelo.dao.UsuarioDAO;
import modelo.dto.UsuarioDTO;

/**
 *
 * @author papitojaime
 */
@Named(value = "loginMB")
@SessionScoped
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginMB implements Serializable {

    private UsuarioDAO dao = new UsuarioDAO();
    private UsuarioDTO dto;
    
    @PostConstruct
    public void init(){
        dto=new UsuarioDTO();
    }
    
    public String login(){
        //dto=new UsuarioDTO();
        try{
            dto = dao.readUser(dto);
            if(dto==null)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡Datos incorrectos!", "\nLos datos de inicio de inicio de sesión no son correctos"));
                 dto = new UsuarioDTO();
                 return null;
            }
            else
            {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("UsuarioLogeado", dto.getEntidad());
                return preparePrincipal();
            }
            
        }catch(Exception e)
        {
            
            e.printStackTrace();
            return null;
        }
    }
    
    public String preparePrincipal(){
        return "/Principal?faces-redirect=true";
    }
    
    public String UserName(){
       dto.setEntidad((Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UsuarioLogeado"));
       if(dto!=null)
       return dto.getEntidad().getNombreUsuario();
       else
           return "null";
    }
}
