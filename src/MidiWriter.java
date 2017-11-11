import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;

import java.io.File;
import java.io.IOException;

/**
 * This function is used to write the musicString into the midi file 'song.mid'
 */
class MidiWriter {
    void createMidiFile(int tempo, String musicString) // throws IOException, InvalidMidiDataException
    {
        System.out.println("Writing in midi... Please wait");
        String midiFileNameEnd = "song.mid";
        Pattern pattern = new Pattern(musicString).setVoice(0).setInstrument("Piano").setTempo(tempo);
        try {
            MidiFileManager.savePatternToMidi(pattern, new File(midiFileNameEnd));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
