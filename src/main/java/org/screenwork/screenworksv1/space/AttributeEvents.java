package org.screenwork.screenworksv1.space;

import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerStartSprintingEvent;

public class AttributeEvents {

    public AttributeEvents() {
        Attributes attributes = new Attributes();


        GlobalEventHandler globalEventHandler = new GlobalEventHandler();

        globalEventHandler.addListener(PlayerStartSprintingEvent.class, event -> {
            attributes.loadAttributes(event.getPlayer());
            event.getPlayer().setAdditionalHearts(attributes.getStrength());
            System.out.println("Player " +  event.getPlayer().getName() + " has " + attributes.getStrength() + " additional hearts!");
        });
    }

}
