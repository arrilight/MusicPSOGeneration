import java.util.ArrayList;

/**
 * This class represents the notion of the musical key.
 * It generates the key based on the tonic and the scale and
 * stores all the possible allowed values.
 */
class Key {

    private ArrayList<Integer> notes; // from 48 to 96
    private boolean scale; // false for major, true for minor
    private int tonic;

    /**
     * Default constructor.
     *
     * @param tonic is the first note of the key.
     * @param scale major or minor
     */
    Key(int tonic, String scale) {
        scale = scale.toLowerCase();
        switch (scale) {
            case "major":
                this.scale = false;
                break;
            case "minor":
                this.scale = true;
                break;
            default:
                throw new IllegalArgumentException("Only 'major' or 'minor' for scale is allowed");
        }
        this.notes = new ArrayList<>();
        findFirstTonic(tonic);
    }

    /**
     * This function is used to find the first tonic within the allowed range to
     * build the key values out of it.
     *
     * @param tonic –– input note (any values between 48 and 96 included)
     */
    private void findFirstTonic(int tonic) {
        if (tonic < 48 || tonic > 96)
            throw new IllegalArgumentException("Only values between 48 and 96 for tonic are allowed");
        int i = 1;
        while (tonic - 12 * i >= 48) {
            i++;
        }
        this.tonic = tonic - 12 * (i - 1);
        i = 1;
        while (tonic + 12 * i <= 96) {
            i++;
        }
        int high = tonic + 12 * (i - 1); // the biggest possible value for a key
        System.out.println("Tonic is: " + this.tonic);
        System.out.println("High is: " + high);
        System.out.println("Scale is: " + scale);
        notes.add(this.tonic); // add tonic to the notes
        findKeyNotes(high); // and find other notes
    }

    /**
     * This function uses tonic and high to generate all the
     * possible values for a key.
     *
     * @param high -- the biggest allowed value (tonic + 12n)
     */
    private void findKeyNotes(int high) {
        int semitone1, semitone2;
        if (!this.scale) { // if major
            semitone1 = 2;
            semitone2 = 6;
        } else {
            semitone1 = 1;
            semitone2 = 4;
        }
        int note = tonic;
        boolean end = false;
        while (!end) {
            for (int i = 0; i < 7; i++) {
                if (i == semitone1 || i == semitone2) {
                    note++;
                    if (note <= high)
                        notes.add(note);
                    else
                        end = true;
                } else {
                    note += 2;
                    if (note <= high)
                        notes.add(note);
                    else
                        end = true;
                }
            }
        }
    }

    ArrayList<Integer> getNotes() {
        return notes;
    }

    boolean isMajor() {
        return !scale;
    }

    boolean isMinor() {
        return scale;
    }

    int getTonic() {
        return tonic;
    }

    int getFirst() {
        return getTonic();
    }

    int getLast() {
        return notes.get(notes.size() - 1);
    }

    int getSubdominant() {
        return notes.get(3);
    }

    int getDominant() {
        return notes.get(4);
    }

    /**
     * This function checks whether the note is in the key or not
     *
     * @param note that needs to be checked
     * @return true if it is in the key, otherwise false
     */
    boolean isNote(int note) {
        for (int v : notes) {
            if (v == note)
                return true;
        }
        return false;
    }
}
