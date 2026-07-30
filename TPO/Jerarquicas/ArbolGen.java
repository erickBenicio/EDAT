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
 * @author Santiago Porollan ^ Franco Benitez ^ Erick Benicio
 */
public class ArbolGen {

    private NodoGen raiz;

    // Crea un árbol genérico vacío
    public ArbolGen() {
        this.raiz = null;
    }

    // Dado un elemento elemNuevo y un elemento elemPadre, agrega elemNuevo como hijo de la primer aparición
    //de elemPadre. Para que la operación termine con éxito debe existir un nodo en el árbol con elemento =
    //elemPadre. No se establece ninguna preferencia respecto a la posición del hijo respecto a sus posibles hermanos.
    //Esta operación devuelve verdadero cuando se pudo agregar elemNuevo a la estructura y falso en caso contrario.

    public boolean insertar(Object elem, Object elemPadre) {

        boolean exito = true;
        if (this.raiz == null) {
            // si el arbol esta vacio, ponemos el elem nuevo en la raiz
            this.raiz = new NodoGen(elem, null, null);
        } else {
            // si no esta vacio, se busca al padre
            NodoGen nodoPadre = obtenerNodo(this.raiz, elemPadre);
            if (nodoPadre != null) {
                // Si encontro el nodo padre pregunto por el HEI
                NodoGen hijo = nodoPadre.getHijoIzquierdo();
                NodoGen nuevo = new NodoGen(elem, null, null);
                if (hijo == null) {
                    // Si tiene HEI pregunta si no posee hermano derecho le setea el nodo nuevo creado anteriormente
                    // Sino lo tiene recorre los hermanos derecho de HEI hasta llegar al ultimo y le setea el nodo nuevo
                    // creado anteriormente
                    nodoPadre.setHijoIzquierdo(nuevo);
                } else {
                    //Si tiene hijo izquierdo, debo insertarlo al final de sus hermanos derechos
                    while (hijo.getHermanoDerecho() != null) {
                        hijo = hijo.getHermanoDerecho();
                    }
                    hijo.setHermanoDerecho(nuevo);
                }
            } else {
                exito = false;
            }
        }
        return exito;
    }

    private NodoGen obtenerNodo(NodoGen n, Object buscado) {

        // metodo privado que busca un elemento y devuelve el nodo que
        // lo contiene. Si no se encuentra buscado devuelve null
        NodoGen resultado = null;
        if (n != null) {
            if (n.getElem().equals(buscado)) {
                // si el buscado es n, lo devuelve
                resultado = n;
            } else {
                // no es el buscado: busca primero en el HEI
                resultado = obtenerNodo(n.getHijoIzquierdo(), buscado);
                // si no lo encuentra en el HEI, busca en los HD
                if (resultado == null) {
                    resultado = obtenerNodo(n.getHermanoDerecho(), buscado);
                }
            }
        }
        return resultado;
    }

    // Devuelve verdadero si el elemento pasado por parámetro está en el árbol, y falso en caso contrario.
    public boolean pertenece(Object buscado) {

        boolean existe = false;
        if (this.raiz != null) {//&& buscado!= null
            existe = perteneceAux(this.raiz, buscado);
        }
        return existe;
    }

    private boolean perteneceAux(NodoGen n, Object buscado) {
        // Devuelve verdadero si el elemento pasado por parametro esta en el arbol, y falso en caso contrario.
        boolean encontrado;
        encontrado = false;

        if (n != null) {
            if (n.getElem().equals(buscado)) {
                // si el buscado es n, lo devuelve
                encontrado = true;
            } else {
                // no es el buscado: busca primero en el HEI
                encontrado = perteneceAux(n.getHijoIzquierdo(), buscado);
                // si no lo encuentra en el HEI, busca en los HD
                if (!encontrado) {
                    encontrado = perteneceAux(n.getHermanoDerecho(), buscado);
                }
            }
        }
        return encontrado;
    }

    // Devuelve falso si hay al menos un elemento cargado en el árbol y verdadero en caso contrario.
    public boolean esVacio() {

        return this.raiz == null;
    }

    // Dado un elemento devuelve el valor almacenado en su nodo padre (busca la primera aparición de elemento).
    public Object padre(Object buscado) {
        Object res = null;
        if (this.raiz != null) {
            if (!this.raiz.getElem().equals(buscado)) {   // Si el elemento buscado no está en la raíz lo busca
                res = padreAux(this.raiz, buscado, this.raiz.getElem());
            }
        }
        return res;
    }

