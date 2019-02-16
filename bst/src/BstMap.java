public class BstMap <K extends Comparable<K>,V> implements Map<K,V> {
//使用泛型就要全部指明类型 否则重载会变成Object
    private Node root;
    private int size;

    private class Node{
        K key;
        V value;
        Node left;
        Node right;
        public Node(K key,V value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
        public Node(){
            key = null;
            value = null;
            left = null;
            right = null;
        }
    }

    public BstMap(){
        root = new Node();
        size = 0;
    }

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
    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean contains(K key) {
        return getNode(root,key) != null;
    }

    //仅仅权限不能作为重载标志
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
        return node;
    }

    @Override
    public void add(K key, V value) {
        root = add(root,key,value);
    }

    private Node minimum(Node node){
        if(node.left==null)
            return node;
        return minimum(node.left);
    }

    private Node removeMin(Node node){
        if(node.left==null){
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }

        //通过递归 实现删掉根节点还能自动用返回的node连接上原来的右子树
        node.left = removeMin(node.left);
        return node;
    }

    private Node remove(Node node,K key){
        if(node==null)
            return null;
        if(node.key.compareTo(key)>0){
            node.left = remove(node.left,key);//更新
            return node;
        }
        else if(node.key.compareTo(key)<0){
            node.right = remove(node.right,key);//更新
            return node;
        }
        else{// e.equals(node.e)
            if(node.left==null){
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }
            if(node.right==null){
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }
            //最麻烦的情况--选择前驱/后继都可以 治理使用后继
            Node successor = minimum(node.right);//key1 find
            successor.left = node.left;
            successor.right = removeMin(node.right);//key2 update attribution
            node.right = node.left = null;
            //size 不需要改变
            return successor;
        }
    }
    @Override
    public V remove(K key) {
        V res = get(key);
        root = remove(root,key);
        return res;
    }

    @Override
    public V get(K key) {
        Node node = getNode(root,key);
        return node == null ? null:node.value;
    }

    @Override
    public String toString(){
        return "size of map == " + size + "\n";
    }

    @Override
    public void set(K key, V value) {
        Node tmpnode = getNode(root,key);
        if(tmpnode==null)
            throw new IllegalArgumentException("doesn't exist"+key);
        tmpnode.value = value;
    }
    public static void main(String[] args){
        //时间测试
        long starttime = System.nanoTime();

        BstMap<String,Integer> bstmap = new BstMap<>();
        System.out.println(bstmap);

        long endtime = System.nanoTime();
        System.out.println("total time is:"+(endtime-starttime)/1e9+"\n");
    }
}
