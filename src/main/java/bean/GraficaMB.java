/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entidades.Producto;
import entidades.Venta;
import javax.inject.Named;  
import java.io.Serializable;  
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;  
import modelo.dao.ProductoDAO;
import modelo.dao.VentaDAO;
import org.primefaces.PrimeFaces;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author ramms
 */
@ManagedBean(name = "graficaMB")
@Named(value = "graficaMB")
public class GraficaMB implements Serializable{
    private PieChartModel pieModel;
    private BarChartModel animatedModel2;
    private List<Producto> listado;
    private List<Venta> listadoT;
    

    @PostConstruct
    public void init() {
        createPieModels();
        createAnimatedModels();
    }
        
    public void showGrafica(){
        init();
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('DialogoGraficaExistencias').show();");
        PrimeFaces currentV = PrimeFaces.current();
        currentV.executeScript("PF('DialogoGraficaVentas').show();");
    }
    
    public PieChartModel getPieModel() {
        return pieModel;
    }
    
    public BarChartModel getAnimatedModel2() {
        return animatedModel2;
    }
     
    private void createPieModels() {
        createPieModel();
    }
    
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
        listadoT = new ArrayList();
        
        VentaDAO dao = new VentaDAO();
        
        listadoT = dao.readAll();
        
        ChartSeries dineroVentas = new ChartSeries();
        dineroVentas.setLabel("Total Vendido");
        for(int i=0;i<listadoT.size();i++){
            BigDecimal numT = listadoT.get(i).getTotal();
            Number total = (Number)numT;
            String numVenta =String.valueOf(listadoT.get(i).getIdVenta());
            dineroVentas.set("Numero de Venta:"+numVenta,total);
        }
 
        model.addSeries(dineroVentas);
 
        return model;
    }
    
     private void createAnimatedModels() {
        animatedModel2 = initBarModel();
        animatedModel2.setTitle("Total por Venta");
        animatedModel2.setAnimate(true);
        animatedModel2.setLegendPosition("ne");
        Axis yAxis = animatedModel2.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(1500);
    }
    
    private void createPieModel() {
        pieModel = new PieChartModel();
        listado = new ArrayList();
        ProductoDAO dao = new ProductoDAO();
        listado = dao.readAll();
        
        for(int i=0;i<listado.size();i++){
            int e = listado.get(i).getExistencia();
            Number numE = (Number)e;
            pieModel.set((String)listado.get(i).getNombre(),e);
        }
                
        pieModel.setTitle("Existencias en el Almacén");
        pieModel.setLegendPosition("c");
    }
    
    
    

    
}
