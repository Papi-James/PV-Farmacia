package modelo.dao;


import entidades.Usuario;
import java.util.List;
import modelo.dto.UsuarioDTO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utilerias.HibernateUtil;

/**
 *
 * @author papitojaime
 */
public class UsuarioDAO {
    
    public void create(UsuarioDTO dto){
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
    
    public void update(UsuarioDTO dto){
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
    
    public void delete(UsuarioDTO dto){
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
    
    public UsuarioDTO read(UsuarioDTO dto){
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trans = s.getTransaction();
        
        try{
        trans.begin();
        dto.setEntidad(s.get(dto.getEntidad().getClass(),dto.getEntidad().getIdUsuario() ));
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
        Query q = s.createQuery("from Usuario u order by u.idUsuario");
        l=q.list();
        trans.commit();
        }catch(HibernateException he)
        {
            if(trans!=null && trans.isActive())
                trans.rollback();
        }
        return l;
    }
    
    public UsuarioDTO readUser(UsuarioDTO dto) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trans = session.getTransaction();
        List l= null;
        
         try{
            trans.begin();
            Query q = session.createQuery("from Usuario u where u.nombreUsuario = '"+dto.getEntidad().getNombreUsuario()+"' and u.contrasenia = '"+dto.getEntidad().getContrasenia()+"'");
            l = q.list();
            trans.commit();
        }catch(HibernateException he){
        if(trans!=null && trans.isActive())
            trans.rollback();
        }
         if(l.isEmpty())
             return null;
         else
         {
         dto.setEntidad((Usuario)l.get(0));
         return dto;
         }
    }
    
    /*public static void main(String[] args) {
        CateDAO dao = new CategoriaDAO();
        CategoriaDTO dto = new CategoriaDTO();
        
        dto.getEntidad().setNombre("Medicamento");
        dto.getEntidad().setDescripcion("Medicamentos varios para la familia ");
        dao.create(dto);
        //System.out.println(dao.readAll());
        //dto.getEntidad().setCodCategoria(1);
        //System.out.println(dao.read(dto));
    }*/
}
