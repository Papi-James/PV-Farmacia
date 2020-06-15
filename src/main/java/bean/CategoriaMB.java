package bean;

import static bean.BaseBean.ACC_ACTUALIZAR;
import static bean.BaseBean.ACC_CREAR;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelo.dao.CategoriaDAO;
import modelo.dto.CategoriaDTO;

/**
 *
 * @author papitojaime
 */
@ManagedBean(name = "categoriaMB")
@Named(value = "categoriaMB")
@SessionScoped
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaMB extends BaseBean implements Serializable{

    private CategoriaDAO dao = new CategoriaDAO();
    private CategoriaDTO dto;
    private List<CategoriaDTO> listaDeCategorias;
    
    @PostConstruct
    public void init(){
        listaDeCategorias = new ArrayList<>();
        listaDeCategorias = dao.readAll();
    }
    
    public String prepareAdd(){
        dto= new CategoriaDTO();
        setAccion(ACC_CREAR);
        return "/categoria/categoriaForm?faces-redirect=true";
    }
    
    public String prepareUpdate(){
        setAccion(ACC_ACTUALIZAR);
        return "/categoria/categoriaForm?faces-redirect=true";
    }
    
    public String prepareIndex(){
        init();
        return "/categoria/listadoCategorias?faces-redirect=true";
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
    
    public void seleccionarCategoria(){
        String claveSel = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new CategoriaDTO();
        dto.getEntidad().setIdCategoria(Integer.parseInt(claveSel));
        
        try{
            dto = dao.read(dto);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public String buscarNombreCategoria(int id){
        dto = new CategoriaDTO();
        dto.getEntidad().setIdCategoria(id);
        
        System.out.println("Dentro de buscar nombre    ID RECIBIDO="+id);
        
        try{
            dto = dao.read(dto);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        if (dto.getEntidad()!=null)
            return dto.getEntidad().getNombre();
        else
            return "Nada";
    }
    
    public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {
        if (arg1.getId().equals("formNombreC")) {
            if (((String) arg2).length() < 1) {
                throw new ValidatorException(new FacesMessage("El nombre de la categoria no puede estar vacio"));
            }
        } else if(arg1.getId().equals("formDescripcionC")) {
            if (((String) arg2).length() < 1) {
                throw new ValidatorException(new FacesMessage("La descripcion de la categoria no puede estar vacia"));
            }
        } 
        
    }
    
}
