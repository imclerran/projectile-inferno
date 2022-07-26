package com.droptableteams.game.systems;

import com.badlogic.gdx.Gdx;
import com.droptableteams.game.LibECS.ComponentManager;
import com.droptableteams.game.LibECS.interfaces.AbstractSystem;
import com.droptableteams.game.components.DestinationMovementComponent;
import com.droptableteams.game.components.LocationComponent;
import com.droptableteams.game.components.VelocityComponent;
import com.droptableteams.game.util.Utils;

import java.util.HashSet;

public class DestinationMovementSystem extends AbstractSystem {

    public DestinationMovementSystem(int id) {
        _idSet = new HashSet<Integer>();
        _idSet.add(id);
        _type = "DestinationMovementSystem";
        
    }

    @Override
    public void update(int id) {
        ComponentManager cm = ComponentManager.getInstance();
        DestinationMovementComponent dmc = (DestinationMovementComponent)cm.getComponent(id, "DestinationMovementComponent");
        LocationComponent lc = (LocationComponent)cm.getComponent(id, "LocationComponent");
        VelocityComponent vc = (VelocityComponent)cm.getComponent(id, "VelocityComponent");

        travelTowardsDestination(dmc,lc,vc);
    }

    private void travelTowardsDestination(DestinationMovementComponent dmc, LocationComponent lc, VelocityComponent vc) {
        float x = lc.getX();
        float y = lc.getY();
        float destX = dmc.getNextX();
        float destY = dmc.getNextY();
        float xLen = destX-x;
        float yLen = destY-y;

        if(0 == xLen & 0 == yLen) { // are we there yet???
            handleAlreadyHere(dmc, lc, vc);
        }
        else { // else: not there yet...
            float speed = vc.getModifiedSpeed();
            float distance = (float)Math.sqrt(xLen*xLen + yLen*yLen);

            float deltaTime = Gdx.graphics.getDeltaTime();
            if(speed*deltaTime == distance) { // if would travel extactly to destination...
                goToDestination(lc,dmc);
            }
            else if(distance < speed*deltaTime) { // if would travel past...
                handleWouldTravelPast(dmc, lc, vc);
            }
            else { // else: no over-travel
                float angle = atan(xLen, yLen);
                float dx = (float)Math.cos(angle)*speed*deltaTime;
                float dy = (float)Math.sin(angle)*speed*deltaTime;
                lc.setX(x+dx);
                lc.setY(y+dy);
            }
        }
    }

    private void handleWouldTravelPast(DestinationMovementComponent dmc, LocationComponent lc, VelocityComponent vc) {
        if(0 < dmc.getStayFor()) { // if must stay at the next dest, then go destination
            goToDestination(lc, dmc);
        }
        else { // else: don't need to stay at location
            boolean hasNext = dmc.incrementNextDest();
            if(!hasNext) { // if there is no destination after the next, go to next.
                goToDestination(lc, dmc);
            }
            else { // there is another destination, start travel towards that instead
                travelTowardsDestination(dmc, lc, vc);
            }
        }
    }

    private void handleAlreadyHere(DestinationMovementComponent dmc, LocationComponent lc, VelocityComponent vc) {
        long hereFor = dmc.getHereFor(Utils.nanosToMillis(System.nanoTime()));
        long stayFor = dmc.getStayFor();

        if(hereFor >= stayFor) {
            boolean hasNext = dmc.incrementNextDest();
            if(hasNext) {
                travelTowardsDestination(dmc, lc, vc);
            }
        }
    }

    private void goToDestination(LocationComponent lc, DestinationMovementComponent dmc) {
        lc.setX(dmc.getNextX());
        lc.setY(dmc.getNextY());
        dmc.setHereSinceMillis(Utils.nanosToMillis(System.nanoTime()));
    }

    private float atan(float x, float y) {
        return -1f*(float)(Math.atan2(x, y) - Math.PI/2);
    }
}
