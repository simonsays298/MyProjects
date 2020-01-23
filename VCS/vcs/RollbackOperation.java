//Serbanoiu Simona-Mihaela 321CD
package vcs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import utils.OperationType;

public class RollbackOperation extends VcsOperation {

    public RollbackOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }
    // Function to find the index of an element in a primitive array in Java

    /**
     * Executes the operation.
     * @param vcs the vcs
     * @return the return code
     */
    public int execute(Vcs vcs) {
        //stergem toate modificarile din lista de comenzi
        //de filesystem
        vcs.getStagedChanges().clear();
        //cautam commit-ul cu cel mai mare Id din branch-ul curent
        int[] tab = new int[vcs.getCurrentBranch().getCommits().size()];
        for (int i = 0; i < vcs.getCurrentBranch().getCommits().size(); i++) {
            tab[i] = vcs.getCurrentBranch().getCommits().get(i).getId();
        }

        int max = Arrays.stream(tab).max().getAsInt();
        //peluam indexul commit-ului cu cel mai mare ID
        int index = Arrays.stream(tab).boxed().collect(Collectors.toList()).indexOf(max);
        // actualizam snapshot-ul vcs-ului la acesta
        vcs.setActiveSnapshot(vcs.getCurrentBranch().getCommits().get(index).getSnapshot());

        return 0;

    }

}
