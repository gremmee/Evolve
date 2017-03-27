package nl.gremmee.evolve;

import java.awt.Graphics;
import java.util.LinkedList;

import nl.gremmee.evolve.objects.EvolveObject;
import nl.gremmee.evolve.objects.Meeple;

public class Handler {
    LinkedList<EvolveObject> object = new LinkedList<EvolveObject>();

    public void addObject(EvolveObject aObject) {
        this.object.add(aObject);
    }

    public void fight(Meeple aObject, Meeple aOpponent) {
        Meeple me = (Meeple) aObject;
        if (aOpponent.equals(me.getFather()) || aOpponent.equals(me.getMother()) //
                || me.equals(aOpponent.getFather()) || me.equals(aOpponent.getMother())) {
        } else {
            me.fight((Meeple) aOpponent);
        }
    }

    public EvolveObject getGameObject(EvolveObject aObject) {
        int index = this.object.indexOf(aObject);
        if (index != -1) {
            return this.object.get(index);
        }
        return null;
    }

    public void mate(Meeple aObject, Meeple aOpponent) {
        Meeple me = (Meeple) aObject;
        if (aOpponent.equals(me.getFather()) || aOpponent.equals(me.getMother()) //
                || me.equals(aOpponent.getFather()) || me.equals(aOpponent.getMother())) {
        } else {
            Meeple child = me.mate((Meeple) aOpponent);
            object.add(child);
        }
    }

    public void removeDead() {
        for (EvolveObject evolveObject : object) {
            if (!evolveObject.isAlive()) {
                object.remove(evolveObject);
                break;
            }
        }
    }

    public void removeObject(EvolveObject aObject) {
        this.object.remove(aObject);
    }

    public void render(Graphics aGraphics) {
        for (int i = 0; i < object.size(); i++) {
            EvolveObject tempObject = object.get(i);
            tempObject.render(aGraphics);
        }
    }

    public EvolveObject samePlace(EvolveObject aObject) {
        for (EvolveObject evolveObject : this.object) {
            if (!aObject.equals(evolveObject)) {
                if ((aObject.getX() == evolveObject.getX()) && (aObject.getY() == evolveObject.getY())) {
                    return evolveObject;
                }
            }
        }
        return null;
    }

    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            Meeple tempObject = (Meeple) object.get(i);
            tempObject.update();
            Meeple opponent = (Meeple) samePlace(tempObject);
            if (opponent != null) {
                if (!opponent.getGender().equals(tempObject.getGender())) {
                    System.out.println(tempObject.toString() + " mates " + opponent.toString());
                    mate(tempObject, opponent);
                } else {
                    System.out.println(tempObject.toString() + " fights " + opponent.toString());
                    fight(tempObject, opponent);
                }
            }
        }
    }
}
