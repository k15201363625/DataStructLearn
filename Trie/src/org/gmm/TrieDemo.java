package org.gmm;

//求解某个前缀所能对应的所有单词权值之和
import java.util.TreeMap;

public class TrieDemo {

    private class Node{

        public int value;//为0可以代表没有单词或者无贡献单词
        public TreeMap<Character, Node> next;

        public Node(int value){
            this.value = value;
            next = new TreeMap<>();
        }

        public Node(){
            this(0);
        }
    }

    private Node root;

    /** Initialize your data structure here. */
    public TrieDemo() {

        root = new Node();
    }

    public void insert(String key, int val) {

        Node cur = root;
        for(int i = 0 ; i < key.length() ; i ++){
            char c = key.charAt(i);
            if(cur.next.get(c) == null)
                cur.next.put(c, new Node());
            cur = cur.next.get(c);
        }
        cur.value = val;
    }

    //求前缀对应的权值和
    public int sum(String prefix){
        Node cur = root;
        for(int i=0;i<prefix.length();i++){
            char c = prefix.charAt(i);
            if(cur.next.get(c)==null)
                return 0;
            cur = cur.next.get(c);
        }

        return sum(cur);
    }

    private int sum(Node node){
        int res = node.value;

        for(char c:node.next.keySet())
            res += sum(node.next.get(c));

        return res;
    }


}
