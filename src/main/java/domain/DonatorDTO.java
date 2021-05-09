package domain;

public class DonatorDTO {
    String nume;
    String telefon;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public DonatorDTO(String nume, String telefon) {
        this.nume = nume;
        this.telefon = telefon;
    }

    @Override
    public String toString()
    {
        return this.getNume() + "  tel:" + this.getTelefon();
    }
}
