package com.android.interview.algorithm.tree;

/**
 * author： Created by shiming on 2018/8/23 16:34
 * mailbox：lamshiming@sina.com
 */

public class Node {
    int data;   //节点数据
    Node leftChild; //左子节点的引用
    Node rightChild; //右子节点的引用

    public Node(int data){
        this.data = data;
    }
    //打印节点内容
    public void display(){
        System.out.println(data);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}