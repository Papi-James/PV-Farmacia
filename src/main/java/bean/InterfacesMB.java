package bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
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
    
}
