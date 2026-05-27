package TPO;

import conjuntistas.ArbolAVL;
import conjuntistas.TablaHash;
import grafos.Grafo;

public class MudanzasCompartidas {
    //Atributos
    private ArbolAVL ciudades;
    private Grafo mapaRutas;
    private TablaHash clientes;

    do{
        switch (opcion) {
            case '1':
                //carga inicial
                break;
            case '2':
                //ABM de ciudades
                break;
            case '3':
                //ABM de la red de rutas
                break;
            case '4':
                //ABM de Clientes
                break;
            case '5':
                //ABM de pedidos
                break;
            case '6':
                //Consulta sobre x cliente
                break;
            case '7':
                //Consulta sobre ciudades
                break;
            case '8':
                //Consulta sobre viajes
                break;
            case '9':
                //Verificar viaje
                break;
            case '0':
                //Mostrar sistema
                break;
            case 'q':
                //Salir
                break;
            default:
                break;
        }
    }
}
