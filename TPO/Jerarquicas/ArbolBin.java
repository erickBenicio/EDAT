/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jerarquicas;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;
/**
 *
 * @author Erick Benicio
 */
public class ArbolBin {
    
    private NodoArbol raiz;
    
    public ArbolBin(){
        this.raiz= null;
    }
    
    public boolean insertar(Object elemNuevo, Object elemPadre, char queHijo){
        /*Inserta elemNuebo como hijo del primer nodo encontrado en preorden
        igual a elempadre, como hijo izquierdo (i) o derecho (d), segun
        lo indique el parametro lugar.*/
        boolean exito= true;
        
        if(this.raiz== null){
            //si el arbol esta vacio, ponemos el elemNuevo en la raiz
            this.raiz= new NodoArbol(elemNuevo, null, null);
        }else{
            //si no esta vacio, se busca el padre
            NodoArbol nodoPadre= obtenerNodo(this.raiz, elemPadre);
            if(nodoPadre != null){
                if(queHijo== 'I' && nodoPadre.getIzquierdo()==null){
                    //si nodoPadre existe y no tiene HI, se lo agrega
                    nodoPadre.setIzquierdo(new NodoArbol(elemNuevo,null,null));
                }else{
                    if(queHijo== 'D' && nodoPadre.getDerecho()== null ){
                        //si nodoPadre existe y no tiene HD, se lo agrega
                        nodoPadre.setDerecho(new NodoArbol(elemNuevo,null,null));
                    }else{
                        //si el padre no existe o ya tiene ese hijo, da error
                        exito= false;
                    }
                }
            }else{
                exito= false;
            }
        }
        return exito;
    }
    private NodoArbol obtenerNodo(NodoArbol n, Object padre){
        /*metodo Privado que busca un elemento y devuelve el nodo
        que lo contien, si no encuentra el buscado, devuelve null*/
        NodoArbol res= null;
        if (n!= null) {
            if (n.getElem().equals(padre)) {
                //si el buscado es n, lo devuelve
                res= n;
            }else{
                //no es el buscado: busca primero en el HI
                res= obtenerNodo(n.getIzquierdo(),padre);
                //si no lo encuentra en HI busca en el HD
                if (res==null) {
                    res= obtenerNodo(n.getDerecho(),padre);
                }
            }
        }
        return res;
    }
    
    public boolean esVacio(){
        
        return this.raiz==null;
    }
    
    public Object padre(Object elem){
        /* Dado un Object devuelve el valor almacenado en su nodo padre 
        (busca la primera aparición de elemento)*/
        Object res=null;//Caso en el que nodo padre sea la raiz
        
        if(this.raiz!= null){
            if(!this.raiz.getElem().equals(elem)){
                res= padreAux(this.raiz, elem);
            }
        }
        
        return res;
    }
    
    private Object padreAux(NodoArbol nodo, Object elem){
        Object res=null;
        NodoArbol hIzq, hDer;
        
        if(nodo!=null){
            hIzq= nodo.getIzquierdo();
            hDer= nodo.getDerecho();
            
            if(hIzq!= null && hIzq.getElem().equals(elem) || hDer!= null && hDer.getElem().equals(elem)){
                res= nodo.getElem();
            }else{
                res= padreAux(hIzq, elem);
                if(res== null){
                    res= padreAux(hDer, elem);
                }
            }
        }
        
        return res;
    }
    
    public int altura(){
        
        return alturaRec(this.raiz);
    }
    
    private int alturaRec(NodoArbol n){
        int r=-1, r1, r2;
        
        if(n!= null){
            if(n.getIzquierdo()== null && n.getDerecho()== null){
                r= 0;
            }else{
                r1= 1+ alturaRec(n.getIzquierdo());
                r2= 1+ alturaRec(n.getDerecho());
                if(r1 >= r2){
                    r= r1;
                }else{
                    r=r2;
                }
            }
        }
        return r;
        
    }
    public int nivel(Object buscado){
        int res=-1;
        
        if(this.raiz!=null){
            res= nivelRec(this.raiz, buscado, -1);
        }
        return res;
    }
    
    private int nivelRec(NodoArbol nodo, Object buscado, int nivel){
        
        if(nodo!= null){
            
            if(nodo.getElem().equals(buscado)){
                nivel++;
            }else{
                nivel= nivelRec(nodo.getIzquierdo(), buscado, nivel);
                
                if(nivel!= -1){
                    nivel++;
                }else{
                    nivel= nivelRec(nodo.getDerecho(), buscado, nivel);
                    if(nivel!= -1){
                        nivel++;
                    }
                }
            }
        }
        
        return nivel;
    }
    
