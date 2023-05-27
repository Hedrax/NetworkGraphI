import Resource.Util;

public class Main {
    public static void main(String[] args) {
        String input;
        while (true) {
            input = Util.Input.commandInterface();
            Command command;
            switch (input) {
                case "getNodes" -> command = new UpdatedRetreiveNodeCommand();
                case "getBasic" -> command = new GetBasicNodesCommand();
                case "help" -> command = new HelpCommand();
                case "login" -> command = new LoginCommand();
                case "manual" -> command = new ManualRideCommand();
                case "db-clear"-> command = new DatabaseClearanceCommand();
                case "db-inj"-> command = new DatabaseAddManualUsersCommand();
                case "or-r-counter"-> command = new OverRideCounterCommand();
                case "g-inj"-> command = new GraphInjectionCommand();
                case "g-stop"-> command = new GraphStopCommand();
                case "g-cons"-> command = new GraphConstructionCommand();
                case "g-counter"-> command = new GraphCountPrintCommand();
                case "g-update-db"-> command = new GraphUpdateDatabase();
                case "a-vd" -> command = new GetVertexDegree();
                case "a-dd" -> command = new GetDistributionDegree();
                case "a-shortest-path" -> command = new FindShortestPath();
                case "a-da" -> command = new DensityAnalysis();
                case "a-ca" -> command = new CentralityAnalysis();
                case "a-cca" -> command = new ConnectedComponentsAnalysis();
                case "a-cc" -> command = new ClusteringCoefficients();
                case "a-cd" -> command = new CommunityDiscovery();
                case "a-nv" -> command = new Networkvisualization();
                case "quit"-> command = new QuitCommand();
                default -> {
                    Util.Print.invalidInput();
                    command = new HelpCommand();
                }
            }
            command.execute();
        }
    }
}
//
//
//