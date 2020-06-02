/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
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
    private UsuarioDTO dto = new UsuarioDTO();
    /**
     * Creates a new instance of LoginMB
     */
 
    public String login(){
        //dto=new UsuarioDTO();
        try{
            dto = dao.readUser(dto);
            if(dto==null)
                return "/index?faces-redirect=true";
            else
            {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("UsuarioLogeado", dto);
                return "/Principal?faces-redirect=true";
            }
        }catch(Exception e)
        {
            
            e.printStackTrace();
            return "/index?faces-redirect=true";
        }
    }
}
