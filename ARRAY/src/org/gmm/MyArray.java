package org.gmm;
/**
 *
 * @param <E>
 *     java中泛型不能使用基本数据类型需要使用wraaper类型代替
 *     java中泛型不能直接使用new，需要强制类型转换
 * NOTE:
 * 数组适用于索引有具体意义 插入删除操作较少
 *
 */
public class MyArray<E> {

    private E[] data;
    private int size;
    //使用data.length可以代替 不同于c++
    //private int capicity;

    public MyArray(int capicity){
        //new 泛型数组技巧
        data = (E[]) new Object[capicity];
        size = 0;
        //this.capicity = capicity;
    }

    public MyArray(){
        this(10);
    }

    public MyArray(E[] arr){
        data = (E[])new Object[arr.length];
        for(int i=0;i<arr.length;i++)
            data[i] = arr[i];
        size = arr.length;
    }
    //在add remove中使用
    private void resize(int newCapicity){
        E[] tmpdata = (E[]) new Object[newCapicity];
        for(int i=0;i<size;i++){
            tmpdata[i] = data[i];
        }
        data = tmpdata;
    }

    public int getSize(){
        return size;
    }
    
    public int getCapicity(){
        return data.length;
    }

    public boolean isEmpty(){
        return size==0;
    }



    public void addLast(E e){
/*        if(size==data.length)
            throw new IllegalArgumentException("array is full");
        data[size++]=e;*/
        add(size,e);
    }
    public void addFirst(E e){
        add(0,e);
    }
    //insert 指定位置
    public void add(int index,E e){
        /*if(size == data.length)
            throw new IllegalArgumentException("add is failed,array is full");
        */
        if(index<0||index>size)
            throw new IllegalArgumentException("add is failed,index error");

        if(size == data.length)
            resize(2*data.length);//标准库中使用1.5

        for(int i=size-1;i>=index;i++){
            data[i+1] = data[i];
        }
        data[index]=e;
        size++;
    }

    public E remove(int index){
        if(index<0||index>=size)
            throw new IllegalArgumentException("remove failed,indx is out of range.");
        E res = data[index];
        for(int i=index+1;i<size;i++){
            data[i-1]=data[i];
        }
        //由于垃圾回收机制 需要删除引用方便gc
        //System.out.printf("size = %d\n",size);
        data[size-1]=null;
        size--;
        //使用lazy机制 而不是eager机制
        //if(size<data.length/2){
        if(size<data.length/4&&data.length/2!=0){
            //lazy机制
            resize(data.length/2);
        }
        return res;
    }
    //只删除一个
    public boolean removeElement(E e){
        int tmpindex = find(e);
        if(tmpindex!=-1){
            remove(tmpindex);
            return true;
        }
        else
            return false;
    }

    public E removeFirst(){
        return remove(0);
    }
    public E removeLast(){
        return remove(size-1);
    }


    //实现访问权限控制
    public E get(int index){
        if(index<0||index>=size)
            throw new IllegalArgumentException("get failed,index is out of range.");
        return data[index];
    }
    public void set(int index,E e){
        if(index<0||index>=size)
            throw new IllegalArgumentException("get failed,index is out of range.");
        data[index] = e;
    }

    public boolean contains(E e){
        for(E d : data){
            //使用equals进行值判定
            if(d.equals(e))
                return true;
        }

        return false;
    }

    public int find(E e){
        for(int i = 0;i<size;i++){
            if(data[i].equals(e))
                return i;
        }
        return -1;
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append(String.format("Array: Size = %d,Capicity = %d\n",size,data.length));
        res.append('[');
        for(int i=0;i<size;i++){
            res.append(data[i]);
            if(i!=size-1)
                res.append(", ");
        }
        res.append("]\n");

        return res.toString();//类型转换
    }

    public void swap(int i,int j){
        if((i<0||i>=size)||(j<0||j>=size))
            throw new IllegalArgumentException("index error in swap process");
        E tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }


    public static void main(String[] args){
        //使用泛型 可以不指定 但是标准下需要指定泛型类型
        //MyArray myArray = new MyArray(100);
        //如果不指定会在数据填充时候自动判断
        MyArray<Integer> myArray = new MyArray<>();
        for(int i=0;i<11;i++){
            myArray.addLast((int)(Math.random()*100));
        }
        System.out.println(myArray);
        myArray.remove(2);
        myArray.removeElement(myArray.get(3));
        System.out.println(myArray);

    }
}
