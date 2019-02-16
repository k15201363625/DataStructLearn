package org.gmm;

public class UnionFindTree implements UnionFind {


    int[] parent;
    int[] rank;//int size
    int size;

    public UnionFindTree(int num){
        parent = new int[num];
        rank = new int[num];
        for(int i=0;i<num;i++){
            parent[i] = i;
            rank[i] = 1;
        }
        size = num;
    }

    @Override
    public boolean isConnected(int p, int q) {
        if(p<0||p>=size||q<0||q>=size)
            throw new IllegalArgumentException("error p or q index in isConnected operation");
        return find(p)==find(q);
    }

    //按照秩归并
    @Override
    public void unionElements(int p, int q) {
        if(p<0||p>=size||q<0||q>=size)
            throw new IllegalArgumentException("error p or q index in union operation");

        if ((p = find(p)) == (q = find(q)))
            return;
        if(rank[p]<rank[q])
            parent[p] = q;
        else if(rank[p]>rank[q])
            parent[q] = p;
        else{
            parent[q] = p;
            rank[p] ++;
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    //加上路径压缩---递归完全压缩但是未必快
    private int find1(int p){
        if(parent[p]==p)
            return p;
        return parent[p] = find1(p);
    }

    //加上路径压缩---非递归不能完全压缩但是减少系统开销 测试中更快 通过对于一个节点多次压缩可以实现完全压缩 so差距不大
    private int find(int p){
        while(parent[p]!=p){
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }
}
