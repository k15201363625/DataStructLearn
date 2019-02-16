package org.gmm;

public class AvlTreeMap<K extends Comparable<K>,V> implements Map<K,V> {
    private AvlTree<K,V> avl;

    public AvlTreeMap(){
        avl = new AvlTree<>();
    }

    @Override
    public int getSize() {
        return avl.getSize();
    }

    @Override
    public boolean isEmpty() {
        return avl.isEmpty();
    }

    @Override
    public boolean contains(K key) {
        return avl.contains(key);
    }

    @Override
    public void add(K key, V value) {
        avl.add(key,value);
    }

    @Override
    public V remove(K key) {
        return avl.remove(key);
    }

    @Override
    public V get(K key) {
        return avl.get(key);
    }

    @Override
    public void set(K key, V value) {
        avl.set(key,value);
    }
}
