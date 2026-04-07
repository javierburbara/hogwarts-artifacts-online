package edu.tcu.cs.hogwartsartifactsonline.user;

import edu.tcu.cs.hogwartsartifactsonline.system.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<HogwartsUser> findAll() {
        return repository.findAll();
    }

    public HogwartsUser findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("user", id));
    }

    public HogwartsUser save(HogwartsUser user) {
        return repository.save(user);
    }

    public HogwartsUser update(Integer id, HogwartsUser update) {
        return repository.findById(id)
                .map(old -> {
                    old.setUsername(update.getUsername());
                    old.setEnabled(update.isEnabled());
                    old.setRoles(update.getRoles());
                    return repository.save(old);
                })
                .orElseThrow(() -> new ObjectNotFoundException("user", id));
    }

    public void delete(Integer id) {
        HogwartsUser user = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("user", id));

        repository.deleteById(user.getId());
    }
}
