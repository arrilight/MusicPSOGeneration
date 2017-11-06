import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random rand = new Random();
        String scale;
        if (rand.nextInt(2) == 0)
            scale = "minor";
        else
            scale = "major";
        Key key = new Key(rand.nextInt(13) + 60, scale);
        StringBuilder sb = new StringBuilder();
        ChordPSO gen = new ChordPSO(key, 50);
        ArrayList<Point3D> chords = gen.start();
        MusicPSO mpso = new MusicPSO(chords, key, 50);
        ArrayList<Integer> music = mpso.start();
        for (int i = 0; i < 32; i++) {
            if (i % 2 == 0) {
                int m = music.get(i);
                Point3D c = chords.get(i / 2);
                sb.append(c.getX()).append("h+").append(c.getY()).append("h+").append(c.getZ()).append("h+")
                        .append(m).append("q ");
            }
            else {
                sb.append(music.get(i)).append("q ");
            }
        }
        MidiWriter writer = new MidiWriter();
        writer.createMidiFile(1, 120, sb.toString());

    }
}