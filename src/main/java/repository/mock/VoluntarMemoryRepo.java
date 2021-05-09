package repository.mock;

import domain.Voluntar;
import repository.VoluntarRepo;

public class VoluntarMemoryRepo extends AbstractRepository<Integer, Voluntar> implements VoluntarRepo {

    @Override
    public Voluntar login(String username, String password) {
        for ( Voluntar voluntar : this.getAll()) {
            if (username.equals(voluntar.getUser()) && (password.equals(voluntar.getPassword())))
                return voluntar;
        }

        return null;
    }
}