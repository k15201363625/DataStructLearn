package org.gmm;

/**
 * union合并
 * isconnected检查 连接情况
 *
 * 并查集： 解决网络连接情况问题
 *         对集合快速取并
 * 限制： 不能添加元素 改变元素
 *
 * 具体实现：（都是借助数组）
 *
 * quick find 逻辑上是 线性数组结构
 * find快O1    union On慢
 *
 *
 * quick union 逻辑上 树形结构 每个节点指向父亲
 * 1 为了减少合并时全部元素遍历的问题 采用先擒王思路 使用根节点合并思路
 * 2 优化：
 * 为了解决合并过程退化 从Oh-> On
 * 按照size合并 ->按照rank合并
 * 路径压缩(即便改变了rank但是也不需要那么准，所以是rank不是depth)
 *
 * 复杂度分析：Oh 在优化之后达到了Olog*n   ---->log*n=1(n<=1) log*(logn)(n>1)   --- 接近于O1
 */

public interface UnionFind {
    boolean isConnected(int p,int q);
    void unionElements(int p,int q);
    int getSize();
}
