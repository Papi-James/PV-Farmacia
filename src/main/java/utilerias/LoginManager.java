package utilerias;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author papitojaime
 */
public class LoginManager {
 
    public LoginManager(){}

    private final static String LOGIN_NAME_SESSION_ATTRIBUTE = "nombreUsuario";
    private final static String LOGIN_ID_SESSION_ATTRIBUTE = "idUsuario";

    public void login(HttpServletRequest request, HttpServletResponse response, String nombreUsuario, int IDUsuario) {
        
        HttpSession s = request.getSession(true);
        s.setAttribute(LOGIN_NAME_SESSION_ATTRIBUTE, nombreUsuario);
        s.setAttribute(LOGIN_ID_SESSION_ATTRIBUTE, IDUsuario);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {

        HttpSession s = request.getSession(false);
        s.removeAttribute(LOGIN_NAME_SESSION_ATTRIBUTE);
        s.removeAttribute(LOGIN_ID_SESSION_ATTRIBUTE);

        if (s != null) {
            s.invalidate();
        }
    }

    public boolean getLoginName(HttpServletRequest request, HttpServletResponse response) {

        HttpSession s = request.getSession(false);
        
        if (s == null) {
            return false;
        } else {

            return s.getAttribute(LOGIN_NAME_SESSION_ATTRIBUTE) != null;
        }
    }
    
    public int getLoginID(HttpServletRequest request, HttpServletResponse response){
         HttpSession s = request.getSession(false);
        
        if (s == null) {
            return -1;
        } else {

            return Integer.parseInt((String)s.getAttribute(LOGIN_NAME_SESSION_ATTRIBUTE));
        }
    }

}
