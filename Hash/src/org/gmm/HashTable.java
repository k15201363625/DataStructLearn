package org.gmm;


import java.util.TreeMap;

/**
 * hash
 * 基本思路：
 * 从key->索引 映射实现O(1)操作
 *
 * 基本实现方法：
 * 转化为整数(
 *      float 使用计算机编码等效int实现
 *      string使用类似于多进制数计算int
 *      其他类型也可以用多进制数计算
 * )
 * NOTE:转化过程中常常一边转换一边取模 防止越界
 * 对于int 使用各种hash函数实现：
 * 小数字 直接对应/偏移 大数字 使用特殊hash函数
 * NOTE:取模的数值 https://planetmath.org/
 *
 * 解决冲突：
 * 链地址法 开放地址法(必须resize) 二次hash 公共溢出区
 *
 * 开放地址法：
 * alpha作为衰减因子 num/capacity
 * 线性探测法 平方探测法 二次hash...
 *
 *
 * hash扩充收缩M优化：
 * HashMap默认的初始化大小为16，之后每次扩充为原来的2倍。
 * HashTable默认的初始大小为11，之后每次扩充为原来的2n+1
 *
 *
 * 当前code使用 treemap数组 链地址法 + 动态resize 实现基本hashtable
 *
 * 复杂度分析：
 * [1]
 * 均摊复杂度O(1)--resize
 * [2]
 * 查询问题：
 * 对于固定M O(N/M)
 * 对于不固定M O(1)----开放地址法在合适的alpha设置下也可以达到
 */
public class HashTable<K extends Comparable<K>,V> {
    private TreeMap<K,V>[] hashtable;
    private int size;
    private int M;

    //伸缩resize
    private static final int upper = 10;
    private static final int lower = 2;
    private static final int initcapicity = 7;

    public HashTable(int M){
        this.M = M;
        size = 0;
        hashtable = new TreeMap[M];
        for(int i=0;i<M;i++)
            hashtable[i] = new TreeMap<>();
    }
    public HashTable(){
        this(initcapicity);
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
                resize(2*M);
        }
    }

    public V remove(K key){
        V res = null;
        TreeMap<K,V> map = hashtable[hash(key)];
        if(map.containsKey(key)){
            res = map.get(key);
            size --;
            if(size<lower*M && M/2 >=initcapicity)
                resize(M/2);//为0不能操作
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

