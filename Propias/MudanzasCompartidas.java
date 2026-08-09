package EDAT.Propias;

import EDAT.Diccionario.DiccionarioAVL;
import EDAT.Grafos.GrafoEtiquetado;
import java.util.HashMap;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
                    cargarDatosIniciales("lote_inicial.txt");
                    break;
                case "2":
                    abmCiudades();
                    break;
                case "3":
                    abmRutas();
                    break;
                case "4":
                    abmClientes();
                    break;
                case "5":
                    abmPedidos();
                    break;
                case "6":
                    consultaCliente();
                    break;
                case "7":
                    consultaCiudades();
                    break;
                case "8":
                    consultasViajes();
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

    // --- MÓDULO DE LOG ---
    public void escribirLog(String mensaje) {
        try (FileWriter escritor = new FileWriter("log_mudanzas.txt", true)) {
            escritor.write(mensaje + "\n");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo log: " + e.getMessage());
        }
    }

    // --- MÓDULO DE CARGA INICIAL ---
    public void cargarDatosIniciales(String rutaArchivo) {
        System.out.println("Iniciando carga de datos desde: " + rutaArchivo);
        escribirLog("--- INICIO DE EJECUCION DEL SISTEMA ---");
        
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int contCiudades = 0, contClientes = 0, contRutas = 0, contSolicitudes = 0;

            while ((linea = br.readLine()) != null) {
                // Ignorar lineas vacias
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split(";");
                String tipoCarga = partes[0].trim().toUpperCase();

                switch (tipoCarga) {
                    case "C": // C;5000; Córdoba; Córdoba
                        String cp = partes[1].trim();
                        String nombreC = partes[2].trim();
                        String prov = partes[3].trim();
                        
                        Ciudad nuevaCiudad = new Ciudad(cp, nombreC, prov);
                        ciudades.insertar(nuevaCiudad.getCodigoP(), nuevaCiudad);
                        mapaRutas.insertarVertice(nuevaCiudad.getCodigoP()); // Vértice en el grafo
                        contCiudades++;
                        break;

                    case "P": // P;DNI;35678965; FERNANDEZ; JUAN CARLOS; 299-4495117
                        String idCliente = partes[1].trim() + partes[2].trim();
                        String apellido = partes[3].trim();
                        String nombre = partes[4].trim();
                        String telefono = partes[5].trim();
                        String email = partes.length > 6 ? partes[6].trim() : "Sin email"; // Manejo seguro si falta el email
                        
                        Cliente nuevoCliente = new Cliente(idCliente, nombre, apellido, telefono, email);
                        clientes.put(idCliente, nuevoCliente);
                        contClientes++;
                        break;

                    case "R": // R;5000;8324;1108.5
                        String origenRuta = partes[1].trim();
                        String destinoRuta = partes[2].trim();
                        double distancia = Double.parseDouble(partes[3].trim());
                        
                        mapaRutas.insertarArco(origenRuta, destinoRuta, distancia);
                        contRutas++;
                        break;

                    case "S": // S;5000;8300;15/06/2023;DNI;35678965; 13;5; Sarmiento 3400; Roca 2100;T
                        String cpOrigen = partes[1].trim();
                        String cpDestino = partes[2].trim();
                        String fecha = partes[3].trim();
                        String idClienteSol = partes[4].trim() + partes[5].trim();
                        int m3 = Integer.parseInt(partes[6].trim());
                        int bultos = Integer.parseInt(partes[7].trim());
                        String domRetiro = partes[8].trim();
                        String domEntrega = partes[9].trim();
                        boolean pagado = partes[10].trim().equalsIgnoreCase("T");
                        
                        Solicitud nuevaSol = new Solicitud(cpOrigen, cpDestino, fecha, idClienteSol, m3, bultos, domRetiro, domEntrega, pagado);
                        
                        // Buscar la ciudad origen y agregar la solicitud
                        Ciudad ciudadO = (Ciudad) ciudades.obtenerDato(cpOrigen);
                        if (ciudadO != null) {
                            ciudadO.agregarSolicitud(nuevaSol);
                            contSolicitudes++;
                        } else {
                            escribirLog("ADVERTENCIA: Intento de cargar solicitud en ciudad inexistente (" + cpOrigen + ")");
                        }
                        break;
                        
                    default:
                        escribirLog("ADVERTENCIA: Linea no reconocida -> " + linea);
                        break;
                }
            }
            
            System.out.println("Carga finalizada con éxito.");
            System.out.println("Ciudades: " + contCiudades + " | Clientes: " + contClientes + 
                               " | Rutas: " + contRutas + " | Solicitudes: " + contSolicitudes);
            
            escribirLog("Carga inicial exitosa. C: " + contCiudades + ", P: " + contClientes + ", R: " + contRutas + ", S: " + contSolicitudes);
            
            // Punto 10 implícito en el log al iniciar
            escribirLog("\n--- ESTADO INICIAL DEL SISTEMA ---");
            escribirLog("AVL de Ciudades:\n" + ciudades.toString());
            escribirLog("Mapa de Rutas (Grafo):\n" + mapaRutas.toString());
            
        } catch (IOException e) {
            System.out.println("Error fatal leyendo el archivo: " + e.getMessage());
            escribirLog("ERROR CRÍTICO: Fallo al leer el archivo de carga inicial.");
        } catch (NumberFormatException e) {
            System.out.println("Error de formato numérico en el archivo txt.");
            escribirLog("ERROR CRÍTICO: Formato numérico inválido en el txt.");
        }
    }

    //ABM de Ciudades
    public void abmCiudades() {
        String subOpcion = "";
        do {
            System.out.println("\n--- ABM DE CIUDADES ---");
            System.out.println("A. Alta de Ciudad");
            System.out.println("B. Baja de Ciudad");
            System.out.println("M. Modificación de Ciudad");
            System.out.println("R. Regresar al Menú Principal");
            System.out.print("Ingrese una opción: ");
            subOpcion = teclado.nextLine().toUpperCase();

            switch (subOpcion) {
                case "A":
                    System.out.print("Ingrese Código Postal (4 dígitos): ");
                    String cpAlta = teclado.nextLine().trim();
                    
                    if (ciudades.existeClave(cpAlta)) {
                        System.out.println("ERROR: La ciudad con CP " + cpAlta + " ya existe en el sistema.");
                    } else {
                        System.out.print("Ingrese Nombre de la Ciudad: ");
                        String nombre = teclado.nextLine().trim();
                        System.out.print("Ingrese Nombre de la Provincia: ");
                        String provincia = teclado.nextLine().trim();
                        
                        Ciudad nueva = new Ciudad(cpAlta, nombre, provincia);
                        ciudades.insertar(cpAlta, nueva);
                        mapaRutas.insertarVertice(cpAlta); // Importante: Sincronizar con el Grafo
                        
                        System.out.println("Ciudad registrada exitosamente.");
                        escribirLog("ALTA CIUDAD: Se creó la ciudad " + nombre + " (" + cpAlta + ")");
                    }
                    break;

                case "B":
                    System.out.print("Ingrese el Código Postal de la ciudad a eliminar: ");
                    String cpBaja = teclado.nextLine().trim();
                    
                    if (!ciudades.existeClave(cpBaja)) {
                        System.out.println("ERROR: La ciudad no existe.");
                    } else {
                        // Eliminar de ambas estructuras
                        ciudades.eliminar(cpBaja);
                        mapaRutas.eliminarVertice(cpBaja); // Esto también desconecta las rutas asociadas
                        
                        System.out.println("Ciudad eliminada del sistema y del mapa de rutas.");
                        escribirLog("BAJA CIUDAD: Se eliminó la ciudad con CP " + cpBaja + " y todas sus rutas conectadas.");
                    }
                    break;

                case "M":
                    System.out.print("Ingrese el Código Postal de la ciudad a modificar: ");
                    String cpMod = teclado.nextLine().trim();
                    
                    Ciudad ciudadMod = (Ciudad) ciudades.obtenerDato(cpMod);
                    if (ciudadMod == null) {
                        System.out.println("ERROR: La ciudad no existe.");
                    } else {
                        System.out.println("Ciudad encontrada: " + ciudadMod.toString());
                        System.out.println("NOTA: El código postal (" + cpMod + ") es inmutable y no puede modificarse.");
                        
                        System.out.print("Ingrese nuevo Nombre (deje en blanco para mantener actual): ");
                        String nuevoNombre = teclado.nextLine().trim();
                        if (!nuevoNombre.isEmpty()) {
                            ciudadMod.setNombreCiudad(nuevoNombre);
                        }
                        
                        System.out.print("Ingrese nueva Provincia (deje en blanco para mantener actual): ");
                        String nuevaProv = teclado.nextLine().trim();
                        if (!nuevaProv.isEmpty()) {
                            ciudadMod.setNombreProvincia(nuevaProv);
                        }
                        
                        System.out.println("Ciudad modificada exitosamente.");
                        escribirLog("MODIFICACIÓN CIUDAD: Se actualizaron los datos de la ciudad CP " + cpMod);
                    }
                    break;

                case "R":
                    System.out.println("Regresando al menú principal...");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (!subOpcion.equals("R"));
    }
    //ABM de rutas
    public void abmRutas() {
        String subOpcion = "";
        do {
            System.out.println("\n--- ABM DE RUTAS ---");
            System.out.println("A. Alta de Ruta");
            System.out.println("B. Baja de Ruta");
            System.out.println("M. Modificación de Distancia de Ruta");
            System.out.println("R. Regresar al Menú Principal");
            System.out.print("Ingrese una opción: ");
            subOpcion = teclado.nextLine().toUpperCase();

            switch (subOpcion) {
                case "A":
                    System.out.print("Ingrese el Código Postal de la ciudad origen: ");
                    String origenAlta = teclado.nextLine().trim();
                    System.out.print("Ingrese el Código Postal de la ciudad destino: ");
                    String destinoAlta = teclado.nextLine().trim();
                    
                    if (!mapaRutas.existeVertice(origenAlta) || !mapaRutas.existeVertice(destinoAlta)) {
                        System.out.println("ERROR: Una o ambas ciudades no existen en el sistema. Debe darlas de alta primero.");
                    } else if (mapaRutas.existeArco(origenAlta, destinoAlta)) {
                        System.out.println("ERROR: Ya existe una ruta entre estas dos ciudades.");
                    } else {
                        try {
                            System.out.print("Ingrese la distancia en kilómetros (ej. 150.5): ");
                            double distancia = Double.parseDouble(teclado.nextLine().trim());
                            
                            boolean exito = mapaRutas.insertarArco(origenAlta, destinoAlta, distancia);
                            if (exito) {
                                System.out.println("Ruta registrada exitosamente.");
                                escribirLog("ALTA RUTA: Conexión creada entre " + origenAlta + " y " + destinoAlta + " (" + distancia + " km).");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("ERROR: Formato de distancia inválido. Debe ingresar números.");
                        }
                    }
                    break;

                case "B":
                    System.out.print("Ingrese el Código Postal de la ciudad origen: ");
                    String origenBaja = teclado.nextLine().trim();
                    System.out.print("Ingrese el Código Postal de la ciudad destino: ");
                    String destinoBaja = teclado.nextLine().trim();
                    
                    if (mapaRutas.existeArco(origenBaja, destinoBaja)) {
                        mapaRutas.eliminarArco(origenBaja, destinoBaja);
                        System.out.println("Ruta eliminada exitosamente.");
                        escribirLog("BAJA RUTA: Se eliminó la conexión entre " + origenBaja + " y " + destinoBaja);
                    } else {
                        System.out.println("ERROR: No existe una ruta registrada entre esas ciudades.");
                    }
                    break;

                case "M":
                    System.out.print("Ingrese el Código Postal de la ciudad origen: ");
                    String origenMod = teclado.nextLine().trim();
                    System.out.print("Ingrese el Código Postal de la ciudad destino: ");
                    String destinoMod = teclado.nextLine().trim();
                    
                    if (mapaRutas.existeArco(origenMod, destinoMod)) {
                        try {
                            System.out.print("Ingrese la nueva distancia en kilómetros: ");
                            double nuevaDistancia = Double.parseDouble(teclado.nextLine().trim());
                            
                            // Modificar en el TDA estándar significa eliminar el enlace viejo y crear uno nuevo
                            mapaRutas.eliminarArco(origenMod, destinoMod);
                            mapaRutas.insertarArco(origenMod, destinoMod, nuevaDistancia);
                            
                            System.out.println("Distancia de la ruta modificada exitosamente.");
                            escribirLog("MODIFICACION RUTA: Conexión entre " + origenMod + " y " + destinoMod + " actualizada a " + nuevaDistancia + " km.");
                        } catch (NumberFormatException e) {
                            System.out.println("ERROR: Formato de distancia inválido.");
                        }
                    } else {
                        System.out.println("ERROR: No existe una ruta registrada entre esas ciudades para modificar.");
                    }
                    break;

                case "R":
                    System.out.println("Regresando al menú principal...");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (!subOpcion.equals("R"));
    }

    //ABM de Clientes
    public void abmClientes() {
        String subOpcion = "";
        do {
            System.out.println("\n--- ABM DE CLIENTES ---");
            System.out.println("A. Alta de Cliente");
            System.out.println("B. Baja de Cliente");
            System.out.println("M. Modificación de Cliente");
            System.out.println("R. Regresar al Menú Principal");
            System.out.print("Ingrese una opción: ");
            subOpcion = teclado.nextLine().toUpperCase();

            switch (subOpcion) {
                case "A":
                    System.out.print("Ingrese el Tipo de Documento (Ej. DNI, PAS): ");
                    String tipoAlta = teclado.nextLine().trim().toUpperCase();
                    System.out.print("Ingrese el Número de Documento: ");
                    String numAlta = teclado.nextLine().trim();
                    String claveAlta = tipoAlta + numAlta;
                    
                    if (clientes.containsKey(claveAlta)) {
                        System.out.println("ERROR: El cliente con documento " + claveAlta + " ya está registrado.");
                    } else {
                        System.out.print("Ingrese Apellido/s: ");
                        String apellido = teclado.nextLine().trim().toUpperCase();
                        System.out.print("Ingrese Nombre/s: ");
                        String nombre = teclado.nextLine().trim().toUpperCase();
                        System.out.print("Ingrese Teléfono: ");
                        String telefono = teclado.nextLine().trim();
                        System.out.print("Ingrese Email: ");
                        String email = teclado.nextLine().trim();
                        
                        Cliente nuevoCliente = new Cliente(claveAlta, nombre, apellido, telefono, email);
                        clientes.put(claveAlta, nuevoCliente);
                        
                        System.out.println("Cliente registrado exitosamente.");
                        escribirLog("ALTA CLIENTE: Se registró a " + apellido + ", " + nombre + " (" + claveAlta + ").");
                    }
                    break;

                case "B":
                    System.out.print("Ingrese el Tipo de Documento del cliente a dar de baja: ");
                    String tipoBaja = teclado.nextLine().trim().toUpperCase();
                    System.out.print("Ingrese el Número de Documento: ");
                    String numBaja = teclado.nextLine().trim();
                    String claveBaja = tipoBaja + numBaja;
                    
                    if (clientes.containsKey(claveBaja)) {
                        clientes.remove(claveBaja);
                        System.out.println("Cliente eliminado del sistema.");
                        escribirLog("BAJA CLIENTE: Se eliminó del registro al cliente " + claveBaja + ".");
                    } else {
                        System.out.println("ERROR: El cliente no se encuentra registrado.");
                    }
                    break;

                case "M":
                    System.out.print("Ingrese el Tipo de Documento del cliente a modificar: ");
                    String tipoMod = teclado.nextLine().trim().toUpperCase();
                    System.out.print("Ingrese el Número de Documento: ");
                    String numMod = teclado.nextLine().trim();
                    String claveMod = tipoMod + numMod;
                    
                    Cliente clienteMod = clientes.get(claveMod);
                    
                    if (clienteMod == null) {
                        System.out.println("ERROR: El cliente no se encuentra registrado.");
                    } else {
                        System.out.println("Cliente encontrado: " + clienteMod.toString());
                        System.out.println("NOTA: El documento (" + claveMod + ") es inmutable. Deje en blanco los campos que no desee alterar.");
                        
                        System.out.print("Ingrese nuevo Apellido: ");
                        String nuevoApe = teclado.nextLine().trim().toUpperCase();
                        if (!nuevoApe.isEmpty()) clienteMod.setApellido(nuevoApe);
                        
                        System.out.print("Ingrese nuevo Nombre: ");
                        String nuevoNom = teclado.nextLine().trim().toUpperCase();
                        if (!nuevoNom.isEmpty()) clienteMod.setNombre(nuevoNom);
                        
                        System.out.print("Ingrese nuevo Teléfono: ");
                        String nuevoTel = teclado.nextLine().trim();
                        if (!nuevoTel.isEmpty()) clienteMod.setTelefono(nuevoTel);
                        
                        System.out.print("Ingrese nuevo Email: ");
                        String nuevoMail = teclado.nextLine().trim();
                        if (!nuevoMail.isEmpty()) clienteMod.setMail(nuevoMail);
                        
                        System.out.println("Datos del cliente actualizados con éxito.");
                        escribirLog("MODIFICACION CLIENTE: Se actualizaron los datos de " + claveMod + ".");
                    }
                    break;

                case "R":
                    System.out.println("Regresando al menú principal...");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (!subOpcion.equals("R"));
    }

    //ABM de pedidos
    public void abmPedidos() {
        String subOpcion = "";
        do {
            System.out.println("\n--- ABM DE PEDIDOS ---");
            System.out.println("A. Alta de Pedido");
            System.out.println("B. Baja de Pedido");
            System.out.println("M. Modificación de Pedido");
            System.out.println("R. Regresar al Menú Principal");
            System.out.print("Ingrese una opción: ");
            subOpcion = teclado.nextLine().toUpperCase();

            switch (subOpcion) {
                case "A":
                    System.out.print("Ingrese CP de la ciudad ORIGEN: ");
                    String cpO = teclado.nextLine().trim();
                    System.out.print("Ingrese CP de la ciudad DESTINO: ");
                    String cpD = teclado.nextLine().trim();
                    
                    Ciudad ciudadOrigen = (Ciudad) ciudades.obtenerDato(cpO);
                    Ciudad ciudadDestino = (Ciudad) ciudades.obtenerDato(cpD);
                    
                    if (ciudadOrigen == null || ciudadDestino == null) {
                        System.out.println("ERROR: Una o ambas ciudades no están registradas en el sistema.");
                        break; // Corta la ejecución de este caso
                    }
                    
                    System.out.print("Ingrese el ID del Cliente (Ej: DNI12345678): ");
                    String idCliente = teclado.nextLine().trim().toUpperCase();
                    
                    if (!clientes.containsKey(idCliente)) {
                        System.out.println("ERROR: El cliente no existe. Debe darlo de alta primero.");
                        break;
                    }
                    
                    try {
                        System.out.print("Ingrese fecha de solicitud (DD/MM/AAAA): ");
                        String fecha = teclado.nextLine().trim();
                        System.out.print("Ingrese volumen en metros cúbicos (Ej: 15): ");
                        int m3 = Integer.parseInt(teclado.nextLine().trim());
                        System.out.print("Ingrese cantidad de bultos: ");
                        int bultos = Integer.parseInt(teclado.nextLine().trim());
                        System.out.print("Ingrese domicilio de retiro: ");
                        String domRet = teclado.nextLine().trim();
                        System.out.print("Ingrese domicilio de entrega: ");
                        String domEnt = teclado.nextLine().trim();
                        System.out.print("¿El envío está pagado? (S/N): ");
                        boolean pagado = teclado.nextLine().trim().equalsIgnoreCase("S");
                        
                        Solicitud nuevaSol = new Solicitud(cpO, cpD, fecha, idCliente, m3, bultos, domRet, domEnt, pagado);
                        ciudadOrigen.agregarSolicitud(nuevaSol);
                        
                        System.out.println("Pedido registrado exitosamente.");
                        escribirLog("ALTA SOLICITUD: Nuevo viaje de " + cpO + " a " + cpD + " (Cliente: " + idCliente + ")");
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR: Ingrese valores numéricos válidos para M3 y Bultos.");
                    }
                    break;

                case "B":
                    System.out.print("Ingrese CP de la ciudad ORIGEN: ");
                    String cpOBaja = teclado.nextLine().trim();
                    System.out.print("Ingrese CP de la ciudad DESTINO: ");
                    String cpDBaja = teclado.nextLine().trim();
                    
                    Ciudad origenBaja = (Ciudad) ciudades.obtenerDato(cpOBaja);
                    if (origenBaja != null) {
                        lineales.dinamicas.Lista listaBaja = origenBaja.getSolicitudesHacia(cpDBaja);
                        if (listaBaja.esVacia()) {
                            System.out.println("No hay pedidos registrados desde " + cpOBaja + " hacia " + cpDBaja);
                        } else {
                            System.out.println("--- Pedidos encontrados ---");
                            for (int i = 1; i <= listaBaja.longitud(); i++) {
                                Solicitud sol = (Solicitud) listaBaja.recuperar(i);
                                System.out.println("[" + i + "] " + sol.toString());
                            }
                            System.out.print("Ingrese el número (índice) del pedido a eliminar: ");
                            try {
                                int indice = Integer.parseInt(teclado.nextLine().trim());
                                if (indice >= 1 && indice <= listaBaja.longitud()) {
                                    listaBaja.eliminar(indice);
                                    System.out.println("Pedido eliminado exitosamente.");
                                    escribirLog("BAJA SOLICITUD: Se eliminó el pedido " + indice + " de la ruta " + cpOBaja + " -> " + cpDBaja);
                                } else {
                                    System.out.println("ERROR: Índice fuera de rango.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("ERROR: Debe ingresar un número.");
                            }
                        }
                    } else {
                        System.out.println("ERROR: La ciudad de origen no existe.");
                    }
                    break;

                case "M":
                    System.out.print("Ingrese CP de la ciudad ORIGEN: ");
                    String cpOMod = teclado.nextLine().trim();
                    System.out.print("Ingrese CP de la ciudad DESTINO: ");
                    String cpDMod = teclado.nextLine().trim();
                    
                    Ciudad origenMod = (Ciudad) ciudades.obtenerDato(cpOMod);
                    if (origenMod != null) {
                        lineales.dinamicas.Lista listaMod = origenMod.getSolicitudesHacia(cpDMod);
                        if (listaMod.esVacia()) {
                            System.out.println("No hay pedidos registrados en esta ruta.");
                        } else {
                            System.out.println("--- Pedidos encontrados ---");
                            for (int i = 1; i <= listaMod.longitud(); i++) {
                                Solicitud sol = (Solicitud) listaMod.recuperar(i);
                                System.out.println("[" + i + "] " + sol.toString());
                            }
                            System.out.print("Ingrese el número (índice) del pedido a modificar: ");
                            try {
                                int indice = Integer.parseInt(teclado.nextLine().trim());
                                if (indice >= 1 && indice <= listaMod.longitud()) {
                                    Solicitud solMod = (Solicitud) listaMod.recuperar(indice);
                                    System.out.println("Modificando pedido. Deje en blanco (o ingrese -1 para números) para no alterar el valor.");
                                    
                                    System.out.print("Nuevos m3 (Actual: " + solMod.getM3() + "): ");
                                    String volStr = teclado.nextLine().trim();
                                    if (!volStr.isEmpty() && !volStr.equals("-1")) solMod.setM3(Integer.parseInt(volStr));
                                    
                                    System.out.print("Nuevos bultos (Actual: " + solMod.getCantBultos() + "): ");
                                    String bultosStr = teclado.nextLine().trim();
                                    if (!bultosStr.isEmpty() && !bultosStr.equals("-1")) solMod.setCantBultos(Integer.parseInt(bultosStr));
                                    
                                    System.out.print("Nuevo domicilio retiro (Actual: " + solMod.getDomicilioRetiro() + "): ");
                                    String domR = teclado.nextLine().trim();
                                    if (!domR.isEmpty()) solMod.setDomicilioRetiro(domR);
                                    
                                    System.out.print("Nuevo domicilio entrega (Actual: " + solMod.getDomicilioEntrega() + "): ");
                                    String domE = teclado.nextLine().trim();
                                    if (!domE.isEmpty()) solMod.setDomicilioEntrega(domE);
                                    
                                    System.out.print("¿Pagado? (Actual: " + solMod.isPago() + ") (S/N/Enter para saltar): ");
                                    String pagoStr = teclado.nextLine().trim();
                                    if (pagoStr.equalsIgnoreCase("S")) solMod.setPago(true);
                                    else if (pagoStr.equalsIgnoreCase("N")) solMod.setPago(false);
                                    
                                    System.out.println("Pedido modificado exitosamente.");
                                    escribirLog("MODIFICACION SOLICITUD: Actualizado pedido " + indice + " de ruta " + cpOMod + " -> " + cpDMod);
                                } else {
                                    System.out.println("ERROR: Índice fuera de rango.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("ERROR: Entrada numérica inválida.");
                            }
                        }
                    } else {
                        System.out.println("ERROR: La ciudad de origen no existe.");
                    }
                    break;

                case "R":
                    System.out.println("Regresando al menú principal...");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (!subOpcion.equals("R"));
    }

    //Consulta sobre clientes
    public void consultaCliente() {
        System.out.println("\n--- CONSULTA DE CLIENTE ---");
        System.out.print("Ingrese el Tipo de Documento (Ej. DNI, PAS): ");
        String tipo = teclado.nextLine().trim().toUpperCase();
        System.out.print("Ingrese el Número de Documento: ");
        String num = teclado.nextLine().trim();
        String clave = tipo + num;
        
        // Búsqueda en O(1)
        Cliente clienteBuscado = clientes.get(clave);
        
        if (clienteBuscado != null) {
            System.out.println("\n--- Información del Cliente ---");
            System.out.println("Documento: " + clienteBuscado.getIdCliente());
            System.out.println("Apellido:  " + clienteBuscado.getApellido());
            System.out.println("Nombre:    " + clienteBuscado.getNombre());
            System.out.println("Teléfono:  " + clienteBuscado.getTelefono());
            System.out.println("Email:     " + clienteBuscado.getMail());
            System.out.println("-------------------------------");
        } else {
            System.out.println("ERROR: No se encontró ningún cliente registrado con el documento " + clave);
        }
    }

    //Consulta ciudades y prefijos
    public void consultaCiudades() {
        String subOpcion = "";
        do {
            System.out.println("\n--- CONSULTAS SOBRE CIUDADES ---");
            System.out.println("1. Buscar ciudad por Código Postal exacto");
            System.out.println("2. Buscar ciudades por prefijo");
            System.out.println("R. Regresar al Menú Principal");
            System.out.print("Ingrese una opción: ");
            subOpcion = teclado.nextLine().toUpperCase();

            switch (subOpcion) {
                case "1":
                    System.out.print("Ingrese el Código Postal exacto (4 dígitos): ");
                    String cpExacto = teclado.nextLine().trim();
                    Ciudad ciudadBuscada = (Ciudad) ciudades.obtenerDato(cpExacto);
                    
                    if (ciudadBuscada != null) {
                        System.out.println("\n--- Información de la Ciudad ---");
                        System.out.println("Código Postal: " + ciudadBuscada.getCodigoP());
                        System.out.println("Nombre:        " + ciudadBuscada.getNombreCiudad());
                        System.out.println("Provincia:     " + ciudadBuscada.getNombreProvincia());
                        System.out.println("--------------------------------");
                    } else {
                        System.out.println("ERROR: No se encontró ninguna ciudad con el CP " + cpExacto);
                    }
                    break;

                case "2":
                    System.out.print("Ingrese el prefijo del Código Postal (1 a 4 dígitos, ej. '83'): ");
                    String prefijo = teclado.nextLine().trim();
                    
                    if (prefijo.isEmpty() || prefijo.length() > 4) {
                        System.out.println("ERROR: Prefijo inválido. Debe tener entre 1 y 4 dígitos.");
                        break;
                    }
                    
                    // Rellenado dinámico para conformar el rango lógico
                    String minCP = prefijo;
                    String maxCP = prefijo;
                    while (minCP.length() < 4) {
                        minCP += "0";
                        maxCP += "9";
                    }
                    
                    System.out.println("Buscando rango en el Árbol AVL: " + minCP + " a " + maxCP + "...");
                    lineales.dinamicas.Lista resultadosClaves = ciudades.listarRango(minCP, maxCP);
                    
                    if (resultadosClaves.esVacia()) {
                        System.out.println("No se encontraron ciudades con ese prefijo.");
                    } else {
                        System.out.println("\n--- Ciudades Encontradas (" + resultadosClaves.longitud() + ") ---");
                        for (int i = 1; i <= resultadosClaves.longitud(); i++) {
                            // Tu listarRango devuelve la clave (String), la usamos para buscar el objeto completo
                            String claveEncontrada = (String) resultadosClaves.recuperar(i);
                            Ciudad c = (Ciudad) ciudades.obtenerDato(claveEncontrada);
                            System.out.println("- " + c.toString());
                        }
                    }
                    break;

                case "R":
                    System.out.println("Regresando al menú principal...");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (!subOpcion.equals("R"));
    }

    //Consulta sobre viajes: Dada una ciudad A y otra B
    public void consultasViajes() {
        System.out.println("\n--- CONSULTAS SOBRE VIAJES ---");
        System.out.print("Ingrese el CP de la ciudad ORIGEN (A): ");
        String cpA = teclado.nextLine().trim();
        System.out.print("Ingrese el CP de la ciudad DESTINO (B): ");
        String cpB = teclado.nextLine().trim();

        Ciudad ciudadA = (Ciudad) ciudades.obtenerDato(cpA);
        Ciudad ciudadB = (Ciudad) ciudades.obtenerDato(cpB);

        if (ciudadA == null || ciudadB == null) {
            System.out.println("ERROR: Una o ambas ciudades no están dadas de alta en el sistema.");
            return; // Corta la ejecución tempranamente
        }

        String subOpcion = "";
        do {
            System.out.println("\nRuta: " + ciudadA.getNombreCiudad() + " -> " + ciudadB.getNombreCiudad());
            System.out.println("1. Camino que pase por MENOS ciudades (escalas)");
            System.out.println("2. Camino de MENOR distancia (kilómetros)");
            System.out.println("3. Obtener todos los caminos que pasen por una ciudad C");
            System.out.println("4. Verificar si es posible llegar en un máximo de X kilómetros");
            System.out.println("R. Regresar al Menú Principal");
            System.out.print("Ingrese una opción: ");
            subOpcion = teclado.nextLine().toUpperCase();

            switch (subOpcion) {
                case "1":
                    lineales.dinamicas.Lista caminoMenosCiudades = mapaRutas.caminoMasCorto(cpA, cpB);
                    if (caminoMenosCiudades.esVacia()) {
                        System.out.println("No existe ningún camino posible entre estas dos ciudades.");
                    } else {
                        System.out.println("Camino con menos ciudades encontrado:");
                        toStringCamino(caminoMenosCiudades);
                        System.out.println("Total de ciudades visitadas: " + caminoMenosCiudades.longitud());
                    }
                    break;

                case "2":
                    lineales.dinamicas.Lista caminoMenorDistancia = mapaRutas.caminoMasCortoEtiquetas(cpA, cpB);
                    if (caminoMenorDistancia.esVacia()) {
                        System.out.println("No existe ningún camino posible entre estas dos ciudades.");
                    } else {
                        System.out.println("Camino más corto en kilómetros encontrado:");
                        toStringCamino(caminoMenorDistancia);
                    }
                    break;

                case "3":
                    System.out.print("Ingrese el CP de la ciudad intermedia (C): ");
                    String cpC = teclado.nextLine().trim();
                    Ciudad ciudadC = (Ciudad) ciudades.obtenerDato(cpC);
                    
                    if (ciudadC == null) {
                        System.out.println("ERROR: La ciudad intermedia C no existe en el sistema.");
                    } else if (cpC.equals(cpA) || cpC.equals(cpB)) {
                        System.out.println("ERROR: La ciudad intermedia no puede ser igual al origen o al destino.");
                    } else {
                        lineales.dinamicas.Lista todosLosCaminos = mapaRutas.todosCaminosPasandoPorC(cpA, cpB, cpC);
                        if (todosLosCaminos.esVacia()) {
                            System.out.println("No se encontró ningún camino de " + cpA + " a " + cpB + " que pase obligatoriamente por " + cpC + ".");
                        } else {
                            System.out.println("Se encontraron " + todosLosCaminos.longitud() + " caminos posibles:");
                            for (int i = 1; i <= todosLosCaminos.longitud(); i++) {
                                System.out.print("Opción " + i + ": ");
                                toStringCamino((lineales.dinamicas.Lista) todosLosCaminos.recuperar(i));
                            }
                        }
                    }
                    break;

                case "4":
                    try {
                        System.out.print("Ingrese el límite máximo de kilómetros (X): ");
                        double limiteKm = Double.parseDouble(teclado.nextLine().trim());
                        
                        boolean esPosible = mapaRutas.existeCaminoConX(cpA, cpB, limiteKm);
                        if (esPosible) {
                            System.out.println("SÍ, es posible llegar de " + ciudadA.getNombreCiudad() + " a " + ciudadB.getNombreCiudad() + " en " + limiteKm + " km o menos.");
                        } else {
                            System.out.println("NO, no existe ningún camino que cumpla con esa restricción de kilometraje.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR: Formato numérico inválido.");
                    }
                    break;

                case "R":
                    System.out.println("Regresando...");
                    break;

                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (!subOpcion.equals("R"));
    }

    // Método auxiliar para imprimir las listas de CPs de forma legible
    private void toStringCamino(lineales.dinamicas.Lista camino) {
        String textoCamino = "";
        for (int i = 1; i <= camino.longitud(); i++) {
            textoCamino += (String) camino.recuperar(i);
            if (i < camino.longitud()) {
                textoCamino += " -> ";
            }
        }
        System.out.println(textoCamino);
    }

}
