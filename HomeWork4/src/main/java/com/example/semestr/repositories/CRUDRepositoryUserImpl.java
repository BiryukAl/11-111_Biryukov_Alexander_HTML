package com.example.semestr.repositories;

import com.example.semestr.entities.User;
import jakarta.jws.soap.SOAPBinding;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CRUDRepositoryUserImpl implements CRUDRepositoryUser {

    //language=SQL
    private final String SQL_FIND_ALL = "SELECT * FROM user_oris_hm4";

    //language=SQL
    private final String SQL_SAVE_USER = "INSERT INTO user_oris_hm4(login, name, password) VALUES (?,?,?)";

    private final DataSource dataSource;

    public CRUDRepositoryUserImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Function<ResultSet, User> rsMapper = resultSet -> {

        try{

            /*private int id;
            private String name;
            private String login;
            private String password;*/

            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");

            return User.builder()
                    .id(id)
                    .login(login)
                    .name(name)
                    .password(password)
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    };

    @Override
    public void save(User user) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SAVE_USER, PreparedStatement.RETURN_GENERATED_KEYS);
        ){
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getLogin());

            statement.executeUpdate();

            ResultSet keygen = statement.getGeneratedKeys();

            if (keygen.next()){

                user.setId(keygen.getLong("id"));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL);
        ) {

            while (resultSet.next()){

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

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(Long id) {

    }
}
