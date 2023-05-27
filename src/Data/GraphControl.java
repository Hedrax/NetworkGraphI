package Data;

import Data.Database.Database;
import Data.Graph.ConstructionNode;
import Data.Graph.Graph;
import Resource.Node;
import Resource.Util;

import java.util.*;

public class GraphControl {
    static Graph graph = new Graph();
    static public Runnable GraphConstruction = GraphControl::constructGraph;
    static public Runnable DatabaseUpdate = GraphControl::appendToDataBase;

    static boolean working;
    //We inject node for initialization in the root, map, and also the queue to discover followings and followers
    static public void injectGraphNode(String username){
        Node node = searchNode(username);
        if (node == null || Objects.equals(username, "")){
            Util.Print.graphInjectionError();
        }
        else {
            discoverNewNode(node.userName,true);
        }
    }
    //Graph Construction control fn
    static public void stopConstruction(){working = false;}
    //printing the count of the nodes in the graph
    static public void graphCount(){
        Util.Print.graphCount(graph.counter);
    }
    //searching the node in the node table DB
    static Node searchNode(String username){
        Node node = Database.selectNode("@"+username);
        if (node == null){
            node = Database.selectNode(username);
            if (node == null) {
                return null;
            }
        }
        return node;
    }

    //it supposed to handle the followings and followers and append them to curr node
    static private void constructGraph(){
        working = true;
        String target;
        ConstructionNode node;
        Node dbNode;
        int i = 0;
        while (working && graph.getNumWaitingQueue() > 0){
            //get the target
            target = graph.getQueue(); // became 0
            node = graph.searchMap(target);
            dbNode = searchNode(target);
            i++;
            if (dbNode != null) {
                HashSet<String> followers = new HashSet<>(dbNode.followers);
                HashSet<String> followings = new HashSet<>(dbNode.following);
                handleFollows(followings, true,node);
                handleFollows(followers, false,node);
            }
            if (i%10 == 0) {
                Util.Print.graphCount(graph.counter);
                Util.Print.graphQueueRemaining(graph.getNumWaitingQueue());
            }
        }
        doubleCheck();
    }

    private static void handleFollows(HashSet<String> list, boolean isItFollowings, ConstructionNode parent) {
        list.forEach(currNode->{
            try {
                currNode = currNode.substring(1);
            }catch(Exception e){return;}
            ConstructionNode targetNode = graph.searchMap(currNode);
            //we add it since we're using hashsets to clear redundant things
            if (targetNode == null)
                discoverNewNode(currNode,false);
            targetNode = graph.searchMap(currNode);
            //making connection from the other side
            if (isItFollowings)
                targetNode.followers.add(targetNode);
            else
                targetNode.following.add(targetNode);

            //making connection from our side (parent)
            if (isItFollowings)
                parent.following.add(targetNode);
            else
                parent.followers.add(targetNode);
        });
    }
    static void discoverNewNode(String username,boolean root){
        //searching the database for the node
        Node dbNode = searchNode(username);
        ConstructionNode node;
        if (dbNode ==null){
            node = new ConstructionNode(username);
        }else{
            node = new ConstructionNode(dbNode);
        }
        graph.addToMap(node);
        graph.counter++;
        graph.addToQueue(username);
        if (root)
            graph.root = node;
    }
    //traverse through the graph
    static public void appendToDataBase() {
        int i = 0;
        Queue<ConstructionNode> queue = new ArrayDeque<>();
        queue.add(graph.root);

        while (!queue.isEmpty()) {
            ConstructionNode currentNode = queue.poll();
            currentNode.followerCount = currentNode.followers.size();
            currentNode.followingCount = currentNode.following.size();
            appendToNewTable(new Node(currentNode));
            currentNode.visited = true;
            for (ConstructionNode neighbor : getNeighbors(currentNode)) {
                if (!neighbor.visited) {
                    queue.add(neighbor);
                }
            }
            i++;
            if (i%100 == 0){
                Util.Print.appendToDatabase(i);
            }
        }
    }

    private static void appendToNewTable(Node currentNode) {
        Database.addNodeToNewTable(currentNode);
    }


    private static HashSet<ConstructionNode> getNeighbors(Data.Graph.ConstructionNode currentNode) {
        HashSet<ConstructionNode> list = new HashSet<>(currentNode.followers);
        list.addAll(currentNode.following);
        return list;
    }
    //double check on the construction by breadth first search
    static void doubleCheck(){
        int i = 0;
        Queue<ConstructionNode> queue = new ArrayDeque<>();
        queue.add(graph.root);

        while (!queue.isEmpty()) {
            ConstructionNode currentNode = queue.poll();

            HashSet<String> followers = new HashSet<>(NodesToStrings(currentNode.followers));
            HashSet<String> followings = new HashSet<>(NodesToStrings(currentNode.following));
            handleFollows(followers, false,currentNode);
            handleFollows(followings, true,currentNode);

            currentNode.visited = false;
            for (ConstructionNode neighbor : getNeighbors(currentNode)) {
                if (neighbor.visited) {
                    queue.add(neighbor);
                }
            }
            i++;
            if (i%100 == 0) {
                Util.Print.graphQueueRemaining(i);
            }

        }
    }

    private static List<String> NodesToStrings(HashSet<ConstructionNode> list) {
        List <String> list1 = new ArrayList<>();
        for (ConstructionNode i : list){
            list1.add(i.userName);
        }
        return list1;
    }

}

