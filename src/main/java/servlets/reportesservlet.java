/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SessionImplementor;
import utilerias.HibernateUtil;


@WebServlet(name = "ReportePDF", urlPatterns = {"/medico/reportesservlet", "/categoria/reportesservlet", "/usuario/reportesservlet", "/venta/reportesservlet", "/Entrada/reportesservlet", "/detalle/reportesservlet", "/producto/reportesservlet", "/reportesservlet"})
public class reportesservlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, JRException {
        
        String accion = request.getParameter("accion");
        
        if(accion.equals("reporteEntradas")){
            reporteEntradas(request, response);
        }else{
            if(accion.equals("reportemedicos")){
                reporteMedicos(request, response);
            }else{
                if(accion.equals("reporteVentas")){
                    reporteVentasG(request, response);
                }else{
                    if(accion.equals("reporteProductosE")){
                        reporteProductosE(request, response);
                    }else{
                        if(accion.equals("reporteProductosG")){
                            reporteProductosG(request, response);
                        }else{
                            if(accion.equals("reporteUsuarios")){
                                reporteUsuarios(request, response);
                            }else{
                                if(accion.equals("reporteDetalleV")){
                                    reporteDetalleV(request, response);
                                }else{
                                    if(accion.equals("reporteDetalleE")){
                                        reporteDetalleE(request, response);
                                    }else{
                                       if(accion.equals("reporteCategoria")){
                                            reporteCategoria(request, response);
                                       } 
                                    }
                                }
                            }    
                        }    
                    }
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(reportesservlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(reportesservlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(reportesservlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(reportesservlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public void reporteMedicos(HttpServletRequest request, HttpServletResponse response) throws SQLException, JRException {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        SessionImplementor implementor = (SessionImplementor)s;
        Transaction trans = s.getTransaction();
        try {
            try (ServletOutputStream sos = response.getOutputStream()) {
                File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/ListaMedicos.jasper"));
                byte[] bytes;
                trans.begin();
                bytes = JasperRunManager.runReportToPdf(reporte.getPath(),null,implementor.connection());
                trans.commit();
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                sos.write(bytes, 0, bytes.length);
                sos.flush();
               
            }
        } catch (IOException e) {
        }
    }

    private void reporteVentasG(HttpServletRequest request, HttpServletResponse response) throws SQLException, JRException {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        SessionImplementor implementor = (SessionImplementor)s;
        Transaction trans = s.getTransaction();
        try {
            try (ServletOutputStream sos = response.getOutputStream()) {
                File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/GeneralVentas.jasper"));
                byte[] bytes;
                trans.begin();
                bytes = JasperRunManager.runReportToPdf(reporte.getPath(),null,implementor.connection());
                trans.commit();
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                sos.write(bytes, 0, bytes.length);
                sos.flush();
               
            }
        } catch (IOException e) {
        }
    }

    private void reporteProductosE(HttpServletRequest request, HttpServletResponse response) throws SQLException, JRException {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        SessionImplementor implementor = (SessionImplementor)s;
        Transaction trans = s.getTransaction();
        try {
            try (ServletOutputStream sos = response.getOutputStream()) {
                File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/ListaProductosE.jasper"));
                byte[] bytes;
                trans.begin();
                bytes = JasperRunManager.runReportToPdf(reporte.getPath(),null,implementor.connection());
                trans.commit();
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                sos.write(bytes, 0, bytes.length);
                sos.flush();
               
            }
        } catch (IOException e) {
        }
    }

    private void reporteProductosG(HttpServletRequest request, HttpServletResponse response) throws SQLException, JRException {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        SessionImplementor implementor = (SessionImplementor)s;
        Transaction trans = s.getTransaction();
        try {
            try (ServletOutputStream sos = response.getOutputStream()) {
                File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/ListaProductosGe.jasper"));
                byte[] bytes;
                trans.begin();
                bytes = JasperRunManager.runReportToPdf(reporte.getPath(),null,implementor.connection());
                trans.commit();
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                sos.write(bytes, 0, bytes.length);
                sos.flush();
               
            }
        } catch (IOException e) {
        }
    }

    private void reporteUsuarios(HttpServletRequest request, HttpServletResponse response) throws SQLException, JRException {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        SessionImplementor implementor = (SessionImplementor)s;
        Transaction trans = s.getTransaction();
        try {
            try (ServletOutputStream sos = response.getOutputStream()) {
                File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/ListaUsuarios.jasper"));
                byte[] bytes;
                trans.begin();
                bytes = JasperRunManager.runReportToPdf(reporte.getPath(),null,implementor.connection());
                trans.commit();
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                sos.write(bytes, 0, bytes.length);
                sos.flush();
               
            }
        } catch (IOException e) {
        }
    }
    
    private void reporteEntradas(HttpServletRequest request, HttpServletResponse response) throws SQLException, JRException {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        SessionImplementor implementor = (SessionImplementor)s;
        Transaction trans = s.getTransaction();
        try {
            try (ServletOutputStream sos = response.getOutputStream()) {
                File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/GeneralEntradas.jasper"));
                byte[] bytes;
                trans.begin();
                bytes = JasperRunManager.runReportToPdf(reporte.getPath(),null,implementor.connection());
                trans.commit();
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                sos.write(bytes, 0, bytes.length);
                sos.flush();
               
            }
        } catch (IOException e) {
        }
    }

    private void reporteDetalleV(HttpServletRequest request, HttpServletResponse response) throws JRException {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        SessionImplementor implementor = (SessionImplementor)s;
        Transaction trans = s.getTransaction();
        try {
            try (ServletOutputStream sos = response.getOutputStream()) {
                int idDV = Integer.parseInt(request.getParameter("idDV"));
                Map parametro = new HashMap();
                parametro.put("Id_Venta", idDV);
                File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/DetalleVenta.jasper"));
                byte[] bytes;
                trans.begin();
                bytes = JasperRunManager.runReportToPdf(reporte.getPath(),parametro,implementor.connection());
                trans.commit();
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                sos.write(bytes, 0, bytes.length);
                sos.flush();
               
            }
        } catch (IOException e) {
        }
    }

    private void reporteDetalleE(HttpServletRequest request, HttpServletResponse response) throws JRException {
       Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        SessionImplementor implementor = (SessionImplementor)s;
        Transaction trans = s.getTransaction();
        try {
            try (ServletOutputStream sos = response.getOutputStream()) {
                int idDE = Integer.parseInt(request.getParameter("idDE"));
                Map parametro = new HashMap();
                parametro.put("Id_Entrada", idDE);
                File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/DetalleEntrada.jasper"));
                byte[] bytes;
                trans.begin();
                bytes = JasperRunManager.runReportToPdf(reporte.getPath(),parametro,implementor.connection());
                trans.commit();
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                sos.write(bytes, 0, bytes.length);
                sos.flush();
               
            }
        } catch (IOException e) {
        }
    }

    private void reporteCategoria(HttpServletRequest request, HttpServletResponse response) throws JRException {
       Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        SessionImplementor implementor = (SessionImplementor)s;
        Transaction trans = s.getTransaction();
        try {
            try (ServletOutputStream sos = response.getOutputStream()) {
                File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/GeneralCategoria.jasper"));
                byte[] bytes;
                trans.begin();
                bytes = JasperRunManager.runReportToPdf(reporte.getPath(),null,implementor.connection());
                trans.commit();
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                sos.write(bytes, 0, bytes.length);
                sos.flush();
               
            }
        } catch (IOException e) {
        }
    }
    
    

}
