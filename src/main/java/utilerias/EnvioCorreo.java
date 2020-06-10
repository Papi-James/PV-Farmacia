/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ramms
 */
public class EnvioCorreo {
    
    public void enviarEmail(String correoDestinatario, String asunto, String texto) throws MessagingException{
        try{
            Properties p = new Properties();
            p.setProperty("mail.smtp.host","smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable","true");
            p.setProperty("mail.smtp.starttls.required","true");
            p.setProperty("mail.smtp.port","587");
            p.setProperty("mail.smtp.user","xaviescom98@gmail.com");
            p.setProperty("mail.smtp.auth","true");
            Session s = Session.getDefaultInstance(p);
            MimeMessage mensaje = new MimeMessage(s);
            mensaje.setFrom(new InternetAddress("xaviescom98@gmail.com"));
            mensaje.addRecipients(Message.RecipientType.TO,correoDestinatario);
            mensaje.setSubject(asunto);
            mensaje.setText(texto);
            Transport t = s.getTransport("smtp");
            t.connect("xaviescom98@gmail.com","xaviyjaime:v");
            t.sendMessage(mensaje,mensaje.getAllRecipients());
            t.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    public static void main(String[] args) {
        /*EnvioCorreo u = new EnvioCorreo();
        String cd = "xavigarcia7098@gmail.com";
        String asunto = "registro prueba";
        String texto = "Su registro fue satisfactorio";
        try {
            u.enviarEmail(cd, asunto, texto);
        } catch (MessagingException ex) {
            Logger.getLogger(EnvioCorreo.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
}
