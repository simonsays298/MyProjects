//Serbanoiu Simona-Mihaela 321CD
package vcs;

import java.util.ArrayList;

import utils.OperationType;

public class LogOperation extends VcsOperation {

    public LogOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the operation.
     * @param vcs the vcs
     * @return the return code
     */
    public int execute(Vcs vcs) {
        for (int i = 0; i < vcs.getBranches().size(); i++) {
            //verificam daca branch-ul are vreun commit
            if (vcs.getBranches().get(i).hasCommits()) {
                //afisal Id-ul simesajul commit-ului
                for (int j = 0; j < vcs.getBranches().get(i).getCommits().size(); j++) {
                    vcs.getOutputWriter().write("Commit id: " + vcs.getBranches().
                            get(i).getCommits().get(j).getId() + "\n");
                    vcs.getOutputWriter().write("Message: " + vcs.getBranches().
                            get(i).getCommits().get(j).getMessage().toString() + "\n");
                    if (j != vcs.getBranches().get(i).getCommits().size() - 1) {
                        vcs.getOutputWriter().write("\n");
                    }
                }

            }
        }
        return 0;

    }

}
