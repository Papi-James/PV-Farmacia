package bean;

import static bean.BaseBean.ACC_ACTUALIZAR;
import static bean.BaseBean.ACC_CREAR;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.Part;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelo.dao.ProductoDAO;
import modelo.dto.ProductoDTO;
import org.apache.commons.io.IOUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

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
    String nombreBuscador="";
    private List<ProductoDTO> listaCoincidente;
    
    @PostConstruct
    public void init(){
        listaDeProductos = new ArrayList<>();
        listaDeProductos = dao.readAll();
        listaCoincidente = new ArrayList<>();
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
    
    public StreamedContent mostrarImagen()
    {
        if(dto!=null)
        {
        if (dto.getEntidad().getImagen() != null)
            return new DefaultStreamedContent(new ByteArrayInputStream(dto.getEntidad().getImagen()));
        else
        {
            File imagendefault = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/")+"imagenes/productoSinFoto.png");
            byte[] ArregloBytes = new byte[(int) imagendefault.length()];

            FileInputStream fis;
            try {
                fis = new FileInputStream(imagendefault);
                fis.read(ArregloBytes);
                fis.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProductoMB.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ProductoMB.class.getName()).log(Level.SEVERE, null, ex);
            }
            

            return new DefaultStreamedContent(new ByteArrayInputStream(ArregloBytes));
        }
        }
        else
            return null;
            
    }
    
    public void detalleProducto(){
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('DialogoDetalleProducto').show();");
    }
    
    public void escuchaBusqueda(AjaxBehaviorEvent  event){
        listaCoincidente = dao.readByNameOrSustance(nombreBuscador);
    }
    
}
