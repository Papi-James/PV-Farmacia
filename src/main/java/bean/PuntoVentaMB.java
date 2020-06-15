/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelo.dao.DetalleVentaDAO;
import modelo.dao.ProductoDAO;
import modelo.dao.MedicoDAO;
import modelo.dao.VentaDAO;
import modelo.dto.DetalleVentaDTO;
import modelo.dto.MedicoDTO;
import modelo.dto.ProductoDTO;
import modelo.dto.VentaDTO;
import utilerias.Venta_Producto;
import org.primefaces.PrimeFaces;

/**
 *
 * @author papitojaime
 */
@ManagedBean(name = "puntoVentaMB")
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
    private MedicoDAO daoMedico = new MedicoDAO();
    private ProductoDTO dtoProducto;
    private MedicoDTO dtoMedico;
    private int multiplicador=1;
    private int contadorProductos=1;
    private int idEnCanasta;
    private boolean banderaMedicoExistente;
    private boolean tipoVenta;
    String mensajeExito="¡La venta se ha realizado exitosamente!";
        
    
    @PostConstruct
    public void init(){
        //dtoMedico= new MedicoDTO();
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
            if(dtoProducto.getEntidad().isReceta())
            {
                dtoMedico= new MedicoDTO();
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('DialogoMedico').show();");
            }
            else
            {
                MeterProductoNuevoCanasta(false);
            }
        }
        else
        {
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('DialNoRegistrado').show();");
        }
        }
        else
        {
            int cantidadAnterior = canasta.get(indiceEnLista).getCantidad();
            canasta.get(indiceEnLista).setCantidad(multiplicador+cantidadAnterior);
            canasta.get(indiceEnLista).setSubtotal(canasta.get(indiceEnLista).getPrecio().multiply(new BigDecimal(cantidadAnterior+multiplicador)));
            Total = canasta.get(indiceEnLista).getPrecio().multiply(new BigDecimal(multiplicador)).add(Total);
            multiplicador=1;    
            Codigo="";
        }
        
        
    }
    
    public void MeterProductoNuevoCanasta(boolean conMedico){
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
            
            if(conMedico)
            {
                if(banderaMedicoExistente==false)
                {
                    int idUltimoMedico = daoMedico.createConRetorno(dtoMedico);
                    itemCanasta.setIdMedico(idUltimoMedico);
                }
                else
                {
                    itemCanasta.setIdMedico(dtoMedico.getEntidad().getIdMedico());
                }
                
            }
            
            Total = itemCanasta.getSubtotal().add(Total);
            contadorProductos++;
            canasta.add(itemCanasta);
            multiplicador=1;    
            Codigo="";
    }
    
    public void buscarMedico()
    {
        dtoMedico = daoMedico.readByCedula(dtoMedico);
        if(dtoMedico==null)
        {
            System.out.println("No se encontro medico registrado");
            dtoMedico=new MedicoDTO();
            banderaMedicoExistente = false;
        }
        else
        {
            banderaMedicoExistente = true;
        }
    }
    
    public void limpiarMedico()
    {
        dtoMedico= null;
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
            prepareVenta();
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
    
    public void prepareVenta(){
        if (dtoMedico!=null)
        {
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('DialogoTipoVenta').show();");
        }
        else
            realizarVenta(false);
    }
    
    public void realizarVenta(boolean conAntibiotico)
    {
        mensajeExito="¡La venta se ha realizado exitosamente!";
        
        VentaDTO dtoVenta = new VentaDTO();
        VentaDAO daoVenta = new VentaDAO();
        HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        
        dtoVenta.getEntidad().setIdUsuario(Integer.parseInt(s.getAttribute("idUsuario").toString()));
        dtoVenta.getEntidad().setFecha(new Timestamp(new Date().getTime()));
        dtoVenta.getEntidad().setTotal(Total);
        
        int idDeVentaCreada = daoVenta.createConRetorno(dtoVenta);
        
        DetalleVentaDTO dtoDVenta;
        DetalleVentaDAO daoDVenta = new DetalleVentaDAO();
        
        for(int i=0; i<canasta.size();i++)
        {
            dtoDVenta = new DetalleVentaDTO();
            
            dtoDVenta.getEntidad().setIdVenta(idDeVentaCreada);
            dtoDVenta.getEntidad().setIdProducto(canasta.get(i).getIdProducto());
            
            dtoProducto.getEntidad().setIdProducto(canasta.get(i).getIdProducto());
            dtoProducto = dao.read(dtoProducto);
            int nuevoStock=dtoProducto.getEntidad().getExistencia()-canasta.get(i).getCantidad();
            if(nuevoStock<0)
                nuevoStock=0;
            
            dtoProducto.getEntidad().setExistencia(nuevoStock);
            dao.update(dtoProducto);
            
            dtoDVenta.getEntidad().setPrecio(canasta.get(i).getPrecio());
            dtoDVenta.getEntidad().setCantidad(canasta.get(i).getCantidad());
            if(conAntibiotico && canasta.get(i).isReceta() )
            {
                dtoDVenta.getEntidad().setIdMedico(dtoMedico.getEntidad().getIdMedico());
                dtoDVenta.getEntidad().setTipoVenta(tipoVenta);
                daoDVenta.create(dtoDVenta);
            }
            else
            {
                dtoDVenta.getEntidad().setIdMedico(0);
                dtoDVenta.getEntidad().setTipoVenta(true);
                daoDVenta.create(dtoDVenta);
            }
            
        }
        
        if(conAntibiotico)
            mensajeExito += "\n\nFolio: "+idDeVentaCreada;
        
        
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('DialExitoso').show();");
        
        Total=new BigDecimal(0);
        canasta.clear();
        Codigo="";
        multiplicador=1;
        contadorProductos=1;
        dtoMedico=null;
    }
    
    
}
