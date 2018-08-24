package com.android.interview.algorithm.tree;

/**
 * author： Created by shiming on 2018/8/23 16:15
 * mailbox：lamshiming@sina.com
 */

public class BinaryTreeDemo {
    /*
    什么是二叉树：二叉树是每个结点最多有两个子树的树结构。通常子树被称作“左子树”（left subtree）和“右子树”（right subtree）。二叉树常被用于实现二叉查找树和二叉堆。
    树的数据结构能够同时具备数组查找快的优点以及链表插入和删除快的优点
     */
    // 对于有序数组，查找很快，并介绍可以通过二分法查找，但是想要在有序数组中插入一个数据项，就必须先找到插入数据项的位置，然后将所有插入位置后面的数据项全部向后移动一位，来给新数据腾出空间，平均来讲要移动N/2次，这是很费时的。同理，删除数据也是
  // 另外一种数据结构——链表，链表的插入和删除很快，我们只需要改变一些引用值就行了，但是查找数据却很慢了，因为不管我们查找什么数据，都需要从链表的第一个数据项开始，遍历到找到所需数据项为止，这个查找也是平均需要比较N/2次。
   public static void binaryTree(){
       BinaryTree bt = new BinaryTree();
       bt.insert(50);
       bt.insert(20);
       bt.insert(80);
       bt.insert(10);
       bt.insert(30);
       bt.insert(60);
       bt.insert(90);
       bt.insert(25);
       bt.insert(85);
       bt.insert(100);
//       bt.delete(10);//删除没有子节点的节点
//       bt.delete(30);//删除有一个子节点的节点
//       bt.delete(80);//删除有两个子节点的节点

       Node node = bt.find(50);
       // 中序遍历
       System.out.println("中序遍历的开始");
       bt.infixOrder(node);
       //10 20 25 30 50 60 80 85 90 100
       System.out.println();
       System.out.println("中序遍历的结束");

       System.out.println("前序遍历的开始");
       bt.preOrder(node);
       //50 10 20 25 30 60 80 85 90 100
       System.out.println();
       System.out.println("前序遍历的结束");

       System.out.println("后序遍历的开始");
       bt.postOrder(node);
       //10 20 25 30 60 80 85 90 100 50
       System.out.println();
       System.out.println("后序遍历的结束");



       System.out.println("找到最大的结点"+bt.findMax().data);
       System.out.println("找到最小的结点"+bt.findMin().data);
       System.out.println("找到结点为100的Node ："+bt.find(100));
       System.out.println("找到结点为200的Node"+bt.find(200));
   }


}
