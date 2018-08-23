package com.android.interview.algorithm;

import java.util.ArrayList;
import java.util.Stack;

/**
 * author： Created by shiming on 2018/8/23 15:21
 * mailbox：lamshiming@sina.com
 * 输入个链表的头结点，从尾到头反过来打印出每个结点的值。
 */

class ListNodeDemo {

    public static void demo() {
        ListNode listNode = new ListNode(10);
        ListNode listNode1 = new ListNode(11);
        ListNode listNode2 = new ListNode(12);
        ListNode listNode3 = new ListNode(13);
        ListNode listNode4 = new ListNode(14);
        listNode.next=listNode1;
        listNode1.next=listNode2;
        listNode2.next=listNode3;
        listNode3.next=listNode4;

        ArrayList<Integer> integers = printListFromTailToHead(listNode);
        String string = integers.toString();
        System.out.println("其实把链表转换了下，只收录其中的头和尾巴："+string);

        printListInversely(listNode);
    }

    private static void printListInversely(ListNode listNode) {
        if (listNode!=null) {
            doWhat(listNode);
           // System.out.println("每个该打印的元素  ："+listNode.val);
        }

    }

    private static void doWhat(ListNode listNode) {
        Stack<ListNode> stack = new Stack<>();
        while (listNode!=null){
            stack.push(listNode);
            listNode=listNode.next;
        }
        System.out.println(" stack "+stack.size());
//        for (int i=0;i<stack.size();i++){
//            System.out.println("每个该打印的元素 ::"+stack.get(i).val);
//        }
        ListNode tmp;
        while (!stack.empty()){
            // 移除堆栈顶部的对象，并作为此函数的值返回该对象。
            tmp = stack.pop();
            System.out.println("tmp="+tmp.val);
          //  System.out.println("每个该打印的元素 ："+tmp.val);
        }
    }

    public static class ListNode {
        int val;
        ListNode next;
        public ListNode(int  v){
            this.val=v;
        }
    }

    static ArrayList<Integer> arrayList = new ArrayList<Integer>();//建立一个新的列表

    /**
     *  其实把链表转换了下，
     * @param listNode
     * @return
     */
    public static ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        arrayList.clear();
        if (listNode != null) {
            printListFromTailToHead(listNode.next);//指向下一个节点
            arrayList.add(listNode.val);//将当前节点的值存到列表中
        }
        return arrayList;
    }
}
