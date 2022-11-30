package com.example.semestr.repositories;

import com.example.semestr.entities.User;
import com.example.semestr.exceptions.DbException;
import com.example.semestr.exceptions.DuplicateEntryException;
import com.example.semestr.exceptions.NoFoundRows;
import com.example.semestr.exceptions.NotUniqueLogin;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        try (PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement(SQL_SAVE_USER, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.executeUpdate();

            ResultSet keygen = preparedStatement.getGeneratedKeys();

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

        try (Statement statement = dataSource.getConnection().createStatement();
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


    //language=SQL
    private final String SQL_FIND_USER_BY_ID = "SELECT * FROM user_oris_hm4 WHERE id = ? ";

    @Override
    public User findById(Long id) {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SQL_FIND_USER_BY_ID);
        ) {
            preparedStatement.setLong(1, id);

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
    private final String SQL_FIND_USER_BY_LOG_AND_PASS = "SELECT * FROM user_oris_hm4 WHERE login = ? AND  password = ?";

    public User findByLoginAndPassword(String login, String password) {
        try (PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement(SQL_FIND_USER_BY_LOG_AND_PASS);
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

        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SQL_FIND_USER_BY_LOGIN);
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


    //language=SQL
    private final String SQL_UPDATE = "UPDATE user_oris_hm4 SET name = ?, login = ? WHERE id = ? ";

    @Override
    public void update(User user) throws NoFoundRows, NotUniqueLogin {
        try (PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setLong(3, user.getId());

            int updRows = preparedStatement.executeUpdate();

            if (updRows == 0) {
                throw new NoFoundRows();
            }
        } catch (SQLException e) {
            throw new NotUniqueLogin(e);
        } catch (NoFoundRows e) {
            throw new NoFoundRows(e);
        }
    }

    //language=SQL
    private final String SQL_UPDATE_PASSWORD = "UPDATE user_oris_hm4 SET password = ? WHERE id = ? ";

    public void updatePassword(User user) throws NoFoundRows {
        try (PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement(SQL_UPDATE_PASSWORD)) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setLong(2, user.getId());

            int updRows = preparedStatement.executeUpdate();

            if (updRows == 0) {
                throw new NoFoundRows();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoFoundRows e) {
            throw new NoFoundRows(e);
        }
    }


    //language=SQL
    private final String SQL_DELETE_BY_ID = "DELETE FROM user_oris_hm4 WHERE id  = ? ";

    @Override
    public void delete(Long id) {
        try (PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement(SQL_DELETE_BY_ID);
        ) {
            preparedStatement.setLong(1, id);
            int delRows = preparedStatement.executeUpdate();

            if (delRows == 0) {
                throw new NoFoundRows();
            }

        } catch (SQLException | NoFoundRows e) {
            throw new RuntimeException(e);
        }

    }
}
