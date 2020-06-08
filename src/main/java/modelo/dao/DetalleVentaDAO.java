/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.util.List;
import modelo.dto.DetalleVentaDTO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utilerias.HibernateUtil;

/**
 *
 * @author papitojaime
 */
public class DetalleVentaDAO {
    public void create(DetalleVentaDTO dto){
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
    
    public void createSinMedico(DetalleVentaDTO dto){
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trans = s.getTransaction();
        
        try{
        trans.begin();
        Query query = s.createSQLQuery("INSERT INTO detalleventa (idproducto,idventa,cantidad,precio) VALUES (:v1,:v2,:v3,:v4)");
        query.setParameter("v1", dto.getEntidad().getIdProducto());
        query.setParameter("v2",dto.getEntidad().getIdVenta());
        query.setParameter("v3",dto.getEntidad().getCantidad());
        query.setParameter("v4",dto.getEntidad().getPrecio());
         query.executeUpdate();
        trans.commit();
        }catch(HibernateException he)
        {
            if(trans!=null && trans.isActive())
                trans.rollback();
        }
    }
    
    public void update(DetalleVentaDTO dto){
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
    
    public void delete(DetalleVentaDTO dto){
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
    
    public DetalleVentaDTO read(DetalleVentaDTO dto){
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trans = s.getTransaction();
        
        try{
        trans.begin();
        dto.setEntidad(s.get(dto.getEntidad().getClass(),dto.getEntidad().getIdDVenta() ));
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
        Query q = s.createQuery("from DetalleVenta dv order by dv.idDVenta");
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