    private Object padreAux(NodoGen nodoActual, Object buscado, Object padre) {

        Object resultado = null;
        if (nodoActual != null) {
            if (nodoActual.getElem().equals(buscado)) {
                resultado = padre;
            } else {
                resultado = padreAux(nodoActual.getHermanoDerecho(), buscado, padre);
                if (resultado == null) {
                    resultado = padreAux(nodoActual.getHijoIzquierdo(), buscado, nodoActual.getElem());
                }
            }
        }
        return resultado;
    }

    // Devuelve la altura del árbol, es decir la longitud del camino más largo desde la raíz hasta una hoja (Nota:
    //un árbol vacío tiene altura -1 y una hoja tiene altura 0).
    public int altura() {

        int res = -1;
        if (this.raiz != null) {
            res = alturaAux(this.raiz, -1);
        }
        return res;
    }

    private int alturaAux(NodoGen n, int altura) {

        int resIzq, resDer;
        if (n != null) {
            resIzq = alturaAux(n.getHijoIzquierdo(), altura + 1);
            resDer = alturaAux(n.getHermanoDerecho(), altura);
            altura = resDer;
            if (resIzq > resDer) {
                altura = resIzq;
            }
        }
        return altura;
    }

    // Devuelve el nivel de un elemento en el árbol. Si el elemento no existe en el árbol devuelve -1.
    public int nivel(Object elem) {

        int res = -1;
        if (this.raiz != null) {
            res = nivelAux(this.raiz, elem, -1);
        }
        return res;
    }

    private int nivelAux(NodoGen n, Object buscado, int nivel) {

        if (n != null) {
            if (n.getElem().equals(buscado)) {
                nivel++;
            } else {
                nivel = nivelAux(n.getHijoIzquierdo(), buscado, nivel);
                if (nivel != -1) {
                    nivel++;
                } else {
                    nivel = nivelAux(n.getHermanoDerecho(), buscado, nivel);
                }
            }
        }
        return nivel;
    }

    // Si el elemento se encuentra en el árbol, devuelve una lista con el camino desde la raíz hasta dicho elemento
    //(es decir, con los ancestros del elemento). Si el elemento no está en el árbol devuelve la lista vacía.
    public Lista ancestros(Object elem) {
        Lista ls = new Lista();

        if (this.raiz != null) {
            ancestrosAux(this.raiz, ls, elem);
        }
        return ls;
    }

    private boolean ancestrosAux(NodoGen n, Lista ls, Object elem) {

        boolean encontrado = false;
        if (n != null) {
            if (n.getElem().equals(elem)) {
                encontrado = true;
            } else {
                encontrado = ancestrosAux(n.getHijoIzquierdo(), ls, elem);
                if (encontrado) {
                    ls.insertar(n.getElem(), ls.longitud() + 1);
                } else {
                    encontrado = ancestrosAux(n.getHermanoDerecho(), ls, elem);
                }
            }
        }
        return encontrado;
    }

    // Genera y devuelve un árbol genérico que es equivalente (igual estructura y contenido de los nodos) que el
    //árbol original.
    public ArbolGen clone() {

        ArbolGen clon = new ArbolGen();
        if (this.raiz != null) {
            clon.raiz = cloneAux(this.raiz);
        }
        return clon;
    }

    private NodoGen cloneAux(NodoGen n) {
        NodoGen nuevo = null;

        if (n != null) {
            // Obtengo el nodo raiz
            nuevo = new NodoGen(n.getElem(), null, null);
            // Recorro los subarboles izquierdo y derecho
            nuevo.setHijoIzquierdo(cloneAux(n.getHijoIzquierdo()));
            nuevo.setHermanoDerecho(cloneAux(n.getHermanoDerecho()));
        }
        return nuevo;
    }

    // Quita todos los elementos de la estructura.
    public void vaciar() {
        this.raiz = null;
    }

    // Genera y devuelve una cadena de caracteres que indica cuál es la raíz del árbol y quienes son los hijos de
    //cada nodo.
    public String toString() {

        return toStringAux(this.raiz);

    }

