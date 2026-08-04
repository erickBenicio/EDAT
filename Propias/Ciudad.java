/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDAT.Propias;
import java.util.HashMap;
import lineales.dinamicas.Lista;
/**
 *
 * @author erick
 */
public class Ciudad implements Comparable<Ciudad>{
    
    
    private String codigoPostal;
    private String nombreCiudad;
    private String nombreProvincia;
    private HashMap <String, Lista> solicitudesDestino;

    public Ciudad (String cp, String nc, String np){
        this.codigoPostal= cp;
        this.nombreCiudad= nc;
        this.nombreProvincia= np;
        this.solicitudesDestino= new HashMap<>();

    }

    public void agregarSolicitud(Solicitud s) {
        String destino = s.getPostalDestino();
        // Si no existe una lista para esa ciudad destino, se crea
        if (!this.solicitudesDestino.containsKey(destino)) {
            this.solicitudesDestino.put(destino, new Lista());
        }
        // Inserto la solicitud en la lista correspondiente
        Lista listaDestino = this.solicitudesDestino.get(destino);
        listaDestino.insertar(s, listaDestino.longitud() + 1);
    }

    // Retorna la lista de solicitudes
    public Lista getSolicitudesHacia(String cpDestino) {
        if (this.solicitudesDestino.containsKey(cpDestino)) {
            return this.solicitudesDestino.get(cpDestino);
        }
        return new Lista(); // Retorna lista vacía si no hay
    }
    public String getCodigoP(){
        return codigoPostal;
    }

    public void setCodigoP(String cp){
        this.codigoPostal= cp;
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

    @Override
    public int compareTo(Ciudad otraCiudad) {
        // Aprovechamos el compareTo nativo de la clase String
        return this.codigoPostal.compareTo(otraCiudad.getCodigoP());
    }
    
    @Override
    public String toString() {
        return codigoPostal + " - " + nombreCiudad + ", " + nombreProvincia;
    }

}
