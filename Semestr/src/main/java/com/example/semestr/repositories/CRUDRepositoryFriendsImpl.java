package com.example.semestr.repositories;

import com.example.semestr.entities.FriendDC;
import com.example.semestr.entities.RelationFriend;
import com.example.semestr.exceptions.NoFoundRows;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CRUDRepositoryFriendsImpl {

    private final DataSource dataSource;

    public CRUDRepositoryFriendsImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Function<ResultSet, RelationFriend> rsMapper = resultSet -> {
        try {
            Long idUser = resultSet.getLong("id_user");
            Long idFriend = resultSet.getLong("id_friend");

            return RelationFriend.builder()
                    .idUser(idUser)
                    .idFriend(idFriend)
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    private final Function<ResultSet, FriendDC> friendMapper = resultSet -> {
        try {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");

            return FriendDC.builder()
                    .id(id)
                    .name(name)
                    .build();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    //language=SQL
    private final String SQL_FIND_FRIENDS_BY_USER_ID = "SELECT id, name FROM user_oris_hm4 WHERE id IN (SELECT id_friend FROM friends_oris where id_user = ? )";

    public List<FriendDC> findFriendsByIdUser(Long id) {
        List<FriendDC> friends = new ArrayList<>();

        try (PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement(SQL_FIND_FRIENDS_BY_USER_ID)
        ) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                friends.add(friendMapper.apply(resultSet));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return friends;
    }

    //language=SQL
    private final String SQL_SAVE_FRIEND = "INSERT INTO friends_oris(id_user, id_friend) VALUES (?,?)";

    public void save(Long idUser, Long idFriend) {
        try (PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement(SQL_SAVE_FRIEND)
        ) {
            preparedStatement.setLong(1, idUser);
            preparedStatement.setLong(2, idFriend);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //language=SQL
    public final String SQL_DELETE = "DELETE FROM friends_oris WHERE id_user = ? AND id_friend = ? ";

    public void delete(Long idUser, Long idFriend) throws NoFoundRows {
        try (PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement(SQL_DELETE);
        ) {
            preparedStatement.setLong(1, idUser);
            preparedStatement.setLong(2, idFriend);

            int delRows = preparedStatement.executeUpdate();

            if (delRows == 0) {
                throw new NoFoundRows();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoFoundRows e){
            throw new NoFoundRows(e);
        }
    }


    //language=SQL
    public final String SQL_FIND_FRIEND = "SELECT id_user, id_friend FROM friends_oris WHERE id_user = ? AND  id_friend = ? ";
    public RelationFriend isFriend(Long idUser, Long idFriend){
        try (PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement(SQL_FIND_FRIEND);
        ) {
            preparedStatement.setLong(1, idUser);
            preparedStatement.setLong(2, idFriend);

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
    private final String SQL_FRIEND_COUNT = "SELECT count(*) as count from friends_oris WHERE id_user = ?";

    public Long countFriends(Long id){
        try (PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement(SQL_FRIEND_COUNT);
        ){

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getLong("count");
            }else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //language=SQL
    private final String SQL_SUBSCRIBERS_COUNT = "SELECT count(*) as count from friends_oris WHERE id_friend = ?";



    public Long countSubscribers(Long id){
        try (PreparedStatement preparedStatement = dataSource.getConnection()
                .prepareStatement(SQL_SUBSCRIBERS_COUNT);
        ){

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getLong("count");
            }else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





}
