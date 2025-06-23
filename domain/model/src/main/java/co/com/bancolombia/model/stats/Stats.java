package co.com.bancolombia.model.stats;

import java.time.LocalDateTime;

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

    public Stats() {
    }

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp() {
        this.timestamp = LocalDateTime.now();
    }

    public int getTotalContactoClientes() {
        return totalContactoClientes;
    }

    public void setTotalContactoClientes(int totalContactoClientes) {
        this.totalContactoClientes = totalContactoClientes;
    }

    public int getMotivoReclamo() {
        return motivoReclamo;
    }

    public void setMotivoReclamo(int motivoReclamo) {
        this.motivoReclamo = motivoReclamo;
    }

    public int getMotivoGarantia() {
        return motivoGarantia;
    }

    public void setMotivoGarantia(int motivoGarantia) {
        this.motivoGarantia = motivoGarantia;
    }

    public int getMotivoDuda() {
        return motivoDuda;
    }

    public void setMotivoDuda(int motivoDuda) {
        this.motivoDuda = motivoDuda;
    }

    public int getMotivoCompra() {
        return motivoCompra;
    }

    public void setMotivoCompra(int motivoCompra) {
        this.motivoCompra = motivoCompra;
    }

    public int getMotivoFelicitaciones() {
        return motivoFelicitaciones;
    }

    public void setMotivoFelicitaciones(int motivoFelicitaciones) {
        this.motivoFelicitaciones = motivoFelicitaciones;
    }

    public int getMotivoCambio() {
        return motivoCambio;
    }

    public void setMotivoCambio(int motivoCambio) {
        this.motivoCambio = motivoCambio;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
