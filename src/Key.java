import java.util.ArrayList;

class Key {

    private ArrayList<Integer> notes; // from 48 to 96
    private boolean scale; //false for major, true for minor
    private int tonic;

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
        int high = tonic + 12 * (i - 1);
        System.out.println("Tonic is: " + this.tonic);
        System.out.println("High is: " + high);
        System.out.println("Scale is: " + scale);
        notes.add(this.tonic);
        findKeyNotes(high);
    }

    private void findKeyNotes(int high) {
        int semitone1, semitone2;
        if (!this.scale) {
            semitone1 = 2;
            semitone2 = 6;
        }
        else {
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

    boolean isNote(int note) {
        for (int v: notes) {
            if (v == note)
                return true;
        }
        return false;
    }
}
