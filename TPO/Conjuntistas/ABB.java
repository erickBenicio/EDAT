package TPO.Conjuntistas;
import lineales.dinamicas.Lista;

public class ABB {
    
    private NodoABB raiz;
    
    public ABB(){
        this.raiz= null;
    }
    
    public boolean pertenece(Comparable elem){
        boolean pertenece= false;
        if(elem!= null){
            pertenece= perteneceAux(this.raiz, elem);
        }
        
        return pertenece;
    }
    
    private boolean perteneceAux(NodoABB n, Comparable elem){
        
        boolean verif= false;
        
        if(n!= null){
            if(elem.compareTo(n.getElem())==0){
                verif= true;
            }else{
                if(elem.compareTo(n.getElem())< 0){
                    verif= perteneceAux(n.getIzquierdo(), elem);
                }else{
                    verif= perteneceAux(n.getDerecho(), elem);
                }
                
            }
        }  
        
        return verif;    
    }
    
    
    public boolean insertar(Comparable elem){
        boolean exito= true;
        
        if(this.raiz== null){
            this.raiz= new NodoABB(elem, null, null);
        }else{
            exito= insertarAux(this.raiz, elem);
            
        }
        
        return exito;
    }
    
    private boolean insertarAux(NodoABB n, Comparable elem){
        
        //Precondicion n no es nulo
        boolean exito= true;
        
        if(elem.compareTo(n.getElem())== 0){
            //Reporta error, elem repetido
            exito= false;
        }else if(elem.compareTo(n.getElem()) < 0){
            //elem es menor que n.getElem
            //Si tiene HI baja a la izquierda, sino agrega elem
            if(n.getIzquierdo()!= null){
                exito= insertarAux(n.getIzquierdo(), elem);
            }else{
                n.setIzquierdo(new NodoABB(elem,null,null));
            }
        }else{
            //elem es mayor que n.getElem
            //Si tiene HD baja a la derecha, sino agrega elem
            if(n.getDerecho()!= null){
                exito= insertarAux(n.getDerecho(), elem);
            }else{
                n.setDerecho(new NodoABB(elem,null,null));
            }
        }
        
        return exito;
    }
    
    //Anda de perlas
    public boolean eliminar(Comparable elem){
        boolean exito= false;
        NodoABB a;
        if(this.raiz!= null){
            a= eliminarAux(elem,this.raiz);
            if(a!=null){
                exito=true;
            }
        }
        return exito;
    }
    
    private NodoABB eliminarAux(Comparable elem, NodoABB n) {

        if (elem.compareTo(n.getElem()) < 0) {
            //Si elem es menor a n va por la izquierda
            n.setIzquierdo(eliminarAux(elem, n.getIzquierdo()));
        } else if (elem.compareTo(n.getElem()) > 0) {
            //si elem es mayor a n va por la derecha
            n.setDerecho(eliminarAux(elem, n.getDerecho()));
        } else {
            // Si no es ninguno de esos es porque elem = n
            //Nodo encontrado, realiza la eliminación

            if (n.getIzquierdo() == null && n.getDerecho() == null) {
                // Caso 1: el nodo es una hoja (no tiene hijos)
                n = null;
            } else if (n.getIzquierdo() == null) {
                // Caso 2: el nodo tiene solo un hijo a la derecha
                return n.getDerecho();
            } else if (n.getDerecho() == null) {
                // Caso 3: el nodo tiene solo un hijo a la izquierda
                return n.getIzquierdo();
            } else {
                // Caso 4: el nodo tiene dos hijos
                // Encuentra el nodo más pequeño en el subárbol derecho (sucesor)
                NodoABB sucesor = encontrarSucesor(n.getDerecho());
                // Copia el elemento del sucesor al nodo actual
                n.setElem(sucesor.getElem());
                // Elimina el sucesor recursivamente en el subárbol derecho
                n.setDerecho(eliminarAux(sucesor.getElem(), n.getDerecho()));
            }
        }

        return n;
    }

    private NodoABB encontrarSucesor(NodoABB n) {
        while (n.getIzquierdo() != null) {
            n = n.getIzquierdo();
        }
    return n;
    }
    
    public Lista listar(){
        
        Lista ls= new Lista();
        listarAux(this.raiz, ls);
        return ls;
    }
    
