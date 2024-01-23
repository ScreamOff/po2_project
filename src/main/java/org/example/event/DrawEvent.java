package org.example.event;

import org.example.base.Card;
import org.example.event.init.Event;
import org.example.server.ServerClient;

import java.util.List;

public class DrawEvent implements Event {

    public void consume(List<ServerClient> clientList, Card card) {
        clientList.forEach(client -> client.sendEvent(new CardEvent(client.getId(), card)));
    }
}