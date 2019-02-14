package org.gmm;
//recursive
//问题缩减 解决当前问题 分成两个部分

public class Sum {

    public static int sum(int arr[],int l) {
        if (l == arr.length)
            return 0;
        return arr[l]+sum(arr,l+1);
    }


    public static void main(String[] args){
        int [] arr = new int[]{1,2,3,4,5,6};
        System.out.println(Sum.sum(arr,0));
    }

    /**
     *
     * @param deepth
     * @return
     * 调试的时候为函数加上递归深度作为参数 并且在执行操作的时候使用
     * system.out.print()打印递归深度以及当前操作
     * 从而可视化调试
     */
    //使用递归深度作为参数 通过递归深度配合打印输出 调试
    public String getDeepString(int deepth){
        StringBuilder deepstring = new StringBuilder();
        for(int i =0;i<deepth;i++){
            deepstring.append("--");
        }
        return deepstring.toString();
    }
}


//链表天然递归性质
//删除全部的a元素 返回头节点
//leetcode 提交方法
/*
class Solution{
    public ListNode removeElements(ListNode head,int val){
        if(head == null)
            return null;
        head.next = removeElements(head.next,val);
        //考虑头节点
        return head.val == val ? head.next:head;
    }
}
*/


