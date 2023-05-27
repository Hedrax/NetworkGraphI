package Data.Graph;

import Resource.Node;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
    Map<Character, TrieNode> children;
    ConstructionNode value;

    public TrieNode() {
        children = new HashMap<>();
        value = null;

    }
}

public class Trie {
    TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void addNode(ConstructionNode node) {
        String word = node.userName;
        TrieNode curr = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!curr.children.containsKey(c)) {
                curr.children.put(c, new TrieNode());
            }
            curr = curr.children.get(c);
        }
        curr.value = node;
    }

    public ConstructionNode findNode(String word) {
        TrieNode curr = root;
        if (word == null)
            return null;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!curr.children.containsKey(c)) {
                return null;
            }
            curr = curr.children.get(c);
        }
        return curr.value;
    }
}
