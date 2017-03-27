package nl.gremmee.evolve.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import nl.gremmee.evolve.Evolve;
import nl.gremmee.evolve.ID;

public class Meeple extends EvolveObject {
    private Gender gender;

    private int health;
    private Meeple father;
    private Meeple mother;
    private Random random = new Random();

    public Meeple(int aX, int aY, ID aId) {
        super(aX, aY, aId);
        // setHealth(random.nextInt(1000));
        setHealth((1000));
        this.gender = (random.nextInt(2) == 0) ? Gender.Male : Gender.Female;
        setVelX(2);
        setVelY(2);
    }

    public void fight(Meeple aOpponent) {
        int diff = this.health - aOpponent.health;

        int me = random.nextInt(Math.max(1, Math.round(this.health * 100)));
        int you = random.nextInt(Math.max(1, Math.round(aOpponent.health * 100)));

        if (me > you) {
            this.health += diff;
            aOpponent.health -= diff;
            if (aOpponent.health <= 0) {
                aOpponent.setAlive(false);
            }
        } else {
            this.health -= diff;
            aOpponent.health += diff;
            if (this.health <= 0) {
                this.setAlive(false);
            }
        }
    }

    public Meeple getFather() {
        return father;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Meeple getMother() {
        return mother;
    }

    public Meeple mate(Meeple aOpponent) {
        Meeple child = new Meeple(this.getX(), this.getY(), ID.Player);
        if (Gender.Male.equals(this.gender)) {
            child.setFather(this);
            child.setMother(aOpponent);
        } else {
            child.setFather(aOpponent);
            child.setMother(this);
        }
        child.setHealth((child.getFather().health / 2) + (child.getMother().health / 2));
        this.setHealth(this.health / 2);
        aOpponent.setHealth(aOpponent.health / 2);
        return child;
    }

    @Override
    public void render(Graphics aGraphics) {
        int red = 0;
        int blue = 0;
        if (gender.equals(Gender.Female)) {
            red = Math.max(0, Math.min(255, Math.round(this.health)));
            // System.out.println("red " + red);
        } else {
            blue = Math.max(0, Math.min(255, Math.round(this.health)));
            // System.out.println("blue " + blue);
        }
        aGraphics.setColor(new Color(red, 0, blue));
        aGraphics.fillRect(getX(), getY(), 8, 8);
        aGraphics.drawString("" + this.health, getX(), getY());
        Meeple father = getFather();
        if (father != null) {
            aGraphics.setColor(Color.BLUE);

            aGraphics.drawLine(getX(), getY(), father.getX(), father.getY());
        }
        Meeple mother = getFather();
        if (mother != null) {
            aGraphics.setColor(Color.RED);

            aGraphics.drawLine(getX(), getY(), mother.getX(), mother.getY());
        }
    }

    public void setFather(Meeple aFather) {
        this.father = aFather;
    }

    public void setMother(Meeple aMother) {
        this.mother = aMother;
    }

    @Override
    public void update() {
        if (this.isAlive()) {
            int rx = random.nextInt(3) - 1;
            int ry = random.nextInt(3) - 1;

            setX(getX() + rx);
            setY(getY() + ry);
            int x = getX();
            int y = getY();

            if ((y <= 0) || (y >= (Evolve.HEIGHT - 32))) {
                setVelY(getVelY() * -1);
            }
            if ((x <= 0) || (x >= Evolve.WIDTH)) {
                setVelX(getVelX() * -1);
            }
        }
    }

    private void setHealth(int aHealth) {
        this.health = aHealth;
    }
}
