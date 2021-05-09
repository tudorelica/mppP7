package domain;

import java.io.Serializable;

public class Donatie implements Identifiable<Integer>, Serializable {

    private int ID;
    private int ID_caz_caritabil;
    private String nume_donator;
    private String numar_telefon;
    private double suma;

    public Donatie(int ID, int ID_caz_caritabil, String nume_donator, String numar_telefon, double suma) {
        this.ID = ID;
        this.ID_caz_caritabil = ID_caz_caritabil;
        this.nume_donator = nume_donator;
        this.numar_telefon = numar_telefon;
        this.suma = suma;
    }

    @Override
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public int getID_caz_caritabil() {
        return ID_caz_caritabil;
    }

    public void setID_caz_caritabil(int ID_caz_caritabil) {
        this.ID_caz_caritabil = ID_caz_caritabil;
    }

    public String getNume_donator() {
        return nume_donator;
    }

    public void setNume_donator(String nume_donator) {
        this.nume_donator = nume_donator;
    }

    public String getNumar_telefon() {
        return numar_telefon;
    }

    public void setNumar_telefon(String numar_telefon) {
        this.numar_telefon = numar_telefon;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }
}
