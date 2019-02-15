import java.util.*;
import java.util.PriorityQueue;

public class Solution {
    //leetcode347 by java priorityqueue
    public List<Integer> topKFrequent(int[] nums,int k){
        //统计频率
        TreeMap<Integer,Integer> map = new TreeMap<>();
        for(int num:nums){
            if(map.containsKey(num))
                map.put(num,map.get(num)+1);
            else
                map.put(num,1);
        }

        //得到前k个 使用priority实现NlgM
//        PriorityQueue<Integer> pq = new PriorityQueue<>(
////                //匿名内部类 class A implements Comparator<>{ compare()}
////                new Comparator<Integer>(){
////                    @Override
////                    public int compare(Integer a,Integer b){
////                        return map.get(a) - map.get(b);
////                    }
////                });
        PriorityQueue<Integer> pq = new PriorityQueue<>(
                (a,b) -> map.get(a) - map.get(b)
                //传入参数->返回结果----对应于只需要实现一个函数 函数中还有一句话
                //lambda表达式不需要分号 本质是省略函数体大括号
        );

        for(int key:map.keySet()){
            if(pq.size()<k){
                pq.add(key);
            }//peek找到顶端
            else if(map.get(key)>map.get(pq.peek())){
                pq.remove();
                pq.add(key);
            }
        }

        LinkedList<Integer> res = new LinkedList<>();
        while(!pq.isEmpty()){
            res.add(pq.remove());
        }
        return res;

    }
}
