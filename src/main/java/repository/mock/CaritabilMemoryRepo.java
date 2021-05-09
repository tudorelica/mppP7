package repository.mock;

import domain.CazCaritabil;
import repository.CaritabilRepo;

import java.util.List;
import java.util.stream.Collectors;

public class CaritabilMemoryRepo extends AbstractRepository<Integer, CazCaritabil> implements CaritabilRepo {

    @Override
    public List<CazCaritabil> filterByName(String titlu) {
        return this.getAll().stream().filter(x->x.getTitlu().toLowerCase().contains(titlu.toLowerCase())).collect(Collectors.toList());
    }

}