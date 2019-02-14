import java.util.*;

/**
 * 此时没有重复元素 可以改变定义为>=/<
 * 多用递归实现(但是容易变成链表 从而stack负担过重)
 * extentions:
 * get ceil and floor
 * add depth attribution for node
 * add count(data) attribution for node
 * add size of subtree attribution for node---get kth num /get rate of num
 */
public class BST<E extends Comparable<E>> {
    private Node root = null;
    private int size = 0;
    /**
     * 内类--node
     */
    //默认是private？？
    public BST(){
        root = null;
        size = 0;
    }
    public BST(Node root){
        this.root = root;
        size = countSize(root);
    }

    private int countSize(Node node){
        if(node==null)
            return 0;
        return countSize(node.right)+countSize(node.left)+1;
    }
    private class Node{
        E e;
        Node left = null,right = null;

        public Node(E e){
            this.e = e;
            right = left = null;
        }
    }

    public void add(E e){
        root = add(root,e);
    }
    //仅仅权限不能作为重载标志
    //通过返回新的节点以及root可以为空 简化终止条件判断 实现任务下放
    private Node add(Node node,E e){
        //默认当前节点可以null 很大程度建华代码
        if(node==null){
            size++;
            return new Node(e);
        }

        if(e.compareTo(node.e)<0)
            node.left = add(node.left,e);
        else if(e.compareTo(node.e)>0) //忽略==0情况
            node.right = add(node.right,e);
        return node;
    }

    public boolean contains(E e){
        return contains(root,e);
    }
    //使用地柜实现需要节点作为参数
    private boolean contains(Node node,E e){
        if(node == null)
            return false;
        if(e.compareTo(node.e)==0)
            return true;
        else if(e.compareTo(node.e)>0)
            return contains(node.left,e);
        else
            return contains(node.right,e);

    }

    public Node find(E e){
        return find(root,e);
    }

    private Node find(Node node,E e){
        if(node == null)
            return null;
        if(e.compareTo(node.e)==0)
            return node;
        else if(e.compareTo(node.e)>0)
            return find(node.left,e);
        else
            return find(node.right,e);
    }


    //traverse--遍历
    //pre常用 in顺序排列 post优先解决子问题
    public void preOrder(){
        preOrder(root);
    }
    //非递归实现 类似postorderNG2风格
    public void preOrderNR(){
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        Node curnode = root;
        while(!stack.isEmpty()){
            curnode = stack.pop();
            System.out.println(curnode.e);
            if(curnode.right!=null)
                stack.push(curnode.right);
            if(curnode.left!=null)
                stack.push(curnode.left);
        }
    }
    //类似in遍历风格
    public void preOrderNG2(){
        Node cur = root;
        Stack<Node> stack = new Stack<>();
        while(cur!=null||!stack.isEmpty()){
            while(cur!=null){
                System.out.println(cur.e);
                stack.push(cur);
                cur = cur.left;
            }
            if(!stack.isEmpty()){
                cur = stack.pop();
                //System.out.println(cur.e);
                cur = cur.right;
            }
        }
    }
    public void inOrder(){
        inOrder(root);
    }
    //非递归实现
    public void inOrderNR(){
        Node cur = root;
        Stack<Node> stack = new Stack<>();
        while(cur!=null||!stack.isEmpty()){
            while(cur!=null){
                stack.push(cur);
                cur = cur.left;
            }
            if(!stack.isEmpty()){
                cur = stack.pop();
                System.out.println(cur.e);
                cur = cur.right;
            }
        }
    }

    public void postOrder(){
        postOrder(root);
    }
    //使用是否已经遍历完右节点作为标记
    //postordernr1
    //private enum finished {YES,NO};
    private class btnNode{
        public Node node = null;
        public boolean isfinished = false;
        public btnNode(Node node,boolean flag){
            this.node = node;
            this.isfinished = flag;
        }
    }
    //借助表示位 占用空间增大 程序套路没什么变化
    public void postOrderNR(){
        Stack<btnNode> stack = new Stack<>();
        btnNode btn = null;
        Node cur = root;
        while(cur!=null||!stack.isEmpty()){
            while(cur!=null){
                btn = new btnNode(cur,false);
                stack.push(btn);
                cur = cur.left;
            }
            if(!stack.isEmpty()){
                btn = stack.pop();
                if(btn.isfinished==false){//第一次需要放回
                    cur = btn.node.right;
                    btn.isfinished = true;
                    stack.push(btn);
                }
                else{//第二次直接输出
                    System.out.println(btn.node.e);
                }
            }
        }
    }
    //另外一种思路
    //使用pre作为标志 postordernr2
    //对于节点 --- 左右都空 pre已经是left/right(已经结束左右)-->输出
    //            先放right 后放left

