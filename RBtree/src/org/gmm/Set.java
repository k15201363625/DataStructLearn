package org.gmm;

public interface Set<E> {
    //增删查 没有改  多用于数目统计 不能有重复 无序(不同于c++)
    //使用二分搜索树实现 lgn复杂度(O(h))
    //使用bst必须数据具有可比性 使用linkedlist则不需要


    //有序集合(java自带的set 常用tree结构实现)
    //无序集合(使用哈希表实现)
    //multiset--多重集合 不能用哈希表实现 通常用拓展搜索树实现

    boolean isEmpty();
    int getSize();
    boolean contain(E e);
    void add(E e);
    void remove(E e);

}
