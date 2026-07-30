package Diccionario;

public class NodoAVLDicc {
    //Atributos//
    private Comparable clave;
    private Object dato;
    private int altura;
    private NodoAVLDicc izquierdo;
    private NodoAVLDicc derecho;
    
    public NodoAVLDicc(Comparable clave,Object dato,NodoAVLDicc hijoIzquierdo,NodoAVLDicc hijoDerecho){
        /*Contructor del nodo del Diccionario*/
        this.clave = clave;
        this.dato = dato;
        this.derecho = hijoIzquierdo;
        this.derecho = hijoDerecho;
        altura = 0;
    }

    public Comparable getClave(){
        return clave;
    }
    
    public void setClave(Comparable clave){
        this.clave = clave;
    }
    
    public Object getDato(){
        return dato;
    }
    
    public void setDato(Object dato){
        this.dato = dato;
    }
    
    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public NodoAVLDicc getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoAVLDicc hijoIzquierdo) {
        this.izquierdo = hijoIzquierdo;
    }

    public NodoAVLDicc getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoAVLDicc hijoDerecho) {
        this.derecho = hijoDerecho;
    }
    
    public void recalcularAltura() {
        /*Suponiendo que no tiene hijos seteo la altura a 0*/
        this.altura = 0;
        if (this.izquierdo != null && this.derecho != null) {
            this.altura = Math.max(this.izquierdo.getAltura(), this.derecho.getAltura()) + 1;
        } else {
            if (this.izquierdo != null) {
                this.altura = this.izquierdo.getAltura() + 1;
            } else {
                if (this.derecho != null) {
                    this.altura = this.derecho.getAltura() + 1;
                }
            }
        }
        
    }

}
