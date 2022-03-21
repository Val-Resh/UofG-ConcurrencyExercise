import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
    //list of valid commands.
    private static final String[] VALID_COMMANDS = {"start", "cancel", "running", "get", "exit", "abort"};
    private static final String[] COMMANDS_WITH_N = {"start", "cancel", "get"};

    public static void main(String[] args) {
        run();
    }

    /**
     * Main runnable method that executes the interface for the user to enter commands.
     */
    private static void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            System.out.printf("Enter a command%n");
            String input = "";
            try {
                input = reader.readLine();
            }catch (IOException e){
                e.getStackTrace();
            }
            if(validateCommandInput(input)){
                if(!executeCommand(input)) break;
            }
            else System.out.printf("Invalid command%n");
        }
    }

    /**
     * Method executes the command associated with command word.
     * The user's input command is split by space, then a switch case is used to identify the correct command to execute.
     * @param command command to execute input by user.
     * @return boolean : true for program to continue running, false to terminate.
     */
    private static boolean executeCommand(String command){
        String[] splitCommand = command.trim().split(" ");
        switch (splitCommand[0]){
            case "start":
                long startNumberCalculation = Long.parseLong(splitCommand[1]);
                Commands.start(startNumberCalculation);
                break;
            case "cancel":
                long cancelThreadWithNumber = Long.parseLong(splitCommand[1]);
                Commands.cancel(cancelThreadWithNumber);
                break;
            case "running":
                Commands.running();
                break;
            case "get":
                long numberCalculationResult = Long.parseLong(splitCommand[1]);
                Commands.get(numberCalculationResult);
                break;
            case "exit":
                return false;
            case "abort":
                Commands.abort();
                return false;
            default:
                System.out.printf("Invalid command%n");
        }
        return true;
    }

    /**
     * Validates the user input commands.
     * @param input user's input command.
     * @return true if valid, false otherwise.
     */
    private static boolean validateCommandInput(String input){
        String[] commands = input.trim().split(" ");
        int numberOfCommands = commands.length;

        if(numberOfCommands <= 2){
            boolean isFirstInputValid = false;

            for(String command : VALID_COMMANDS){
                if(command.equals(commands[0])) {
                    isFirstInputValid = true;
                    break;
                }
            }

            if(numberOfCommands == 1 && isFirstInputValid) {
                for(String command : COMMANDS_WITH_N){
                    if(command.equals(commands[0])) return false;
                }
                return true;
            }
            if(numberOfCommands == 2 && isFirstInputValid) {
                try {
                    Long.parseLong(commands[1]);
                    return true;
                } catch (NumberFormatException ignored){}
            }
        }

        return false;
    }
}
