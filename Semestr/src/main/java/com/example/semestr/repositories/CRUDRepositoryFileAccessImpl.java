package com.example.semestr.repositories;

import com.example.semestr.entities.FileAccess;
import com.example.semestr.exceptions.DbException;
import com.example.semestr.exceptions.DuplicateEntryException;
import com.example.semestr.exceptions.NoFoundRows;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CRUDRepositoryFileAccessImpl implements CRUDRepositoryFileAccess {

    private final DataSource dataSource;

    public CRUDRepositoryFileAccessImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    private Function<ResultSet, FileAccess> rsMapper = resultSet -> {

        try {
            Long fileId = resultSet.getLong("file_id");
            Long userId = resultSet.getLong("user_id");

            return FileAccess.builder()
                    .fileId(fileId)
                    .userId(userId)
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };


    //language=SQL
    private final String SQL_SAVE = " INSERT INTO file_user_id (file_id, user_id) VALUES (? , ?) ";

    @Override
    public void save(Long fileId, Long userId) throws DbException {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SQL_SAVE)) {
            preparedStatement.setLong(1, fileId);
            preparedStatement.setLong(2, userId);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new DuplicateEntryException(e);
        }
    }

    @Override
    public void save(FileAccess fileAccess) throws DbException {

        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SQL_SAVE)) {
            preparedStatement.setLong(1, fileAccess.getFileId());
            preparedStatement.setLong(2, fileAccess.getUserId());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public List<FileAccess> findAll() {
        return null;
    }

    //language=SQL
    private final String SQL_UPDATE = "UPDATE file_user_id SET file_id = ?, user_id = ?  WHERE file_id = ? AND user_id = ?  ";


    @Override
    public void update(FileAccess fileAccess) {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SQL_UPDATE)) {
            preparedStatement.setLong(1, fileAccess.getFileId());
            preparedStatement.setLong(2, fileAccess.getUserId());
            preparedStatement.setLong(3, fileAccess.getFileId());
            preparedStatement.setLong(4, fileAccess.getUserId());

            int updRows = preparedStatement.executeUpdate();

            if (updRows == 0) {
                throw new NoFoundRows();
            }
        } catch (SQLException | NoFoundRows e) {
            throw new RuntimeException(e);
        }
    }


    //language=SQL
    private final String SQL_DELETE = "DELETE FROM file_user_id WHERE file_id = ? AND user_id = ?";

    @Override
    public void delete(FileAccess fileAccess) {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SQL_DELETE);
        ) {
            preparedStatement.setLong(1, fileAccess.getFileId());
            preparedStatement.setLong(2, fileAccess.getUserId());

            int delRows = preparedStatement.executeUpdate();

            if (delRows == 0) {
                throw new NoFoundRows();
            }

        } catch (SQLException | NoFoundRows e) {
            throw new RuntimeException(e);
        }

    }

    public void delete(Long fileId, Long userId) {
        try (PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement(SQL_DELETE);
        ) {
            preparedStatement.setLong(1, fileId);
            preparedStatement.setLong(2, userId);

            int delRows = preparedStatement.executeUpdate();

            if (delRows == 0) {
                throw new NoFoundRows();
            }

        } catch (SQLException | NoFoundRows e) {
            throw new RuntimeException(e);
        }

    }

    //language=SQL
    private final String SQL_DELETE_BY_FILE_ID = "DELETE FROM file_user_id WHERE file_id = ?";

    public void deleteByFileId(Long fileId) throws NoFoundRows {
        try (PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement(SQL_DELETE_BY_FILE_ID);
        ) {
            preparedStatement.setLong(1, fileId);

            int delRows = preparedStatement.executeUpdate();

            if (delRows == 0) {
                throw new NoFoundRows();
            }

        } catch (SQLException | NoFoundRows e) {
            throw new NoFoundRows(e);
        }
    }


    //language=SQL
    private final String SQL_FIND_BY_FILE_ID = "SELECT file_id, user_id from file_user_id where file_id = ?";

    public List<FileAccess> findByFileId(Long fileId) {
        List<FileAccess> fileAccesses = new ArrayList<>();

        try (PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement(SQL_FIND_BY_FILE_ID);
        ) {
            preparedStatement.setLong(1, fileId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                fileAccesses.add(rsMapper.apply(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return fileAccesses;
    }

    //language=SQL
    private final String SQL_FIND_BY_USER_ID = "SELECT file_id, user_id from file_user_id where user_id = ?";

    public List<FileAccess> findByUserId(Long userId) {
        List<FileAccess> fileAccesses = new ArrayList<>();

        try (PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement(SQL_FIND_BY_USER_ID);
        ) {
            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                fileAccesses.add(rsMapper.apply(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return fileAccesses;
    }


    //language=SQL
    private final String SQL_FIND_BY_FILE_ID_AND_USER_ID = "SELECT file_id, user_id from file_user_id where file_id = ? AND user_id = ?";

    public FileAccess findByFileIdAndUserId(Long fileId, Long userId) throws NoFoundRows {
        try (PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement(SQL_FIND_BY_FILE_ID_AND_USER_ID);
        ) {
            preparedStatement.setLong(1, fileId);
            preparedStatement.setLong(2, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return rsMapper.apply(resultSet);
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new NoFoundRows(e);
        }

    }


}
