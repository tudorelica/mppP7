package repository;

import domain.CazCaritabil;
import domain.Voluntar;

import java.util.List;

public interface CaritabilRepo extends Repository<Integer, CazCaritabil>{
    List<CazCaritabil> filterByName(String titlu);
}
