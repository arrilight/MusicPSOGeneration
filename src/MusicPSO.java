import java.util.ArrayList;
import java.util.Random;

class MusicPSO {
    private ArrayList<Point3D> chords;
    private ArrayList<Integer> music;
    private Key key;
    private int globalBest;
    private int bestFitness;
    private int numOfParticles;

    MusicPSO(ArrayList<Point3D> chords, Key key, int numOfParticles) {
        this.chords = chords;
        music = new ArrayList<>();
        this.key = key;
        this.numOfParticles = numOfParticles;
    }

    ArrayList<Integer> start() {
        for (int i = 0; i < 32; i++) {
            globalBest = 0;
            bestFitness = 0;
            music.add(generate());
        }
        return music;
    }

    private int generate() {
        Random random = new Random();
        ArrayList<MusicParticle> particles = new ArrayList<>();
        int min = key.getTonic() + 12;
        for (int i = 0; i < numOfParticles; i++) {
            particles.add(new MusicParticle(min));
        }
        while (!isGood()) {
            calculateFitness(particles);
            findGlobalBest(particles);
            for (MusicParticle p : particles) {
                int vel = (int) Math.round(p.getVelocity() + 2 * random.nextDouble() * (p.getMyBest() - p.getPosition())
                        + 2 * random.nextDouble() * (globalBest - p.getPosition()));
                p.setVelocity(vel);
                int pos = (p.getPosition() + vel) % 12 + min;
                p.setPosition(pos);
            }
        }
        return globalBest;
    }

    private void calculateFitness(ArrayList<MusicParticle> list) {
        for (MusicParticle p: list) {
            int fit = 0;
            int index = music.size();
            int note = p.getPosition();
            Point3D chord = chords.get(index / 2);
            if (key.isNote(note)) {
                fit++;
            }
            if (index % 2 == 0) {
                if (note == chord.getX() + 12 || note == chord.getY() + 12 ||
                        note == chord.getZ() + 12) {
                    fit++;
                }
            }
            else {
                if (note > chord.getZ())
                    fit++;
            }
            p.setFitness(fit);
            if (fit > p.getBestFitness()) {
                p.setMyBest(note);
                p.setBestFitness(fit);
            }
        }
    }

    private void findGlobalBest(ArrayList<MusicParticle> list) {
        int max = 0;
        int best = 0;
        for(MusicParticle p: list) {
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
