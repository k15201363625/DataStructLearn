package org.gmm;
import com.gmm.FileOperation;
import java.util.ArrayList;

/**
 *
 * @param <K>
 * @param <V>
 *
 * rbtree红黑树 具体实现细节见add操作
 *
 * 思路：
 * 2,3树 -> 左偏红黑树 这里实现的是左偏红黑树
 * 红节点正常只能位于黑节点左侧(广义红黑树没有这样的性质)
 * 染色问题 用2,3树结构(合并后分裂)性质(绝对平衡) 从而得到红黑树的性质
 *
 * 红黑树性质：
 * 【填充每个叶子结点左右子节点为null黑色节点】
 * 1 根节点黑色
 * 2 填充的null节点为黑色
 * 3 只有红色黑色两种节点
 * 4*红色节点子节点只能是黑色节点 黑色节点子节点可以红可以黑
 * (在left红黑树中)只能红街店位于黑节点左侧
 * 5*黑平衡(由于2,3树完全平衡性会 以及红黑节点红作为填充节点黑为原始节点性质决定)
 *
 * extension:
 * 删除操作没有实现******（更麻烦）
 *
 * 标准红黑树不是左偏的
 * 标准红黑树可以理解成2,3 2,4树(2,4树只能出现 红黑红 情况)
 * 标准红黑树实现更麻烦
 * **标准红黑树性质：（特有的）
 * 任何违规/偏斜可以再三次调整内解决 从而真正优于avl
 *
 *
 * 比较：
 * 红黑树插入 删除 查询都是O(logn)
 * 但是红黑树本身极限情况下达到2logn高度 查询更费时间
 * 但是插入删除 使用标准实现可以快速调整 从而优于avl
 *
 * news:
 * 红黑树作者是算法一书作者
 * 他的老师Donald Knuth(高德纳)很著名 有著作(《计算机程序设计艺术》《具体数学》)
 */

public class RBtree <K extends Comparable<K>,V>{
    //使用泛型就要全部指明类型 否则重载会变成Object
    private Node root;
    private int size;

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node{
        K key;
        V value;
        Node left;
        Node right;
        boolean color;
        public Node(K key,V value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            color = RED;//默认使用red初始化
        }
    }

    public RBtree(){
        root = null;
        size = 0;
    }
    //------------------------------
    //红黑树正式代码 -- 添加add操作代码
    //颜色判断 左旋右旋 颜色反转

    //颜色处理 由于红黑树空节点为黑色性质
    private boolean isRed(Node node){
        if(node==null)
            return BLACK;
        return node.color;
    }

    //不同于AVL旋转 需要先旋转 之后染色
    //一个节点保持原有颜色 从而不违反源节点父节点的要求
    //旋转下来的节点y 因为插入的都是红色 不会有黑黑 为了维持红黑树性质 需要变成红色
    //理解重点**：
    //***** 旋转只是一种中间过程 旋转后保证bst性质(avl能保证 相同旋转操作) 但是基本红黑树性质不能立刻恢复
    //***** 通常用来搭配下一步操作 恢复红黑树的性质 所以只是中间工具

    //left 使用一个黑 一个红思路考虑 或者两个红情况
    private Node leftRotate(Node y){
        Node x = y.right;

        y.right = x.left;
        x.left = y;

        x.color = y.color;
        y.color = RED;//不能改变红黑树性质

        return x;
    }

    //right 两个红的情况(左左)
    private Node rightRotate(Node y){
        Node x = y.left;

        y.left = x.right;
        x.right = y;

        x.color = y.color;
        y.color = RED;
        return x;
    }

    //颜色反转 一黑周围两个红 使用23树理解
    private void flipColors(Node node){
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    //添加元素
    //添加节点 根节点 或者空节点都是black
    public void add(K key,V value){
        root = add(root,key,value);
        root.color = BLACK;
    }

    /**
     *
     *
     * @param node
     * @param key
     * @param value
     * @return
     * 同样在回溯过程中对于祖先节点进行平衡调节
     */
    private Node add(Node node,K key,V value){
        //对于空节点不需要调整平衡直接返回
        if(node==null){
            size++;
            return new Node(key,value);
            //红节点
        }
        if(key.compareTo(node.key)<0)
            node.left = add(node.left,key,value);
        else if(key.compareTo(node.key)>0)
            node.right = add(node.right,key,value);
        else
            node.value = value;

        //调整非空节点平平衡性质
        //红 -> 黑 (正常，左旋,变色)
        //红 -> 红 (左旋+右旋+变色，右旋+变色)
        //1 2 3右旋 左旋 变色 完成所有问题
        if(isRed(node.right)&&!isRed(node.left))
            node = leftRotate(node);
        if(isRed(node.left)&&isRed(node.left.left))
            node = rightRotate(node);
        if(isRed(node.left)&&isRed(node.right))
            flipColors(node);

        return node;

    }

    //--------------------------------------

    //辅助判断函数--二分搜索树性质判断
    public boolean isBst(){
        ArrayList<K> keys = new ArrayList<>();
        inorder(root,keys);
        for(int i=0;i<keys.size();i++){
            if(keys.get(i-1).compareTo(keys.get(i))>=0)
                return false;
        }
        return true;
    }
    private void inorder(Node node, ArrayList<K> keys){
        if(node==null)
            return;
        inorder(node.left,keys);
        keys.add(node.key);
        inorder(node.right,keys);
    }



    //获取制定节点
    private Node getNode(Node node,K key){
        if(node == null)
            return null;
        if(key.compareTo(node.key)==0)
            return node;
        else if(key.compareTo(node.key)<0){
            return getNode(node.left,key);
        }
        else{
            return getNode(node.right,key);
        }
    }


    public int getSize() {
        return size;
    }


    public boolean isEmpty() {
        return size==0;
    }

    public boolean contains(K key) {
        return getNode(root,key) != null;
    }


    private Node minimum(Node node){
        if(node.left==null)
            return node;
        return minimum(node.left);
    }


    public V get(K key) {
        Node node = getNode(root,key);
        return node == null ? null:node.value;
    }

    public String toString(){
        return "size of map == " + size + "\n";
    }

    public void set(K key, V value) {
        Node tmpnode = getNode(root,key);
        if(tmpnode==null)
            throw new IllegalArgumentException("doesn't exist"+key);
        tmpnode.value = value;
    }

    // 删除
    public V remove(K key){
        throw new UnsupportedOperationException("No remove operation in RBTree!");
    }

    public static void main(String[] args){

        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            RBtree<String, Integer> map = new RBtree<>();
            for (String word : words) {
                if (map.contains(word))
                    map.set(word, map.get(word) + 1);
                else
                    map.add(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
        }

        System.out.println();
    }
}
