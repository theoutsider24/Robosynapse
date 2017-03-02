package server.utility.events;

import server.utility.entities.MovableObject;
import server.utility.entities.player.Player;

public class ScannedEvent extends AbstractEvent {

    public ScannedEvent(Player actor, MovableObject subject) {
        this.actor = actor;
        this.subject = subject;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("A " + subject.getName() + "(ID:" + getId() + ") was spotted." + System.getProperty("line.separator") 
                + "Location: " + getPosition().x + ", " + getPosition().y + ". Facing: " + getRotation() + " degrees.");
        return sb.toString();
    }
}
