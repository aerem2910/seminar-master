#Программа представляет собой консольное приложение для управления списком пользователей. 
 
Вот основные функции, которые реализованы в программе:

Создание пользователя (CREATE):

Вводятся данные нового пользователя, такие как имя, фамилия и номер телефона.
Созданный пользователь добавляется в репозиторий (в данном случае, в текстовый файл).
Чтение пользователя по идентификатору (READ):

Пользователь вводит идентификатор (ID) пользователя.
Программа ищет пользователя с указанным ID в репозитории и выводит информацию о нем.
Обновление пользователя (UPDATE):

Пользователь вводит идентификатор (ID) пользователя, данные которого нужно обновить.
Вводятся новые данные для пользователя (имя, фамилия, номер телефона).
Информация о пользователе обновляется в репозитории.
Чтение всех пользователей (READ_ALL):

Выводится список всех пользователей из репозитория.
Удаление пользователя (DELETE):

Пользователь вводит идентификатор (ID) пользователя, которого нужно удалить.
Программа удаляет пользователя с указанным ID из репозитория.
Выход из программы (EXIT):

Завершение выполнения программы.
Основные компоненты программы:

UserController: Контроллер, обрабатывающий действия пользователя.
UserRepository: Репозиторий, ответственный за работу с пользователями и хранение их данных.
User: Модель пользователя.
FileOperation: Реализация операций ввода/вывода в файл.
DBConnector: Создание файла для хранения данных и удаление пользователя с идентификатором 1 при запуске.
Программа ориентирована на взаимодействие с пользователями через командную строку.


Изменения, которые внесли в код, в основном связаны с реорганизацией структуры проекта в соответствии с заданием. Вот основные изменения:

Удаление пользователей (deleteUser в UserController):

java
Copy code
public void deleteUser(Long userId) {
    if (repository.delete(userId)) {
        System.out.println("User deleted successfully");
    } else {
        System.out.println("User not found");
    }
}
Перенос метода createUser из UserView в UserController:

java
Copy code
public void createUser(User user) {
    repository.save(user);
}
Изменение метода saveUser в UserController:

java
Copy code
public void saveUser(User user) {
    repository.create(user);
}
Добавление метода delete в GBRepository:

java
Copy code
boolean delete(Long id);
Перенос логики DAO в репозиторий (UserRepository):

java
Copy code
public boolean delete(Long id) {
    List<User> users = findAll();
    boolean removed = users.removeIf(user -> user.getId().equals(id));
    if (removed) {
        write(users);
    }
    return removed;
}
Добавление интерфейса GBRepository с методами для работы с пользователями:

java
Copy code
public interface GBRepository {
    List<User> findAll();
    User create(User user);
    Optional<User> findById(Long id);
    Optional<User> update(Long userId, User update);
    boolean delete(Long id);
}
Изменения в DBConnector (удаление пользователя с идентификатором 1):

java
Copy code
public static void main(String[] args) {
    DBConnector.createDB();

    GBRepository userRepository = new UserRepository(new FileOperation(DBConnector.DB_PATH));
    UserController userController = new UserController(userRepository);

    userController.deleteUser(1L);
}
Важно отметить, что для корректной работы метода deleteUser в UserController, был добавлен метод delete в интерфейс GBRepository и его реализация в UserRepository.
