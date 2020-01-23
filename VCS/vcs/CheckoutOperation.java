//Serbanoiu Simona-Mihaela 321CD
package vcs;

import java.util.ArrayList;

import utils.ErrorCodeManager;
import utils.OperationType;

public class CheckoutOperation extends VcsOperation {

    private static final int VCS_BAD_CMD_CODE = -1;
    private static final int VCS_BAD_PATH_CODE = -2;
    private static final int VCS_STAGED_OP_CODE = -3;

    public CheckoutOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the operation.
     * @param vcs the vcs
     * @return the return code
     */
    public int execute(Vcs vcs) {
        String formatCheckout = "-c";
        // daca avem checkout + branch
        if (!operationArgs.get(1).equals(formatCheckout)) {

            int exists = 0;
            for (int i = 0; i < vcs.getBranches().size(); i++) {
                if (vcs.getBranches().get(i).getName().equals(operationArgs.get(1))) {
                    exists = 1;
                }
            }
            //daca nu exista branch-ul dat ca input, afisam eroare
            if (exists == 0) {
                ErrorCodeManager.getInstance().checkExitCode(vcs.getOutputWriter(),
                        VCS_BAD_CMD_CODE);

            } else {
                //altfel daca schimbarile nu au fost commited, afisam eroare
                if (!vcs.getStagedChanges().isEmpty()) {
                    ErrorCodeManager.getInstance().checkExitCode(vcs.getOutputWriter(),
                            VCS_STAGED_OP_CODE);
                    return 0;
                } else {
                    //in caz contrar setam branch-ul curent ca fiind cel dat ca input
                    vcs.getCurrentBranch().setName(operationArgs.get(1));
                    return 0;
                }

            }

        } else {
            //daca avem forma checkout + -c + commitId
            int exists = 0;
            for (int i = 0; i < vcs.getBranches().size(); i++) {
                //cautam branch-ul curent in care ne aflam
                if (vcs.getBranches().get(i).getName().equals(vcs.getCurrentBranch().getName())) {
                    //Verificam daca id-ul commit-ului dat ca input exista
                    for (int j = 0; j < vcs.getBranches().get(i).getCommits().size(); j++) {
                        if (vcs.getBranches().get(i).getCommits().get(j).getId() == Integer
                                .parseInt(operationArgs.get(2))) {

                            exists = 1;
                            //daca da stergem toate commit-urile de dupa el
                            for (int k = j + 1; k < vcs.getBranches().get(i).
                                    getCommits().size(); k++) {
                                vcs.getBranches().get(i).getCommits().remove(k);
                            }
                            //actualizam snapshot-ul vcs-ului la cel al commit-ului
                            vcs.setActiveSnapshot(vcs.getBranches().get(i).getCommits().
                                    get(j).getSnapshot());

                        }

                    }
                    break;

                }
            }
            //daca nu avem deloc modificari de dupa commit
            //primim eroare ca acesta nu exista
            if (vcs.getStagedChanges().isEmpty()) {
                if (exists == 0) {
                    ErrorCodeManager.getInstance().checkExitCode(vcs.getOutputWriter(),
                            VCS_BAD_PATH_CODE);
                    return 0;
                }
            }
            //altfel daca exista modificari trebuie
            //sa mai realizam un commit
            if (!vcs.getStagedChanges().isEmpty()) {

                ErrorCodeManager.getInstance().checkExitCode(vcs.getOutputWriter(),
                        VCS_STAGED_OP_CODE);
                return 0;
            }
        }

        return 0;

    }
}
