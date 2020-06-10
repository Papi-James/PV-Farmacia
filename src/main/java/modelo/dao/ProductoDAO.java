package modelo.dao;


import entidades.Producto;
import java.math.BigDecimal;
import java.util.List;
import modelo.dto.ProductoDTO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utilerias.HibernateUtil;

/**
 *
 * @author papitojaime
 */
public class ProductoDAO {
    
    public void create(ProductoDTO dto){
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trans = s.getTransaction();
        
        try{
        trans.begin();
        s.save(dto.getEntidad());
        trans.commit();
        }catch(HibernateException he)
        {
            if(trans!=null && trans.isActive())
                trans.rollback();
        }
    }
    
    public void update(ProductoDTO dto){
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trans = s.getTransaction();
        
        try{
        trans.begin();
        s.update(dto.getEntidad());
        trans.commit();
        }catch(HibernateException he)
        {
            if(trans!=null && trans.isActive())
                trans.rollback();
        }
    }
    
    public void delete(ProductoDTO dto){
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trans = s.getTransaction();
        
        try{
        trans.begin();
        s.delete(dto.getEntidad());
        trans.commit();
        }catch(HibernateException he)
        {
            if(trans!=null && trans.isActive())
                trans.rollback();
        }
    }
    
    public ProductoDTO read(ProductoDTO dto){
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trans = s.getTransaction();
        
        try{
        trans.begin();
        dto.setEntidad(s.get(dto.getEntidad().getClass(),dto.getEntidad().getIdProducto() ));
        trans.commit();
        }catch(HibernateException he)
        {
            if(trans!=null && trans.isActive())
                trans.rollback();
        }
        return dto;
    }
    
     public ProductoDTO readByBarras(ProductoDTO dto){
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trans = s.getTransaction();
        List l = null;
        try{
        trans.begin();
        Query q = s.createQuery("from Producto p where p.codBarras='"+dto.getEntidad().getCodBarras()+"'");
        l=q.list();
        dto.setEntidad((Producto)l.get(0));
        trans.commit();
        }catch(HibernateException he)
        {
            if(trans!=null && trans.isActive())
                trans.rollback();
        }
        return dto;
    }
    
    public List readAll(){
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trans = s.getTransaction();
        List l = null;
        try{
        trans.begin();
        Query q = s.createQuery("from Producto p order by p.idProducto");
        l=q.list();
        trans.commit();
        }catch(HibernateException he)
        {
            if(trans!=null && trans.isActive())
                trans.rollback();
        }
        return l;
    }
    
    public List readByNameOrSustance(String parametro){
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trans = s.getTransaction();
        List l = null;
        try{
        trans.begin();
        Query q = s.createQuery("from Producto p where lower(p.nombre) like lower('%"+parametro+"%') or lower(p.sustancia) like lower('%"+parametro+"%') order by p.idProducto");
        l=q.list();
        trans.commit();
        }catch(HibernateException he)
        {
            if(trans!=null && trans.isActive())
                trans.rollback();
        }
        return l;
    }
    
    public static void main(String[] args) {
        ProductoDAO dao = new ProductoDAO();
        ProductoDTO dto = new ProductoDTO();
        
        //dto.getEntidad().setIdProducto(11);
        dto.getEntidad().setIdCategoria(1);
        dto.getEntidad().setNombre("Biomesina");
        dto.getEntidad().setPresentacion("Tabletas c/10");
        dto.getEntidad().setMarca("BIOMEP");
        dto.getEntidad().setCodBarras("123221371");
        dto.getEntidad().setPrecio(BigDecimal.valueOf(35));
        dto.getEntidad().setCosto(BigDecimal.valueOf(30));
        dto.getEntidad().setIva(0.0f);
        dto.getEntidad().setProveedor("algo");
        dto.getEntidad().setExistencia(5);
        dto.getEntidad().setNombreComercial("Aspirina");
        dto.getEntidad().setSustancia("Butilhioscina 10mg");
        dto.getEntidad().setFraccion("IV");
        dto.getEntidad().setClaveSat("1234");
        dto.getEntidad().setReceta(false);
        
        
        dao.create(dto);
    }
}
