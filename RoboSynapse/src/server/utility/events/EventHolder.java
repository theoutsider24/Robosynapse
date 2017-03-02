package server.utility.events;

import server.utility.common.Constants;

public class EventHolder {
    protected AbstractEvent trackedEvent;
    protected final double turnGenerated; //may switch to time generated
    protected int turnsToLive;
    protected int actorId;
    protected int subjectId;
    
    public EventHolder(AbstractEvent tracAbstractEvent, int actorId, int turnsToLive, double turnGenerated) {
        this.trackedEvent = trackedEvent;
        this.turnGenerated = turnGenerated;
        this.actorId = actorId;
        this.subjectId = subjectId;
        this.turnsToLive = turnsToLive;
    }
    
    public double getTurnGenerated() {
        return turnGenerated;
    }
    
    public void tick() {
        turnsToLive--;
    }
    
    public void revive() {
        turnsToLive = Constants.EVENT_LIFE;
    }
    
    public int getTurnsToLive() {
        return turnsToLive;
    }
    
    public int getActorId() {
        return actorId;
    }
    
    public int getSubjectId() {
        return trackedEvent.getId();
    }
}