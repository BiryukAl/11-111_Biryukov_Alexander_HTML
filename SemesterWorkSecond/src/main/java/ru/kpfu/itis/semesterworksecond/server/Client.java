package ru.kpfu.itis.semesterworksecond.server;

import ru.kpfu.itis.semesterworksecond.logic.CoreGame;
import ru.kpfu.itis.semesterworksecond.myExceptions.MessageException;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Socket {

//    InputStream inputStream;

//    OutputStream outputStream;

    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;

    CoreGame coreGame;


    public Client(String host, int port) throws IOException {
        super(host, port);
        this.inputStream = (ObjectInputStream) getInputStream();
        this.outputStream = (ObjectOutputStream) getOutputStream();
        setSoTimeout(1000);
    }

    public boolean checkConnect() throws MessageException {
        try {
            outputStream.writeObject(new DataMessage(1));
            outputStream.flush();
            if (((DataMessage)inputStream.readObject()).getHead() == 1  ){
                return true;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new MessageException(e);
        }
        return false;
        /*outputStream.write(1);
        outputStream.flush();
        if (inputStream.read() == 1)
            return true;
        return false;*/
    }


    public boolean isMyStep() {
        return false;
    }

}
