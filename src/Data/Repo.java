package Data;

import Data.Database.Database;
import Data.Graph.GraphAnalysis;
import Data.Remote.Profile;
import Data.Remote.Remote;
import Resource.Node;
import Resource.Util;
import edu.uci.ics.jung.graph.Graph;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
public class Repo {
    static int numberOfNodes;
    static boolean BasicInfo = false;
    static int BasicCallCounter = 0;

    static Graph graph;
    static public void retrieveNodes(int numberOfNodes){
        String targetUserName;
        GetNodes[] getNodes = new GetNodes[7];
        Thread[] threads = new Thread[7];
        for (int j=0;j<numberOfNodes;j++){
            synchronized (Thread.currentThread()) {
                for (int i = 0;i<7;i++){
                    targetUserName = getTopUndiscoveredUserName();
                    if (targetUserName ==null)
                        break;
                    getNodes[i] = new GetNodes(targetUserName,i);
                    threads[i] = new Thread(getNodes[i]);
                    threads[i].start();
                }
                //wait for the threads
                for (int i = 0; i < 7; i++) {
                    try {
                        if(threads[i] == null)
                            break;
                        threads[i].join();
                        getNodes[i].node.following = new HashSet<>(getNodes[i].node.following).stream().toList();
                        getNodes[i].node.followers = new HashSet<>(getNodes[i].node.followers).stream().toList();
                        Set<String> undiscovered = new HashSet<>(getNodes[i].node.followers);
                        undiscovered.addAll(getNodes[i].node.following);
                        undiscovered.forEach(element-> {if (!element.isEmpty())addUndiscovered(element.substring(1));});
                        //adding node
                        Database.addNode(getNodes[i].node);
                        Util.Print.nodeRetrieved();
                    } catch (InterruptedException e) {
                        Util.Print.printErrot(e);
                    }
                }

            }
        }
    }

    public static Graph GetGraph() {
        if (graph == null)
            graph = GraphAnalysis.buildGraph();
        return graph;
    }

    static class GetNodesModified implements Runnable{
        int profileNum;
        Remote remote;
        String targetUserName = "";
        Set<String> undiscovered;
        Node node;
        GetNodesModified(int profileNum){
            this.profileNum = profileNum;
        }
        @Override
        public void run() {
            while (Repo.numberOfNodes > 0) {
                synchronized (this) {
                    Util.Print.iterationCounter(numberOfNodes);
                    //get an element to be discovered from dataBase
                    targetUserName = getTopUndiscoveredUserName();
                    if (targetUserName == null) {
                        break;
                    }
                    Repo.numberOfNodes--;
                    //discover the element's information
                    try {
                        remote = new Remote(targetUserName, profileNum);
                        node = remote.getNode();
                    } catch (InterruptedException e) {
                        Util.Print.printErrot(e);
                    }

                    //make a unique list of followers and followings
                    undiscovered = new HashSet<>(node.followers);
                    undiscovered.addAll(node.following);
                    //iterate through the lists you made and append to undiscovered if it's not found in the node table
                    undiscovered.forEach(userName -> {
                        if (!Database.nodeExists(userName)) addToUndiscovered(userName);
                    });
                    node.userName = targetUserName;
                    node.following = new HashSet<>(node.following).stream().toList();
                    node.followers = new HashSet<>(node.followers).stream().toList();
                    //add the node you retrieved
                    addNodeToDB(node);
                }
                //close driver
                remote.connection.closeDriver();
            }

        }
    }
    static class GetBasicNode implements Runnable{
        int profileNum;
        Remote remote;
        String targetUserName = "";
        Node node;
        GetBasicNode(int profileNum){
            this.profileNum = profileNum;
        }
        @Override
        public void run() {
            remote = new Remote(targetUserName, profileNum);
            while (Repo.numberOfNodes > 0) {
                synchronized (this) {
                    Repo.numberOfNodes--;
                    Util.Print.iterationCounter(numberOfNodes);
                    //get an element to be discovered from dataBase
                    try {
                        targetUserName = getTopUndiscoveredUserName();
                        if (targetUserName == null) {
                            break;
                        }
                        targetUserName = annotationChecking(targetUserName);
                    }catch (Exception e){
                        Util.Print.printErrot(e);
                        continue;}
                    remote.setTarget(targetUserName);
                    //discover the element's information
                    try {
                        node = remote.getBasicNode();
                    } catch (InterruptedException e) {
                        Util.Print.printErrot(e);
                    }
                    //add the node you retrieved
                    addNodeToDB(node);
                }
                //close driver
            }
            remote.connection.closeDriver();
        }
    }
    static String annotationChecking(String targetUserName){
        if (targetUserName.charAt(0) == '@'){
            targetUserName = targetUserName.substring(1);
        }
        return targetUserName;
    }
    static String getTopUndiscoveredUserName(){
        try {
            return Database.selectFirstElementInUnDiscovered();
        }catch (Exception e) {
             return getTopUndiscoveredUserName();
            }
    }
    static void addToUndiscovered(String userName){
        Database.addToUnDiscovered(userName.substring(1));
    }
    static void addNodeToDB(Node node){
        Database.addNode(node);
    }

    static void organizingCalls(int numberOfThreads) throws InterruptedException {
        synchronized (Thread.currentThread()){
            for (int i =0;i< numberOfThreads;i++){
                if (Repo.BasicInfo)
                    new Thread(new GetBasicNode(i)).start();
                else
                    new Thread(new GetNodesModified(i)).start();
                Thread.sleep(200);
            }
        }
    }
    static public void retrieveNodesModified(int numberOfNodes) {
        Repo.numberOfNodes = numberOfNodes;
        try {
            Repo.organizingCalls(Profile.numberOfProfiles);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    static public void BasicInfoFlag(boolean val){Repo.BasicInfo = val;}
    static public void modifyIterations(int i){
        Repo.numberOfNodes = i;
    }

    static public void relog(){
        for (int i = 0; i< Profile.numberOfProfiles; i++){
            Remote remote = new Remote("",i);
            remote.login();
        }
    }
    public static void ClearDatabase(String choice){
        Database.DataBaseTable db;
        if (Objects.equals(choice, "n"))
            db = Database.DataBaseTable.nodes;
        else if (Objects.equals(choice, "d"))
            db = Database.DataBaseTable.nodeToDiscover;
        else
            db = Database.DataBaseTable.newNodes;
        Database.deleteDB(db);
    }
    public static void addUndiscovered(String userName){
        Database.addToUnDiscovered(userName);
    }
    public static void manualRide(int profileNum) {
        Remote remote = new Remote("",profileNum);
        remote.ManualControl();
    }
}



class GetNodes implements Runnable{
    String targetUserName;
    Node node = new Node();
    int profileNum;
    GetNodes(String targetUserName,int profileNum){
        this.profileNum = profileNum; this.targetUserName=targetUserName;
    }


    @Override
    public void run() {
        Remote remote = new Remote(targetUserName,profileNum);
        try {
                node = remote.getNode();
        } catch (Exception ignored) {}
        remote.connection.closeDriver();
        node.userName = targetUserName;
    }
    public Node export(){
        return node;
    }
}