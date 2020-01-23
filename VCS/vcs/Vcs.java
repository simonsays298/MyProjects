//Serbanoiu Simona-Mihaela 321CD
package vcs;

import filesystem.FileSystemOperation;
import filesystem.FileSystemSnapshot;
import utils.OperationType;
import utils.OutputWriter;
import utils.Visitor;

import java.util.ArrayList;
import java.util.List;

public final class Vcs implements Visitor {
    private final OutputWriter outputWriter;

    public OutputWriter getOutputWriter() {
        return outputWriter;
    }

    private FileSystemSnapshot activeSnapshot;

    public void setActiveSnapshot(FileSystemSnapshot activeSnapshot) {
        this.activeSnapshot = activeSnapshot;
    }

    public FileSystemSnapshot getActiveSnapshot() {
        return activeSnapshot;
    }
    //lista de string-uri pentru comenzile de filesystem
    private List<String> stagedChanges;
    //un camp cu branch-ul curent
    private Branch currentBranch;
    //toate branch-urile
    private ArrayList<Branch> branches;
    //o lista de tipul OperationType cu tipurile
    //comenzilor din filesystem
    private List<OperationType> types;

    /**
     * Vcs constructor.
     * @param outputWriter the output writer
     */
    public Vcs(OutputWriter outputWriter) {
        this.outputWriter = outputWriter;
    }

    /**
     * Does initialisations.
     */
    public void init() {
        this.activeSnapshot = new FileSystemSnapshot(outputWriter);

        // TODO other initialisations
        //initializam branch-ul curent ca fiind master
        setCurrentBranch(new Branch());
        getCurrentBranch().setName("master");
        //initializam lista de comenzi
        this.setStagedChanges(new ArrayList<String>());
        //initializam lista de branch-uri
        this.setBranches(new ArrayList<Branch>());
        //initializam primul commit
        Commit comit = new Commit();
        comit.setSnapshot(this.activeSnapshot.cloneFileSystem());
        Branch masterBranch = new Branch();
        masterBranch.setName("master");
        //punem in lista de branch-uri pe cel master
        //ii adaugam si primul commit
        getBranches().add(masterBranch);
        getBranches().get(0).getCommits().add(comit);
        //initializam si lista de tipuri de comezi
        this.setTypes(new ArrayList<>());
    }

    /**
     * Visits a file system operation.
     * @param fileSystemOperation the file system operation
     * @return the return code
     */
    public int visit(FileSystemOperation fileSystemOperation) {
        return fileSystemOperation.execute(this.activeSnapshot);
    }

    /**
     * Visits a vcs operation.
     * @param vcsOperation the vcs operation
     * @return return code
     */
    @Override
    public int visit(VcsOperation vcsOperation) {
        // TODO
        return vcsOperation.execute(this);
        // return 0;
    }

    public ArrayList<Branch> getBranches() {
        return branches;
    }

    public void setBranches(ArrayList<Branch> branches) {
        this.branches = branches;
    }

    public Branch getCurrentBranch() {
        return currentBranch;
    }

    public void setCurrentBranch(Branch currentBranch) {
        this.currentBranch = currentBranch;
    }

    public List<String> getStagedChanges() {
        return stagedChanges;
    }

    public void setStagedChanges(ArrayList<String> stagedChanges) {
        this.stagedChanges = stagedChanges;
    }

    public List<OperationType> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<OperationType> types) {
        this.types = types;
    }

    // TODO methods through which vcs operations interact with this

}
