package co.com.bancolombia.model.stats;

import java.time.LocalDateTime;

/**
 * Entidad de dominio que representa las estadísticas de contacto de clientes.
 *
 * <p>Incluye información sobre diferentes motivos de contacto, un timestamp y un hash identificador.</p>
 */
public class Stats {
    private LocalDateTime timestamp;
    private int totalContactoClientes;
    private int motivoReclamo;
    private int motivoGarantia;
    private int motivoDuda;
    private int motivoCompra;
    private int motivoFelicitaciones;
    private int motivoCambio;
    private String hash;

    /**
     * Constructor por defecto.
     */
    public Stats() {
    }

    /**
     * Constructor completo para inicializar todos los campos de la entidad.
     *
     * @param timestamp Fecha y hora del registro
     * @param totalContactoClientes Total de contactos de clientes
     * @param motivoReclamo Cantidad de contactos por reclamo
     * @param motivoGarantia Cantidad de contactos por garantía
     * @param motivoDuda Cantidad de contactos por duda
     * @param motivoCompra Cantidad de contactos por compra
     * @param motivoFelicitaciones Cantidad de contactos por felicitaciones
     * @param motivoCambio Cantidad de contactos por cambio
     * @param hash Identificador hash del registro
     */
    public Stats(LocalDateTime timestamp, int totalContactoClientes, int motivoReclamo, int motivoGarantia, int motivoDuda, int motivoCompra, int motivoFelicitaciones, int motivoCambio, String hash) {
        this.timestamp = timestamp;
        this.totalContactoClientes = totalContactoClientes;
        this.motivoReclamo = motivoReclamo;
        this.motivoGarantia = motivoGarantia;
        this.motivoDuda = motivoDuda;
        this.motivoCompra = motivoCompra;
        this.motivoFelicitaciones = motivoFelicitaciones;
        this.motivoCambio = motivoCambio;
        this.hash = hash;
    }

    /**
     * Obtiene el timestamp del registro.
     * @return Fecha y hora del registro
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Asigna el timestamp al momento actual.
     */
    public void setTimestamp() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Obtiene el total de contactos de clientes.
     * @return Total de contactos
     */
    public int getTotalContactoClientes() {
        return totalContactoClientes;
    }

    /**
     * Asigna el total de contactos de clientes.
     * @param totalContactoClientes Total de contactos
     */
    public void setTotalContactoClientes(int totalContactoClientes) {
        this.totalContactoClientes = totalContactoClientes;
    }

    /**
     * Obtiene la cantidad de contactos por reclamo.
     * @return Cantidad de reclamos
     */
    public int getMotivoReclamo() {
        return motivoReclamo;
    }

    /**
     * Asigna la cantidad de contactos por reclamo.
     * @param motivoReclamo Cantidad de reclamos
     */
    public void setMotivoReclamo(int motivoReclamo) {
        this.motivoReclamo = motivoReclamo;
    }

    /**
     * Obtiene la cantidad de contactos por garantía.
     * @return Cantidad de garantías
     */
    public int getMotivoGarantia() {
        return motivoGarantia;
    }

    /**
     * Asigna la cantidad de contactos por garantía.
     * @param motivoGarantia Cantidad de garantías
     */
    public void setMotivoGarantia(int motivoGarantia) {
        this.motivoGarantia = motivoGarantia;
    }

    /**
     * Obtiene la cantidad de contactos por duda.
     * @return Cantidad de dudas
     */
    public int getMotivoDuda() {
        return motivoDuda;
    }

    /**
     * Asigna la cantidad de contactos por duda.
     * @param motivoDuda Cantidad de dudas
     */
    public void setMotivoDuda(int motivoDuda) {
        this.motivoDuda = motivoDuda;
    }

    /**
     * Obtiene la cantidad de contactos por compra.
     * @return Cantidad de compras
     */
    public int getMotivoCompra() {
        return motivoCompra;
    }

    /**
     * Asigna la cantidad de contactos por compra.
     * @param motivoCompra Cantidad de compras
     */
    public void setMotivoCompra(int motivoCompra) {
        this.motivoCompra = motivoCompra;
    }

    /**
     * Obtiene la cantidad de contactos por felicitaciones.
     * @return Cantidad de felicitaciones
     */
    public int getMotivoFelicitaciones() {
        return motivoFelicitaciones;
    }

    /**
     * Asigna la cantidad de contactos por felicitaciones.
     * @param motivoFelicitaciones Cantidad de felicitaciones
     */
    public void setMotivoFelicitaciones(int motivoFelicitaciones) {
        this.motivoFelicitaciones = motivoFelicitaciones;
    }

    /**
     * Obtiene la cantidad de contactos por cambio.
     * @return Cantidad de cambios
     */
    public int getMotivoCambio() {
        return motivoCambio;
    }

    /**
     * Asigna la cantidad de contactos por cambio.
     * @param motivoCambio Cantidad de cambios
     */
    public void setMotivoCambio(int motivoCambio) {
        this.motivoCambio = motivoCambio;
    }

    /**
     * Obtiene el hash identificador del registro.
     * @return Hash del registro
     */
    public String getHash() {
        return hash;
    }

    /**
     * Asigna el hash identificador del registro.
     * @param hash Hash del registro
     */
    public void setHash(String hash) {
        this.hash = hash;
    }
}
