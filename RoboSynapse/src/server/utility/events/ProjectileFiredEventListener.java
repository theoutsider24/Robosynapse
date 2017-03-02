package server.utility.events;

import java.util.EventListener;

public interface ProjectileFiredEventListener extends EventListener {
    public void registerNewProjectile(ProjectileFiredEvent e);
}
