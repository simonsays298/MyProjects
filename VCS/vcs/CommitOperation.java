//Serbanoiu Simona-Mihaela 321CD
package vcs;

import utils.ErrorCodeManager;
import utils.IDGenerator;
import utils.OperationType;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.stream.Collectors;



public class CommitOperation extends VcsOperation {
    public static final int VCS_BAD_CMD_CODE = -1;

    /**
     * Commit operation constructor.
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public CommitOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the operation.
     * @param vcs the vcs
     * @return the return code
     */
    public int execute(Vcs vcs) {
        //daca avem modificari inainte de a da commit
        if (!vcs.getStagedChanges().isEmpty()) {
            //eliberam lista de modificari
            vcs.getStagedChanges().clear();
            //daca avem in plus -m la commit
            if (operationArgs.get(1).equals("-m")) {
                //salvam mesajul de dupa intr-un string
                String[] string = new String[operationArgs.size() - 2];
                int k = 0;
                for (int i = 2; i < operationArgs.size(); i++) {
                    string[k] = operationArgs.get(i);
                    k++;
                }
                String st = Arrays.stream(string).collect(Collectors.joining(" "));
                //cream un nou commit prin constructorul aferent clasei
                Commit comit = new Commit(IDGenerator.generateCommitID(),
                        vcs.getActiveSnapshot().cloneFileSystem(), st.toString());
                //adaugam commit-ul la branch-ul curent
                vcs.getCurrentBranch().getCommits().add(comit);
                for (int i = 0; i < vcs.getBranches().size(); i++) {
                    //apoi il adaugam si in branch-ul curent din lista de branch-uri
                    if (vcs.getBranches().get(i).getName().
                            equals(vcs.getCurrentBranch().getName())) {
                        vcs.getBranches().get(i).getCommits().add(comit);

                    }
                }

            }

        } else {
            //altfel primim eroare pentru comanda proasta
            ErrorCodeManager.getInstance().checkExitCode(vcs.getOutputWriter(), VCS_BAD_CMD_CODE);
        }

        return 0;
    }

}
