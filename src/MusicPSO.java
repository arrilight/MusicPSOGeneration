import java.util.ArrayList;
import java.util.Random;

/**
 * This class generates music based on the chords generated in the ChordPSO.
 */
class MusicPSO {
    private ArrayList<Point3D> chords; // chords that were generated earlier
    private ArrayList<Integer> music; // generated music
    private Key key; // key of the music
    private int globalBest; // global best point of the search
    private int bestFitness; // fitness of the global best point
    private int numOfParticles; //number of particles in PSO

    MusicPSO(ArrayList<Point3D> chords, Key key, int numOfParticles) {
        this.chords = chords;
        music = new ArrayList<>();
        this.key = key;
        this.numOfParticles = numOfParticles;
    }

    /**
     * This function is used to start the PSO algorithm for music
     *
     * @return ArrayList of notes for music
     */
    ArrayList<Integer> start() {
        double startTime = System.nanoTime(); // for statistics
        for (int i = 0; i < 32; i++) {
            globalBest = 0;
            bestFitness = 0;
            System.out.println("Generating note number: " + (music.size() + 1));
            music.add(generate());
        }
        System.out.println("Execution time for MusicPSO: " + (System.nanoTime() - startTime) / 1000000 + "ms");
        return music;
    }

    /**
     * This function is used to generate the music
     *
     * @return note in a key
     */
    private int generate() {
        Random random = new Random();
        ArrayList<MusicParticle> particles = new ArrayList<>();
        int min = key.getTonic() + 12;
        for (int i = 0; i < numOfParticles; i++) { // initialize particles
            particles.add(new MusicParticle(min));
        }
        while (!isGood()) { // while ending criteria is not met
            calculateFitness(particles); // calculate fitness
            findGlobalBest(particles); // find global best
            for (MusicParticle p : particles) { // update velocity and position for every particle
                int vel = (int) Math.round(p.getVelocity() + 2 * random.nextDouble() * (p.getMyBest() - p.getPosition())
                        + 2 * random.nextDouble() * (globalBest - p.getPosition()));
                p.setVelocity(vel);
                int pos = (p.getPosition() + vel) % 12 + min;
                p.setPosition(pos);
            }
        }
        return globalBest;
    }

    /**
     * This function is used for calculating particle fitness
     *
     * @param list of particles
     */
    private void calculateFitness(ArrayList<MusicParticle> list) {
        for (MusicParticle p : list) { // for every particle in the list
            int fit = 0;
            int index = music.size();
            int note = p.getPosition();
            boolean end = index == 31;
            Point3D chord = chords.get(index / 2); // chord that plays with the note
            if (key.isNote(note)) { // if note is in the key
                if (!end)
                    fit++; // fitness+1
                else if (note % 12 == key.getTonic() % 12)
                    fit++;
            }
            if (index % 2 == 0) { // if the note is played with the chord
                if (note == chord.getX() + 12 || note == chord.getY() + 12 ||
                        note == chord.getZ() + 12) { // check whether it is the same note
                    fit++; // and if it is, add fitness
                }
            } else {
                if (!end && note > chord.getZ()) {
                    if (index > 0 && Math.abs(note - music.get(index - 1)) < 4) // if note is not far fro the other note
                        fit++; // add to the fintess
                }

            }
            if (end) {
                note = key.getTonic() + 12;
                fit = 2;
                p.setPosition(note);
            }
            p.setFitness(fit);
            if (fit > p.getBestFitness()) {
                p.setMyBest(note);
                p.setBestFitness(fit);
            }
        }
    }

    /**
     * This function is used to find the global best within the particles
     *
     * @param list is array list of particles
     */
    private void findGlobalBest(ArrayList<MusicParticle> list) {
        int max = 0;
        int best = 0;
        for (MusicParticle p : list) {
            if (p.getFitness() > max) {
                max = p.getFitness();
                best = p.getPosition();
            }
        }
        this.globalBest = best;
        bestFitness = max;
    }

    private boolean isGood() {
        return bestFitness == 2;
    }
}
