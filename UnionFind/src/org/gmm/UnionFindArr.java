package org.gmm;

public class UnionFindArr implements UnionFind{

    int[] id;
    int size;

    public UnionFindArr(int num){
        id = new int[num];
        for(int i=0;i<num;i++){
            id[i] = i;
        }
        size = num;
    }

    @Override
    public boolean isConnected(int p, int q) {
        if(p<0||p>=size||q<0||q>=size)
            throw new IllegalArgumentException("error p or q index in isConnected operation");
        return find(p)==find(q);
    }

    @Override
    public void unionElements(int p, int q) {
        if(p<0||p>=size||q<0||q>=size)
            throw new IllegalArgumentException("error p or q index in union operation");
        if(find(p)==find(q))
            return;
        for(int i=0;i<size;i++){
            if(id[i]==id[p])
                id[i] = id[q];
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    private int find(int p){
        return id[p];
    }
}
