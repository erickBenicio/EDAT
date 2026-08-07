package EDAT.Propias;

import EDAT.Diccionario.DiccionarioAVL;
import EDAT.Grafos.GrafoEtiquetado;
import java.util.HashMap;
import java.util.Scanner;

public class MudanzasCompartidas {
    //Atributos
    private DiccionarioAVL ciudades;
    private GrafoEtiquetado mapaRutas;
    private HashMap<String, Cliente> clientes;
    private Scanner teclado;

    // Constructor
    public MudanzasCompartidas() {
        this.ciudades = new DiccionarioAVL();
        this.mapaRutas = new GrafoEtiquetado();
        this.clientes = new HashMap<>();
        this.teclado = new Scanner(System.in);
    }

    public static void main(String[] args) {
    
        MudanzasCompartidas sistema = new MudanzasCompartidas();
        sistema.iniciarMenu();
    }

    public void iniciarMenu() {
        String opcion = "";
        do {
            System.out.println("\n--- SISTEMA DE MUDANZAS COMPARTIDAS ---");
            System.out.println("1. Carga inicial del sistema");
            System.out.println("2. ABM de ciudades");
            System.out.println("3. ABM de la red de rutas");
            System.out.println("4. ABM de clientes");
            System.out.println("5. ABM de pedidos (Solicitudes)");
            System.out.println("6. Consulta sobre clientes");
            System.out.println("7. Consultas sobre ciudades");
            System.out.println("8. Consultas sobre viajes");
            System.out.println("9. Verificar viaje (Espacio en camion)");
            System.out.println("0. Mostrar sistema (Auditoría de estructuras)");
            System.out.println("q. Salir");
            System.out.print("Ingrese una opcion: ");
            
            opcion = teclado.nextLine();

            switch (opcion) {
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    break;
                case "7":
                    break;
                case "8":
                    break;
                case "9":
                    break;
                case "0":
                    break;
                case "q":
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
                    break;
            }
        } while (!opcion.equalsIgnoreCase("q"));
    }


    public boolean cargarPedidoMenu(Solicitud nuevaSol) {
        boolean exito = false;
        String cpOrigen = nuevaSol.getPostalOrigen();
        
        Ciudad ciudadOrigen = (Ciudad) ciudades.obtenerDato(cpOrigen);
        if(ciudadOrigen != null) {
            ciudadOrigen.agregarSolicitud(nuevaSol);
            exito = true;
        }
        return exito;
    }
}
