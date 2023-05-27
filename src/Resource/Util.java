package Resource;

import java.util.Locale;
import java.util.Scanner;

public class Util {

    //Responsible for printing statements
    public static class Print{
        private static void printingLine(String out){System.out.println(out);}
        private static void printing(String out){System.out.print(out);}
        public static void invalidInput (){
            printingLine("Invalid Input");}
        public static void nodeRetrieved (){
            printingLine("Node Retrieved");}
        public static void commandExecuted(){
            printingLine("Command Executed");}

        private static void requestNumberOfNodes(){
            printing("How many nodes to be retrieved: 10*");}
        private static void updatedRequestNumberOfNodes(){
            printing("How many nodes to be retrieved: ");}
        private static void requestManualControl(){
            printing("Type in the profile number ");}
        public static void dataBaseError(){
            printingLine("Error in DataBase Process");}

        public static void graphCount(int i){
            printingLine("The graph has :" + i + " nodes");}
        public static void graphUpdatesDatabaseInit(){
            printingLine("Initializing database update");}
        public static void graphInjectionError(){
            printingLine("Error: node which not found in database Cannot be initialized with...");}
        public static void stoppingGraphConstruction(){
            printingLine("Stopping graph construction.......");}
        public static void initGraphConstruction(){
            printingLine("initializing construction");}
        public static void graphQueueRemaining(int i){
            printingLine("Queue has " + i + " Nodes");}
        public static void iterationCounter(int i){
            printingLine("");
            printingLine("--------------------------------------------------------------------------------");
            printingLine("Counting to 0: "+(i+1)+" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            printingLine("--------------------------------------------------------------------------------");
        }
        private static void commandInterface(){
            printing("Command:>> ");
        }
        public static void showCommands(){
            printingLine("--------------------------------------------------------------------------------");
            printingLine("List of Commands");
            printingLine("--------------------------------------------------------------------------------");
            printingLine("getNodes : Remote connection to get new nodes and append it to database");
            printingLine("getBasic : Remote connection to get basic info of new nodes and append to database");
            printingLine("login: automatic login in all available profiles of the chrome driver");
            printingLine("help : show the list of commands");
            printingLine("manual : Control a specific profile of the available one on the chrome driver");
            printingLine("db-clear : Clear specific table in database");
            printingLine("db-inj : Inject twitter username to nodeToBeDiscovered table");
            printingLine("or-r-counter : modify the number of nodes to be retrieved during the process");
            printingLine("g-inj : Inject twitter username queue of temporary constructed graph");
            printingLine("g-stop : stop construction of the temporary graph");
            printingLine("g-cons : start construction of the temporary graph");
            printingLine("g-counter : Print the number of nodes in the temporary graph");
            printingLine("g-update-db : update Database after the construction to create clean table");
            printingLine("a-vd : get the degree of specified vertex");
            printingLine("a-dd : get degree distribution");
            printingLine("a-shortest-path : find the shortest path between two nodes");
            printingLine("a-da : perform density analysis");
            printingLine("a-ca : perform centrality analysis");
            printingLine("a-cca : perform Connected components analysis");
            printingLine("a-cc : Get global clustering coefficient");
            printingLine("a-cd : Perform community discovery algorithm");
            printingLine("a-nv : visualize the constructed graph");
            printingLine("quit : close the system");
            printingLine("--------------------------------------------------------------------------------");
            //Todo show the list of commands
        }

        private static void databaseChoice(){
            printing("What database to clear Discovered or UnDiscovered (d/n/nn): ");
        }
        public static void getDegreeDistribution(){
            printingLine("Initializing Degree Distribution analysis: ");
        }
        private static void dataBaseUsernameInjection(){
            printing("Enter username: ");
        }
        public static void retrievalError() {
            printingLine("Error while retrieving node");
        }
        public static void connectionError() {
            printingLine("Connectiong error");
        }
        public static void appendToDatabase(int i){
            printingLine("Appending to database node:" + i);}

        public static void printErrot(Exception e){printing(e.getMessage());}

        public static void NumberOfModifiedIterations() {Print.printing("Number of Iterations to be set: ");}

        public static void graphInjectionCommand() {Print.printing("Username to be injected in the graph: ");}

        public static void Vertex() {printing("Input the specified Vertex: ");
        }

        public static void getEnding() {
            printing("Input the Finishing destination: ");
        }

        public static void getStarting() {
            printing("Input the starting destination: ");
        }

        public static void initCluster() {
            printingLine("Initializing Clustering analysis");
        }
    }

    public static class Input {
        private static String inputting(){
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        }

        public static int numberOfRequestedNodes(){
            Print.requestNumberOfNodes();
                try {
                return Integer.parseInt(inputting());
                }
                catch (Exception e){Print.invalidInput();}
            return numberOfRequestedNodes();
        }
        public static int updatedNumberOfRequestedNodes(){
            Print.updatedRequestNumberOfNodes();
            try {
                return Integer.parseInt(inputting());
            }
            catch (Exception e){Print.invalidInput();}
            return numberOfRequestedNodes();
        }

        public static String databaseClearance(){
            Print.databaseChoice();
            switch (inputting().toLowerCase(Locale.ROOT)){
                case "n" -> {return "n";}
                case "d" -> {return "d";}
                case "nn" -> {return "nn";}
                default -> {Print.invalidInput();databaseClearance();}
            }
            return "";
        }
        public static String dataBaseManualUserInjection(){
            Print.dataBaseUsernameInjection();
            String input = inputting();
            if (input==null) {
                Print.invalidInput();
                dataBaseManualUserInjection();
            }
             return input;
        }
        public static int numOfProfile(){
            Print.requestManualControl();
            try {
                return Integer.parseInt(inputting());
            }
            catch (Exception e){Print.invalidInput();}
            return numberOfRequestedNodes();
        }
        public static int numberOFModifiedIterations(){
            Print.NumberOfModifiedIterations();
            try {
                return Integer.parseInt(inputting());
            }
            catch (Exception e){Print.invalidInput();}
            return (numberOFModifiedIterations());
        }
        public static String commandInterface(){
            Print.commandInterface();
            return inputting();
        }
        public static String graphInjection(){
            Print.graphInjectionCommand();
            return inputting();
        }

        public static String getVertex() {
            Print.Vertex();
            return inputting();
        }

        public static String starting() {
            Print.getStarting();
            return inputting();
        }
        public static String ending() {
            Print.getEnding();
            return inputting();
        }
    }
}
