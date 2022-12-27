package ru.kpfu.itis.semesterworksecond.server;

import ru.kpfu.itis.semesterworksecond.logic.model.Holder;
import ru.kpfu.itis.semesterworksecond.myExceptions.ServerException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class GameServer {

    int port;

    boolean isAliveServer = true;

    ServerSocket server;

    List<ClientHandler> clients = new ArrayList<>();

    public List<ClientHandler> getClients() {
        return clients;
    }

    Holder nextStepClient = Holder.FIRST;

    Thread thread;


    GameServer thisServer = this;

    public GameServer(int port) {
        this.port = port;
    }

    public void start() {
        thread = new Thread(th);
        thread.start();
    }

    Runnable th = new Runnable() {
        @Override
        public void run() {
            try {
                server = new ServerSocket(port);
            } catch (IOException e) {
                throw new ServerException(e);
            }

            try {
                server.setSoTimeout(1000);
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }

            Socket clientSocket = null;

            while (isAliveServer) {
                try {
                    clientSocket = server.accept();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    clientSocket.setSoTimeout(1000);
                } catch (SocketException e) {
                    throw new RuntimeException(e);
                }

                clients.add(new ClientHandler(thisServer, clientSocket, clients.size()==1?Holder.FIRST:Holder.SECOND ));

            }


        }
    };

    public void doStep() {
        System.out.println("Next step");
        nextStepClient = nextStepClient == Holder.FIRST ? Holder.SECOND : Holder.FIRST;
    }

    public void stop(){
        isAliveServer = false;
    }
}
