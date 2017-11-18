import java.util.Random;

/**
 * This class is used to represent the MusicParticle for PSO.
 */
class MusicParticle {
    private int position; // position of a particle
    private int velocity; // velocity of a particle
    private int fitness; // it's current fitness
    private int myBest; // best location of the particle
    private int bestFitness; // best fitness that particle ever had

    /**
     * Default constructor generates a random value of a particle within the range [min; min+12]
     * @param min is the minimal value for a particle
     */
    MusicParticle(int min) {
        Random random = new Random();
        this.position = random.nextInt(12) + min; //set random position within the range
        this.myBest = position;
        this.velocity = 0;
        this.fitness = 0;
        this.bestFitness = 0;
    }

    int getPosition() {
        return position;
    }

    void setPosition(int position) {
        this.position = position;
    }

    int getVelocity() {
        return velocity;
    }

    void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    int getFitness() {
        return fitness;
    }

    void setFitness(int fitness) {
        this.fitness = fitness;
    }

    int getMyBest() {
        return myBest;
    }

    void setMyBest(int myBest) {
        this.myBest = myBest;
    }

    int getBestFitness() {
        return bestFitness;
    }

    void setBestFitness(int bestFitness) {
        this.bestFitness = bestFitness;
    }
}
