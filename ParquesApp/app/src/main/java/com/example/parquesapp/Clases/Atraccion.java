package com.example.parquesapp.Clases;

public class Atraccion {
    private int id;
    private String name;
    private String descripcion;
    private int wait_time;
    private String last_update;
    private boolean is_open;
    // Constructor
    public Atraccion(int id, String name, int wait_time, String last_update,boolean is_open) {
        this.id = id;
        this.name = name;
        this.wait_time = wait_time;
        this.last_update = last_update;
        this.is_open = is_open;
    }

    public Atraccion(int id, String name, int wait_time, String last_update, String descripcion) {
        this.id = id;
        this.name = name;
        this.wait_time = wait_time;
        this.last_update = last_update;
        this.descripcion = descripcion;
    }
    public int getId() {
        return this.id;
    }
    public String getNombre(){
        return this.name;
    }
    public int getTiempoEspera(){
        return this.wait_time;
    }
    public String getDescripcion(){ return this.descripcion; }
    public String getUltimaActualizacion(){
        return this.last_update;
    }
    public boolean getIs_Open(){ return this.is_open;}

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setWait_time(int wait_time) {
        this.wait_time = wait_time;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }
    public void setIs_open(Boolean is_open) { this.is_open = is_open;}

    // Métodos
    public void saludar() {
        System.out.println(name + ": " + wait_time);
    }

}
