import java.util.ArrayList;
import java.util.Random;

/**
 * Main class of the program
 */
public class Main {

    public static void main(String[] args) {
        Random rand = new Random();
        String scale;
        if (rand.nextInt(2) == 0) //randomly generate the scale
            scale = "minor";
        else
            scale = "major";
        Key key = new Key(rand.nextInt(13) + 60, scale); //randomly generate the key
        StringBuilder sb = new StringBuilder();
        ChordPSO gen = new ChordPSO(key, 50); //start chordPSO
        ArrayList<Point3D> chords = gen.start();
        MusicPSO mpso = new MusicPSO(chords, key, 50); //start musicPSO
        ArrayList<Integer> music = mpso.start();
        for (int i = 0; i < 32; i++) {
            if (i % 2 == 0) {
                int m = music.get(i);
                Point3D c = chords.get(i / 2);
                sb.append(c.getX()).append("qa45+").append(c.getY()).append("qa45+").append(c.getZ()).append("qa45+")
                        .append(m).append("ia55 ");
            } else {
                sb.append(music.get(i)).append("ia55 ");
            }
        }
        MidiWriter writer = new MidiWriter();
        writer.createMidiFile(100, sb.toString());
        System.out.println("Midi was created!");
    }
}
