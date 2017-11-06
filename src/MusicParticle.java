import java.util.Random;

public class MusicParticle {
    private int position;
    private int velocity;
    private int fitness;
    private int myBest;
    private int bestFitness;

    public MusicParticle(int min) {
        Random random = new Random();
        this.position = random.nextInt(12) + min;
        this.myBest = position;
        this.velocity = 0;
        this.fitness = 0;
        this.bestFitness = 0;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getMyBest() {
        return myBest;
    }

    public void setMyBest(int myBest) {
        this.myBest = myBest;
    }

    public int getBestFitness() {
        return bestFitness;
    }

    public void setBestFitness(int bestFitness) {
        this.bestFitness = bestFitness;
    }
}
