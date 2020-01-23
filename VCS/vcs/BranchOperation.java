//Serbanoiu Simona-Mihaela 321CD
package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public class BranchOperation extends VcsOperation {
    private static final int VCS_BAD_CMD_CODE = -1;

    /**
     * Branch operation constructor.
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public BranchOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }
    /**
     * Cream un branch nou, dar nu inainte de a verifica
     * daca exista deja. Daca da atunci intoarcem eroare.
     */
    @Override
    public int execute(Vcs vcs) {
        int exists = 0;
        //verificam daca exista deja branch.ul
        for (int i = 0; i < vcs.getBranches().size(); i++) {
            if (vcs.getBranches().get(i).getName().equals(operationArgs.get(1))) {
                exists = 1;
            }
        }
        //daca nu, il cream; altfel returnam eroare
        if (exists == 0) {
            Branch newBranch = new Branch();
            newBranch.setName(operationArgs.get(1));
            //il adaugam in lista cu toate branch-urile
            vcs.getBranches().add(newBranch);

        } else {
            ErrorCodeManager.getInstance().checkExitCode(vcs.getOutputWriter(), VCS_BAD_CMD_CODE);
        }

        return 0;
    }
}