    private void listarAux(NodoABB n, Lista ls){
        
        if(n!= null){
            listarAux(n.getIzquierdo(), ls);
            ls.insertar((int) n.getElem(), ls.longitud()+1);
            listarAux(n.getDerecho(), ls);
        }
        
    }
    
    public boolean esVacio(){
        return this.raiz== null;
    }
    public Lista listarRango(Comparable minimo, Comparable maximo){
        
        Lista ls= new Lista();
        if(!esVacio()){
        listarRangoAux(this.raiz, ls, minimo, maximo);
        }
        return ls;
    }
    
    private void listarRangoAux(NodoABB n, Lista ls, Comparable min, Comparable max){
        
        int elemMin= n.getElem().compareTo(min);
        int elemMax= n.getElem().compareTo(max);
        
        if(n.getIzquierdo()!= null && elemMin>0){
            listarRangoAux(n.getIzquierdo(), ls, min, max);
        }    
        if(elemMax <= 0 && elemMin >= 0){
            ls.insertar(n.getElem(), ls.longitud()+1);
        }
        if(n.getDerecho()!= null && elemMax < 0){
            listarRangoAux(n.getDerecho(), ls, min, max);
        }    
        
    }
    
    public Comparable minimoElem(){
        //Devuelve el elemento mas chico del Arbol
        Comparable minimo= null;
        if(!esVacio()){
            minimo= minimoElemAux(this.raiz);
        }
        return minimo;
    }
    
    private Comparable minimoElemAux(NodoABB n){
        
        Comparable aux;
        if(n.getIzquierdo()!= null){
            aux= minimoElemAux(n.getIzquierdo());
        }else{
            aux= n.getElem();
        }
        return aux;
    }
    
    public void eliminarMinimo(){
        
        if(!esVacio()){
            eliminarMinAux(this.raiz, this.raiz.getIzquierdo());
        }
        
    }
    
    private void eliminarMinAux(NodoABB padre, NodoABB n){
        
        if(padre.getIzquierdo()==null){
            //El menor es la raiz por lo tanto tengo que eliminarlo
            //y queda como raiz el elem a la derecha
            if(padre.equals(n))
                this.raiz= padre.getDerecho();
        }else{
            
            if(n.getIzquierdo()!= null){
                eliminarMinAux(n, n.getIzquierdo());
            }else{
                padre.setIzquierdo(null);
            }
        }
        
    }
    
    
    
    public Comparable maximoElem(){
        
        Comparable maximo= null;
        if(!esVacio()){
            maximo= maximoElemAux(this.raiz);
        }
        return maximo;
    }
    
    private Comparable maximoElemAux(NodoABB n){
        
        Comparable aux;
        if(n.getDerecho()!= null){
            aux= maximoElemAux(n.getDerecho());
        }else{
            aux= n.getElem();
        }
        return aux;
    }
    
    public void vaciar(){
        this.raiz= null;
    }
    
    public String toString(){
        String mostrar="[]";
        if(this.raiz!= null){
            mostrar= toStringAux(this.raiz, " ");
        }
        return mostrar;
    }
    
    public Lista listarMayoresQue(Comparable elem, int valor){
        
        Lista res= new Lista();
        if(!esVacio()){
            listarAux(elem,valor,this.raiz, res);
        }
        return res;
    }
    
    private void listarAux(Comparable elem, int valor, NodoABB n, Lista res){
        if (n != null) {
            if (elem.compareTo(n.getElem())<0) {
                listarAux(elem, valor, n.getIzquierdo(), res);
            } else if (elem.compareTo(n.getElem())>0) {
                listarAux(elem, valor, n.getDerecho(), res);
            } else { // valor == n.getElem()
                listarSubAux(valor, n, res);
            }
        }
        /*
        if(elem.compareTo(n.getElem())==0){
                listarSubAux(valor, n, res);
                    
        }else{
            if(elem.compareTo(n.getElem())<0){
                listarAux(elem,valor,n.getIzquierdo(),res);
            }else{
                listarAux(elem,valor,n.getDerecho(),res);
            }
                
            
        }*/
    }
    
