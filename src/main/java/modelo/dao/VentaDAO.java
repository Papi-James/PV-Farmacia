package modelo.dao;

import java.util.List;
import modelo.dto.VentaDTO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utilerias.HibernateUtil;

/**
 *
 * @author papitojaime
 */
public class VentaDAO {
    
    public void create(VentaDTO dto){
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
    
    public void update(VentaDTO dto){
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
    
    public void delete(VentaDTO dto){
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
    
    public VentaDTO read(VentaDTO dto){
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trans = s.getTransaction();
        
        try{
        trans.begin();
        dto.setEntidad(s.get(dto.getEntidad().getClass(),dto.getEntidad().getIdVenta() ));
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
        Query q = s.createQuery("from Venta v order by v.idVenta");
        l=q.list();
        trans.commit();
        }catch(HibernateException he)
        {
            if(trans!=null && trans.isActive())
                trans.rollback();
        }
        return l;
    }
}
