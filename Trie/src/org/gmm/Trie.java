package org.gmm;

import sun.reflect.generics.tree.Tree;

import java.util.TreeMap;

/**
 *
 *实现中的思考：----递归使用场景
 * 1 对于有限的循环可以表示的操作 可以使用递归/循环实现
 *   对于无限制的递归形式的循环 无法用循环表示 通常用递归表示
 * 2 对于没有父指针 可以使用递归技巧在退栈过程中实现
 *                 可以使用标记记下父节点以及祖先节点的信息
 * 应用举例：
 * 模式匹配中的* 使用递归 方便实现
 * 普通的trie查找 使用循环/递归可以实现
 * 求取所有的有权的单词  组成的trie的  某个前缀的权值  可以使用递归实现---因为有着无线扩展循环的的特性
 *
 * 总结：无限扩展的循环 通常用递归  退栈特性通常用递归
 *
 *
 * extension:
 * trie(前缀树、字典树)删除操作
 * 三叉字典树 使用更多的节点 但是每个节点只有三个分支 通过字符大小比较决定节点走向  用稍多的时间 换区了更少的空间消耗(保存的指针变少)
 * 压缩Trie 对于没有分支节点的单链进行压缩成为单个后缀节点---对于加入操作需要分裂节点 增加分支
 * 后缀树 后缀数组 后缀自动机 AC自动机---- 字符串匹配常见算法
 *
 * 字符串拓展：
 * 字符串匹配算法 kmp ...
 * 模式匹配问题 --- 正则表达式等等算法
 * 文件压缩算法
 * 编译原理
 * DNA等有着字符串特性的问题
 *
 * trie问题：
 * 空间占用巨大 即便使用TreeMap实现对于分直接点动态存储 任然需要较大的空间
 * 使用压缩字典树 三叉字典树一定程度缓解空间问题
 *
 * 优点：
 * 前缀操作方便 更好利用前缀特性
 * 具有字典特性---可以当做set map使用
 * 对于大量数据查询时候 从logn->len(word)
 *
 * 其他实现方式：
 * 使用TreeMap大数据量下适合 但对于一些莒县的应用场景 使用
 * HashMap array作为next保存方式更好
 *
 * Trie实现 set map--也可以作为键是字符串类型条件下的set map
 */

public class Trie {
    private class Node{
        boolean isWord;
        TreeMap<Character,Node> next;
        //HashMap<Character,Node> next;
        //Node [] next;

        public Node(){
            this(false);
        }
        public Node(boolean isWord){
            this.isWord = isWord;
            next = new TreeMap<>();
        }
    }

    private Node root;
    private int size;

    public Trie(){
        root = new Node();
        size = 0;
    }

    //增加--非递归实现
    public void add(String word){
        Node cur = root;
        for(int i=0;i<word.length();i++){
            char c = word.charAt(i);
            if(cur.next.get(c)==null)
                cur.next.put(c,new Node());
            cur = cur.next.get(c);
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
            if(cur.next.get(c)==null)
                return false;
            cur = cur.next.get(c);
        }
        return cur.isWord;
    }

    //模式匹配--递归检索
    public boolean search(String pattern){
        return match(root,pattern,0);
    }
    //从当前节点开始 匹配index开始的后续内容 如果可以全部匹配返回true
    private boolean match(Node node,String pattern,int index){
        //不同于非递归 需要通过索引作为参数记录pattern中匹配字符的索引
        if(index==pattern.length())
            return node.isWord;

        char c = pattern.charAt(index);

        if(c!='.'){
            if(node.next.get(c)==null)
                return false;
            return match(node.next.get(c),pattern,index+1);
        }
        else{
            for(char nextc : node.next.keySet()){
                if(match(node.next.get(nextc),pattern,index+1))
                    return true;
            }
            return false;
        }
    }


    public int getSize(){
        return size;
    }
    public boolean isEmpty(){
        return size == 0;
    }
}
