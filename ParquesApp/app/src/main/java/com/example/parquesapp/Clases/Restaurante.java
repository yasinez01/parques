package com.example.parquesapp.Clases;

import android.os.Parcel;
import android.os.Parcelable;

public class Restaurante implements Parcelable {
    private int id;
    private String name;
    private int horario_apertura;
    private int horario_cerradura;

    public Restaurante(int id, String name, int horario_apertura, int horario_cerradura) {
        this.id = id;
        this.name = name;
        this.horario_apertura = horario_apertura;
        this.horario_cerradura = horario_cerradura;
    }

    protected Restaurante(Parcel in) {
        id = in.readInt();
        name = in.readString();
        horario_apertura = in.readInt();
        horario_cerradura = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(horario_apertura);
        dest.writeInt(horario_cerradura);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Restaurante> CREATOR = new Creator<Restaurante>() {
        @Override
        public Restaurante createFromParcel(Parcel in) {
            return new Restaurante(in);
        }

        @Override
        public Restaurante[] newArray(int size) {
            return new Restaurante[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHorario_apertura(int horario_apertura) {
        this.horario_apertura = horario_apertura;
    }

    public void setHorario_cerradura(int horario_cerradura) {
        this.horario_cerradura = horario_cerradura;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHorario_apertura() {
        return horario_apertura;
    }

    public int getHorario_cerradura() {
        return horario_cerradura;
    }

    @Override
    public String toString() {
        return "                            "+name +"\n"+
                "Apertura: " + horario_apertura +":00" +
                "                  Cierre: " + horario_cerradura+":00";
    }
}
