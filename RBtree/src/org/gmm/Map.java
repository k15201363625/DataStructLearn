package org.gmm;

public interface Map<K,V> {
    //字典 / 映射
    //应用： 字典 数据库 词频统计
    //增删改查 4操作
    int getSize();
    boolean isEmpty();
    boolean contains(K key);
    void add(K key, V value);
    V remove(K key);
    V get(K key);
    void set(K key, V value);
    //链表中通常使用getNode(K key)获得相应的node便于处理
}
//重新定义节点node数据 变成key value 可以实现

/*
有序映射--tree
无需映射--hash表
多重映射--拓展tree
 */

/*
设计思维：
    为了避免代码重复 可以使用map写出set
    就是底层数据结构本身使用的node是<K,V>类型
    之后V在set中设为空 在Map中正常使用
    java底层用map+包装->set
 */
