package repository;

import domain.Donatie;
import domain.Voluntar;

import java.util.List;

public interface DonatieRepo extends Repository<Integer, Donatie>{
    List<Donatie> filterByDonator(String donator);
    List<Donatie> filterByPhone(String donator);
    List<Donatie> filterByCaritabilID(int ID);
}
