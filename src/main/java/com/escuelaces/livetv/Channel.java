package com.escuelaces.livetv;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Channel {
    public SimpleIntegerProperty Id;
    public SimpleStringProperty Nombre;
    public SimpleStringProperty URL;

    public Channel(int id, String nombre, String url) {
        Id = new SimpleIntegerProperty(id);
        Nombre = new SimpleStringProperty(nombre);
        URL = new SimpleStringProperty(url);
    }
    /*
    public int getId() {
        return idProperty.get();
    }

    public void setId(int id) {
        this.idProperty.set(id);
    }

    public String getNombre() {
        return nombreProperty.get();
    }

    public void setNombre(String nombre) {
        this.nombreProperty.set(nombre);
    }

    public String getUrl() {
        return urlProperty.get();
    }

    public void setUrl(String url) {
        this.urlProperty.set(url);
    }

     */
}
