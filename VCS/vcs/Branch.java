//Serbanoiu Simona-Mihaela 321CD
package vcs;

import java.util.ArrayList;
import java.util.List;

public class Branch {
    private List<Commit> commits;
    private String name;

    public Branch() {
        this.commits = new ArrayList<Commit>();
    }

    /**
     * Urmatoarea metoda seteaza lista de commits pentru fiecare branch in parte.
     * @param commits
     */
    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

    /**
     * Urmatorul setter seteaza numele fiecarui branch in parte.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Preia lista de commits al branch-ului dorit.
     * @return
     */
    public List<Commit> getCommits() {
        return commits;
    }

    /**
     * Preia numele branch-ului dorit.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Urmatoarea metoda verifica daca branch-ul nostru i s-a dat commit la un
     * moment dat.
     * @param commits
     * @return
     */
    public boolean hasCommits() {
        if (this.commits.isEmpty()) {
            return false;
        }
        return true;
    }

}
