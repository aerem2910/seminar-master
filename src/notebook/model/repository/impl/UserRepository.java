package notebook.model.repository.impl;

import notebook.model.User;
import notebook.model.dao.Operation;
import notebook.util.mapper.impl.UserMapper;
import notebook.model.repository.GBRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements GBRepository {
    private final UserMapper mapper;
    private final Operation operation;
    private final List<User> users;  // Добавляем переменную users

    public UserRepository(Operation operation) {
        this.mapper = new UserMapper();
        this.operation = operation;
        this.users = findAll();  // Инициализируем users при создании UserRepository
    }

    @Override
    public List<User> findAll() {
        List<String> lines = operation.readAll();
        List<User> users = new ArrayList<>();
        for (String line : lines) {
            users.add(mapper.toOutput(line));
        }
        return users;
    }

    @Override
    public User create(User user) {
        long max = 0L;
        for (User u : users) {
            long id = u.getId();
            if (max < id) {
                max = id;
            }
        }
        long next = max + 1;
        user.setId(next);
        users.add(user);
        write(users);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<User> update(Long userId, User update) {
        User editUser = users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));
        editUser.setFirstName(update.getFirstName().isEmpty() ? editUser.getFirstName() : update.getFirstName());
        editUser.setLastName(update.getLastName().isEmpty() ? editUser.getLastName() : update.getLastName());
        editUser.setPhone(update.getPhone().isEmpty() ? editUser.getPhone() : update.getPhone());
        write(users);
        return Optional.of(update);
    }

    @Override
    public boolean delete(Long id) {
        boolean removed = users.removeIf(user -> user.getId().equals(id));
        if (removed) {
            write(users);
        }
        return removed;
    }

    private void write(List<User> users) {
        List<String> lines = new ArrayList<>();
        for (User u : users) {
            lines.add(mapper.toInput(u));
        }
        operation.saveAll(lines);
    }
}
