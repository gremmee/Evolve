package nl.gremmee.evolve.objects;

import java.awt.Graphics;

import nl.gremmee.evolve.ID;

public abstract class EvolveObject {
    private boolean isAlive;

    private ID id;
    private int velX;
    private int velY;

    private int x;
    private int y;

    public EvolveObject(int aX, int aY, ID aId) {
        this.x = aX;
        this.y = aY;
        this.id = aId;
        this.isAlive = true;
    }

    public abstract void render(Graphics aGraphics);

    public abstract void update();

    public ID getID() {
        return this.id;
    }

    public int getVelX() {
        return velX;
    }

    public int getVelY() {
        return velY;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public void setAlive(boolean aAlive) {
        this.isAlive = aAlive;
    }

    public void setId(ID aId) {
        this.id = aId;
    }

    public void setVelX(int aVelX) {
        this.velX = aVelX;
    }

    public void setVelY(int aVelY) {
        this.velY = aVelY;
    }

    public void setX(int aX) {
        this.x = aX;
    }

    public void setY(int aY) {
        this.y = aY;
    }
}
