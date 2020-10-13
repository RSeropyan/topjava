package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {

        if (user == null) {
            return null;
        }
        else if (user.isNew()) {
            // CREATE new entity operation has been requested
            user.setId(counter.incrementAndGet());
            repository.put(counter.get(), user);
            return user;
        }
        else {
            // UPDATE an existing entity operation has been requested
            Integer id = user.getId();
            if (repository.containsKey(id)) {
                repository.replace(id, user);
                return user;
            }
            return null;
        }

    }

    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {

        return repository.values()
                .stream()
                .sorted(Comparator
                        .comparing(User::getName)
                        .thenComparing(User::getEmail))
                .collect(Collectors.toList());

    }

    @Override
    public User getByEmail(String email) {

        if (email == null) {
            return null;
        }
        Optional<User> user = repository.values()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
        return user.orElse(null);

    }
}
