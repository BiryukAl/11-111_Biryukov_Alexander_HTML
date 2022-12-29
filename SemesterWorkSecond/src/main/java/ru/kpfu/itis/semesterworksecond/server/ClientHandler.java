package ru.kpfu.itis.semesterworksecond.server;

import javafx.util.Pair;
import ru.kpfu.itis.semesterworksecond.logic.BattleGrid;
import ru.kpfu.itis.semesterworksecond.logic.model.Holder;
import ru.kpfu.itis.semesterworksecond.myExceptions.ClientException;
import ru.kpfu.itis.semesterworksecond.myExceptions.MessageException;
import ru.kpfu.itis.semesterworksecond.server.message.Message;
import ru.kpfu.itis.semesterworksecond.server.message.MessageUtil;
import ru.kpfu.itis.semesterworksecond.server.message.TypeMessage;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    GameServer server;
    Socket clientSocket;
    Holder priorityClient;
    Thread thread;
    TypeMessage typeMessage = null;


    public ClientHandler(GameServer server, Socket clientSocket, Holder priorityClient) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.priorityClient = priorityClient;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                InputStream inputStream = null;
                OutputStream outputStream = null;


                typeMessage = null;
                Message<Serializable> message = null;


                while (server.isAliveServer) {
                    try {
                        inputStream = clientSocket.getInputStream();
                        outputStream = clientSocket.getOutputStream();

                    } catch (IOException e) {
                        throw new ClientException(e);
                    }

                    while (server.isAliveServer) {
                        try {
                            message = MessageUtil.readMessage(inputStream);
                            typeMessage = message.getType();
                            break;
                        } catch (IOException e) {
                            throw new MessageException(e);
                        }
                    }

                    switch (typeMessage) {
                        case CHECK_CONNECT: {


                        }
                        case SEND_PUSH_POINT: {
                            Pair<Integer, Integer> pointIn = (Pair<Integer, Integer>) message.getValue();
                            server.stepForCoreGame(pointIn.getKey(), pointIn.getValue(), priorityClient);
                            server.doStep();
//                            message.getValue()
                            server.doStep();

                            try {
                                MessageUtil.sendMessage(MessageUtil.createMessage(TypeMessage.SEND_BATTLE_GRID, server.getCoreGame().getBattleGrid()),outputStream);
                            } catch (IOException e) {
                                throw new MessageException(e);
                            }
                        }
                        case PULL_OPPONENT_STEP:{
                            try {
                                MessageUtil.sendMessage(MessageUtil.createMessage(TypeMessage.SEND_BATTLE_GRID, server.getCoreGame().getBattleGrid()),outputStream);
                            } catch (IOException e) {
                                throw new MessageException(e);
                            }
                        }
                        case BREAK_GAME: {
                            server.stop();
                            break;
                        }
                        case IS_MY_STEP: {
                            try {
                                MessageUtil.sendMessage(
                                        MessageUtil.createMessage(TypeMessage.STEP_HOLDER, server.nextStepClient)
                                        , outputStream);
                            } catch (IOException e) {
                                throw new MessageException(e);
                            }

                        }
                        case WHATS_MY_TURN: {
                            try {
                                MessageUtil.sendMessage(MessageUtil.createMessage(TypeMessage.YOU_TURN, priorityClient), outputStream);
                            } catch (IOException e) {
                                throw new MessageException(e);
                            }
                        }

                        case PULL_ALL_STEP:{
                            try {
                                MessageUtil.sendMessage(MessageUtil.createMessage( TypeMessage.ALL_STEP, server.getCoreGame().getCountAllStep()),outputStream);
                            } catch (IOException e) {
                                throw new MessageException(e);
                            }
                        }

                        case GET_BATTLE_GRID: {
                            try {
                                MessageUtil.sendMessage( MessageUtil.createMessage(TypeMessage.SEND_BATTLE_GRID, server.getCoreGame().getBattleGrid()),outputStream);
                            } catch (IOException e) {
                                throw new MessageException(e);
                            }
                        }


                        default: {
                            System.out.println("Type message server -> " + priorityClient + ": " + typeMessage);
                        }
                    }
                }
            }
        });

        thread.start();
    }

    public Holder getPriorityClient() {
        return priorityClient;
    }





    public ClientHandler getOpponent() {
        /*for (ClientHandler c :
                server.getClients()) {
            if (c.getPriorityClient() != priorityClient) {
                return c;
            }
        }
        return null;*/

        return server.clients1.get(priorityClient == Holder.FIRST ? Holder.SECOND : Holder.FIRST);
    }


}
