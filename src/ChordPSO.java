import java.util.ArrayList;
import java.util.Random;

class ChordPSO {
    private int numOfParticles;
    private Key key;
    private ArrayList<Point3D> chords;
    private Point3D globalBest;
    private int bestFitness;

    ChordPSO(Key key, int numOfParticles) {
        this.numOfParticles = numOfParticles;
        this.key = key;
        chords = new ArrayList<>();
    }

    ArrayList<Point3D> start() {
        for(int i = 0; i < 16; i++) {
            globalBest = new Point3D(0, 0, 0);
            bestFitness = 0;
            chords.add(generate());
        }
        return chords;
    }

    private Point3D generate() {
        Random random = new Random();
        ArrayList<ChordParticle> particles = new ArrayList<>();
        for(int i = 0; i < numOfParticles; i++) {
            ChordParticle particle = new ChordParticle(key.getTonic());
            particle.setMyBest(particle.getPosition());
            particles.add(particle);
        }
        while (!isGood()) {
            calculateFitness(particles);
            findGlobalBest(particles);
            for (ChordParticle p : particles) {
                Point3D vel = p.getVelocity();
                int velX = (int) Math.round(vel.getX() + 2 * random.nextDouble() * (p.getMyBest().getX() - p.getPosition().getX())
                        + 2 * random.nextDouble() * (globalBest.getX() - p.getPosition().getX()));
                int velY = (int) Math.round(vel.getY() + 2 * random.nextDouble() * (p.getMyBest().getY() - p.getPosition().getY())
                        + 2 * random.nextDouble() * (globalBest.getY() - p.getPosition().getY()));
                int velZ = (int) Math.round(vel.getZ() + 2 * random.nextDouble() * (p.getMyBest().getZ() - p.getPosition().getZ())
                        + 2 * random.nextDouble() * (globalBest.getZ() - p.getPosition().getZ()));
                p.setVelocity(new Point3D(velX, velY, velZ));
                int posX = (velX + p.getPosition().getX()) % 15 + key.getTonic();
                int posY = (velY + p.getPosition().getY()) % 15 + key.getTonic();
                int posZ = (velZ + p.getPosition().getZ()) % 15 + key.getTonic();
                p.setPosition(new Point3D(posX, posY, posZ));
            }
        }
        return new Point3D(globalBest.getX(), globalBest.getY(), globalBest.getZ());
    }

    private void calculateFitness(ArrayList<ChordParticle> list) {
        for (ChordParticle p: list) {
            int fit = 0;
            Point3D pos = p.getPosition();
            if (pos.getX() == key.getTonic() || pos.getX() == key.getSubdominant()
                    || pos.getX() == key.getDominant())
                fit++;
            if (key.isMajor()) {
                if (pos.getY() == pos.getX() + 4)
                    fit++;
                if (pos.getZ() == pos.getY() + 3)
                    fit++;
            }
            else {
                if (pos.getY() == pos.getX() + 3)
                    fit++;
                if (pos.getZ() == pos.getY() + 4)
                    fit++;
            }
            int index = chords.size();
            if (index > 2) {
                if (pos.getX() == chords.get(index - 1).getX() &&
                        pos.getX() == chords.get(index - 2).getX() &&
                        pos.getX() == chords.get(index - 3).getX())
                    fit = 0;
            }

            p.setFitness(fit);
            if (fit > p.getBestFitness()) {
                p.setMyBest(p.getPosition());
                p.setBestFitness(fit);
            }
        }
    }

    private void findGlobalBest(ArrayList<ChordParticle> list) {
        int max = 0;
        Point3D best = new Point3D(0, 0,  0);
        for(ChordParticle p: list) {
            if (p.getFitness() > max) {
                max = p.getFitness();
                best = p.getPosition();
            }
        }
        this.globalBest = new Point3D(best.getX(), best.getY(), best.getZ());
        bestFitness = max;
    }

    private boolean isGood() {
        return bestFitness == 3;
    }
}
