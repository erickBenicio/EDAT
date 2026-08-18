package EDAT.Propias;
/**
 *
 * @author erick
 */

public class Solicitud {
    //Atributos
    private String postalOrigen;
    private String postalDestino;
    private String fechaSol;
    private String idCliente;
    private int m3;
    private int cantBultos;
    private String domicilioRetiro;
    private String domicilioEntrega;
    private boolean pagado;

    public Solicitud(String cpO, String cpD, String unaFecha, String idCliente, int m3, int cantBul, String domRet, String domEnt, boolean pagado){
        this.postalOrigen= cpO;
        this.postalDestino= cpD;
        this.fechaSol= unaFecha;
        this.idCliente= idCliente;
        this.m3= m3;
        this.cantBultos= cantBul;
        this.domicilioRetiro= domRet;
        this.domicilioEntrega= domEnt;
        this.pagado= pagado;
    }
    public String getPostalOrigen() {
        return postalOrigen;
    }
    public void setPostalOrigen(String postalOrigen) {
        this.postalOrigen = postalOrigen;
    }
    public String getPostalDestino() {
        return postalDestino;
    }
    public void setPostalDestino(String postalDestino) {
        this.postalDestino = postalDestino;
    }
    public String getFechaSol() {
        return fechaSol;
    }
    public void setFechaSol(String fechaSol) {
        this.fechaSol = fechaSol;
    }
    //CORRECTO QUE PUEDA SETEAR UNA CLAVE COMO DNI?
    public String getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
    public int getM3() {
        return m3;
    }
    public void setM3(int m3) {
        this.m3 = m3;
    }
    public int getCantBultos() {
        return cantBultos;
    }
    public void setCantBultos(int cantBultos) {
        this.cantBultos = cantBultos;
    }
    public String getDomicilioRetiro() {
        return domicilioRetiro;
    }
    public void setDomicilioRetiro(String domicilioRetiro) {
        this.domicilioRetiro = domicilioRetiro;
    }
    public String getDomicilioEntrega() {
        return domicilioEntrega;
    }
    public void setDomicilioEntrega(String domicilioEntrega) {
        this.domicilioEntrega = domicilioEntrega;
    }
    public boolean isPago() {
        return pagado;
    }
    public void setPago(boolean pago) {
        this.pagado = pago;
    }
    @Override
    public String toString() {
        return "Viaje a " + postalDestino + " | Cliente: " + idCliente + " | Volumen: " + m3 + " m3";
    }

    
}
