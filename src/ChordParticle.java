import java.util.Random;

/**
 * This class is used to represent the ChordParticle for PSO.
 * It is a three dimensional point in the space.
 */
class ChordParticle {
    private Point3D position; // position of a particle
    private Point3D velocity; // velocity of a particle
    private Point3D myBest; // best location of the particle
    private int bestFitness; // best fitness that particle ever had
    private int fitness; // it's current fitness

    /**
     * Generates a particle with random values within the range [min; min+12]
     * @param min is the smallest allowed value for the particle
     */
    ChordParticle(int min) {
        Random rand = new Random();
        this.position = new Point3D(rand.nextInt(13) + min,
                rand.nextInt(13) + min, rand.nextInt(13) + min);
        this.velocity = new Point3D(1,
                1, 1); // velocity is always (1, 1, 1) in the start
        bestFitness = -1;
        myBest = new Point3D(min + 1, min, min);
    }

    Point3D getPosition() {
        return position;
    }

    void setPosition(Point3D position) {
        this.position = position;
    }

    Point3D getVelocity() {
        return velocity;
    }

    void setVelocity(Point3D velocity) {
        this.velocity = velocity;
    }

    Point3D getMyBest() {
        return myBest;
    }

    void setMyBest(Point3D myBest) {
        this.myBest = myBest;
    }

    int getBestFitness() {
        return bestFitness;
    }

    void setBestFitness(int bestFitness) {
        this.bestFitness = bestFitness;
    }

    int getFitness() {
        return fitness;
    }

    void setFitness(int fitness) {
        this.fitness = fitness;
    }
}

