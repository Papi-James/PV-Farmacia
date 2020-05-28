package modelo.dao;

import java.util.List;
import modelo.dto.EntradaDTO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utilerias.HibernateUtil;

/**
 *
 * @author papitojaime
 */
public class EntradaDAO {
 
    public void create(EntradaDTO dto){
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
    
    public void update(EntradaDTO dto){
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
    
    public void delete(EntradaDTO dto){
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
    
    public EntradaDTO read(EntradaDTO dto){
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trans = s.getTransaction();
        
        try{
        trans.begin();
        dto.setEntidad(s.get(dto.getEntidad().getClass(),dto.getEntidad().getIdEntrada() ));
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
        Query q = s.createQuery("from Entrada e order by e.idEntrada");
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
