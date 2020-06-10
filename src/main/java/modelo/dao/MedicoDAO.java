package modelo.dao;

import entidades.Medico;
import java.sql.Connection;
import java.util.List;
import modelo.dto.MedicoDTO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utilerias.HibernateUtil;

/**
 *
 * @author papitojaime
 */
public class MedicoDAO {
    
    public void create(MedicoDTO dto){
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
    
    public int createConRetorno(MedicoDTO dto){
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trans = s.getTransaction();
        int idGenerado=-1;
        try{
        trans.begin();
        idGenerado=(int)s.save(dto.getEntidad());
        trans.commit();
        }catch(HibernateException he)
        {
            if(trans!=null && trans.isActive())
                trans.rollback();
        }
        return idGenerado;
    }
    
    public void update(MedicoDTO dto){
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
    
    public void delete(MedicoDTO dto){
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
    
    public MedicoDTO read(MedicoDTO dto){
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trans = s.getTransaction();
        
        try{
        trans.begin();
        dto.setEntidad(s.get(dto.getEntidad().getClass(),dto.getEntidad().getIdMedico() ));
        trans.commit();
        }catch(HibernateException he)
        {
            if(trans!=null && trans.isActive())
                trans.rollback();
        }
        return dto;
    }
    
    public MedicoDTO readByCedula(MedicoDTO dto){
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trans = s.getTransaction();
        List l = null;
        try{
        trans.begin();
        Query q = s.createQuery("from Medico m where cedula='"+dto.getEntidad().getCedula()+"'");
        l=q.list();
        if(l.isEmpty())
            return null;
        dto.setEntidad((Medico)l.get(0));
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
        Query q = s.createQuery("from Medico m order by m.idMedico");
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
