/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import static bean.BaseBean.ACC_ACTUALIZAR;
import static bean.BaseBean.ACC_CREAR;
import entidades.DetalleEntrada;
import entidades.DetalleVenta;
import entidades.Venta;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelo.dao.DetalleVentaDAO;
import modelo.dao.ProductoDAO;
import modelo.dao.VentaDAO;
import modelo.dto.DetalleVentaDTO;
import modelo.dto.ProductoDTO;
import modelo.dto.VentaDTO;
import org.primefaces.PrimeFaces;
import utilerias.Venta_Producto;

/**
 *
 * @author ramms
 */
@Named(value = "ventaMB")
@SessionScoped
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaMB extends BaseBean implements Serializable {


    private VentaDAO dao = new VentaDAO();
    private VentaDTO dto;
    private List<VentaDTO> listaDeVentas;
    private int VentasHechasEnRango=0;
    private BigDecimal totalEnRango=new BigDecimal(0);
    private String fechainicio;
    private Timestamp fechafinal;
    private List<Venta_Producto> listaDeDetalles;
    
    
    @PostConstruct
    public void init(){
        listaDeVentas = new ArrayList<>();
        listaDeVentas = dao.readAll();
        listaDeDetalles = new ArrayList<>();
    }
    
    public String prepareIndex(){
        init();
        return "/Venta/listadoVentas?faces-redirect=true";
    }
    
    public String back(){
        return prepareIndex();
    }
    
    public boolean validate(){
        boolean valido = true;
        return valido;
    }
    
    public String prepareCC()
    {
        List listaDeVentasRango = dao.readByDate();
        VentasHechasEnRango = listaDeVentasRango.size();
        
        for(int i=0; i<listaDeVentasRango.size();i++)
        {
            totalEnRango = totalEnRango.add(((Venta)listaDeVentasRango.get(i)).getTotal());
        }
        
        Date fechaaux= new Date();
        fechainicio = (fechaaux.getYear()+1900)+"-"+(fechaaux.getMonth()+1)+"-"+fechaaux.getDate()+" 08:00:00"; 
        fechafinal = new Timestamp(new Date().getTime());
        
        return "/InterfazCorte?faces-redirect=true";
    }
    
    public void seleccionarVenta(){
        String claveSel = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new VentaDTO();
        dto.getEntidad().setIdVenta(Integer.parseInt(claveSel));
        
        try{
            dto = dao.read(dto);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void detalleVenta(){
        recuperarListaDeDetalles();
        
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('DialogoDetalleVenta').show();");
    }
    
    public void recuperarListaDeDetalles(){
       
        listaDeDetalles = new ArrayList<>();
        
       List<DetalleVenta> listaDetail = new ArrayList<>();
       DetalleVentaDAO daoDVenta = new DetalleVentaDAO();
       ProductoDAO  daoProducto = new ProductoDAO();
       ProductoDTO  dtoProducto = new ProductoDTO();
       Venta_Producto vp;
       
       listaDetail = daoDVenta.readByIdVenta(dto);

       for(int i=0;i<listaDetail.size();i++)
       {
           vp = new Venta_Producto();
           
           dtoProducto.getEntidad().setIdProducto(listaDetail.get(i).getIdProducto());
           dtoProducto = daoProducto.read(dtoProducto);
           
           vp.setCantidad(listaDetail.get(i).getCantidad());
           vp.setMarca(dtoProducto.getEntidad().getMarca());
           vp.setReceta(dtoProducto.getEntidad().isReceta());
           vp.setSustancia(dtoProducto.getEntidad().getSustancia());
           vp.setPresentacion(dtoProducto.getEntidad().getPresentacion());
           vp.setPrecio(listaDetail.get(i).getPrecio());
           vp.setNombre(dtoProducto.getEntidad().getNombre());
           vp.setSubtotal(listaDetail.get(i).getPrecio().multiply(new BigDecimal(listaDetail.get(i).getCantidad())));
           
           if(dtoProducto.getEntidad().isReceta())
           {
               vp.setIdMedico(listaDetail.get(i).getIdMedico());
           }
           
           listaDeDetalles.add(vp);
           
            
       }
       
    }
    
}