    public void vaciar(){
        this.raiz=null;
    }
    
    public ArbolBin clone(){
        ArbolBin arbolClon = new ArbolBin();
        
        if(this.raiz != null){
            
            NodoArbol nodoAux = new NodoArbol(this.raiz.getElem(), null, null);
            arbolClon.raiz= nodoAux;
            if(this.raiz.getIzquierdo()!= null){
                nodoAux.setIzquierdo(clonarAux(this.raiz.getIzquierdo()));
            }
            if(this.raiz.getDerecho()!= null){
                nodoAux.setDerecho(clonarAux(this.raiz.getDerecho()));
            }
        }
        
        return arbolClon;
    }
    
    private NodoArbol clonarAux(NodoArbol n){
        NodoArbol auxNodo= new NodoArbol(null, null, null);
        
        if( n!= null){
            auxNodo.setElem(n.getElem());
            if(n.getIzquierdo()!= null){
                auxNodo.setIzquierdo(clonarAux(n.getIzquierdo()));
            }
            if(n.getDerecho()!= null){
                auxNodo.setDerecho(clonarAux(n.getDerecho()));
            }
        }
        return auxNodo;
    }
    
    public String toString(){
        String mostrar="[]";
        if(this.raiz!= null){
            mostrar= toStringAux(this.raiz, " ");
        }
        return mostrar;
    }
    
    private String toStringAux(NodoArbol aux, String mostrar){
        NodoArbol hIzq, hDer;
        
        if(aux!= null){
            mostrar=mostrar+ "\n" +aux.getElem()+ "\t";
            hIzq= aux.getIzquierdo();
            hDer= aux.getDerecho();
            if(hIzq!= null && hDer!= null){//Si ambos hijos no nulos
                mostrar=mostrar+ " HijoIzq: " + hIzq.getElem()+" , "+ "\tHijoDer: "+ hDer.getElem()+" , ";
            }else{
                if(hIzq!= null){//Si el izquierdo no es nulo
                    mostrar=mostrar+ "HijoIzq: " + hIzq.getElem() +" , "+ "\tHijoDer: - ";
                }else{
                    if(hDer!=null){//Si el derecho no es nulo
                        mostrar=mostrar+ "HijoIzq: - "+ "\tHijoDer: "+ hDer.getElem()+ " , ";
                    }else{//Si ambos son nulos
                        mostrar=mostrar+ "HijoIzq: - "+" , "+"\tHijoDer: - ";
                    }
                }
            }
            mostrar= toStringAux(hIzq, mostrar);
            mostrar= toStringAux(hDer, mostrar);
        }
        
    return mostrar;
    }
    
    public Lista listarPreorden(){
        Lista L1= new Lista();
        
        listarPreordenAux(this.raiz, L1);
        return L1;
    }
    
    private void listarPreordenAux(NodoArbol n, Lista L1){
        
        if(n!= null){
            L1.insertar((int) n.getElem(), L1.longitud()+1);
            
            listarPreordenAux(n.getIzquierdo(), L1);
            listarPreordenAux(n.getDerecho(), L1);
        }
    }
    
    public Lista listarPosorden(){
        Lista L1= new Lista();
        listarPosordenAux(this.raiz, L1);
        return L1;
    }
    
    private void listarPosordenAux(NodoArbol n, Lista L1){
        
        if(n!= null){
            listarPosordenAux(n.getIzquierdo(), L1);
            listarPosordenAux(n.getDerecho(), L1);
            L1.insertar((int) n.getElem(), L1.longitud()+1);
        }
    }
    
    public Lista listarInorden(){
        Lista L1= new Lista();
        listarInordenAux(this.raiz, L1);
        return L1;
    }
    
    private void listarInordenAux(NodoArbol n, Lista L1){
        
        if(n!= null){
            listarInordenAux(n.getIzquierdo(), L1);
            L1.insertar((int) n.getElem(), L1.longitud()+1);
            listarInordenAux(n.getDerecho(), L1);
        }
    }
    
    
    public Lista listarPorNiveles(){
        Lista L1= new Lista();
        
        if(this.raiz!= null){
            listarPorNivelesAux(this.raiz,L1);
        }
        
        return L1;
    }
    
