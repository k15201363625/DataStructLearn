/**
 * heap
 * extension:
 * d-ary heap(多项堆)--降低层数 但是siftDown会变多
 * index heap拿到中间元素（通过索引） 在图论算法中可以使用作为优化MaxHeap
 * 二项堆 斐波那契堆 左偏堆
 * 以上4个需要继续学习****************
 *
 * 广义队列
 * stack queue heap都算队列 有进出就是队列
 */


import org.gmm.MyArray;//需要在模块中添加模块依赖 体现在imi文件中
import java.util.Random;
/**
 * 优先队列--入队没有特别 出队有优先级
 * 可以使用 线性结构 普通顺序线性结构 堆等等实现
 * 这里使用Heap实现
 *
 * 二叉堆性质：
 * (1)完全二叉树
 * (2)最大堆--所有节点小于父节点
 * 存储方法：
 * (1)数组存储：
 * 索引关系 i(从1开始)--i*2 i*2+1(对于完全二叉树成立)  [i/2]
 * (从0开始使用i*2+1 i*2+2)
 */
//**重点**---heapify可以达到O(n)复杂度
//泛型中implements携程extends才对
public class MaxHeap<E extends Comparable<E>> {

    private MyArray<E> data;
    //heapify


    public MaxHeap(E[] arr){
        //使用最后一个非叶子结点进行调整parent(size-1)
        data = new MyArray<>(arr);
        for(int i=parent(data.getSize()-1);i>=0;i--){
            siftDown(i);
        }
    }

    public MaxHeap(int capicity){
        data = new MyArray<E>(capicity);
    }

    public MaxHeap(){
        data = new MyArray<>();
    }
    public int getSize(){
        return data.getSize();
    }
    public boolean isEmpty(){
        return data.isEmpty();
    }

    private int parent(int k){
        if(k<=0)
            throw new IllegalArgumentException("error index for MaxHeap");
        return (k-1)/2;
    }
    private int leftChild(int k){
        return k*2+1;
    }
    private int rightChild(int k){
        return k*2+2;
    }
    public void add(E e){
        data.addLast(e);
        siftUp(data.getSize()-1);
    }
    private void siftUp(int index){
        //制定data的泛型才能使用
        while(index>0 && data.get(parent(index)).compareTo(data.get(index))<0){
            data.swap(index,parent(index));
            index = parent(index);
        }
    }
    public E findMax(){
        if(data.getSize()==0)
            throw new IllegalArgumentException("heap is empty");
        return data.get(0);
    }

    public E extractMax(){
        E res = findMax();
        data.swap(0,data.getSize()-1);
        data.removeLast();

        siftDown(0);
        return res;
    }
    private void siftDown(int k){
        while(leftChild(k)<data.getSize()){
            int j = leftChild(k);
            if(j+1 < data.getSize() && data.get(j).compareTo(data.get(j+1))<0){
                j ++;
            }//data[j]是left right中更大的
            if(data.get(k).compareTo(data.get(j))<0){
                data.swap(k,j);
                k = j;
            }
            else
                break;
        }
    }

    public E replace(E e){
        E res = findMax();
        data.set(0,e);
        return res;
    }



    public static void main(String [] args){
        MaxHeap<Integer> maxHeap = new MaxHeap<>(1000);
        Random random = new Random();
        for(int i=0;i<1000;i++){
            maxHeap.add(random.nextInt(10000));
        }
        Integer[] arr = new Integer[1000];
        for (int i=0;i<maxHeap.getSize();i++){
            arr[i] = maxHeap.extractMax();
        }
        for(int i=1;i<maxHeap.getSize();i++){
            if(arr[i-1]<arr[i])
                throw new IllegalArgumentException("error in test maxHeap");
        }
        System.out.println("maxHeap test completed");

        //测试heapify

        for(int i=0;i<1000;i++){
            arr[i] = (random.nextInt(10000));
        }

        //坑 如果使用int[]无法强制转换报错？？
        MaxHeap<Integer> mh2 = new MaxHeap<>(arr);
        for (int i =0;i<10;i++){
            System.out.println(mh2.extractMax());
        }

    }
}

