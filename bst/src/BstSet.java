public class BstSet<E extends Comparable<E>> implements Set<E> {

    private BST<E> bst;

    public BstSet(){//构造函数没有泛型标志
        bst = new BST<>();
    }

    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    @Override
    public int getSize() {
        return bst.getSize();
    }

    @Override
    public boolean contain(E e) {
        return bst.contains(e);
    }

    @Override
    public void add(E e) {
        bst.add(e);
    }

    @Override
    public void remove(E e) {
        bst.remove(e);
    }
}
