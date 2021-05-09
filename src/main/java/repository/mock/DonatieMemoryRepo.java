package repository.mock;

import domain.Donatie;
import repository.DonatieRepo;

import java.util.List;
import java.util.stream.Collectors;

public class DonatieMemoryRepo extends AbstractRepository<Integer, Donatie> implements DonatieRepo {

    @Override
    public List<Donatie> filterByDonator(String nume) {
        return getAll().stream().filter(x->x.getNume_donator().toLowerCase().contains(nume.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    public List<Donatie> filterByPhone(String phone) {
        return getAll().stream().filter(x->x.getNumar_telefon().toLowerCase().equals(phone.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    public List<Donatie> filterByCaritabilID(int ID) {
        return getAll().stream().filter(x->x.getID().equals(ID)).collect(Collectors.toList());
    }
}
