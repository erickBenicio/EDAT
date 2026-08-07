package EDAT.Propias;


public class Cliente {
    private String idCliente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String mail;

    public Cliente(String idCliente, String n, String a, String t, String m){
        this.idCliente= idCliente;
        this.nombre= n;
        this.apellido= a;
        this.telefono= t;
        this.mail= m;
    }
    public String getIdCliente() { 
        return idCliente; 
    }
    public void setIdCliente(String idCliente) { 
        this.idCliente = idCliente; 
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    
    @Override
    public String toString() {
        return "Cliente [" + idCliente + "] " + apellido + ", " + nombre + " - Tel: " + telefono;
    }
}
