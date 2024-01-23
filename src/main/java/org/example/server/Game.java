package org.example.server;

import lombok.extern.slf4j.Slf4j;
import org.example.base.Card;
import org.example.base.Deck;
import org.example.event.*;
import org.example.event.init.Event;
import org.example.event.init.ReadyEvent;

import java.util.*;

@Slf4j
public class Game {
    private final List<ServerClient> clientList;
    private final Deck deck;



    private final List<ScoreBoard> scoreboard = new ArrayList<ScoreBoard>();


    public Game(List<ServerClient> clientList) {
        this.clientList = clientList;
        deck = new Deck();
    }


    void startGame() {


        this.clientList.forEach(serverClient -> {
            var c1 = new CardEvent(serverClient.getId(), deck.drawCard());
            var c2 = new CardEvent(serverClient.getId(), deck.drawCard());

            this.handleEvent(serverClient.getId(), c1);
            this.handleEvent(serverClient.getId(), c2);
        });



    }


    public void handleEvent(UUID clientId, Event event) {
        log.info("Handle event from client: {}, event: {}", clientId, event);
        if (event instanceof DrawEvent) {
            ((DrawEvent) event).consume(clientList, deck.drawCard());
        } else if (event instanceof ReadyEvent) {
            boolean allReady = ((ReadyEvent) event).consume(clientList);
            if (allReady) {
                startGame();

            }
        } else if (event instanceof CardEvent) {
            //zapisze ze gracz w rece
            this.clientList.forEach(serverClient -> serverClient.sendEvent(event));
        } else if (event instanceof HitEvent) {
            for (ServerClient client : clientList) {
                if (!Objects.equals(((HitEvent) event).getId(),client.getId())) {
                    client.sendEvent(new PingEvent());
                }
            }
            Card card = deck.drawCard();
            this.clientList.forEach(serverClient -> serverClient.sendEvent(new CardEvent(((HitEvent) event).getId(), card)));
        } else if (event instanceof StandEvent) {
            ((StandEvent) event).consume(clientList);
            log.info(clientList.toString());
            if(clientList.getFirst().isStand() && !clientList.getLast().isStand()){
                clientList.getLast().sendEvent(new PingEvent());
            }
            if(!clientList.getFirst().isStand() && clientList.getLast().isStand()){
                clientList.getFirst().sendEvent(new PingEvent());
            }
            if(clientList.getFirst().isStand() && clientList.getLast().isStand()){
                this.clientList.forEach(serverClient -> serverClient.sendEvent(new EndingRequest()));
            }

        } else if (event instanceof BustEvent){
            ((BustEvent) event).consume(clientList);

        } else if (event instanceof Ending) {
            ((Ending) event).consume(scoreboard,clientList);

        }
    }
}

