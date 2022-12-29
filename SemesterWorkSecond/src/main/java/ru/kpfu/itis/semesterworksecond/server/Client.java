package ru.kpfu.itis.semesterworksecond.server;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Pair;
import ru.kpfu.itis.semesterworksecond.logic.BattleGrid;
import ru.kpfu.itis.semesterworksecond.logic.model.Holder;
import ru.kpfu.itis.semesterworksecond.myExceptions.MessageException;
import ru.kpfu.itis.semesterworksecond.myExceptions.ServerException;
import ru.kpfu.itis.semesterworksecond.server.message.Message;
import ru.kpfu.itis.semesterworksecond.server.message.MessageUtil;
import ru.kpfu.itis.semesterworksecond.server.message.TypeMessage;
import ru.kpfu.itis.semesterworksecond.utils.Utilites;

import java.io.*;
import java.net.Socket;

public class Client extends Socket {
    InputStream inputStream;
    OutputStream outputStream;
    //    CoreGame coreGame;
    Holder myTurn;
    boolean gameOverLoop = false;

    public Client(String host, int port) throws IOException {
        super(host, port);
        this.inputStream = getInputStream();
        this.outputStream = getOutputStream();
        setSoTimeout(1000);
    }

    Long localAllStep = 0L;

    public boolean checkConnect() throws MessageException {
        /*
        try {
            outputStream.writeObject(new DataMessage(1));
            outputStream.flush();
            if (((DataMessage) inputStream.readObject()).getHead() == 1) {
                return true;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new MessageException(e);
        }
        return false;
        */

        /*
        outputStream.write(1);
        outputStream.flush();
        if (inputStream.read() == 1)
            return true;
        return false;
        */

        try {
            MessageUtil.sendMessage(
                    MessageUtil.createMessage(TypeMessage.CHECK_CONNECT, null),
                    outputStream);
            if (MessageUtil.readMessage(inputStream).getType() == TypeMessage.CONNECT_TRUE) {
                return true;
            }
        } catch (IOException e) {
            throw new MessageException(e);
        }
        return false;
    }

