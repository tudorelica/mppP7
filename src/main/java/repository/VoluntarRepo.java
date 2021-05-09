package repository;

import domain.Voluntar;

public interface VoluntarRepo extends Repository<Integer,Voluntar>{
    Voluntar login(String user,String password);
}
