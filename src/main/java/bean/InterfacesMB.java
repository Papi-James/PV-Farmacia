package bean;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author papitojaime
 */
@Named(value = "interfacesMB")
@SessionScoped
@Data
@AllArgsConstructor
public class InterfacesMB implements Serializable {
    
    public String prepareInterfazAdmin(){
        return "/InterfazAdmin?faces-redirect=true";
    }
    
    public String preparePrincipal(){
        return "/Principal?faces-redirect=true";
    }
    
    public String prepareCC(){
        return "/InterfazCorte?faces-redirect=true";
    }
    
    public String prepareBuscadorProductos(){
        return "/producto/busquedaProducto?faces-redirect=true";
    }
    
    public void noAuth(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/faces/errores/NoUserDenied.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(InterfacesMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void alredyLoged(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/faces/Principal.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(InterfacesMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void noAdmin(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/faces/errores/NoAdminDenied.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(InterfacesMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    
}