    public void startGame(Stage stage) {
//        coreGame = new CoreGame();
        myTurn = isMyTurn();
        GridPane newGridPane;
        try {
            MessageUtil.sendMessage(MessageUtil.createMessage(TypeMessage.GET_BATTLE_GRID, null), outputStream);
        } catch (IOException e) {
            throw new MessageException(e);
        }
        try {
            Message<Serializable> msg = MessageUtil.readMessage(inputStream);
            if (msg.getType() == TypeMessage.SEND_BATTLE_GRID) {
                BattleGrid battleGrid = (BattleGrid) msg.getValue();
                newGridPane = Utilites.drawGridPainAndGameOver(battleGrid.grid, myTurn).getKey();
            } else {
                newGridPane = new GridPane();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        newGridPane = coreGame.draw();
        newGridPane.setId("playingArea");
    }

    public void startGame(AnchorPane anchorPane) {

        //        coreGame = new CoreGame();
        myTurn = isMyTurn();
        GridPane newGridPane1;
        try {
            MessageUtil.sendMessage(MessageUtil.createMessage(TypeMessage.GET_BATTLE_GRID, null), outputStream);
        } catch (IOException e) {
            throw new MessageException(e);
        }
        try {
            Message<Serializable> msg = MessageUtil.readMessage(inputStream);
            if (msg.getType() == TypeMessage.SEND_BATTLE_GRID) {
                BattleGrid battleGrid = (BattleGrid) msg.getValue();
                newGridPane1 = Utilites.drawGridPainAndGameOver(battleGrid.grid, myTurn).getKey();
            } else {
                newGridPane1 = new GridPane();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        newGridPane = coreGame.draw();
        newGridPane1.setId("playingArea");

        EventHandler<MouseEvent> eventPlayingArea = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Node clickedNode = event.getPickResult().getIntersectedNode();

                if (clickedNode != newGridPane1) {
                    Integer colIndex = GridPane.getColumnIndex(clickedNode);
                    Integer rowIndex = GridPane.getRowIndex(clickedNode);

                    if (colIndex == null) colIndex = 0;
                    if (rowIndex == null) rowIndex = 0;

                    System.out.println("Client" + myTurn + " clicked cell col: " + colIndex + " and row: " + rowIndex);

                    if (!isMyStep()) {
                        System.out.println("isMyStep: Нельзя кликнуть, не твоя очередь, ошибка синхронизации");
                        return;
                    }

                    Pair<Integer, Integer> clickPoint = new Pair<>(colIndex, rowIndex);

                    Message<Serializable> messageIn = null;
                    try {
                        MessageUtil.sendMessage(MessageUtil.createMessage(TypeMessage.SEND_PUSH_POINT, clickPoint)
                                , outputStream);
                        messageIn = MessageUtil.readMessage(inputStream);
                    } catch (IOException e) {
                        throw new MessageException(e);
                    }
                    GridPane newGridPane = null;
                    Holder gameOver = null;
                    if (messageIn.getType() == TypeMessage.SEND_BATTLE_GRID) {

                        BattleGrid battleGrid = (BattleGrid) messageIn.getValue();

                        Pair<GridPane, Holder> dataIn = Utilites.drawGridPainAndGameOver(battleGrid.grid, myTurn == Holder.FIRST ? Holder.SECOND : Holder.FIRST);
                        newGridPane = dataIn.getKey();
                        gameOver = dataIn.getValue();
                    }

                    localAllStep++;

                    /*GridPane newGridPane = coreGame.draw1().getKey();

                    Holder gameOver = coreGame.draw1().getValue();*/


                    GridPane removeGridPane = new GridPane();
                    removeGridPane.setId("playingArea");

                    anchorPane.getChildren().remove(removeGridPane);

                    newGridPane.setId("playingArea");

                    newGridPane.setOnMouseClicked(this);

                    anchorPane.getChildren().add(newGridPane);


                    if (gameOver != null) {
                        Label winnerText = new Label();
                        winnerText.setAlignment(Pos.CENTER);
                        winnerText.setContentDisplay(ContentDisplay.CENTER);
                        winnerText.setLayoutX(494);
                        winnerText.setLayoutY(185);
                        winnerText.setFont(new Font(96.0));
                        winnerText.setTextFill(Color.web("#FFFFFF"));
                        winnerText.setText("Winner " + (gameOver == Holder.FIRST ? "Red" : "Blue") + "!!!");

                        anchorPane.getChildren().add(winnerText);
                        try {
                            MessageUtil.sendMessage(MessageUtil.createMessage(TypeMessage.WIN_Holder, null), outputStream);
                        } catch (IOException e) {
                            throw new MessageException(e);
                        }
                        gameOverLoop = true;
                    }


                }
            }
        };


        newGridPane1.setOnMouseClicked(eventPlayingArea);
        anchorPane.getChildren().add(newGridPane1);


        while (!gameOverLoop) {

            Long serverStep = -1L;

            try {
                MessageUtil.sendMessage(MessageUtil.createMessage(TypeMessage.PULL_ALL_STEP,null),outputStream);
            Message<Serializable> message = MessageUtil.readMessage(inputStream);
            if (message.getType() == TypeMessage.ALL_STEP){
                serverStep = (Long) message.getValue();
            }
            } catch (IOException e) {
                throw new MessageException(e);
            }


            if (serverStep != localAllStep){
                BattleGrid battleGrid1 = null;
                try {
                    MessageUtil.sendMessage(MessageUtil.createMessage(TypeMessage.GET_BATTLE_GRID,null),outputStream);
                    Message<Serializable> message =  MessageUtil.readMessage(inputStream);
                    if (message.getType() == TypeMessage.SEND_BATTLE_GRID){
                       battleGrid1 = (BattleGrid) message.getValue();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Pair<GridPane, Holder> dravka = Utilites.drawGridPainAndGameOver(battleGrid1.grid, myTurn);

                GridPane newGridPane2 = dravka.getKey();
                Holder gameOver = dravka.getValue();
                if (localAllStep< 2){
                    gameOver = null;
                }
                if (gameOver == null){
                    gameOverLoop = true;
                }

                newGridPane2.setId("playingArea");
                newGridPane2.setOnMouseClicked(eventPlayingArea);

                anchorPane.getChildren().add(newGridPane2);

            }


        }


    }


    public boolean isMyStep() {
        try {
            MessageUtil.sendMessage(MessageUtil.
                            createMessage(TypeMessage.IS_MY_STEP,
                                    null),
                    outputStream);

            Message<Serializable> msg = MessageUtil.readMessage(inputStream);

            if (msg.getType() == TypeMessage.STEP_HOLDER) {
                return msg.getValue() == myTurn;
            }
        } catch (IOException e) {
            throw new MessageException(e);
        }
        return false;
    }

    public Holder isMyTurn() {
        try {
            MessageUtil.sendMessage(MessageUtil.createMessage(TypeMessage.WHATS_MY_TURN, null),
                    outputStream);
            Message<Serializable> message = MessageUtil.readMessage(inputStream);
            if (message.getType() == TypeMessage.YOU_TURN) {
                return (Holder) message.getValue();
            } else {
                throw new ServerException();
            }
        } catch (IOException e) {
            throw new MessageException(e);
        }
    }

    public Holder getMyTurn() {
        return myTurn;
    }

    public void setMyTurn(Holder myTurn) {
        this.myTurn = myTurn;
    }
}
