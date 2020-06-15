/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entidades.Usuario;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelo.dao.UsuarioDAO;
import modelo.dto.UsuarioDTO;

/**
 *
 * @author papitojaime
 */
@ManagedBean(name = "loginMB")
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
                
                HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                s.setAttribute("nombreUsuario",  dto.getEntidad().getNombreUsuario());
                s.setAttribute("tipoUsuario",  dto.getEntidad().getTipoUsuario());
                s.setAttribute("idUsuario",  dto.getEntidad().getIdUsuario());
                dto=null;
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
       HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
       String us = s.getAttribute("nombreUsuario").toString();
       if(us!=null)
       return us;
       else
           return "null";
    }
    
    public boolean isLoged()
    {
       HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
       //String us = s.getAttribute("nombreUsuario").toString(); 
       if(s.getAttribute("nombreUsuario")==null)
           return false;
       else
           return true;
    }
    
    public boolean isAdministrador(){
       int userType = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tipoUsuario");
       if(userType==1)
        return true;
       else
           return false;
    }
    
    public String logout()
    {
        HttpSession s = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        s.invalidate();
        return prepareLogin();
    }
    
    public String prepareLogin()
    {
        return "/index?faces-redirect=true";
    }
}
