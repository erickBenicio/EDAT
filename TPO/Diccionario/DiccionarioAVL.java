package Diccionario;
import lineales.dinamicas.Lista;
import TPO.Cliente;//falta importar la clave de cliente


public class DiccionarioAVL {
    //Atributo//
    private NodoAVLDicc raiz;
    
    //Constructor//
    public DiccionarioAVL(){
        raiz = null;
    }
    
    public boolean insertar(Comparable clave, Object dato) {
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoAVLDicc(clave, dato, null, null);
        } else {
            exito = insertarAux(this.raiz, clave, dato, null);
        }
        return exito;
    }
    
    private boolean insertarAux(NodoAVLDicc n,Comparable clave,Object dato,NodoAVLDicc padre){
        /*Precondicion n no es nulo*/
        boolean exito=true;
        if((clave.compareTo(n.getClave())==0)){
            /*Reportar error: Elemento repetido */
            exito=false;
        }else{
            if(clave.compareTo(n.getClave())< 0){
                /*Elemento es menor que n.getElem()
                Si tiene HI baja a la izquierda, sino agrega elemento*/
                if(n.getIzquierdo()!=null){
                    exito=insertarAux(n.getIzquierdo(),clave,dato,n);
                }else{
                    n.setIzquierdo(new NodoAVLDicc(clave,dato,null,null));
                }
            }else{
                /*Elemento es mayor que n.getElem()
                si tiene HD baja a la derecha, sino agrega elemento*/
                if(n.getDerecho()!=null){
                    exito=insertarAux(n.getDerecho(),clave,dato,n);
                }else{
                    n.setDerecho(new NodoAVLDicc(clave,dato,null,null));
                }
            }
        }
        /*Actualiza la altura de cada nodo a la vuelta y balancea el arbol*/
            n.recalcularAltura();
            NodoAVLDicc aux;
            aux=balancear(n);
            if(aux!=null)
                /*Si hubo rotaciones, enlaza el nuevo subArbol y actualiza las alturas*/
                reenlace(aux,padre);
            
        return exito;
    }
    
    private int calcularBalance(NodoAVLDicc nodo){
        int balance=0;
        if(nodo.getIzquierdo()!=null && nodo.getDerecho()!=null){
            balance=(nodo.getIzquierdo().getAltura())-(nodo.getDerecho().getAltura());
        }else{
            /*Calcula balance del nodo con un hijo nulo: altura -1*/
            if(nodo.getIzquierdo()!=null)
                /*Es +1 porque -(-1)= 1 */
                balance=nodo.getIzquierdo().getAltura()+1;
            if(nodo.getDerecho()!=null)
                balance=-1-nodo.getDerecho().getAltura();
        }
        return balance;
    }
    
    private NodoAVLDicc balancear(NodoAVLDicc nodo) {
        NodoAVLDicc aux = null;
        /*Calcula el balance del nodo*/
        int balance = calcularBalance(nodo);
        /*Si el balance es 0, entonces el arbol esta perfectamente baleanceado
          Si el balance es 1, entonces el arbol esta levemente inclinado hacia la izquierda
          Si el balance es -1, entonces el arbol esta levemente inclinado hacia la derecha*/
        if (balance == 2) {
            /*Si el balance es 2, el arbol esta inclinado hacia la izquierda*/
            /*Calcula balance del hijo izquierdo*/
            int balHijoIzq = -1;
            if (nodo.getIzquierdo() != null) {
                balHijoIzq = calcularBalance(nodo.getIzquierdo());
            }
            if (balHijoIzq >= 0) {
                /*El hijo izquierdo esta inclinado hacia la izquierda o tiene balance 0,
                    aplica rotacion simple a derecha*/
                aux = rotarDerecha(nodo);
            } else {
                /*El hijo izquierdo esta inclinado hacia la derecha, por lo tanto,
                    aplica rotacion doble izquierda-derecha*/
                aux = rotarIzquierdaDerecha(nodo);
            }
        } else if (balance == -2) {
            /*Si el balance es -2, el arbol esta inclinado hacia la derecha*/
            /*Calcula balance del hijo Derecho*/
            int balHijoDer = -1;
            if (nodo.getIzquierdo() != null) {
                balHijoDer = calcularBalance(nodo.getDerecho());
            }
            if (balHijoDer <= 0) {
                /*El hijo derecho esta inclinado hacia la derecha o tiene balance 0,
                    aplica rotacion simple a izquierda*/
                aux = rotarIzquierda(nodo);
            } else {
                /*El hijo derecho esta inclinado hacia la izquierda, por lo tanto,
                    aplica rotacion doble derecha-izquierda*/
                aux = rotarDerechaIzquierda(nodo);
            }
        }
        return aux;
    }
        
    
    
   private NodoAVLDicc rotarIzquierda(NodoAVLDicc r) {
        NodoAVLDicc h = r.getDerecho();
        NodoAVLDicc temp = h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(temp);
        /*Despues de rotar, recalculo las alturas*/
        r.recalcularAltura();
        if (temp != null) {
            temp.recalcularAltura();
        }
        h.recalcularAltura();
        /*Retorna la nueva raiz del subArbol*/
        return h;
    }

    private NodoAVLDicc rotarDerecha(NodoAVLDicc r){
        NodoAVLDicc h=r.getIzquierdo();
        NodoAVLDicc temp=h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(temp);
        /*Despues de rotar, recalculo las alturas*/
        r.recalcularAltura();
        /*Temp no lo recalculo porque pasa de ser un hijo al hijo de otro*/
        h.recalcularAltura();
        /*Retorna la nueva raiz del subArbol */
        return h;
    }
    
    private NodoAVLDicc rotarIzquierdaDerecha(NodoAVLDicc r2){
        /*r1 pivot hijo del nodo desbalanceado
          r2 pivot nodo desbalanceado*/
        NodoAVLDicc r1=r2.getIzquierdo(),aux;
        aux=rotarIzquierda(r1);
        reenlace(aux,r2);
        aux=rotarDerecha(r2);
        /*Retorna la nueva raiz del subArbol */
        return aux;
    }
  
    private NodoAVLDicc rotarDerechaIzquierda(NodoAVLDicc r2) {
        /*r1 pivot hijo del nodo desbalanceado
          r2 pivot nodo desbalanceado*/
        NodoAVLDicc r1 = r2.getDerecho(), aux;
        aux = rotarDerecha(r1);
        reenlace(aux, r2);
        aux = rotarIzquierda(r2);
        /*Retorna la nueva raiz del subArbol */
        return aux;
    }

    private void reenlace(NodoAVLDicc nodo,NodoAVLDicc padre){
        if(padre!=null){
            if(padre.getClave().compareTo(nodo.getClave())<0)
                padre.setDerecho(nodo);
            else
                padre.setIzquierdo(nodo);
        }else{
            this.raiz=nodo;
        }
    }
 public boolean eliminar(Comparable clave){
       boolean elimino;
       elimino=eliminarAux(clave,this.raiz,null);
       return elimino;
    }
    
      private boolean eliminarAux(Comparable elem,NodoAVLDicc nodo,NodoAVLDicc padre){
        boolean exito=false;
        if(nodo!=null){
            /*Pregunto si es nodo.getElem() es que hay que eliminar*/
            if(elem.compareTo(nodo.getClave()) == 0){
                /*Llamo metodo que diferencia casos*/
                diferenciarCasos(nodo,padre);
                /*Si encontro el nodo, entonces se va a poder eliminar*/
                exito=true;
            }else{
                /*Si no es el elemento buscado, lo busco en los hijos*/
                /*Recorre la rama correspondiente*/
                if(elem.compareTo(nodo.getClave())<0){
                    if(nodo.getIzquierdo()!=null)
                        /*Recorre hijo izquierdo*/
                        exito=eliminarAux(elem,nodo.getIzquierdo(),nodo);
                } else {
                    if (nodo.getDerecho() != null) /*Recorre los hijos derecho*/ {
                        exito = eliminarAux(elem, nodo.getDerecho(), nodo);
                    }
                }
                if (exito) {
                    /*Actualizo la altura del nodo y balancea el arbol*/
                    nodo.recalcularAltura();
                    NodoAVLDicc aux;
                    aux = balancear(nodo);
                    if (aux != null) {
                        reenlace(aux, padre);
                    }
                }
            }
        }
        return exito;
    }
    
    private void diferenciarCasos(NodoAVLDicc nodo,NodoAVLDicc padre){
        /*Metodo que diferencia los casos eliminar segun la
        cantidad de hijos del nodo*/
        if(nodo.getIzquierdo()!=null && nodo.getDerecho()!=null){
            /*LLamo al caso 3*/
            caso3(nodo.getDerecho(),padre,nodo);
        }else if(nodo.getIzquierdo()!=null || nodo.getDerecho()!=null){
            /*Llamo al caso 2*/
            caso2(nodo,padre);
        }else{
            /*Llamo al caso 1*/
            caso1(nodo, padre);
        }
    }

    private void caso1(NodoAVLDicc nodo,NodoAVLDicc padre){
        /*Si no tiene hijos*/
        if(padre==null)
            /*Si el nodo es la raiz sin hijos*/
            this.raiz=null;
        else{
            /*Reconoce al hijo que tiene que setear a null
            y lo setea*/
            if(nodo.getClave().compareTo(padre.getClave())<0)
                padre.setIzquierdo(null);
            else{
                padre.setDerecho(null);
            }
        }
        /*LA ALTURA SE RECALCULA RECURSIVAMENTE CUANDO VUELVE*/
        
    }

    private void caso2(NodoAVLDicc nodo, NodoAVLDicc padre) {
        boolean ladoIzq = false;
        /*Si tiene un hijo*/
        if (padre == null) {
            /*Si el nodo es la raiz, la nueva raiz
            es el hijo del nodo*/
            if (nodo.getIzquierdo() != null) {
                this.raiz = nodo.getIzquierdo();
            } else {
                this.raiz = nodo.getDerecho();
            }
        } else {
            /*Reconoce el lado del padre que tiene que setear*/
            if (nodo.getClave().compareTo(padre.getClave()) < 0) {
                ladoIzq = true;
            }
            /*Reconoce donde esta el nieto del padre*/
            if (nodo.getIzquierdo() != null) {
                /*El nieto esta del lado izquierdo del nodo*/
                if (ladoIzq) /*Setea el lado izquierdo del padre con 
                    el nieto*/ {
                    padre.setIzquierdo(nodo.getIzquierdo());
                } else /*Setea el lado derecho del padre con 
                    el nieto*/ {
                    padre.setDerecho(nodo.getIzquierdo());
                }
            } else {
                /*El nieto esta del lado derecho del nodo*/
                if (ladoIzq) /*Setea el lado izquierdo del padre con 
                    el nieto*/ {
                    padre.setIzquierdo(nodo.getDerecho());
                } else /*Setea el lado derecho del padre con 
                    el nieto*/ {
                    padre.setDerecho(nodo.getDerecho());
                }
            }
        }
    }


    private void caso3(NodoAVLDicc candi, NodoAVLDicc padre, NodoAVLDicc elim) {
        /*Busco el candidato del nodo a elimnar*/
        /*Candidato: hijo menor del subarbol derecho*/
        if (candi.getIzquierdo() != null) {
            /*Recorre hasta el ultimo hijo izquierdo*/
            caso3(candi.getIzquierdo(), padre, elim);
        } else {
            eliminarAux(candi.getClave(), elim, padre);
            /*Inserta el ultimo hijo izquierdo*/
            candi.setIzquierdo(elim.getIzquierdo());
            candi.setDerecho(elim.getDerecho());
            /*Actualizo la altura del candidato*/
            candi.recalcularAltura();
            if (padre != null) {
                /*Verifica que nodo elim no sea la raiz*/
                if (padre.getClave().compareTo(elim.getClave()) > 0) {
                    padre.setIzquierdo(candi);
                } else {
                    padre.setDerecho(candi);
                }
                /*La altura del padre se actualiza y balancea a la vuelta de la recursicion*/
            } else /*Si es la raiz, entonces setea la raiz*/ {
                this.raiz = candi;
            }
        }
    }
    
    public Object obtenerDato(Comparable clave) {
        Object dato = obtenerDatoAux(clave, this.raiz);
        return dato;
    }

    private Object obtenerDatoAux(Comparable clave, NodoAVLDicc nodo) {
        Object dato = null;
        if (nodo != null) {
            if (clave.compareTo(nodo.getClave()) == 0) {
                /*Si la clave es igual a la clave del nodo,
                entonces la clave pertenece al Diccionario*/
                dato = nodo.getDato();
            } else if (clave.compareTo(nodo.getClave()) < 0) {
                /*Si la clave es menor a la clave del nodo
                entonces lo busca en los hijos izquierdos*/
                dato = obtenerDatoAux(clave, nodo.getIzquierdo());
            } else {
                /*Si la clave es mayor a la clave del nodo
                entonces lo busca en los hijos derechos*/
                dato = obtenerDatoAux(clave, nodo.getDerecho());
            }
        }
        return dato;
    }

    public boolean existeClave(Comparable clave) {
        boolean existe = existeClaveAux(clave, this.raiz);
        return existe;
    }

    private boolean existeClaveAux(Comparable clave, NodoAVLDicc nodo){
        boolean existe=false;
        if(nodo!=null){
            if(clave.compareTo(nodo.getClave())== 0){
                /*Si elem es igual al elemento del nodo,
                entonces elem pertence a AVL*/
                existe=true;
            }else if(clave.compareTo(nodo.getClave())<0){
                /*Si el elemento es menor al elemento del nodo
                entonces lo busca en los hijos izquierdos*/
                existe=existeClaveAux(clave,nodo.getIzquierdo());
            }else{
                /*Si el elemento es mayor al elemento del nodo
                entonces lo busca en los hijos derechos*/
                existe=existeClaveAux(clave,nodo.getDerecho());
            }
        }
        return existe;
    }
    
    public Lista listarClaves(){
        Lista list=new Lista();
        listarClavesAux(list,this.raiz);
        return list;
    }
    
    private void listarClavesAux(Lista list,NodoAVLDicc nodo){
        /*Metodo que devuelve una lista de todos los elementos
        del AVl en un recorrido Inorden*/
        if(nodo!=null){
            /*Recorre hasta al ultimo hijo izquierdo*/
            listarClavesAux(list,nodo.getIzquierdo());
            /*Agrega el elemento en la lista*/
            list.insertar(nodo.getClave(), list.longitud()+1);
            /*Recorre a hasta el ultimo hijo derecho*/
            listarClavesAux(list,nodo.getDerecho());
        }
    }
    
    public Lista listarDatos() {
        Lista list = new Lista();
        listarDatosAux(list, this.raiz);
        return list;
    }

    private void listarDatosAux(Lista list, NodoAVLDicc nodo) {
        /*Metodo que devuelve una lista de todos los elementos
        del AVl en un recorrido Inorden*/
        if (nodo != null) {
            /*Recorre hasta al ultimo hijo izquierdo*/
            listarDatosAux(list, nodo.getIzquierdo());
            /*Agrega el elemento en la lista*/
            list.insertar(nodo.getDato(), list.longitud() + 1);
            /*Recorre a hasta el ultimo hijo derecho*/
            listarDatosAux(list, nodo.getDerecho());
        }
    }
    
    public Lista listarRango(Comparable elemMinimo,Comparable elemMaximo){
        Lista list=new Lista();
        if(elemMinimo.compareTo(elemMaximo)<=0){
            listarRangoAux(elemMinimo,elemMaximo,this.raiz,list);
        }
        return list;
    }
    
    private void listarRangoAux(Comparable elemMinimo, Comparable elemMaximo, NodoAVLDicc nodo, Lista list) {
        if (nodo != null) {
            /*Recorro el arbol en inOrden para buscar los mayores o iguales a elemMinimo
                Y menores o iguales a elemMaximo*/
            if (elemMinimo.compareTo(nodo.getClave()) < 0 && nodo.getIzquierdo() != null) {
                listarRangoAux(elemMinimo, elemMaximo, nodo.getIzquierdo(), list);
            }
            /*Pregunto si el elemento del nodo es mayor o igual a elemMinimo
                    Y si el elemento es menor o igual a elemMaximo*/
            if (elemMinimo.compareTo(nodo.getClave()) <= 0 && elemMaximo.compareTo(nodo.getClave()) >= 0) {
                /*Si se cumple entonces lo inserto en la lista*/
                list.insertar(nodo.getClave(), list.longitud() + 1);
            }
            if (elemMaximo.compareTo(nodo.getClave()) > 0 && nodo.getDerecho() != null) {
                listarRangoAux(elemMinimo, elemMaximo, nodo.getDerecho(), list);
            }

        }
    } 
    
    public String toString(){
        String arbol="null";
        if(this.raiz!=null)
            arbol=toStringAux(this.raiz);
        return arbol;
    }
    
    private String toStringAux(NodoAVLDicc nodo){
        String arbol="";
        if(nodo!=null){
            NodoAVLDicc hijoI=nodo.getIzquierdo(),hijoD=nodo.getDerecho();
            if(hijoI!=null)
                arbol+=nodo.getClave()+" HI: "+hijoI.getClave();
            else
                arbol+=nodo.getClave()+" HI: null";
            if(hijoD!=null)
                arbol+=" HD: "+hijoD.getClave();
            else
                arbol+=" HD: null";
            arbol+= " Altura: "+nodo.getAltura();
            arbol+="\n";
            arbol+=toStringAux(nodo.getIzquierdo());
            arbol+=toStringAux(nodo.getDerecho());
        }
        return arbol;
    }
    
    public String toStringConDatos(){
        /*Metodo que ademas de mostrarte la clave con los hijos, muestra la informacion del dato*/
        String arbol="null";
        if(this.raiz!=null)
            arbol=toStringConDatosAux(this.raiz);
        return arbol;
    }
    
    private String toStringConDatosAux(NodoAVLDicc nodo){
        String arbol="";
        if(nodo!=null){
            NodoAVLDicc hijoI=nodo.getIzquierdo(),hijoD=nodo.getDerecho();
            if(hijoI!=null)
                arbol+=nodo.getClave()+" HI: "+hijoI.getClave();
            else
                arbol+=nodo.getClave()+" HI: null";
            if(hijoD!=null)
                arbol+=" HD: "+hijoD.getClave();
            else
                arbol+=" HD: null";
            arbol+="\n---DATOS DE "+nodo.getClave()+"---\n";
            arbol+= nodo.getDato().toString()+"\n";
            arbol+=toStringConDatosAux(nodo.getIzquierdo());
            arbol+=toStringConDatosAux(nodo.getDerecho());
        }
        return arbol;
    }
    
}
