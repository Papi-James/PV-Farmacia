package bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelo.dao.UsuarioDAO;
import modelo.dto.UsuarioDTO;
import utilerias.EnvioCorreo;

/**
 *
 * @author papitojaime
 */
@Named(value = "usuarioMB")
@SessionScoped
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioMB extends BaseBean implements Serializable {

    private UsuarioDAO dao = new UsuarioDAO();
    private UsuarioDTO dto;
    private List<UsuarioDTO> listaDeUsuarios;
    private List<String> tu;

    @PostConstruct
    public void init() {
        listaDeUsuarios = new ArrayList<>();
        listaDeUsuarios = dao.readAll();
        tu = new ArrayList<>();
        tu.add("Administrador");
        tu.add("Cajero");
    }

    public String prepareAdd() {
        dto = new UsuarioDTO();
        setAccion(ACC_CREAR);
        return "/usuario/usuarioForm?faces-redirect=true";
    }

    public String prepareUpdate() {
        setAccion(ACC_ACTUALIZAR);
        return "/usuario/usuarioForm?faces-redirect=true";
    }

    public String prepareIndex() {
        init();
        return "/usuario/listadoUsuarios?faces-redirect=true";
    }

    public String back() {
        return prepareIndex();
    }

    public boolean validate() {
        boolean valido = true;
        return valido;
    }

    public String add() {
        Boolean valido = validate();
        if (valido) {
            dao.create(dto);
            EnvioCorreo c = new EnvioCorreo();

            String correo = dto.getEntidad().getEmail();
            String asunto = "Registro prueba";
            String conTexto = "El registro del usuario fue realizado exitosamente";
            try {
                c.enviarEmail(correo, asunto, conTexto);
            } catch (MessagingException ex) {
                Logger.getLogger(EnvioCorreo.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (valido) {
                return prepareIndex();
            } else {
                return prepareAdd();
            }
        }
        return prepareAdd();
    }

    public String update() {
        Boolean valido = validate();
        if (valido) {
            dao.update(dto);
            if (valido) {
                return prepareIndex();
            } else {
                return prepareUpdate();
            }
        }
        return prepareUpdate();
    }

    public String returnTipoUsuario(int i) {
        return tu.get(i - 1);
    }

    public String returnNombreUsuario(int i) {
        dto = new UsuarioDTO();
        dto.getEntidad().setIdUsuario(i);
        dto = dao.read(dto);

        return dto.getEntidad().getNombre() + " " + dto.getEntidad().getPaterno();
    }

    public String delete() {
        dao.delete(dto);
        return prepareIndex();
    }

    public void seleccionarUsuario() {
        String claveSel = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new UsuarioDTO();
        dto.getEntidad().setIdUsuario(Integer.parseInt(claveSel));

        try {
            dto = dao.read(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {
        if (arg1.getId().equals("formNombreUsuario")) {
            if (((String) arg2).length() < 1) {
                throw new ValidatorException(new FacesMessage("Se debe de introducir un nombre de usuario"));
            }
            else if (((String) arg2).length() < 6) {
                throw new ValidatorException(new FacesMessage("El nombre de usuario del nuevo usuario debe de tener al menos 6 caracteres"));
            }
        } else if(arg1.getId().equals("formContrasenia")) {
            if (((String) arg2).length() < 1) {
                throw new ValidatorException(new FacesMessage("Se debe de introducir una contraseña de usuario"));
            }
        } else if(arg1.getId().equals("formNombre")) {
            if (((String) arg2).length() < 1) {
                throw new ValidatorException(new FacesMessage("El campo Nombre no puede estar vacio"));
            }
        } else if(arg1.getId().equals("formMaterno")) {
            if (((String) arg2).length() < 1) {
                throw new ValidatorException(new FacesMessage("El campo Materno no puede estar vacio"));
            }
        } else if(arg1.getId().equals("formPaterno")) {
            if (((String) arg2).length() < 1) {
                throw new ValidatorException(new FacesMessage("El campo Paterno no puede estar vacio"));
            }
        } else if(arg1.getId().equals("formContra")) {
            if (((String) arg2).length() < 1) {
                throw new ValidatorException(new FacesMessage("El campo Contraseña no puede estar vacio"));
            }
            else if (((String) arg2).length() < 6) {
                throw new ValidatorException(new FacesMessage("La contraseña del usuario debe de tener al menos 6 caracteres"));
            }
        }
        
    }

}
