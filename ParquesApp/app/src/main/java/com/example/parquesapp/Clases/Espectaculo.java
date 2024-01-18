package com.example.parquesapp.Clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Espectaculo implements Parcelable {
    private int id;
    private String name;
    private int duracion;
    private String horarios;
    private String descripcion;

    public Espectaculo(int id, String name, int duracion, String horarios, String descripcion) {
        this.id = id;
        this.name = name;
        this.duracion = duracion;
        this.horarios = horarios;
        this.descripcion = descripcion;
    }

    protected Espectaculo(Parcel in) {
        id = in.readInt();
        name = in.readString();
        duracion = in.readInt();
        horarios = in.readString();
        descripcion = in.readString();
    }

    public static final Creator<Espectaculo> CREATOR = new Creator<Espectaculo>() {
        @Override
        public Espectaculo createFromParcel(Parcel in) {
            return new Espectaculo(in);
        }

        @Override
        public Espectaculo[] newArray(int size) {
            return new Espectaculo[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDuracion() {
        return duracion;
    }

    public String getHorarios() {
        return horarios;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "                              "+name +"\n"+
                "\n                    "+ horarios + "  ("+ duracion + " min)\n"+
                "\n" + descripcion;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(duracion);
        dest.writeString(horarios);
        dest.writeString(descripcion);
    }
}
