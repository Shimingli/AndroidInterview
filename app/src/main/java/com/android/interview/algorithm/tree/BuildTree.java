package com.android.interview.algorithm.tree;

import java.util.Arrays;

/**
 * author： Created by shiming on 2018/8/29 10:11
 * mailbox：lamshiming@sina.com
 */

class BuildTree {
    public static Node reConstructBinaryTree(int[] pre, int[] in) {
        if (pre == null || in == null || pre.length != in.length)//如果先序或者中序数组有一个为空的话，就无法建树，返回为空
            return null;
        else {
            return reBulidTree(pre, 0, pre.length - 1, in, 0, in.length - 1);
        }
    }

    /**
     * @param pre
     * @param startPre
     * @param endPre
     * @param in
     * @param startIn
     * @param endIn
     * @return
     */
    private static Node reBulidTree(int[] pre, int startPre, int endPre, int[] in, int startIn, int endIn) {
        if (startPre > endPre || startIn > endIn)//先对传的参数进行检查判断
            return null;
        int root = pre[startPre];//数组的开始位置的元素是跟元素
        int locateRoot = locate(root, in, startIn, endIn);//得到根节点在中序数组中的位置 左子树的中序和右子树的中序以根节点位置为界

        if (locateRoot == -1) //在中序数组中没有找到跟节点，则返回空
            return null;
        Node treeRoot = new Node(root);//创建树根节点
        treeRoot.leftChild = reBulidTree(pre, startPre + 1, startPre + locateRoot - startIn, in, startIn, locateRoot - 1);//递归构建左子树
        treeRoot.rightChild = reBulidTree(pre, startPre + locateRoot - startIn + 1, endPre, in, locateRoot + 1, endIn);//递归构建右子树
        return treeRoot;
    }

    //找到根节点在中序数组中的位置，根节点之前的是左子树的中序数组，根节点之后的是右子树的中序数组
    private static int locate(int root, int[] in, int startIn, int endIn) {
        for (int i = startIn; i < endIn; i++) {
            if (root == in[i])
                return i;
        }
        return -1;

    }

    public static Node reConstructBinaryTreeNew(int[] pre, int[] in) {
        if (pre.length == 0 || in.length == 0)
            return null;
         Node node = new Node(pre[0]);
        for (int i = 0; i < pre.length; i++) {
            if (pre[0] == in[i]) {
                node.leftChild = reConstructBinaryTreeNew(Arrays.copyOfRange(pre, 1, i + 1), Arrays.copyOfRange(in, 0, i));
                node.rightChild = reConstructBinaryTreeNew(Arrays.copyOfRange(pre, i + 1, pre.length), Arrays.copyOfRange(in, i + 1, in.length));
                break;
            }
        }
        return node;
    }
}
