/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineales.estaticas;

/**
 *
 * @author erick
 */
public class Pila {

    private int tope;
    private Object[] arrPila;
    private static final int TAMANIO = 10;
    //Constructor vacio
    public Pila() {
        this.arrPila = new Object[TAMANIO];
        this.tope = -1;
    }

    public boolean apilar(Object nuevoElem) {
        boolean exito;
        
        if (this.tope+1 >= this.TAMANIO){
            //error pila llena//
            exito = false;
        }else{
            //pone el elemento en el tope de la fila e incrementa en la fila//
            this.tope++;
            this.arrPila[tope]=nuevoElem;
            exito=true;
            }
        return exito;
    }
    //Metodo que desapila la pila y deuvuelve False, en caso de que este vacia.
    public boolean desapilar(){
        boolean exito;
        if(this.tope<=-1){
            //error pila vacia//
            exito=false;
        }else{
            //va eliminando y desapilando de la fila//
            this.arrPila[tope]=null;
            this.tope--;
            exito=true;
        }
        return exito;
    }
    //Metodo que devuelve el tope de la pila, o Null si esta vacia.
    public Object obtenerTope(){
        Object elem;
        if(this.tope<=-1){
           //el tope es -1 por lo  tanto la pila esta vacia//
           elem= null;
        }else{
           //sino se lo asigno//
           elem= arrPila[tope];
        }
        return elem;
    }
    //Metodo que verifica si la pila se encuentra vacia.
    public boolean esVacia(){
        
        return this.tope== -1;
    }
    //Metodo vaciar Pila.
    public void vaciar(){
        int i;
        //Una repetitiva for que pone null a cada elemento (entre 0 y tope)//
        for(i=0; i<= this.tope; i++){
            this.arrPila[i]=null;
        }
        this.tope=-1;
    }
    //Metodo que clona la Pila original.
    public Pila clone(){
        Pila pClon = new Pila();
        pClon.tope = this.tope;
        pClon.arrPila = this.arrPila.clone();
        return pClon;
    }
    
    public String toString(){
       String mostrar="";
       if(this.tope<=-1)
           mostrar="Pila Vacia";
       else{
           mostrar="[";
            int i=0;
            while (i<=this.tope){
           mostrar+=" "+this.arrPila[i].toString();
           i++;
       }
       mostrar+="]";
       }
       return mostrar;
   }
    
    public Boolean esCapicua (Pila pAux){
        int n= 0, m;
        m= pAux.tope;
        Boolean esCapicua=false;
        Pila pMetodo= new Pila();
        
        while(!pAux.esVacia()){
            
            pMetodo.apilar(pAux.obtenerTope());
            System.out.println("Pila apilandose" +pMetodo);
            if(pMetodo.obtenerTope().equals(pAux.obtenerTope())){ //ACA FIJATE CUAL PIJA ES EL 
                esCapicua= true;
                System.out.println("ENTRO");
            }
            pAux.desapilar();
            System.out.println("Pila despilandose" + pAux);
            System.out.println("");
            n++;
            m--;
        }
        System.out.println("SALIO");
        return esCapicua;
        
    }
    
}