    public void postOrderNR2(){
        Stack<Node> stack = new Stack<>();
        Node cur = root;
        Node pre = null;
        stack.push(root);
        while(!stack.isEmpty()){
            cur = stack.pop();
            if(cur.left==null&&cur.right==null){
                System.out.println(cur.e);
                pre = cur;
            }
            //这里有坑 pre本身的检查--大难点
            else if(pre!=null&&(pre==cur.left||pre==cur.right)){
                System.out.println(cur.e);
                pre = cur;
            }
            else {
                stack.push(cur);//KEY
                if (cur.right != null)
                    stack.push(cur.right);
                if (cur.left != null)
                    stack.push(cur.left);
            }
        }
    }


    private void preOrder(Node node){
        if(node == null)
            return;
        System.out.println(node.e);
        preOrder(node.left);
        preOrder(node.right);
    }
    //中序遍历就是排序结果
    private void inOrder(Node node){
        if(node == null)
            return;
        inOrder(node.left);
        System.out.println(node.e);
        inOrder(node.right);
    }
    private void postOrder(Node node){
        if(node == null)
            return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.e);
    }


    //层序遍历  图的层序遍历需要标记
    public void bfsTraverse(){
        //queue本身只是借接口 需要选择实现方式
        //queue list继承了collection
        //deque继承了queue(deque也是接口)
        //linkedlist 实现了 list deque
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        Node cur = root;
        while(!queue.isEmpty()){
            cur = queue.remove();
            System.out.println(cur.e);
            if(cur.left!=null)
                queue.add(cur.left);
            if(cur.right!=null)
                queue.add(cur.right);
        }
    }

    //toString
    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        generateBSTstring(root,0,res);
        return res.toString();
    }

    private void generateBSTstring(Node node,int depth,StringBuilder res) {
        if(node == null){
            res.append(generateDepthString(depth)+"null\n");
            return;
        }
        res.append(generateDepthString(depth)+node.e+"\n");
        generateBSTstring(node.left,depth+1,res);
        generateBSTstring(node.right,depth+1,res);
    }

    private String generateDepthString(int depth) {
        StringBuilder res = new StringBuilder();
        for(int i=0;i<depth;i++){
            res.append("--");
        }
        return res.toString();
    }
    //toString end

    //递归方式
    public E minimum(){
        return minimum(root).e;
    }
    private Node minimum(Node node){
        if(node.left==null)
            return node;
        return minimum(node.left);
    }

    public E maximum(){
        return maximum(root).e;
    }
    private Node maximum(Node node){
        if(node.right==null)
            return node;
        return maximum(node.right);
    }

    //递归删除最大最小
    public E removeMin(){
        E res = minimum();
        //removeMin(root);
        root = removeMin(root);
        return res;
    }
    //删除当前子树的最小元素并且返回新的子树的新的根
    //考虑没有左子树的极限情况可以得到返回类型
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
    public E removeMax(){
        E res = maximum();
        //removeMax(root);
        //巨坑---删除可能删掉根节点 从而只能找到自己
        root = removeMax(root);
        return res;
    }
    //删除当前子树的最小元素并且返回新的子树的新的根
    //考虑没有左子树的极限情况可以得到返回类型
    private Node removeMax(Node node){
        if(node.right==null){
            Node leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }
        //通过递归 实现删掉根节点还能自动用返回的node连接上原来的右子树
        node.right = removeMax(node.right);
        return node;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public int getSize(){
        return size;
    }

    //删除节点 -- 关键使用递归考虑几种情况 使用如下思路：删除当前子树的节点 并且返回新的根 通过根的接受 实现删除根时的自动更新 不许哟保存前驱
    public void remove(E e){
        root = remove(root,e);//必须用root接受
    }
    //删除元素 返回新根
    private Node remove(Node node,E e){
        if(node==null)
            return null;
        if(node.e.compareTo(e)>0){
            node.left = remove(node.left,e);//更新
            return node;
        }
        else if(node.e.compareTo(e)<0){
            node.right = remove(node.right,e);//更新
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


    //extension---寻找前驱 后继---需要记录查找value过程中的parent and 拐点(firstparent)
    public static void main(String[] args){
    /*BST<Integer> bst = new BST<>();
    int [] nums = {5,6,8,2,4,6,7};
    for(int num:nums){
        bst.add(num);
    }
    bst.preOrder();
    System.out.println("\n");
    bst.preOrderNR();
    System.out.println("\n");
    bst.inOrder();
    System.out.println("\n");
    bst.inOrderNR();
    System.out.println("\n");
    bst.postOrder();
    System.out.println("\n");
    bst.postOrderNR();
    System.out.println("\n");
    bst.postOrderNR2();
    System.out.println("\n");

    System.out.println(bst);*/

        BST<Integer> bst = new BST<>();
        Random random = new Random();
        for(int i=0;i<10;i++){
            bst.add(random.nextInt(10000));
        }
        System.out.println(bst.size);
        bst.preOrder();
        System.out.println(bst);


        ArrayList<Integer> nums = new ArrayList<>();
        while(!bst.isEmpty()){
            nums.add(bst.removeMax());
        }

        System.out.println(nums);


        for(int i=1;i<nums.size();i++){
            if(nums.get(i-1)<=nums.get(i))
                throw new IllegalArgumentException("Error");
        }
        System.out.println("removeMax is completed");
    }
}