    private void listarSubAux(Comparable valor, NodoABB n, Lista res) {
    if (n != null) {
        if (valor.compareTo(n.getElem()) < 0) {
            listarSubAux(valor, n.getIzquierdo(), res);
        } else if (valor.compareTo(n.getElem()) > 0) {
            res.insertar(n.getElem(),res.longitud()+1); // Insertar al final de la lista
            listarSubAux(valor, n.getDerecho(), res);
        } else { // valor.compareTo(n.getElem()) == 0
            listarSubAux(valor, n.getIzquierdo(), res);
            listarSubAux(valor, n.getDerecho(), res);
        }
    }
}
        
        
        
        
        /*
        if(n!=null){
            if(valor.compareTo(n.getElem())>0 && n.getIzquierdo()==null && n.getDerecho()==null){
                res.insertar(n.getElem(), pos);
            }
            
            
            if(valor.compareTo(n.getElem())< 0){
                //valor es menor que n.getElem
                if(n.getIzquierdo()!=null){
                listarSubAux(valor, n.getIzquierdo(), res, pos);
                }
                res.insertar(n.getElem(), pos);
            }else{
                //valor es mayor que n.getElem
                if(n.getDerecho()!=null){
                    listarSubAux(valor,n.getDerecho(), res, pos);
                }
                res.insertar(n.getElem(), pos);
            }
            if(valor.compareTo(n.getElem())>0){
                res.insertar(n.getElem(), pos);
                listarSubAux(valor, n.getDerecho(), res, pos+1);
            }
        }
        */
    
    public boolean eliminarElemAnterior(Comparable elem){
        
        boolean verif= false;
        if(!esVacio()){
            verif= eliminarAnterior(this.raiz, elem);
        }
        return verif;
    }
    
    private boolean eliminarAnterior(NodoABB n, Comparable elem){
        boolean verif=false;
        if(n!=null){
            if(elem.compareTo(n.getElem())==0){
                verif= eliminarAnteriorAux(n.getIzquierdo(),n);
            }else if(elem.compareTo(n.getElem())>0){
                verif= eliminarAnterior(n.getDerecho(),elem);
            }else{
                verif= eliminarAnterior(n.getIzquierdo(),elem);
            }
        }
        return verif;
        
    }
    
    private boolean eliminarAnteriorAux(NodoABB n, NodoABB padre){
        boolean verif=false;
        if(n!=null){
            if(n.getDerecho()==null){
                if(n.getIzquierdo()==null){ //es hoja
                    padre.setDerecho(null);
                }else{ //tiene HI
                    padre.setDerecho(n.getIzquierdo());
                }
                verif=true;
            }else{
                verif= eliminarAnteriorAux(n.getDerecho(),n);
            }
            
        }
        return verif;
    }
    
    
    public ABB clonarParteInvertida(Comparable elem){
        ABB clon= new ABB();
        if(!esVacio()){
            clon=clonarParteAux(this.raiz, elem);
        }
        
        return clon;
    }
    
    private ABB clonarParteAux(NodoABB n, Comparable elem){
        
        //Aca buscamos el elem para pasarlo despues
        if(n!=null){
            if(elem.compareTo(n.getElem())< 0){
                clonarParteAux(n.getIzquierdo(), elem);
            }if(elem.compareTo(n.getElem())> 0){
                clonarParteAux(n.getDerecho(),elem);
            }
            else if(elem.compareTo(n.getElem())== 0){
                //Encontramos el nodo igual a elem
                ABB clonI= new ABB();
                clonI.raiz= insertarClon(n);
                return clonI;
            }
        }
            
        return null;
    }
    
    private NodoABB insertarClon(NodoABB n) {
    
        NodoABB aux= new NodoABB(n.getElem(),null,null);
        aux.setIzquierdo((n.getDerecho()));
        aux.setDerecho((n.getIzquierdo()));
        return aux;
        
}

    
    
    
    
    private String toStringAux(NodoABB aux, String mostrar){
        NodoABB hIzq, hDer;
        
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
    //Debug public borrar despues//
    public static void main(String[] args) {
        
        ABB a = new ABB();
        
        a.insertar(8);
        a.insertar(3);
        a.insertar(10);
        a.insertar(1);
        a.insertar(6);
        a.insertar(4);
        //a.insertar(7);
        a.insertar(14);
        a.insertar(13);
        
        System.out.println(a.toString());
        
        //a.eliminar(15);
        
        if(a.eliminarElemAnterior(8)){
            System.out.println("sepudo:) "+ a.toString());
        }
        
    }
}
