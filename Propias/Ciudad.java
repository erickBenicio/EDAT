/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDAT.Propias;
import lineales.dinamicas.Lista;
/**
 *
 * @author erick
 */
public class Ciudad {
    
    
    private String codigoPostal;
    private String nombreCiudad;
    private String nombreProvincia;
    private HashMap <String, Lista <Solicitud>>;

    public Ciudad (String cp, String nc, String np){
        this.codigoPostal= cp;
        this.nombreCiudad= nc;
        this.nombreProvincia= np;
        //this.solicitudViaje= new Lista();

    }

    public void agregarSolicitud(Solicitud s){
        this.solicitudViaje.insertar(s,this.solicitudViaje.longitud()+1);
    }

    public Lista getSolicitudes(){
        return this.solicitudViaje;
    }
    
    public String getNombreCiudad(){
        return this.nombreCiudad;
    }

    public void setNombreCiudad(String nc){
        this.nombreCiudad= nc;
    }
    
    public String getNombreProvincia(){
        return this.nombreProvincia;
    }

    public void setNombreProvincia(String np){
        this.nombreProvincia= np;
    }

    public int compareTo(Comparable otroCp){
        int i;
        if(this.codigoPostal.equals(otroCp)){
            i=0;
        }else if(this.codigoPostal < otroCp){

        }
    }

}
