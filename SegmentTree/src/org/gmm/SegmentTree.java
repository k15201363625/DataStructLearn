package org.gmm;

/**
 * 区间统计查询
 * O(n)->O(logn)---更新查询
 * 更新：更新一个元素 更新一个区间
 * 查询：区间特性 最值 和
 * NOTE:总区间长度一定
 *
 * 结构：
 * 每个节点存储一个区间 从底到顶 区间逐渐变大 顶层全部 底部个体
 * 通过使用高层区间内容避免对于底层节点全部遍历
 * 叶子结点不一定只在最后一层
 *
 * 不是完全二叉树 不是满二叉树
 * 但一定是平衡二叉树（堆也是）----保证不会退化（二分搜索树不能保证）
 * 平衡二叉树：叶子结点只能在最后两层
 *
 * 实现：(使用数组实现为了方便变成有荣誉的满二叉树 属于完全二叉树 所以有索引关系)
 *   使用数组存储线段树 只需要4n空间(又浪费 2^k浪费最多)
 *   使用树形结构存储 节省空间但是会麻烦一点
 *
 * 基本操作：
 * 建立  查询  更新（点  区间通常借助lazy标记）
 *
 * tips：
 * 对于非更新的区间问题 使用预处理数据 求取前缀和方法
 * 但是一旦引入更新 前缀和复杂度O(n)
 * 线段树查询更新可以达到o(logn)
 * 对于线段树建立 O(n)复杂度
 *
 *
 * extension:
 * 1:避免区间更新o(m+logn)时间复杂度 使用懒惰标记延迟更新 在需要的时候下放标记
 * 2.二维线段树 高位线段树 使用线段树思想--拆分 聚合(区间树--n维)
 * 3.区间问题其他武器：树状数组 RMQ问题
 * 4.动态线段树：依赖于链表实现的线段树节省空间并且结构灵活 可以根据需要对于线段树进行划分
 *   在需要的地方对于线段树进行拆分
 *
 */
public class SegmentTree<E> {
    private E[] data;
    private E[] tree;
    //使用优先队列设计思想 传入操作符对象进行设定
    private Merger<E> merger;

    //建立线段树
    public SegmentTree(E[] arr,Merger<E> merger){
        this.merger = merger;

        data = (E[])new Object[arr.length];
        for(int i=0;i<arr.length;i++){
            data[i] = arr[i];
        }

        tree = (E[])new Object[arr.length*4];
        buildSegmentTree(0,0,data.length-1);
    }

    private void buildSegmentTree(int treeIndex,int l,int r) {
        //建立线段树 只需要  在treeindex位置创建[l r]闭区间线段树
        if(l==r){
            tree[treeIndex] = data[l];
            return;
        }
        //向左右传播
        int leftIndex = leftChild(treeIndex);
        int rightIndex = rightChild(treeIndex);
        int mid = l + (r-l)/2;

        buildSegmentTree(leftIndex,l, mid);
        buildSegmentTree(rightIndex,mid+1,r);

        //回传
        tree[treeIndex] = merger.merge(tree[leftIndex],tree[rightIndex]);
    }

    //query查询操作 对于queryL queryR进行查询
    public E query(int queryL,int queryR){
        if(queryL<0||queryL>=data.length||queryR<0||queryR>=data.length||queryL>queryR)
            throw new IllegalArgumentException("error query range");
        return query(0,0,data.length-1,queryL,queryR);
    }

    //在制定节点 对应的区间范围内 查询想要的区间的结果
    private E query(int treeIndex, int l, int r, int queryL, int queryR) {
        if(l==queryL && r==queryR){
            return tree[treeIndex];
        }
        //分成左右区间经行查询
        int mid = l + (r-l)/2;
        int leftIndex = leftChild(treeIndex);
        int rightIndex = rightChild(treeIndex);

        if(queryL>=mid+1){
            return query(rightIndex,mid+1,r,queryL,queryR);
        }
        else if(queryR<=mid){
            return query(leftIndex,l,mid,queryL,queryR);
        }
        //合并结果(问题无法华为某个单个子区间的时候)
        E resL = query(leftIndex,l,mid,queryL,mid);
        E resR = query(rightIndex,mid+1,r,mid+1,queryR);
        return merger.merge(resL,resR);
    }

    //update single data
    public void set(int index,E e){
        if(index<0||index>=data.length)
            throw new IllegalArgumentException("error index in set operation");

        data[index] = e;
        //更新线段树
        set(0,0,data.length-1,index,e);
    }

    private void set(int treeIndex,int l,int r,int index,E e) {
        //在以treeindex为索引的线段树中进行点更新
        if(l==r){
            if(l!=index)
                throw new IllegalStateException("set method error");
            tree[treeIndex] = e;
            return;
        }
        //分拆
        int mid = l + (r-l)/2;
        int leftIndex = leftChild(treeIndex);
        int rightIndex = rightChild(treeIndex);
        if(index>=mid+1)
            set(rightIndex,mid+1,r,index,e);
        else//index<=mid
            set(leftIndex,l,mid,index,e);

        //合并结果
        tree[treeIndex] = merger.merge(tree[leftIndex],tree[rightIndex]);
    }


    public int getSize(){
        return data.length;
    }

    public E get(int index){
        if(index<0||index>=data.length)
            throw new IllegalArgumentException("index error in get operation");
        return data[index];
    }

    //模拟完全二叉树 使用完全二叉树的性质
    private int leftChild(int index){
        return 2*index + 1;
    }
    private int rightChild(int index){
        return 2*index + 2;
    }
    @Override
    public String toString(){
        //使用简单的数据打印 以及线段树内维护的tree数据打印
        StringBuilder res1 = new StringBuilder();
        res1.append("data:\n");
        res1.append('[');
        for(int i=0;i<data.length;i++){
            res1.append(data[i]);
            if(i!=tree.length-1)
                res1.append(',');
        }
        res1.append("]\n");
        res1.append("tree:\n[");
        for(int i=0;i<tree.length;i++){
            if(tree[i]!=null)
                res1.append(tree[i]);
            else
                res1.append("null");
            if(i!=tree.length-1)
                res1.append(',');
        }
        res1.append("].");
        return res1.toString();
    }

    public static void main(String[] args) {
        Integer[] nums = {5,8,3,66,-5,9,4};
        SegmentTree<Integer> segmentTree = new SegmentTree<>(nums,
                new Merger<Integer>() {
                    @Override
                    public Integer merge(Integer a, Integer b) {
                        return a+b;
                    }
                }
                //(a,b)->a+b
                );
        System.out.println(segmentTree);

        System.out.println(segmentTree.query(2,6));
        segmentTree.set(3,55);
        System.out.println(segmentTree.query(2,6));

    }
}
