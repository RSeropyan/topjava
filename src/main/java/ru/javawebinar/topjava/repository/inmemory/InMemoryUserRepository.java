package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
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

        if (user.isNew()) {
            // Here we CREATE new entity
            user.setId(counter.incrementAndGet());
            repository.put(counter.get(), user);
            return user;
        }
        else {
            // Here we UPDATE an existing entity
            Integer id = user.getId();
            if (repository.containsKey(id)) {
                repository.replace(id, user);
                return repository.get(id);
            } else {
                return null;
            }
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
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());

    }

    @Override
    public User getByEmail(String email) {

        Optional<User> user = repository.values()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
        return user.orElse(null);

    }
}
