/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import static bean.BaseBean.ACC_ACTUALIZAR;
import static bean.BaseBean.ACC_CREAR;
import entidades.DetalleEntrada;
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
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelo.dao.DetalleEntradaDAO;
import modelo.dao.EntradaDAO;
import modelo.dao.ProductoDAO;
import modelo.dto.DetalleEntradaDTO;
import modelo.dto.EntradaDTO;
import modelo.dto.ProductoDTO;
import org.primefaces.PrimeFaces;
import utilerias.Entrada_Producto;
import utilerias.Venta_Producto;

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
    private String Codigo = "";
    private List<Venta_Producto> listaParaEntrada;
    private ProductoDTO producto;
    private String costo;
    private int cantidad;
    private int contador=0;
    private List<Venta_Producto> listaDeDetalles;
    private int idVP=0;
    private String Factura="";
    private String Proveedor="";
    private BigDecimal total = new BigDecimal(0);

    @PostConstruct
    public void init() {
        listaDeEntradas = new ArrayList<>();
        listaDeEntradas = dao.readAll();
        listaParaEntrada = new ArrayList<>();
    }

    public String prepareIndex() {
        init();
        return "/Entrada/listadoEntradas?faces-redirect=true";
    }
    
    public String prepareAdd()
    {
        return "/Entrada/registroEntrada?faces-redirect=true";
    }

    public String back() {
        return prepareIndex();
    }

    public boolean validate() {
        boolean valido = true;
        return valido;
    }

    public String delete() {
        dao.delete(dto);
        return prepareIndex();
    }

    public void seleccionarEntrada() {
        String claveSel = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new EntradaDTO();
        dto.getEntidad().setIdEntrada(Integer.parseInt(claveSel));

        try {
            dto = dao.read(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void seleccionarRegistroEntrada() {
        String claveSel = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claveSel");
        
        idVP = Integer.parseInt(claveSel);
    }

    public void analizarCodigo() {

        if (Codigo.length() > 7) {
            ///Codigo de producto
            buscarProductoxBarras();
        } else {   //codigo de barras
            buscarProductoxId();
        }
        
        if(producto.getEntidad()!=null)
        {
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('DialogoDetalleEntrada').show();");
        
        costo=producto.getEntidad().getCosto()+"";
        }
        else
        {
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('DialNoRegistrado').show();");
        }
            
    }
    
    public void buscarProductoxBarras(){
        ProductoDAO daoP = new ProductoDAO();
        producto= new ProductoDTO();
        producto.getEntidad().setCodBarras(Codigo);
        
        producto=daoP.readByBarras(producto);
    }
    
    public void buscarProductoxId(){
        ProductoDAO daoP = new ProductoDAO();
        producto= new ProductoDTO();
        producto.getEntidad().setIdProducto(Integer.parseInt(Codigo));
        
        producto=daoP.read(producto);
    }
    
    public void eliminarRegistroEntrada(){
        
        total=total.subtract(new BigDecimal(listaParaEntrada.get(idVP).getCantidad()).multiply(listaParaEntrada.get(idVP).getPrecio()));
        
        listaParaEntrada.remove(idVP);
    }
    
    public void prepareEntrada(){
         PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('DialogoFactura').show();");
        
    
    }
    
    public void registrarEntrada(){
        dto = new EntradaDTO();
        dao = new EntradaDAO();
        DetalleEntradaDTO dtoDE;
        DetalleEntradaDAO daoDE = new DetalleEntradaDAO();
        HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        
        dto.getEntidad().setIdUsuario(Integer.parseInt(s.getAttribute("idUsuario").toString()));
        dto.getEntidad().setNumFactura(Factura);
        dto.getEntidad().setProveedor(Proveedor);
        dto.getEntidad().setTotal(total);
        dto.getEntidad().setFecha(new Timestamp(new Date().getTime()));
        int idEntradaCreado = dao.createConRetorno(dto);
        
        for(int i=0; i<listaParaEntrada.size();i++)
        {
            dtoDE= new DetalleEntradaDTO();
            dtoDE.getEntidad().setCantidad(listaParaEntrada.get(i).getCantidad());
            dtoDE.getEntidad().setCosto(listaParaEntrada.get(i).getPrecio());
            dtoDE.getEntidad().setIdEntrada(idEntradaCreado);
            dtoDE.getEntidad().setIdProducto(listaParaEntrada.get(i).getIdProducto());
            
            producto = new ProductoDTO();
            ProductoDAO daop = new ProductoDAO();
            producto.getEntidad().setIdProducto(listaParaEntrada.get(i).getIdProducto());
            producto = daop.read(producto);
            
            producto.getEntidad().setExistencia(producto.getEntidad().getExistencia()+listaParaEntrada.get(i).getCantidad());
            
            daop.update(producto);
            
            daoDE.create(dtoDE);
            
        }
        Factura="";
        Proveedor="";
        contador=0;
        cantidad=0;
        Codigo="";
        listaParaEntrada.clear();
        
    }
    
    public void agregarAEntrada(){
        
        Venta_Producto vp = new Venta_Producto();
        
        vp.setId(contador);
        vp.setIdProducto(producto.getEntidad().getIdProducto());
        vp.setNombre(producto.getEntidad().getNombre());
        vp.setPresentacion(producto.getEntidad().getPresentacion());
        vp.setSustancia(producto.getEntidad().getSustancia());
        vp.setPrecio(new BigDecimal(String.valueOf(costo)));
        vp.setCantidad(cantidad);
        
        listaParaEntrada.add(vp);
        
        total = total.add(new BigDecimal(Float.parseFloat(costo)*cantidad));
        contador++;
        cantidad=0;
        Codigo="";
        
    }
    
    public void detalleEntrada(){
        recuperarListaDeDetalles();
        
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('DialogoDetalleEntrada').show();");
    }
    
    public void recuperarListaDeDetalles() {

        listaDeDetalles = new ArrayList<>();

        List<DetalleEntrada> listaDetail = new ArrayList<>();
        DetalleEntradaDAO daoDEntrada = new DetalleEntradaDAO();
        ProductoDAO daoProducto = new ProductoDAO();
        ProductoDTO dtoProducto = new ProductoDTO();
        Venta_Producto vp;

        listaDetail = daoDEntrada.readByIdEntrada(dto);

        for (int i = 0; i < listaDetail.size(); i++) {
            vp = new Venta_Producto();

            dtoProducto.getEntidad().setIdProducto(listaDetail.get(i).getIdProducto());
            dtoProducto = daoProducto.read(dtoProducto);

            vp.setCantidad(listaDetail.get(i).getCantidad());
            vp.setMarca(dtoProducto.getEntidad().getMarca());
            vp.setReceta(dtoProducto.getEntidad().isReceta());
            vp.setSustancia(dtoProducto.getEntidad().getSustancia());
            vp.setPresentacion(dtoProducto.getEntidad().getPresentacion());
            vp.setPrecio(listaDetail.get(i).getCosto());
            vp.setNombre(dtoProducto.getEntidad().getNombre());
            vp.setSubtotal(listaDetail.get(i).getCosto().multiply(new BigDecimal(listaDetail.get(i).getCantidad())));

            listaDeDetalles.add(vp);

        }

    }

}
