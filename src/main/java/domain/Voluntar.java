package domain;

import java.io.Serializable;

public class Voluntar implements Identifiable<Integer>, Serializable {


    private int ID;
    private String user;
    private String password;
    private String nume;

    public Voluntar(int ID, String user, String password, String nume) {
        this.ID = ID;
        this.user = user;
        this.password = password;
        this.nume = nume;
    }

    @Override
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}
