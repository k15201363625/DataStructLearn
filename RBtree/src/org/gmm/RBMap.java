package org.gmm;

public class RBMap<K extends Comparable<K>,V> implements Map<K,V> {
    private RBtree<K,V> rbtree;

    public RBMap(){
        rbtree = new RBtree<>();
    }

    @Override
    public int getSize() {
        return rbtree.getSize();
    }

    @Override
    public boolean isEmpty() {
        return rbtree.isEmpty();
    }

    @Override
    public boolean contains(K key) {
        return rbtree.contains(key);
    }

    @Override
    public void add(K key, V value) {
        rbtree.add(key,value);
    }

    @Override
    public V remove(K key) {
        return rbtree.remove(key);
    }

    @Override
    public V get(K key) {
        return rbtree.get(key);
    }

    @Override
    public void set(K key, V value) {
        rbtree.set(key,value);
    }
}

