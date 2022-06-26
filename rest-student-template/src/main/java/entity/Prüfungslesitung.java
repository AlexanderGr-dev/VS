package entity;

public class Prüfungslesitung {

    private int id;
    private String pruefungId;
    private int matrikelNr;
    private int versuch;
    private double note;

    public Prüfungslesitung() {}

    public Prüfungslesitung(int id, String pruefungId, int matrikelNr, int versuch, double note) {
        this.id = id;
        this.pruefungId = pruefungId;
        this.matrikelNr = matrikelNr;
        this.versuch = versuch;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPruefungId() {
        return pruefungId;
    }

    public void setPruefungId(String pruefungId) {
        this.pruefungId = pruefungId;
    }

    public int getMatrikelNr() {
        return matrikelNr;
    }

    public void setMatrikelNr(int matrikelNr) {
        this.matrikelNr = matrikelNr;
    }

    public int getVersuch() {
        return versuch;
    }

    public void setVersuch(int versuch) {
        this.versuch = versuch;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }
}
