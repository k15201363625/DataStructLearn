package org.gmm;
import java.util.TreeMap;

/**
 * 使用已经预定义好的capacity数组作为resize值候选 优化M
 * @param <K>
 * @param <V>
 */
public class HashTableDev<K extends Comparable<K>,V> {
    private TreeMap<K,V>[] hashtable;
    private int size;
    private int M;

    //hash素数表 大约是2倍关系 都是素数 效果好
    private static final int[] capacity
            = {53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593,
            49157, 98317, 196613, 393241, 786433, 1572869, 3145739, 6291469,
            12582917, 25165843, 50331653, 100663319, 201326611, 402653189, 805306457, 1610612741};
    //伸缩resize
    private static final int upper = 10;
    private static final int lower = 2;
    private static int capacityInddex = 0;

    public HashTableDev(){
        this.M = capacity[capacityInddex];
        size = 0;
        hashtable = new TreeMap[M];
        for(int i=0;i<M;i++)
            hashtable[i] = new TreeMap<>();
    }

    //变成整数 之后对于负整数变成绝对值 使用0x7fffffff(01*31)
    private int hash(K key){
        return (key.hashCode()&0x7fffffff %M);
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public void add(K key,V value){
        TreeMap<K,V> map = hashtable[hash(key)];
        if(map.containsKey(key))
            map.put(key,value);//更新
        else{
            map.put(key,value);
            size++;

            if(size>=upper*M)
                if(capacityInddex+1 < capacity.length){
                    capacityInddex++;
                    resize(capacity[capacityInddex]);
                }

        }
    }

    public V remove(K key){
        V res = null;
        TreeMap<K,V> map = hashtable[hash(key)];
        if(map.containsKey(key)){
            res = map.get(key);
            size --;
            if(size<lower*M)
                if(capacityInddex-1>=0){
                    capacityInddex--;
                    resize(capacity[capacityInddex]);//为0不能操作
                }
        }
        return res;
    }

    public void set(K key,V value){
        TreeMap<K, V> map = hashtable[hash(key)];

        if(!map.containsKey(key))
            throw new IllegalArgumentException(key + " doesn't exist!\n");

        map.put(key, value);
    }

    //resize 相同于动态数组 均摊复杂O(1)
    private void resize(int newM){
        //有坑
        TreeMap<K,V>[] newHashTable = new TreeMap[newM];
        for(int i=0;i<newM;i++){
            newHashTable[i] = new TreeMap<>();
        }
        //更换M hash需要重新计算 所以需要遍历一次重新分配位置
        int oldM = M;
        this.M = newM;
        TreeMap<K,V> map = null;
        for(int i=0;i<oldM;i++){
            map = hashtable[i];
            for(K key:map.keySet()){
                newHashTable[hash(key)].put(key,map.get(key));
            }
        }
        this.hashtable = newHashTable;
    }
}


