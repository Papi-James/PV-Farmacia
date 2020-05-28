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
import modelo.dao.EntradaDAO;
import modelo.dto.EntradaDTO;


/**
 *
 * @author ramms
 */
@Named(value = "entradaMB")
@SessionScoped
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntradaMB extends BaseBean implements Serializable {


    private EntradaDAO dao = new EntradaDAO();
    private EntradaDTO dto;
    private List<EntradaDTO> listaDeEntradas;
    
    @PostConstruct
    public void init(){
        listaDeEntradas = new ArrayList<>();
        listaDeEntradas = dao.readAll();
    }
    
    public String prepareAdd(){
        dto= new EntradaDTO();
        setAccion(ACC_CREAR);
        return "/entrada/entradaForm?faces-redirect=true";
    }
    
    public String prepareUpdate(){
        setAccion(ACC_ACTUALIZAR);
        return "/entrada/entradaForm?faces-redirect=true";
    }
    
    public String prepareIndex(){
        init();
        return "/entrada/listadoEntradas?faces-redirect=true";
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
