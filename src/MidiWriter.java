import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;

import java.io.File;
import java.io.IOException;


class MidiWriter {
    public void createMidiFile(int tempo, String musicString) // throws IOException, InvalidMidiDataException
    {
        System.out.println("Writing in midi... Please wait");
        String midiFileNameEnd = "song.mid";
        Pattern pattern = new Pattern(musicString).setVoice(0).setInstrument("Piano").setTempo(tempo);
        try {
            String musicPath = "/Users/georgydzesov/";
            MidiFileManager.savePatternToMidi(pattern, new File(musicPath + midiFileNameEnd));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
