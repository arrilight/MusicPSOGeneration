import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;

import java.io.File;
import java.io.IOException;


class MidiWriter {
    public void createMidiFile(int recordNo, int tempo, String musicString) // throws IOException, InvalidMidiDataException
    {
        String midiFileNameEnd = "file.mid";
        Pattern pattern = new Pattern(musicString).setVoice(0).setInstrument("Piano").setTempo(tempo);
        try {
            String musicPath = "/Users/georgydzesov/";
            MidiFileManager.savePatternToMidi(pattern, new File(musicPath + Integer.toString(recordNo) + midiFileNameEnd));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
