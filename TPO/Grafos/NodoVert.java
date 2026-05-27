package Grafos;

public class NodoVert{

    //Atributos
    private Object vertice=null;
    private NodoVert sigVert=null;
    private NodoAdy primerAdy=null;

    //Constructor
    public NodoVert(Object vert, NodoVert sigVert, NodoAdy primerAdy){
        this.vertice= vert;
        this.sigVert= sigVert;
        this.primerAdy= primerAdy;
    }

    public Object getElem(){
        return this.vertice;
    }
    
    public void setVertice(Object vertice){
        this.vertice= vertice;
    }
    
    public NodoVert getSigVert() {
        return this.sigVert;
    }
    
    public void setSigVert(NodoVert sigVert) {
        this.sigVert = sigVert;
    }
    
    public NodoAdy getPrimerAdy() {
        return primerAdy;
    }
    
    public void setPrimerAdy(NodoAdy primerAdy) {
        this.primerAdy = primerAdy;
    }

}