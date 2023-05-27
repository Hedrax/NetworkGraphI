import Data.Graph.GraphAnalysis;
import Data.GraphControl;
import Data.Repo;
import Resource.Util;
import edu.uci.ics.jung.graph.Graph;

import static Data.Graph.GraphAnalysis.*;
import static Data.Graph.GraphAnalysis.clusteringCoefficients;


interface Command{
    void execute();
}
//executes each command coming from the main class
class LoginCommand implements Command{
    @Override
    public void execute() {
        Repo.relog();
        Util.Print.commandExecuted();
    }
}
class RetrieveNodeCommand implements Command{
    int numberOfNodes = 0;
    @Override
    public void execute(){
        numberOfNodes = Util.Input.numberOfRequestedNodes();
        Repo.retrieveNodes(numberOfNodes);
    }
}

class UpdatedRetreiveNodeCommand implements Command{
    int numberOfNodes = 0;
    @Override
    public void execute() {
        numberOfNodes = Util.Input.updatedNumberOfRequestedNodes();
        Repo.BasicInfoFlag(false);
        Repo.retrieveNodesModified(numberOfNodes);
    }
}
class GetBasicNodesCommand implements Command{
    int numberOfNodes = 0;
    @Override
    public void execute() {
        numberOfNodes = Util.Input.updatedNumberOfRequestedNodes();
        Repo.BasicInfoFlag(true);
        Repo.retrieveNodesModified(numberOfNodes);
    }
}
class HelpCommand implements Command{
    @Override
    public void execute(){
        Util.Print.showCommands();
    }
}
class QuitCommand implements Command{
    @Override
    public void execute(){
        System.exit(1);
    }
}
class OverRideCounterCommand implements Command{
    int numberOfIterations = Util.Input.numberOFModifiedIterations();
    @Override
    public void execute() {
        Repo.modifyIterations(numberOfIterations);
    }
}
class ManualRideCommand implements Command{
    int profileNum;
    @Override
    public void execute(){
        profileNum = Util.Input.numOfProfile();
        Repo.manualRide(profileNum);
    }
}

class DatabaseClearanceCommand implements Command{

    @Override
    public void execute() {
        Repo.ClearDatabase(Util.Input.databaseClearance());
    }
}

class DatabaseAddManualUsersCommand implements Command{
    @Override
    public void execute() {
        Repo.addUndiscovered(Util.Input.dataBaseManualUserInjection());
    }
}
class GraphInjectionCommand implements Command{
    @Override
    public void execute() {
        String username = Util.Input.graphInjection();
        GraphControl.injectGraphNode(username);
        }
}
class GraphStopCommand implements Command{
    @Override
    public void execute() {
        Util.Print.stoppingGraphConstruction();
        GraphControl.stopConstruction();
    }
}
class GraphConstructionCommand implements Command{
    @Override
    public void execute() {
        Util.Print.initGraphConstruction();
        new Thread(GraphControl.GraphConstruction).start();
    }
}
class GraphCountPrintCommand implements Command{
    @Override
    public void execute() {
        GraphControl.graphCount();
    }
}
class GraphUpdateDatabase implements Command{
    @Override
    public void execute() {
        Util.Print.graphUpdatesDatabaseInit();
        new Thread(GraphControl.DatabaseUpdate).start();
    }
}

class GetVertexDegree implements Command{
    @Override
    public void execute() {
        Util.Print.graphUpdatesDatabaseInit();
        Graph graph = Repo.GetGraph();
        String vertex = Util.Input.getVertex();
        vertaxDegree(graph,vertex);
    }
}
class GetDistributionDegree implements Command{
    @Override
    public void execute() {
        Util.Print.getDegreeDistribution();
        Graph graph = Repo.GetGraph();
        degreeDistribution(graph);
    }
}
class FindShortestPath implements Command{
    @Override
    public void execute() {
        String start = Util.Input.starting();
        String end = Util.Input.ending();
        Graph graph = Repo.GetGraph();
        findShortestPath(graph,start,end);
    }
}
class DensityAnalysis implements Command{
    @Override
    public void execute() {
        Graph graph = Repo.GetGraph();
        String vertex = Util.Input.getVertex();
        densityAnalysis(graph);
    }
}
class CentralityAnalysis implements Command{
    @Override
    public void execute() {
        Graph graph = Repo.GetGraph();
        String vertex = Util.Input.getVertex();
        CentralityAnalysis(graph, vertex);
    }
}
class ConnectedComponentsAnalysis implements Command{
    @Override
    public void execute() {
        Graph graph = Repo.GetGraph();
        String vertex = Util.Input.getVertex();
        connectedComponentsAnalysis(graph, vertex);
    }
}
class ClusteringCoefficients implements Command{
    @Override
    public void execute() {
        Util.Print.initCluster();
        Graph graph = Repo.GetGraph();
        clusteringCoefficients(graph);
    }
}

class CommunityDiscovery implements Command{
    @Override
    public void execute() {
        Graph graph = Repo.GetGraph();
        communityDiscovery(graph);
    }
}

class Networkvisualization implements Command{
    @Override
    public void execute() {
        Graph graph = Repo.GetGraph();
        networkvisualization(graph);
    }
}