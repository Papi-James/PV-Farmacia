/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelo.dao.ProductoDAO;
import modelo.dto.DetalleVentaDTO;
import modelo.dto.ProductoDTO;
import utilerias.Venta_Producto;

/**
 *
 * @author papitojaime
 */
@Named(value = "puntoVentaMB")
@SessionScoped
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PuntoVentaMB implements Serializable{

    private List<Venta_Producto> canasta = new ArrayList<>();
    private List<DetalleVentaDTO> detallesProductos;
    private BigDecimal Total=new BigDecimal(0);
    private String Codigo;
    private ProductoDAO dao = new ProductoDAO();
    private ProductoDTO dtoProducto;
    private DetalleVentaDTO dtoVenta;
    private int multiplicador=1;
    private int contadorProductos=1;
    int idEnCanasta;
    
    
    @PostConstruct
    public void init(){
        canasta = new ArrayList<>();
    }

    public void agregaALista(boolean bandera)
    {
        int indiceEnLista=-1;
        if(bandera)
        {
            indiceEnLista=verificarExistenciaEnLista(true);
            if(indiceEnLista==-1)
                buscarProductoxBarras();
        }
        else
        {
            indiceEnLista=verificarExistenciaEnLista(false);
            if(indiceEnLista==-1)
            buscarProductoxId();
        }
        
        if(indiceEnLista==-1)
        {
        if(dtoProducto.getEntidad()!=null)
        {
            Venta_Producto itemCanasta = new Venta_Producto();
            itemCanasta.setId(contadorProductos);
            itemCanasta.setIdProducto(dtoProducto.getEntidad().getIdProducto());
            itemCanasta.setNombre(dtoProducto.getEntidad().getNombre());
            itemCanasta.setPresentacion(dtoProducto.getEntidad().getPresentacion());
            itemCanasta.setMarca(dtoProducto.getEntidad().getMarca());
            itemCanasta.setSustancia(dtoProducto.getEntidad().getSustancia());
            itemCanasta.setReceta(dtoProducto.getEntidad().isReceta());
            itemCanasta.setPrecio(dtoProducto.getEntidad().getPrecio());
            itemCanasta.setBarras(dtoProducto.getEntidad().getCodBarras());
            itemCanasta.setCantidad(multiplicador);
            itemCanasta.setSubtotal(dtoProducto.getEntidad().getPrecio().multiply(new BigDecimal(multiplicador)));
            Total = itemCanasta.getSubtotal().add(Total);
            contadorProductos++;
            canasta.add(itemCanasta);
        }
        }
        else
        {
            int cantidadAnterior = canasta.get(indiceEnLista).getCantidad();
            canasta.get(indiceEnLista).setCantidad(multiplicador+cantidadAnterior);
            canasta.get(indiceEnLista).setSubtotal(canasta.get(indiceEnLista).getPrecio().multiply(new BigDecimal(cantidadAnterior+multiplicador)));
            Total = canasta.get(indiceEnLista).getPrecio().multiply(new BigDecimal(multiplicador)).add(Total);
            
        }
        multiplicador=1;    
        Codigo="";
    }
    
    public void buscarProductoxBarras(){
        dtoProducto= new ProductoDTO();
        dtoProducto.getEntidad().setCodBarras(Codigo);
        
        dtoProducto=dao.readByBarras(dtoProducto);
    }
    
    public void buscarProductoxId(){
        dtoProducto= new ProductoDTO();
        dtoProducto.getEntidad().setIdProducto(Integer.parseInt(Codigo));
        
        dtoProducto=dao.read(dtoProducto);
    }
    
    public void analizarCodigo(){
        if(Codigo.contains("t") || Codigo.contains("T"))
        {
            realizarVenta();
        }
        else
        {
            if(Codigo.contains("*"))
            {
                String partes[]=Codigo.split("\\*");
                multiplicador=Integer.parseInt(partes[0]);
                Codigo=partes[1];
            }
        
            if(Codigo.length()>6)
            {
                ///Codigo de producto
                agregaALista(true);
            }
            else
            {   //codigo de barras
                agregaALista(false);
            }
        }
    }
    
    public int verificarExistenciaEnLista(boolean tipoBusqueda){
        Venta_Producto vp = new Venta_Producto();
        if(canasta.size()>0)
        {
            if(tipoBusqueda==true)
            {
                for(int i=0; i<canasta.size();i++)
                {
                    vp=canasta.get(i);
                    if(vp.getBarras().equals(Codigo))
                        return i;
                }
            }
            else
            {
                for(int i=0; i<canasta.size();i++)
                {
                    vp=canasta.get(i);
                    if(vp.getIdProducto()==Integer.parseInt(Codigo))
                        return i;
                }
                
            }
        }
        return -1;
    }
    
    public void borrarItem()
    {
        for(int i=0; i<canasta.size();i++)
        {
            if(canasta.get(i).getId()==idEnCanasta)
            {
                Total = Total.subtract(canasta.get(i).getSubtotal());
                canasta.remove(i);
            }
        }
        
    }
    
    public void seleccionarItem(){
        String claveSel = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claveSel");
        idEnCanasta = Integer.parseInt(claveSel);
    }
    
    public void realizarVenta()
    {
        Total=new BigDecimal(0);
        canasta.clear();
        Codigo="";
        multiplicador=1;
        contadorProductos=1;
    }
    
    
}
