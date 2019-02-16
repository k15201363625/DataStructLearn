package org.gmm;

public class TrieArr {
    private class Node{
        boolean isWord;
        Node [] next;

        public Node(){
            this(false);
        }
        public Node(boolean isWord){
            this.isWord = isWord;
            next = new Node[26];
        }
    }

    private Node root;
    private int size;

    public TrieArr(){
        root = new Node();
        size = 0;
    }

    //增加--非递归实现
    public void add(String word){
        Node cur = root;
        for(int i=0;i<word.length();i++){
            char c = word.charAt(i);
            if(cur.next[c-'a']==null)
                cur.next[c-'a'] = new Node();
            cur = cur.next[c-'a'];
        }
        if(!cur.isWord){
            cur.isWord = true;
            size++;
        }
    }

    //查询word 非递归实现
    public boolean contains(String word){
        Node cur = root;
        for(int i=0;i<word.length();i++){
            char c = word.charAt(i);
            if(cur.next[c-'a'] == null)
                return false;
            cur = cur.next[c-'a'];
        }
        return cur.isWord;
    }

    public int getSize(){
        return size;
    }
    public boolean isEmpty(){
        return size == 0;
    }
}

