package Data.Graph;

import java.util.ArrayDeque;
import java.util.Queue;
public class Graph {
    public ConstructionNode root;
    private final Trie map;
    public int counter = 0;
    private final Queue <String> nodesToBeDiscovered;
    public Graph(){
        nodesToBeDiscovered = new ArrayDeque<String>();
        map = new Trie();

    }
    //map functions
    public void addToMap(ConstructionNode node){map.addNode(node);}
    public ConstructionNode searchMap(String target){return map.findNode(target);}
    //Queue fns
    public void addToQueue(String username){nodesToBeDiscovered.add(username);}
    public String getQueue(){return nodesToBeDiscovered.poll();}

    public int getNumWaitingQueue(){return nodesToBeDiscovered.size();}
}
