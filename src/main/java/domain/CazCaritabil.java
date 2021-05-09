package domain;

import java.io.Serializable;

public class CazCaritabil implements Identifiable<Integer>, Serializable {

    private int ID;
    private String titlu;
    private String descriere;
    private double sum = 0;

    public void setSum(double sum) {
        this.sum = sum;
    }

    public CazCaritabil(int ID, String titlu, String descriere) {
        this.ID = ID;
        this.titlu = titlu;
        this.descriere = descriere;
    }

    @Override
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    @Override
    public String toString()
    {
        return this.getTitlu() + "     suma: " + Double.toString(sum);
    }
}
