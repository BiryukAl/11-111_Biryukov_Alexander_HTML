package ru.kpfu.itis.semesterworksecond.server.message;

import java.io.Serializable;

public class Message<T extends Serializable> {
    Message(T value, TypeMessage type) {
        this.value = value;
        this.type = type;
    }
    private final T value;
    private final TypeMessage type;

    public T getValue() {
        return value;
    }

    public TypeMessage getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Message{" +
                "value=" + value +
                ", type=" + type +
                '}';
    }
}