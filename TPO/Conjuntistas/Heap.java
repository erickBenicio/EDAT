package TPO.Conjuntistas;

import conjuntistas.ArbolHeap;

public class Heap {
    //Arbol Heap MINIMO
    private static final int TAM =10;
    private Comparable[] heap;
    private int ultimo= 0;
    
    public Heap(){
        
        this.heap= new Comparable[TAM];
    }
    
    // devuelve el elemento que está en la raíz (cima del montículo). Precondición: el árbol no está
    //vacío (si está vacío no se puede asegurar el funcionamiento de la operación).
    public Comparable recuperarCima(){
        
        return this.heap[1];
    }
    
    // devuelve falso si hay al menos un elemento cargado en la tabla y verdadero en caso contrario.
    public boolean esVacio(){
        
        return this.ultimo==0;
    }
    
    // elimina el elemento de la raíz (o cima del montículo). 
    //Si la operación termina con éxito devuelve verdadero y falso si el árbol estaba vacío
    public boolean eliminarCima(){
        
        boolean exito;
        if(this.ultimo==0){
            exito= false;
        }else{
            this.heap[1]= this.heap[ultimo];
            this.ultimo--;
            hacerBajar(1);
            exito= true;
        }
        return exito;
    }
    
    private void hacerBajar(int posPadre){
        
        int posH;
        Comparable aux= this.heap[posPadre];
        boolean salir = false;
        while(!salir){
            posH= posPadre*2;
            if(posH <= this.ultimo){
                //aux tiene al menos un hijo (izq) y lo considera menor
                if(posH < this.ultimo){
                    //hijo menor tiene hermano derecho
                    //el hijo derecho es menor que los 2
                    if(this.heap[posH +1].compareTo(this.heap[posH])<0){
                        posH++;
                    }
                }
                //compara al hijo menor con el padre
                if(this.heap[posH].compareTo(aux)<0){
                    //el hijo es menor que el padre, los intercambia
                    this.heap[posPadre]= this.heap[posH];
                    this.heap[posH]=aux;
                    posPadre= posH;
                }else{
                    //el padre es menor que sus hijos, esta bien ubicado
                    salir= true;
                }
            }else{
                salir= true;
            }
        }
    }
    
    // recibe un elemento y lo inserta en el árbol según el algoritmo que se explicará en la siguiente
    //sección. Si la operación termina con éxito devuelve verdadero y falso en caso contrario.
    //Nota: Los árboles heap aceptan elementos repetidos.
    public boolean insertar(Comparable elemento){
        
        boolean exito= false;
        if(this.ultimo+1 < TAM){
            this.ultimo++;
            this.heap[this.ultimo]= elemento;
            hacerSubir(this.ultimo);
            exito= true;
        }
        return exito;
    }
    
    private void hacerSubir(int posHijo){
        
        int posP;
        Comparable temp= this.heap[posHijo];
        boolean seguir= true;
        while(seguir){
            posP= posHijo/2;
            if(posP>= 1){
                if(this.heap[posP].compareTo(temp)>0){
                    this.heap[posHijo]= this.heap[posP];
                    this.heap[posP]= temp;
                    posHijo= posP;
                }else{
                    seguir= false;
                }
            }else{
                seguir= false;
            }
        }
    }
    
    public Heap clone(){
        
        Heap clon= new Heap();
        clon.heap= this.heap.clone();
        clon.ultimo= this.ultimo;
        return clon;
    }
    
    public String toString(){
        
        String s= "";
        int izq, der;
        for(int i=1; i<= this.ultimo; i++){
            System.out.println("Nodo: "+ this.heap[i]+ " ");
            izq= i*2;
            der= izq +1;
            if(izq<= this.ultimo&& this.heap[izq]!= null){
                System.out.print("HI: "+ this.heap[izq]);
            }else{
                System.out.print("HI: -");
            }
            System.out.print("\t");
            if(der<= this.ultimo&& this.heap[der]!= null){
                System.out.println("HD: "+this.heap[der]);
            }else{
                System.out.println("HD: -");
            }
        }
        return s;
    }
}
