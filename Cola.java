/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineales.estaticas;

/**
 *
 * @author Santiago Porollan ^ Franco Benitez ^ Erick Benicio
 */
public class Cola {
    
    private Object[] arrCola;
    private int frente;
    private int fin;
    private static final int TAMANIO=10;
    
    public Cola(){
        this.arrCola= new Object[this.TAMANIO];
        this.frente= 0;
        this.fin= 0;
    }
    
    // Pone el elemento al final de la cola. Devuelve verdadero si el elemento se pudo agregar en la estructura
    //y falso en caso contrario.
    public boolean poner(Object elem){
        boolean exito=false;
        
        if(frente!=((fin+1)%TAMANIO)){
            //Verifica que el frente no sea igual a fin+1, sino ta lleno
            arrCola[this.fin]=elem;
            this.fin= (this.fin+1)%TAMANIO;
            exito=true;
        }
        return exito;
    }
    
    // Saca el elemento que está en el frente de la cola. Devuelve verdadero si el elemento se pudo sacar (la
    //estructura no estaba vacía) y falso en caso contrario.
    public boolean sacar(){
        boolean exito=true;
        
        if(this.esVacia()){
            //Cola vacia, reporta error
            exito=false;
            
        }else{
            //Al menos hay 1 elemento: avanza frente (de manera circular)
            this.arrCola[this.frente]= null;
            this.frente= (this.frente+1)% this.TAMANIO;
        }
        return exito;
    }
    
    // Devuelve el elemento que está en el frente. Precondición: la cola no está vacía
    public Object obtenerFrente(){
        Object elem=null;
        
        if(!esVacia()){
            //Cola no vacia, retorna elem
            elem= this.arrCola[frente];
        }
        return elem;
    }
    
    // Devuelve verdadero si la cola no tiene elementos y falso en caso contrario
    public boolean esVacia(){
        boolean vacia=false;
        
        if(this.frente== this.fin){
            //Cuando la cola está vacía se verifica frente = final.
            vacia=true;
        }
        return vacia;
    }
    
    // Saca todos los elementos de la estructura.
    public void vaciar(){
        
        if (!this.esVacia()) {
	frente = 0;
	fin = 0;
	for (int i = 0; i < this.arrCola.length; i++) {
            this.arrCola[i] = null;
            }
       }
    }
    
    // Devuelve una copia exacta de los datos en la estructura original, y respetando el orden de los mismos,
    //en otra estructura del mismo tipo
    public Cola clone(){
        
        Cola colaClon= new Cola();
        
        colaClon.frente= this.frente;
        colaClon.fin= this.fin;
        //uso el clon de arreglos
        colaClon.arrCola= this.arrCola.clone();
        return colaClon;
    }
    
    public String toString(){
        int auxFrente= this.frente, longitud= this.arrCola.length;
        String mostrar="[]";
        if(!esVacia()){
           mostrar="[";
            while(auxFrente!= (this.fin)% longitud){
                mostrar += this.arrCola[auxFrente]+",";
                auxFrente= (auxFrente+1)%longitud;
            }
            mostrar+="]";
        }
        return mostrar;
    }
    
}
