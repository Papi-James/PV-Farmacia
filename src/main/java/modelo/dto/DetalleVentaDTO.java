/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dto;

import entidades.DetalleVenta;

/**
 *
 * @author papitojaime
 */
public class DetalleVentaDTO {
 
     private DetalleVenta entidad;

    public DetalleVentaDTO() {
        entidad = new DetalleVenta();
    }

    public DetalleVentaDTO(DetalleVenta entidad) {
        this.entidad = entidad;
    }

    public DetalleVenta getEntidad() {
        return entidad;
    }

    public void setEntidad(DetalleVenta entidad) {
        this.entidad = entidad;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("idDetalleVenta: ").append(getEntidad().getIdDVenta()).append("\n");
        sb.append("idProducto: ").append(getEntidad().getIdProducto()).append("\n");
        sb.append("idMedico: ").append(getEntidad().getIdMedico()).append("\n");
        sb.append("idVenta: ").append(getEntidad().getIdVenta()).append("\n");
        sb.append("cantidad: ").append(getEntidad().getCantidad()).append("\n");
        sb.append("precio: ").append(getEntidad().getPrecio()).append("\n");
        sb.append("tipoVenta: ").append(getEntidad().isTipoVenta()).append("\n");
        return sb.toString();
    }
}
