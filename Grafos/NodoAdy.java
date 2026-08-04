package EDAT.Grafos;

public class NodoAdy{
    //Atributos
    private NodoVert vertice=null;
    private NodoAdy sigAdyacente=null;
    private double etiqueta = 0;
 
    //Contructores
    public NodoAdy(NodoVert vert,NodoAdy siguienteAdy,double etiqueta){
        this.vertice=vert;
        this.sigAdyacente=siguienteAdy;
        this.etiqueta = etiqueta;
    }
    
    //Observadores
    public NodoVert getVertice(){
        return this.vertice;
    }
    
    public NodoAdy getSigAdyacente(){
        return this.sigAdyacente;
    }

    public double getEtiqueta() {
        return etiqueta;
    }
    
    //Modificadores
    public void setVertice(NodoVert vert){
        this.vertice=vert;
    }
    
    public void setSigAdyacente(NodoAdy siguienteAdy){
        this.sigAdyacente=siguienteAdy;
    }

    public void setEtiqueta(double etiqueta) {
        this.etiqueta = etiqueta;
    }
    
    
    
    
}