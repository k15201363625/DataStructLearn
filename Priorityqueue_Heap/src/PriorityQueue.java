/**
 * java中原生的priority提供了对于比较定义的两种解决方案
 * (在java中priority是最小堆)
 * (java中默认的正常比较使用 >+ <- =0)
 *
 * 1 E实现了Comparable接口中的CompareTo函数
 * 2 使用构造priorityqueue函数参数传入比较器(Comparator可以使用)
 *
 * 2方式优点：
 * 对于原生java类的比较方式可用过传入comparator进行改变
 * 对于没有比较能力的原生java类可以加上比较功能而不用继承出新的类
 * 对于原有比较方式可以快速覆盖
 * 结合参数方法可以直接在调用的地方定义类从而简化参数传递过程(因为可以访问调用作用域的变量)
 * 结合匿名内部类 lambda表达式更加简单
 * 【最后两点详细可见leetcode347 不同实现优化】
 *
 * @param <E>
 */
public class PriorityQueue<E extends Comparable<E>> implements Queue<E> {

    private MaxHeap<E> maxHeap;

    public PriorityQueue(){
        maxHeap = new MaxHeap<E>();
    }

    @Override
    public int getSize() {
        return maxHeap.getSize();
    }

    @Override
    public boolean isEmpty() {
        return maxHeap.isEmpty();
    }

    @Override
    public void enQueue(E e) {
        maxHeap.add(e);
    }

    @Override
    public E deQueue() {
        return maxHeap.extractMax();
    }

    @Override
    public E getFirst() {
        return maxHeap.findMax();
    }
}
