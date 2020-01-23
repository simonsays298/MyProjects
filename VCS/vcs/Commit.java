//Serbanoiu Simona-Mihaela 321CD
package vcs;

import filesystem.FileSystemSnapshot;
import utils.IDGenerator;

public class Commit {
    private int id;
    private FileSystemSnapshot snapshot;
    private String message = "";

    /**
     * Metoda de getter pentru snapshot-ul lui commit.
     * @return
     */
    public FileSystemSnapshot getSnapshot() {
        return snapshot;
    }

    /**
     * Metoda de setter pentru setarea snapshot-ului
     *  lui commit.
     * @param snapshot
     */
    public void setSnapshot(FileSystemSnapshot snapshot) {
        this.snapshot = snapshot;
    }

    /**
     * Metoda de getter pentru preluarea
     * mesajului lui commit.
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * Metoda de setter pentru setarea
     * mesajului lui commit.
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public Commit() {
        this.id = IDGenerator.generateCommitID();
        this.message = "First commit";
        this.snapshot = null;

    }

    public Commit(int id, FileSystemSnapshot snapshot, String message) {
        this.id = id;
        this.snapshot = snapshot;
        this.message = message;
    }

    /**
     * Metoda de setter pentru setarea unui
     * id a lui commit.
     * @param id
     */
    public void setId(int id) {
        this.id = IDGenerator.generateCommitID();
    }

    /**
     * Metoda de getter pentru preluarea
     * unui id a lui commit.
     * @return
     */
    public int getId() {
        return id;
    }

}
