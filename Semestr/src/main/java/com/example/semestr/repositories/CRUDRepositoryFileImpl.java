package com.example.semestr.repositories;

import com.example.semestr.entities.FileDC;
import com.example.semestr.exeption.DbException;
import com.example.semestr.exeption.DuplicateEntryException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CRUDRepositoryFileImpl implements CRUDRepositoryFile {

    private final DataSource dataSource;

    public CRUDRepositoryFileImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Function<ResultSet, FileDC> rsMapper = resultSet -> {

        try {
            Long idFile = resultSet.getLong("idFile");
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            Long holderId = resultSet.getLong("holderId");
            String nameFile = resultSet.getString("nameFile");
            boolean publicAccess = resultSet.getBoolean("publicAccess");


            return FileDC.builder()
                    .idFile(idFile)
                    .title(title)
                    .description(description)
                    .holderId(holderId)
                    .nameFile(nameFile)
                    .publicAccess(publicAccess)
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    };

    //language=SQL
    private final String SQL_SAVE_FILE = "INSERT INTO file_oris(title, description, holderid, namefile, publicaccess) VALUES (?,?,?,?,?)";

    @Override
    public void save(FileDC file) throws DbException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_FILE, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, file.getTitle());
            preparedStatement.setString(2, file.getDescription());
            preparedStatement.setLong(3, file.getHolderId());
            preparedStatement.setString(4, file.getNameFile());
            preparedStatement.setBoolean(5, file.isPublicAccess());

            preparedStatement.executeUpdate();

            ResultSet keygen = preparedStatement.getGeneratedKeys();

            if (keygen.next()) {
                file.setIdFile(keygen.getLong("id"));
            }

        } catch (SQLException e) {
            throw new DuplicateEntryException(e);
        }

    }

    @Override
    public List<FileDC> findAll() {
        return null;
    }

    //language=SQL
    private final String SQL_FIND_FILES_BY_PUBLIC_ACCESS = "SELECT * FROM file_oris WHERE publicaccess  = ? ";

    public List<FileDC> findAllPublic() {
        List<FileDC> files = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_FILES_BY_PUBLIC_ACCESS);
        ) {
            preparedStatement.setBoolean(1, true);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                files.add(rsMapper.apply(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return files;
    }


    //language=SQL
    private final String SQL_FIND_FILES_BY_ID_USER = "SELECT * FROM file_oris WHERE holderid  = ? ";

    public List<FileDC> findByIdUser(Long idUser) {
        List<FileDC> files = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_FILES_BY_ID_USER);
        ) {
            preparedStatement.setLong(1, idUser);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                files.add(rsMapper.apply(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return files;
    }


    //language=SQL
    private final String SQL_FIND_FILES_BY_ID = "SELECT * FROM file_oris WHERE id  = ? ";

    @Override
    public FileDC findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_FILES_BY_ID)
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


    @Override
    public void update(FileDC file) {

    }

    //language=SQL
    private final String SQL_DELETE_BY_ID = "DELETE FROM file_oris WHERE id  = ? ";

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID);
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
