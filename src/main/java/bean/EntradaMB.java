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
import modelo.dao.EntradaDAO;
import modelo.dao.VentaDAO;
import modelo.dto.EntradaDTO;
import modelo.dto.ProductoDTO;
import modelo.dto.VentaDTO;

/**
 *
 * @author ramms
 */
@Named(value = "entradaMB")
@SessionScoped
public class EntradaMB extends BaseBean implements Serializable {


    private EntradaDAO dao = new EntradaDAO();
    private EntradaDTO dto;
    private List<ProductoDTO> listaDeCategorias;
    
    @PostConstruct
    public void init(){
        listaDeCategorias = new ArrayList<>();
        listaDeCategorias = dao.readAll();
    }
    
    public String prepareAdd(){
        dto= new EntradaDTO();
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
        dto = new EntradaDTO();
        dto.getEntidad().setIdEntrada(Integer.parseInt(claveSel));
        
        try{
            dto = dao.read(dto);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
