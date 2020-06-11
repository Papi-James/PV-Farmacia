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
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelo.dao.DetalleEntradaDAO;
import modelo.dao.EntradaDAO;
import modelo.dao.ProductoDAO;
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
    private String precio;
    private int cantidad;
    private int contador=0;
    private List<Entrada_Producto> listaDeDetalles;

    @PostConstruct
    public void init() {
        listaDeEntradas = new ArrayList<>();
        listaDeEntradas = dao.readAll();
        listaParaEntrada = new ArrayList<>();
    }

    public String prepareIndex() {
        init();
        return "/entrada/listadoEntradas?faces-redirect=true";
    }
    
    public String prepareAdd()
    {
        return "/entrada/registroEntrada?faces-redirect=true";
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
        
        precio=producto.getEntidad().getPrecio()+"";
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
    
    public void modificarEntrada(){}
    
    public void prepareEntrada(){}
    
    public void agregarAEntrada(){
        
        Venta_Producto vp = new Venta_Producto();
        
        vp.setIdProducto(producto.getEntidad().getIdProducto());
        vp.setNombre(producto.getEntidad().getNombre());
        vp.setPresentacion(producto.getEntidad().getPresentacion());
        vp.setSustancia(producto.getEntidad().getSustancia());
        vp.setPrecio(new BigDecimal(Float.parseFloat(precio)));
        vp.setCantidad(cantidad);
        
        listaParaEntrada.add(vp);
        
        cantidad=0;
        
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
        Entrada_Producto vp;

        listaDetail = daoDEntrada.readByIdEntrada(dto);

        for (int i = 0; i < listaDetail.size(); i++) {
            vp = new Entrada_Producto();

            dtoProducto.getEntidad().setIdProducto(listaDetail.get(i).getIdProducto());
            dtoProducto = daoProducto.read(dtoProducto);

            vp.setCantidad(listaDetail.get(i).getCantidad());
            vp.setMarca(dtoProducto.getEntidad().getMarca());
            vp.setReceta(dtoProducto.getEntidad().isReceta());
            vp.setSustancia(dtoProducto.getEntidad().getSustancia());
            vp.setPresentacion(dtoProducto.getEntidad().getPresentacion());
            vp.setCosto(listaDetail.get(i).getCosto());
            vp.setNombre(dtoProducto.getEntidad().getNombre());

            listaDeDetalles.add(vp);

        }

    }

}