    private String toStringAux(NodoGen n) {

        String s = "";
        if (n != null) {
            //Visita del nodo n
            s += n.getElem().toString() + " -> ";
            NodoGen hijo = n.getHijoIzquierdo();
            while (hijo != null) {
                s += hijo.getElem().toString() + ",";
                hijo = hijo.getHermanoDerecho();
            }
            
            //comienza recorrido de los hijos de n llamados recursivamente
            //para que cada hijo agregue su subcadena a la general
            hijo = n.getHijoIzquierdo();
            while (hijo != null) {
                s += "\n" + toStringAux(hijo);
                hijo = hijo.getHermanoDerecho();
            }
        }
        return s;
    }

    // Devuelve una lista con los elementos del árbol en el recorrido en preorden
    public Lista listarPreorden() {

        Lista salida = new Lista();
        listarPreordenAux(this.raiz, salida);
        return salida;
    }

    private void listarPreordenAux(NodoGen n, Lista ls) {

        if (n != null) {
            ls.insertar(n.getElem(), ls.longitud()+1);
            NodoGen hijo = n.getHijoIzquierdo();
            while(hijo!= null){
                //ls.insertar(n.getElem(), ls.longitud()+1);
                listarPreordenAux(hijo,ls);
                hijo=hijo.getHermanoDerecho();
            }
        }
        
    }

    // Devuelve una lista con los elementos del árbol en el recorrido en inorden
    public Lista listarInorden() {

        Lista salida = new Lista();
        listarInordenAux(this.raiz, salida);
        return salida;
    }

