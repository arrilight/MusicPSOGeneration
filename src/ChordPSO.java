import java.util.ArrayList;
import java.util.Random;

/**
 * This class is used to generate 16 chords (tonic, subdominant and dominant)
 * in a given key using Particle Swarm Optimisation.
 */
class ChordPSO {
    private int numOfParticles; // number of particles
    private Key key; // the key of the melody
    private ArrayList<Point3D> chords; // generated chords
    private Point3D globalBest; // global best point in the search space
    private int bestFitness; // global best fintess in the search space

    /**
     * Default constructor.
     *
     * @param key            is the key of the melody
     * @param numOfParticles is the number of particles in PSO
     */
    ChordPSO(Key key, int numOfParticles) {
        this.numOfParticles = numOfParticles;
        this.key = key;
        chords = new ArrayList<>();
    }

    /**
     * Function to start the PSO algorithm for chords.
     *
     * @return ArrayList of 16 chords
     */
    ArrayList<Point3D> start() {
        double startTime = System.nanoTime(); // for statistics
        for (int i = 0; i < 16; i++) {
            globalBest = new Point3D(0, 0, 0);
            bestFitness = 0;
            System.out.println("Generating chord number: " + (chords.size() + 1));
            chords.add(generate());
        }
        System.out.println("Execution time for ChordPSO: " + (System.nanoTime() - startTime) / 1000000 + "ms");
        return chords;
    }

    /**
     * This function is used to generate chords using PSO.
     *
     * @return generated chord
     */
    private Point3D generate() {
        Random random = new Random();
        ArrayList<ChordParticle> particles = new ArrayList<>();
        for (int i = 0; i < numOfParticles; i++) { //initialization
            ChordParticle particle = new ChordParticle(key.getTonic());
            particle.setMyBest(particle.getPosition());
            particles.add(particle);
        }
        while (!isGood()) { //while ending criteria is not met
            calculateFitness(particles); // calculate fitness
            findGlobalBest(particles); // find global best
            if (isGood())
                break;
            for (ChordParticle p : particles) { //update the velocity and position
                Point3D vel = p.getVelocity();
                int velX = (int) Math.round(vel.getX() + 2 * random.nextDouble() * (p.getMyBest().getX() - p.getPosition().getX())
                        + 2 * random.nextDouble() * (globalBest.getX() - p.getPosition().getX()));
                int velY = (int) Math.round(vel.getY() + 2 * random.nextDouble() * (p.getMyBest().getY() - p.getPosition().getY())
                        + 2 * random.nextDouble() * (globalBest.getY() - p.getPosition().getY()));
                int velZ = (int) Math.round(vel.getZ() + 2 * random.nextDouble() * (p.getMyBest().getZ() - p.getPosition().getZ())
                        + 2 * random.nextDouble() * (globalBest.getZ() - p.getPosition().getZ()));
                p.setVelocity(new Point3D(velX, velY, velZ));
                // %15 + getTonic() is used to keep position in the range
                int posX = (velX + p.getPosition().getX()) % 15 + key.getTonic();
                int posY = (velY + p.getPosition().getY()) % 15 + key.getTonic();
                int posZ = (velZ + p.getPosition().getZ()) % 15 + key.getTonic();
                p.setPosition(new Point3D(posX, posY, posZ));
            }
        }
        return new Point3D(globalBest.getX(), globalBest.getY(), globalBest.getZ());
    }

    /**
     * This function calculates fitness for every particle.
     *
     * @param list of particles
     */
    private void calculateFitness(ArrayList<ChordParticle> list) {
        int index = chords.size();
        for (ChordParticle p : list) { // for every particle
            int fit = 0; //initial fitness is zero
            Point3D pos = p.getPosition();
            if (pos.getX() == key.getTonic() || pos.getX() == key.getDominant() ||
                    pos.getX() == key.getSubdominant()) { // if the first note is good
                fit++;
            }
            //check whether the other notes are good and in the key
            if (key.isMajor()) {
                if (pos.getY() == pos.getX() + 4)
                    fit++;
                if (pos.getZ() == pos.getY() + 3)
                    fit++;
            } else {
                if (pos.getY() == pos.getX() + 3)
                    fit++;
                if (pos.getZ() == pos.getY() + 4)
                    fit++;
            }
            //check whether two accords repeat more than twice
            if (index > 1) {
                if (pos.getX() == chords.get(index - 1).getX() &&
                        pos.getX() == chords.get(index - 2).getX())
                    fit = 0; //if they are, fitness of this particle is zero
            }
            //check whether the first and the last chords are tonic chords
            if ((index == 15 || index == 0) && pos.getX() != key.getTonic())
                fit = 0; //if they are not, fitness of this particle is zero
            // update fitness
            p.setFitness(fit);
            if (fit > p.getBestFitness()) {
                p.setMyBest(p.getPosition());
                p.setBestFitness(fit);
            }
        }
    }

    /**
     * This function is used to find the global best within the particles
     *
     * @param list of particles
     */
    private void findGlobalBest(ArrayList<ChordParticle> list) {
        int max = 0;
        Point3D best = new Point3D(0, 0, 0);
        for (ChordParticle p : list) {
            if (p.getFitness() > max) {
                max = p.getFitness();
                best = p.getPosition();
            }
        }
        this.globalBest = new Point3D(best.getX(), best.getY(), best.getZ());
        bestFitness = max;
    }

    /**
     * This function is an ending criteria for ChordPSO
     *
     * @return true if we found the chord, false otherwise
     */
    private boolean isGood() {
        return bestFitness == 3;
    }
}
