/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ramms
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entrada_Producto implements Serializable{
       private int id;
    private int idProducto;
    private String nombre;
    private String presentacion;
    private String marca;
    private String sustancia;
    private boolean receta;
    private BigDecimal costo;
    private BigDecimal subtotal;
    private float iva;
    private int idMedico;
    private int cantidad;
    private String barras;
    private boolean tipoVenta; 
}
