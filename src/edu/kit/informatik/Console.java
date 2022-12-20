package edu.kit.informatik;

/**
 * This class serves as the console for the commands we give.
 * @author Rezart Toli
 * @version 1.0
 */
public class Console {
    /**
     * quit command placeholder
     */
    public static final String QUIT = "quit";
    /**
     * list command placeholder
     */
    public static final String LIST = "list";
    /**
     * employee command placeholder
     */
    public static final String EMPLOYEE = "employee";
    /**
     * workingtime command placeholder
     */
    public static final String WORKINGTIME = "workingtime";
    /**
     * The work register.
     */
    WorkRegister register = new WorkRegister();
    private boolean hasQuit = false;
    
    
    
    /**
     * This method starts the loop which takes commands until we decide to quit the program.
     */
    public void getCommand() {
        while (!hasQuit) {
            String input = Terminal.readLine();
            String inputArray[] = input.split("\\s");
            String command = inputArray[0];
            switch(inputArray.length) {
                case 1:
                    switch(command) {
                        case QUIT:
                            hasQuit = true;
                            break;
                        default:
                            inputError();
                            break;
                    }
                    break;
                case 2:
                    switch(command) {
                        case LIST:
                            boolean isId = true;
                            int id = 0;
                            try {
                                id = Integer.parseInt(inputArray[1]);
                            } catch (NumberFormatException e) {
                                isId = false;
                            }
                            if (isId == true) {
                                register.listEmployeeWork(id);
                            } else {
                                register.listEmployee(inputArray[1]);
                            }
                            break;
                        default:
                            inputError();
                            break;
                    }
                    break;
                case 4:
                    switch(command) {
                        case WORKINGTIME:
                            register.workTime(inputArray[1], inputArray[2], inputArray[3]);
                            break;
                        default:
                            inputError();
                            break;
                    }
                    break;
                case 5:
                    switch(command) {
                        case EMPLOYEE:
                            register.addEmployee(inputArray[1], inputArray[2], inputArray[3], inputArray[4]);
                            break;
                        default:
                            inputError();
                            break;
                    }
                    break;
                case 6:
                    switch(command) {
                        case WORKINGTIME:
                            register.workTime(inputArray[1], inputArray[2], inputArray[3], 
                                    inputArray[4], inputArray[5]);
                            break;
                        default:
                            inputError();
                            break;
                    }
                    break;
                default:
                    inputError();
                    break;
            }
        }
    }
    
    /**
     * Helper method to make it easier to print error messages.
     */
    private void inputError() {
        Terminal.printError("incorrect input.");
    }
}