package bean;

import static bean.BaseBean.ACC_ACTUALIZAR;
import static bean.BaseBean.ACC_CREAR;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelo.dao.ProductoDAO;
import modelo.dto.ProductoDTO;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author papitojaime
 */
@Named(value = "productoMB")
@SessionScoped
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoMB extends BaseBean implements Serializable {

    private ProductoDAO dao = new ProductoDAO();
    private ProductoDTO dto;
    private Part img;
    private List<ProductoDTO> listaDeProductos;
    
    @PostConstruct
    public void init(){
        listaDeProductos = new ArrayList<>();
        listaDeProductos = dao.readAll();
    }
    
    public String prepareAdd(){
        dto= new ProductoDTO();
        setAccion(ACC_CREAR);
        return "/producto/productoForm?faces-redirect=true";
    }
    
    public String prepareUpdate(){
        setAccion(ACC_ACTUALIZAR);
        return "/producto/productoForm?faces-redirect=true";
    }
    
    public String prepareIndex(){
        init();
        return "/producto/listadoProductos?faces-redirect=true";
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
    
    public void handleImagen(){
        try {
            dto.getEntidad().setImagen(IOUtils.toByteArray(img.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(ProductoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void seleccionarProducto(){
        String claveSel = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new ProductoDTO();
        dto.getEntidad().setIdProducto(Integer.parseInt(claveSel));
        
        try{
            dto = dao.read(dto);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
