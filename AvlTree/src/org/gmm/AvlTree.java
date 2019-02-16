package org.gmm;

import java.util.ArrayList;

/**
 *
 * @param <K>
 * @param <V>
 * >千万不要"连=" java全是引用传递 c++全是值传递所以不需要担心
 *
 * 使用高度 平衡因子 判断在添加删除后对于当前节点的祖先节点造成的不平衡情况
 * 使用递归技巧 在回溯的时候完成对于祖先节点的平衡调整
 *
 * 平衡定义：
 * 不同于以往根节点只能在最后两层的定义
 * 这里定义：人和街店左右子树高度差绝对值<2   平衡因子 = abs(height(left) - height(right))
 *
 * 平衡调整：
 * LL RR LR RL
 * 使用leftRotate rightRotate 完成调整
 *
 * 优化：
 * [1]
 * 在调整之后可以对于已经平衡的子节点 无序再继续上传调整祖先节点
 * ---因为引起问题是由于该子节点所在子树 所以修复问题后无需上传
 * method1: height检测 如果更新后的height==原来的height 那么子节点产生的影响不会波及到原来节点
 * NOTE:这种检测方法西药同时判定retNode == node
 * method2: 设置检查变量，某一个节点如果已经没有不平衡问题 则说明到目前为止问题已经解决 所以不需要上传
 * [2]
 * 使用红黑树 可以再均摊复杂度上获得提升
 * 使用更少的旋转操作
 */
public class AvlTree <K extends Comparable<K>,V>{
    //使用泛型就要全部指明类型 否则重载会变成Object
    private Node root;
    private int size;

    private class Node{
        K key;
        V value;
        Node left;
        Node right;
        int height;//维护高度
        public Node(K key,V value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            height = 1;
        }
    }

    public AvlTree(){
        root = null;
        size = 0;
    }
    //辅助判断函数--平衡判断
    public boolean isBalanced(){
        return isBalanced(root);
    }
    private boolean isBalanced(Node node){
        if(node == null)
            return true;

        int balanceFactor = getBalanceFactor(node);
        if(Math.abs(balanceFactor)>1)
            return false;
        return isBalanced(node.left)&&isBalanced(node.right);
    }

    private int getBalanceFactor(Node node){
        if(node==null)
            return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    private int getHeight(Node node){
        if(node == null)
            return 0;
        return node.height;
    }
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

    //基本平衡操作 leftRotate rightRotate
    //对于y节点进行旋转操作 返回旋转后的新的根节点x 由于可拓展性 可以直接在LL RR LR RL 中发挥作用
    private Node rightRotate(Node y){
        Node x = y.left;
        Node tmp = x.right;
        x.right = y;
        y.left = tmp;
        //更新height**
        x.height = updateHeight(x);
        y.height = updateHeight(y);
        return x;
    }

    private Node leftRotate(Node y){
        Node x = y.right;
        Node tmp = x.left;
        x.left = y;
        y.right = tmp;
        //更新height**
        x.height = updateHeight(x);
        y.height = updateHeight(y);
        return x;
    }


    private int updateHeight(Node node){
        if(node==null)
            return 0;
        return 1 + Math.max(getHeight(node.left),getHeight(node.right));
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


    public void add(K key, V value) {
        root = add(root,key,value);
    }

    //通过返回新的节点以及root可以为空 简化终止条件判断 实现任务下放
    private Node add(Node node,K key,V value){
        //默认当前节点可以null 很大程度建华代码
        if(node==null){
            size++;
            return new Node(key,value);
        }

        if(key.compareTo(node.key)<0)
            node.left = add(node.left,key,value);
        else if(key.compareTo(node.key)>0) //忽略==0情况
            node.right = add(node.right,key,value);
        else
            node.value = value;

        //height
        int oldHeight = node.height;//优化
        node.height = updateHeight(node);

        if(oldHeight==node.height)//优化
            return node;
        //平衡因子求解
        return makeBalanced(node);
    }



    private Node minimum(Node node){
        if(node.left==null)
            return node;
        return minimum(node.left);
    }

    private Node removeMin(Node node){
        Node retNode = null;
        if(node.left==null){
            Node rightNode = node.right;
            node.right = null;
            size--;
            retNode = rightNode;
        }
        else{
        //通过递归 实现删掉根节点还能自动用返回的node连接上原来的右子树
            node.left = removeMin(node.left);
            retNode = node;
        }
        return makeBalanced(node);
    }


    //删除之后也需要维持平衡性
    //删除之后不能直接返回 需要调整平衡 在回溯过程中完成平衡调节
    private Node remove(Node node,K key){
        if(node==null)
            return null;
        Node retNode = null;

        if(node.key.compareTo(key)>0){
            node.left = remove(node.left,key);//更新
            retNode = node;
        }
        else if(node.key.compareTo(key)<0){
            node.right = remove(node.right,key);//更新
            retNode = node;
        }
        else{// e.equals(node.e)
            if(node.left==null){
                Node rightNode = node.right;
                node.right = null;
                size--;
                retNode = rightNode;
            }
            else if(node.right==null){
                Node leftNode = node.left;
                node.left = null;
                size--;
                retNode = leftNode;
            }
            else {
                //最麻烦的情况--选择前驱/后继都可以 治理使用后继
                Node successor = minimum(node.right);//key1 find
                successor.left = node.left;
                //取代原来的removeMin操作
                successor.right = remove(node.right, successor.key);//key2 update attribution
                node.right = node.left = null;
                //size 不需要改变
                retNode = successor;
            }
        }
        //坑 retNode可能是空节点
        if(retNode == null)
            return retNode;

        //更新height

        retNode.height = updateHeight(retNode);
        //优化
        if(retNode == node && node.height==retNode.height)
            return retNode;

        //平衡化
        return makeBalanced(retNode);
    }

    private Node makeBalanced(Node node){
        //平衡因子
        int balanceFactor = getBalanceFactor(node);
        int leftBalanceFactor = getBalanceFactor(node.left);
        int rightBalanceFactor = getBalanceFactor(node.right);

        //加入平衡调节
        //LL
        if(balanceFactor>1&&leftBalanceFactor>0)//==0情况不会出现valanceFactor>1
            return rightRotate(node);
        //LR
        if(balanceFactor<-1&&rightBalanceFactor<0)//==0情况不会出现valanceFactor>1
            return leftRotate(node);

        //LR
        if(balanceFactor>1&&leftBalanceFactor<0){
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        //RL
        if(balanceFactor<-1&&rightBalanceFactor>0){
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public V remove(K key) {
        return remove(root,key).value;
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
    public static void main(String[] args){
        //时间测试
        long starttime = System.nanoTime();

        AvlTree<String,Integer> AvlTree = new AvlTree<>();
        System.out.println(AvlTree);

        long endtime = System.nanoTime();
        System.out.println("total time is:"+(endtime-starttime)/1e9+"\n");
    }
}
