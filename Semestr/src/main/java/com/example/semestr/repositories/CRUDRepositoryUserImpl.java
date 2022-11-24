package com.example.semestr.repositories;

import com.example.semestr.entities.User;
import com.example.semestr.exeption.DbException;
import com.example.semestr.exeption.DuplicateEntryException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CRUDRepositoryUserImpl implements CRUDRepositoryUser {
    private final DataSource dataSource;

    public CRUDRepositoryUserImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Function<ResultSet, User> rsMapper = resultSet -> {

        try {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");

            return User.builder()
                    .id(id)
                    .name(name)
                    .login(login)
                    .password(password)
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    };


    //language=SQL
    private final String SQL_SAVE_USER = "INSERT INTO user_oris_hm4(name, login, password) VALUES (?,?,?)";

    @Override
    public void save(User user) throws DbException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SAVE_USER, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());

            statement.executeUpdate();

            ResultSet keygen = statement.getGeneratedKeys();

            if (keygen.next()) {
                user.setId(keygen.getLong("id"));
            }

        } catch (SQLException e) {
            throw new DuplicateEntryException(e);
        }
    }


    //language=SQL
    private final String SQL_FIND_ALL_USER = "SELECT * FROM user_oris_hm4";

    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_USER);
        ) {
            while (resultSet.next()) {
                users.add(rsMapper.apply(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    //language=SQL
    private final String SQL_FIND_USER_BY_LOG_AND_PASS = "SELECT * FROM user_oris_hm4 WHERE login = ? AND  password = ?";

    public User findByLoginAndPassword(String login, String password) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOG_AND_PASS);
        ) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return rsMapper.apply(resultSet);
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //language=SQL
    private final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM user_oris_hm4 WHERE login = ?";


    public User findByLogin(String login) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
        ) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return rsMapper.apply(resultSet);
            } else {
                return null;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(User user) {
    }

    @Override
    public void delete(Long id) {
    }
}
