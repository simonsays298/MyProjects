//Serbanoiu Simona-Mihaela 321CD
package vcs;

import utils.OperationType;

import java.util.ArrayList;

public class StatusOperation extends VcsOperation {
    /**
     * Status operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public StatusOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }
    /**
     * Printam branch-ul curent in care ne aflam
     * iar apoi toate modificarile facute in acesta.
     */
    @Override
    public int execute(Vcs vcs) {
        //afisez numele branch-ului curent
        vcs.getOutputWriter().write("On branch: " + vcs.getCurrentBranch().getName() + "\n");

        vcs.getOutputWriter().write("Staged changes:" + "\n");
        for (int i = 0; i < vcs.getStagedChanges().size(); i++) {

            if (vcs.getTypes().get(i) == OperationType.TOUCH) {

                vcs.getOutputWriter().write("\t" + "Created file "
                + vcs.getStagedChanges().get(i) + "\n");
            }

            if (vcs.getTypes().get(i) == OperationType.WRITETOFILE) {

                vcs.getOutputWriter().write("\t" + "Added \""
                + vcs.getStagedChanges().get(i) + "\" to file "
                        + vcs.getStagedChanges().get(i) + "\n");
            }
            if (vcs.getTypes().get(i) == OperationType.MAKEDIR) {

                vcs.getOutputWriter().write("\t" + "Created directory "
                + vcs.getStagedChanges().get(i) + "\n");
            }
            if (vcs.getTypes().get(i) == OperationType.CHANGEDIR) {

                vcs.getOutputWriter().write("\t" + "Changed directory "
                + vcs.getStagedChanges().get(i) + "\n");
            }
            if (vcs.getTypes().get(i) == OperationType.REMOVE) {

                vcs.getOutputWriter().write("\t" + "Removed file "
                + vcs.getStagedChanges().get(i) + "\n");
            }

        }
        return 0;
    }

}
