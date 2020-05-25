package bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author papitojaime
 */
@Data
@NoArgsConstructor
public class BaseBean {

    protected static final String ACC_CREAR="CREAR";
    protected static final String ACC_ACTUALIZAR="ACTUALIZAR";
    
    protected String accion;
    
    protected void error(String idMess,String Mess )
    {
        FacesContext.getCurrentInstance().addMessage(idMess, new FacesMessage(FacesMessage.SEVERITY_ERROR,Mess,idMess));
    }
    
    public boolean isModoCrear()
    {
        if(accion!=null)
            return accion.equals(ACC_CREAR);
        else
            return false;
    }
    
    public boolean isModoActualizar()
    {
        if(accion!=null)
            return accion.equals(ACC_ACTUALIZAR);
        else
            return false;
    }
}
