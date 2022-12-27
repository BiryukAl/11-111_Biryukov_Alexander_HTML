package ru.kpfu.itis.semesterworksecond.server;

import ru.kpfu.itis.semesterworksecond.logic.model.Holder;
import ru.kpfu.itis.semesterworksecond.myExceptions.ClientException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler {

    GameServer server;

    Socket clientSocket;

    Holder priorityClient;

    Thread thread;

    int typeMessageClientServer = -1;


    public ClientHandler(GameServer server, Socket clientSocket, Holder priorityClient) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.priorityClient = priorityClient;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                InputStream inputStream = null;
                OutputStream outputStream = null;
                typeMessageClientServer = -1;


                while (server.isAliveServer) {
                    try {
                        inputStream = clientSocket.getInputStream();
                        outputStream = clientSocket.getOutputStream();
                    } catch (IOException e) {
                        throw new ClientException(e);
                    }

                    while (server.isAliveServer) {
                        // TODO: 27.12.2022 Получения класса сообщения
                        try {
                            typeMessageClientServer = inputStream.read();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }


                    switch (typeMessageClientServer) {
                        case 1:{

                        }
                        case 2:{

                        }
                        default:{
                            System.out.println("Type message server -> " + priorityClient + ": " +typeMessageClientServer);
                        }
                    }


                }


            }
        });

        thread.start();
    }


}
