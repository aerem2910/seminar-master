package notebook.util;

import java.io.File;

import notebook.controller.UserController;
import notebook.model.dao.impl.FileOperation;
import notebook.model.repository.GBRepository;
import notebook.model.repository.impl.UserRepository;

public class DBConnector {
    public static final String DB_PATH = "db.txt";

    public static void createDB() {
        try {
            File db = new File(DB_PATH);
            if (db.createNewFile()) {
                System.out.println("DB created");
            } else {
                System.out.println("DB already exists");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    // Удаление пользователя с идентификатором 1
    public static void main(String[] args) {
        DBConnector.createDB();

        GBRepository userRepository = new UserRepository(new FileOperation(DB_PATH));
        UserController userController = new UserController(userRepository);

        userController.deleteUser(1L);
    }
}

