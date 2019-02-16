package org.gmm;

/**
 * 设计通用的map形式
 * 在实现set时候直接将value置为空 可以了
 * @param <E>
 */
public class AvlTreeSet<E extends Comparable<E>> implements Set<E>{
    private  AvlTree<E,Object> avl;

    public AvlTreeSet(){
        avl = new AvlTree<E, Object>();
    }

    @Override
    public boolean isEmpty() {
        return avl.isEmpty();
    }

    @Override
    public int getSize() {
        return avl.getSize();
    }

    @Override
    public boolean contain(E e) {
        return avl.contains(e);
    }

    @Override
    public void add(E e) {
        avl.add(e,null);
    }

    @Override
    public void remove(E e) {
        avl.remove(e);
    }
}
