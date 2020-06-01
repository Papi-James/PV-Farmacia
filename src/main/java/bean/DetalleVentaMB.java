/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import static bean.BaseBean.ACC_ACTUALIZAR;
import static bean.BaseBean.ACC_CREAR;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelo.dao.DetalleVentaDAO;
import modelo.dto.DetalleVentaDTO;


/**
 *
 * @author ramms
 */
@Named(value = "detalleVentaMB")
@SessionScoped
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVentaMB extends BaseBean implements Serializable {

    
    private DetalleVentaDAO dao = new DetalleVentaDAO();
    private DetalleVentaDTO dto;
    private List<DetalleVentaDTO> listaDeDetallesV;
    
    @PostConstruct
    public void init(){
        listaDeDetallesV = new ArrayList<>();
        listaDeDetallesV = dao.readAll();
    }
    
    public String prepareAdd(){
        dto= new DetalleVentaDTO();
        setAccion(ACC_CREAR);
        return "/detalle/detalleVentaForm?faces-redirect=true";
    }
    
    public String prepareUpdate(){
        setAccion(ACC_ACTUALIZAR);
        return "/detalle/detalleVentaForm?faces-redirect=true";
    }
    
    public String prepareIndex(){
        init();
        return "/detalle/listadoDetalleV?faces-redirect=true";
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
    
    public void seleccionarDVenta(){
        String claveSel = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new DetalleVentaDTO();
        dto.getEntidad().setIdVenta(Integer.parseInt(claveSel));
        
        try{
            dto = dao.read(dto);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    
}