    private void listarInordenAux(NodoGen n, Lista ls) {

        if (n != null) {
            //llamado recursivo con primer hijo de n
            if (n.getHijoIzquierdo() != null) {
                listarInordenAux(n.getHijoIzquierdo(), ls);
            }
            //visita del nodo n
            ls.insertar(n.getElem(), ls.longitud() + 1);

            //llamados recursivos con los otros hijos de n
            if (n.getHijoIzquierdo() != null) {
                NodoGen hijo = n.getHijoIzquierdo().getHermanoDerecho();
                while (hijo != null) {
                    listarInordenAux(hijo, ls);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
    }

    // Devuelve una lista con los elementos del árbol en el recorrido en posorden
    public Lista listarPosorden() {

        Lista salida = new Lista();
        listarPosordenAux(this.raiz, salida);
        return salida;
    }
    
    private void listarPosordenAux(NodoGen n, Lista salida){
        
        if(n!= null){
            NodoGen hijo = n.getHijoIzquierdo();
            while(hijo!= null){
                listarPosordenAux(hijo, salida);
                hijo= hijo.getHermanoDerecho();
            }
            salida.insertar(n.getElem(), salida.longitud()+1);
        }
        
    }

    // Devuelve una lista con los elementos del árbol en el recorrido por niveles
    public Lista listarPorNiveles() {

        Lista salida = new Lista();
        if (this.raiz != null) {
            listarPorNivelesAux(this.raiz, salida);
        }
        return salida;
    }

    private void listarPorNivelesAux(NodoGen n, Lista ls) {

        Cola c = new Cola();
        int i = 1;
        c.poner(n);

        while (!c.esVacia()) {
            n = (NodoGen) c.obtenerFrente();
            c.sacar();
            ls.insertar(n.getElem(), i);
            i++;
            n = n.getHijoIzquierdo();
            while (n != null) {
                c.poner(n);
                n = n.getHermanoDerecho();
            }
        }
    }
    
    //Metodo que devuelve el grado de 1 nodo
    public int grado() {
        int grado = -1;
        if (this.raiz != null) {
            grado = gradoAux(this.raiz, -1);
        }
        return grado;
    }

    private int gradoAux(NodoGen n, int res) {

        NodoGen hijo;
        int resultado;
        resultado = 0;
        if (n != null) {
            hijo = n.getHijoIzquierdo();
            if (hijo != null) {
                while (hijo != null) {
                    resultado++;
                    res = gradoAux(hijo, res);
                    if (resultado > res) {
                        res = resultado;
                    }
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return res;
    }

    public int gradoSubarbol(Object elem) {

        int res = -1;
        NodoGen n = obtenerNodo(this.raiz, elem);
        if (n != null) {
            res = gradoAux(n, 0);
        }
        return res;
    }
    
    //Metodo sonFrontera que recibe una lista de elementos almacenada en una estructura del tipo TDA Lista
    //y debe verificar en forma eficiente si la lista contiene los elementos
    //de la frontera del árbol, sin importar el orden en que aparezcan los elementos en la lista.
    //Consideramos la Lista Vacia como VALIDA.
    public boolean sonFrontera(Lista unaLista){
        Lista lClon= unaLista.clone();
        boolean verif;
        
        verif= sonFronteraAux(this.raiz, lClon);
        
        return verif;    
        
    }
    
    //Metodo auxiliar de sonFrontera
    private boolean sonFronteraAux(NodoGen n, Lista lClon){
        boolean verif=false;
        int pos;
        if(lClon.esVacia()){
            verif= true;
        }else{ 
            
            if(n!=null){
            
                if(n.getHijoIzquierdo()== null){
                    if(lClon.localizar(n.getElem())!= -1){
                        pos= lClon.localizar(n.getElem());
                        lClon.eliminar(pos);
                    }
                    
                }
                verif= sonFronteraAux(n.getHijoIzquierdo(), lClon);
                if(!verif){
                    verif= sonFronteraAux(n.getHermanoDerecho(), lClon);
                }
            }
        }
        return verif;
    }
    
    //Considero a la lista vacia como verdadera.
    public boolean verifCamino(Lista ls){
        boolean cond= false;
        Lista clonL= ls.clone();
        if(!esVacio()){
            cond= verifCaminoAux(clonL, this.raiz);
        }
        return cond;
    }
    //Implemente el método verificarCamino que dada una lista, verifica si la
    //misma corresponde a un camino desde la raíz hasta algún elemento del
    //árbol.
    //En teoria despues de trstearlo varias veces con muchos ejemplos cumplio en todos
    //los casos.
    private boolean verifCaminoAux(Lista clonL, NodoGen n){
        
        boolean cond= false;
        if(clonL.esVacia()){
            cond= true;
        }else{
            if(n!= null){
                
                if(n.getElem().equals(clonL.recuperar(1))){
                        clonL.eliminar(1);
                        if(n.getHijoIzquierdo()!= null){
                        cond= verifCaminoAux(clonL, n.getHijoIzquierdo());
                    }
                }
                if(!cond){
                    cond= verifCaminoAux(clonL, n.getHermanoDerecho());
                }
                
            
            }
            
        }
        return cond;
    }
    
    public boolean equals(ArbolGen otro) {
        return equalsAux(this.raiz, otro.raiz);
    }

    private boolean equalsAux(NodoGen este, NodoGen otro) {
        boolean res = false;
        if (este == null && otro == null) {
            res = true;
        } else {
            if (este != null && otro != null) {
                if (este.getElem().equals(otro.getElem())) {
                    res = equalsAux(este.getHijoIzquierdo(), otro.getHijoIzquierdo());
                    if (res) {
                        res = equalsAux(este.getHermanoDerecho(), otro.getHermanoDerecho());
                    }
                }
            }
        }
        return res;
    }
    
    public void insertarEnPos(Object elem, NodoGen padre, int pos){ 
        if(!esVacio())
            insertarPosAux(this.raiz, elem, padre, pos);
    }
    
    private void insertarPosAux(NodoGen n, Object elem, NodoGen padre, int pos){
        
        if(n!=null){
            //considera el caso donde la pos sea invalida, vas a tener que hacer un while
            if(n.equals(padre)){
                insertarPos2(n.getHijoIzquierdo(),elem,pos,1);
            }else{
                insertarPosAux(n.getHijoIzquierdo(), elem, padre, pos);
                insertarPosAux(n.getHermanoDerecho(),elem,padre,pos);
            }
        }
        
    }
    
    private void insertarPos2(NodoGen a, Object elem, int pos, int posAct){
        
        if(a!=null){
            if (pos == 1) {
                a.setHijoIzquierdo((NodoGen) elem);
            }
            if(a.getHermanoDerecho()!=null){
                if(posAct<pos){
                    insertarPos2(a.getHermanoDerecho(),elem,pos,posAct+1);
                }else if(posAct==(pos-1)){
                    a.setHermanoDerecho((NodoGen) elem);
                }
            }
        }else{
            a.setHermanoDerecho((NodoGen)elem);
        }
        
    }
    
    
}

    




