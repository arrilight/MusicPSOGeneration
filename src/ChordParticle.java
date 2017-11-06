import java.util.Random;

public class ChordParticle implements Comparable<ChordParticle> {
    private Point3D position;
    private Point3D velocity;
    private Point3D myBest;
    private int bestFitness;
    private int fitness;

    ChordParticle(int min) {
        Random rand = new Random();
        this.position = new Point3D(rand.nextInt(13) + min,
                rand.nextInt(13) + min, rand.nextInt(13) + min);
        this.velocity = new Point3D(1,
                1, 1);
        bestFitness = -1;
        myBest = new Point3D(min + 1, min, min);
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public Point3D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point3D velocity) {
        this.velocity = velocity;
    }

    public Point3D getMyBest() {
        return myBest;
    }

    public void setMyBest(Point3D myBest) {
        this.myBest = myBest;
    }

    public int getBestFitness() {
        return bestFitness;
    }

    public void setBestFitness(int bestFitness) {
        this.bestFitness = bestFitness;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(ChordParticle o) {
        return Integer.compare(o.fitness, this.fitness);
    }
}

