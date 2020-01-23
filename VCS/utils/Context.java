package utils;

import vcs.BranchOperation;
import vcs.CheckoutOperation;
import vcs.CommitOperation;
import vcs.LogOperation;
import vcs.RollbackOperation;
import vcs.StatusOperation;
import vcs.Vcs;

public final class Context {
    private Vcs vcs;
    private static Context instance = null;
    private InputReader inputReader;
    private OutputWriter outputWriter;

    /**
     * Context constructor.
     */
    private Context() {
    }

    /**
     * Gets the instance.
     * @return the instance
     */
    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }

        return instance;
    }

    /**
     * Initialise the vcs.
     *
     * @param input  the input file
     * @param output the output file
     */
    public void init(String input, String output) {
        inputReader = new InputReader(input);
        outputWriter = new OutputWriter(output);
        vcs = new Vcs(outputWriter);
    }

    /**
     * Runs the context.
     */
    public void run() {
        String operationString = "";
        AbstractOperation operation;
        OperationParser parser = new OperationParser();
        int exitCode;
        boolean wasError;

        this.vcs.init();

        while (true) {
            operationString = this.inputReader.readLine();
            if (operationString == null || operationString.isEmpty()) {
                continue;
            }
            if (operationString.equals("exit")) {
                return;
            }

            operation = parser.parseOperation(operationString);

            exitCode = operation.accept(vcs);
            wasError = ErrorCodeManager.getInstance().checkExitCode(outputWriter, exitCode);


            if (!wasError && this.isTrackable(operation)) {
                // TODO If the operation is trackable, vcs should track
                /*
                 * Am verificat tipul fiecarei comenzi si am executat
                 * unde este nevoie comenzile de vcs.
                 */
                switch (operation.type) {
                case TOUCH:

                    this.vcs.getStagedChanges().add(operation.operationArgs.get(1));
                    this.vcs.getTypes().add(operation.type);

                    break;
                case WRITETOFILE:
                    this.vcs.getStagedChanges().add(operation.operationArgs.get(1));
                    this.vcs.getTypes().add(operation.type);
                    break;
                case STATUS:
                    StatusOperation status = new StatusOperation(operation.type,
                            operation.operationArgs);
                    status.execute(this.vcs);
                    break;
                case BRANCH:
                    BranchOperation newBranch = new BranchOperation(operation.type,
                            operation.operationArgs);
                    newBranch.execute(this.vcs);
                    break;
                case CHECKOUT:
                    CheckoutOperation newCheck = new CheckoutOperation(operation.type,
                            operation.operationArgs);
                    newCheck.execute(this.vcs);
                    break;
                case CHANGEDIR:
                    this.vcs.getStagedChanges().add(operation.operationArgs.get(0));
                    this.vcs.getTypes().add(operation.type);
                    break;
                case MAKEDIR:
                    this.vcs.getStagedChanges().add(operation.operationArgs.get(0));
                    this.vcs.getTypes().add(operation.type);
                    break;
                case REMOVE:
                    this.vcs.getStagedChanges().add(operation.operationArgs.get(0));
                    this.vcs.getTypes().add(operation.type);
                    break;
                case COMMIT:
                    CommitOperation newCommit = new CommitOperation(operation.type,
                            operation.operationArgs);
                    newCommit.execute(this.vcs);
                    break;
                case LOG:
                    LogOperation newLog = new LogOperation(operation.type, operation.operationArgs);
                    newLog.execute(this.vcs);
                    break;
                case ROLLBACK:
                    RollbackOperation newRoll = new RollbackOperation(operation.type,
                            operation.operationArgs);
                    newRoll.execute(this.vcs);
                    break;
                default:
                    break;

                }

            }

        }

    }

    /**
     * Specifies if an operation is vcs trackable or not. You can use it when you
     * implement rollback/checkout -c functionalities.
     * @param abstractOperation the operation
     * @return whether it's trackable or not
     */
    private boolean isTrackable(AbstractOperation abstractOperation) {
        boolean result;

        switch (abstractOperation.type) {
        case CHANGEDIR:
        case MAKEDIR:
        case REMOVE:
        case TOUCH:
        case WRITETOFILE:
            result = true;
            break;
        default:
            result = false;
        }

        return result;
    }
}
