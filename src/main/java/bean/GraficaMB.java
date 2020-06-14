/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.inject.Named;  
import java.io.Serializable;  
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;  
import org.primefaces.PrimeFaces;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author ramms
 */
@Named(value = "graficaMB")
@ManagedBean
public class GraficaMB implements Serializable{
    private PieChartModel pieModel;

    @PostConstruct
    public void init() {
        createPieModels();
    }
    
    public void showGrafica(){
        init();
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('DialogoGraficaExistencias').show();");
    }
    
    public PieChartModel getPieModel() {
        return pieModel;
    }
     
    private void createPieModels() {
        createPieModel();
    }
    
    private void createPieModel() {
        pieModel = new PieChartModel();
        pieModel.set("Mercedess", 700);
        pieModel.set("BMW", 300);
        pieModel.set("Volvo", 400);
        pieModel.setTitle("Existencias en el Almacén");
        pieModel.setLegendPosition("c");
    }

    
}