    private void listarPorNivelesAux(NodoArbol n, Lista L1){
        Cola c1= new Cola();
        int i=1;
        c1.poner(n);
        
        while(!c1.esVacia()){
            n= (NodoArbol) c1.obtenerFrente();
            c1.sacar();
            L1.insertar((int) n.getElem(), i);
            i++;
            if(n.getIzquierdo()!= null){
                c1.poner(n.getIzquierdo());
            }
            if(n.getDerecho()!= null){
                c1.poner(n.getDerecho());
            }
        }
    }
    
    public Lista frontera(){
        Lista L1= new Lista();
        if(this.raiz!= null){
            fronteraAux(this.raiz, L1);
        }
        return L1;
    }
    
    private void fronteraAux(NodoArbol n, Lista L1){
        NodoArbol nodoIzq, nodoDer;
        
        if(n!= null){
            nodoIzq= n.getIzquierdo();
            nodoDer= n.getDerecho();
            
            if(nodoIzq == null && nodoDer == null){
                L1.insertar((int) n.getElem(), L1.longitud()+1);
                
            }else{
                fronteraAux(nodoIzq, L1);
                fronteraAux(nodoDer, L1);
            }
        }
    }
    
    public Boolean verifPatron(Lista patron){
        boolean verif= false;
        int pos=1;
        if(this.raiz!= null && !(patron.esVacia())){
            System.out.println("AA");
            verif= verifPatronAux(patron, this.raiz, pos);
        }
        return verif;
    }
    
    
    private boolean verifPatronAux(Lista patron, NodoArbol n, int pos){
        
        boolean verif=false;
        int longitud= patron.longitud();
            if(n!= null){
                //casos bases:
                if((n.getIzquierdo()== null && n.getDerecho()==null) && n.getElem().equals(patron.recuperar(pos))){
                    verif= true;
                    System.out.println("A");
                }
                if(verif && pos<longitud){
                    System.out.println("B");
                    verif= verifPatronAux(patron, n.getIzquierdo(), pos+1);
                    if(!verif){
                    verif= verifPatronAux(patron, n.getDerecho(), pos+1);
                    }
                }
        
                
        
            }
        return verif;
    }
    
    public boolean equals(ArbolBin otro){
        boolean verif=false;
        if(this.raiz!=null){
            verif=equalsAux(this.raiz, otro.raiz);
        }
        return verif;
    }
    
    private boolean equalsAux(NodoArbol n, NodoArbol otro){
        boolean verifAux;
        
        /*if(n==null && otro== null){
            return true;
        }else if(n==null || otro==null){
            return false;
        }
        if(n.getElem()!=otro.getElem()){
            return false;
        }*/
        if(n.getElem()== otro.getElem()){
            verifAux=true;
        }
        if(n==null || otro==null){
            verifAux=false;
        }
        
        verifAux= equalsAux(n.getIzquierdo(), otro.getIzquierdo()) && equalsAux(n.getDerecho(),otro.getDerecho());
        
        return verifAux; //((n.getElem()==otro.getElem()) && verifAux);
    }
    
    
    /*public ArbolBin clonarInvertido(){
        ArbolBin clonInvertido= new ArbolBin();
        
        if(this.raiz!= null){
            NodoArbol nodoAux= new NodoArbol(this.raiz.getElem(), null, null);
            clonInvertido.raiz= nodoAux;
            if(this.raiz.getIzquierdo()!=null){
                nodoAux.setIzquierdo(clonarInvertidoAux(this.raiz.getDerecho()));
            }
            if(this.raiz.getDerecho()!=null){
                nodoAux.setDerecho((clonarInvertidoAux(this.raiz.getIzquierdo())));
            }
        }
        return clonInvertido;
        
        
    }
    
    private NodoArbol clonarInvertidoAux(NodoArbol n){
        NodoArbol auxNodo= new NodoArbol(null,null,null);
        
        if(n!=null){
            auxNodo.setElem(n.getElem());
            if(n.getIzquierdo()!=null){
                auxNodo.setIzquierdo(clonarInvertidoAux(n.getDerecho()));
            }
            if(n.getDerecho()!=null){
                auxNodo.setDerecho(clonarInvertidoAux(n.getIzquierdo()));
            }
        }
        
        return auxNodo;
        
    }*/
    
    
    
    
}