package ru.kpfu.itis.semesterworksecond.logic;


import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class GameLoop {

    CoreGame coreGame;

    public GameLoop() {
        this.coreGame = new CoreGame();
    }


    EventHandler OnMousePressedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {



        }
    };

}
