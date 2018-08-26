package com.android.interview.algorithm.tree;

import javax.xml.transform.Source;
import java.lang.reflect.Constructor;
import java.util.Objects;

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
       // 第一个插入的结点是 根节点
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
      // bt.delete(80);//删除有两个子节点的节点


       //查找到根节点
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
       //左子树，然后遍历右子树，最后访问根结点。
       bt.postOrder(node);
       //10 20 25 30 60 80 85 90 100 50
       System.out.println();
       System.out.println("后序遍历的结束");



       System.out.println("找到最大的结点"+bt.findMax().data);
       System.out.println("找到最小的结点"+bt.findMin().data);
       System.out.println("找到结点为100的Node ："+bt.find(100));
       System.out.println("找到结点为200的Node"+bt.find(200));




      // 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
       // 例如：前序遍历序列｛ 50 10 20 25 30 60 80 85 90 100｝和中序遍历序列｛10 20 25 30 50 60 80 85 90 100}，
       // 重建二叉树并输出它的头结点。
       // TODO   根节点-->>左子树-->>右子树
       System.out.println("前序遍历的结果为  {50 10 20 25 30 60 80 85 90 100｝");
       //  TODO  左子树 ---》根节点----》 右子树
       System.out.println("中序遍历序列｛10 20 25 30 50 60 80 85 90 100}");
       //前序遍历
       int[] preorderTraversal={50,10,20,25,30,60,80,85,90,100};
       //中序遍历
       int[] inorderTraversal={10,20,25,30,50,60,80,85,90,100};

      // Node demo = demo(preorderTraversal, inorderTraversal);
       System.out.println("start dddddddd");
       Node construct = construct(preorderTraversal, 0, preorderTraversal.length - 1, inorderTraversal, 0, inorderTraversal.length - 1);
       System.out.println("开始了"+construct.data);
       System.out.println("新的中序遍历的开始");
       bt.infixOrder(construct);
       //10 20 25 30 50 60 80 85 90 100
       System.out.println();
       System.out.println("新的中序遍历的结束");
       //while (construct.leftChild!=null){
       //    System.out.print(construct.leftChild.data+" ");
       //    construct=construct.leftChild;
       //}
       //while (construct.rightChild!=null){
       //    System.out.print(construct.rightChild.data+" ");
       //}
   }

    private static Node demo(int[] preorderTraversal, int[] inorderTraversal) {
       //两个数组的长度肯定是一样的，同时不为0，也不为null
       if (preorderTraversal==null||inorderTraversal==null||preorderTraversal.length!=inorderTraversal.length||preorderTraversal.length<1){
           return null;
       }
       return construct(preorderTraversal,inorderTraversal);

    }

    private static Node construct(int[] preorderTraversal, int[] inorderTraversal) {
        // 前序遍历的开始位置
        int preoderStart=0;
        //左子树，然后遍历右子树，最后访问根结点。
        int inorderStart=0;
        // 前序遍历的结束位置
        int preorderEnd=preorderTraversal.length-1;
        // 中序遍历的结束位置
        int inorderlEnd=inorderTraversal.length-1;

        //找出根节点的值 ，前序遍历的第一个位置
       int  value=  preorderTraversal[preoderStart];
       // 在中序遍历中取找根节点的角标
        int index=inorderStart;
        // 不断地查找到根节点的在中序遍历的角标
        while (index<=inorderlEnd&&inorderTraversal[index]!=value){
            index++;
        }
        // 到这里的根节点是 index 40
        if (index>inorderlEnd){
            throw new RuntimeException("不可能的哦");
        }

        //构建根节点
        Node node = new Node(value);
        // 查找左节点
        //左子树对应的前序遍历的位置在[preoderStart+1,preoderStart+index-inorderStart]
        //  左子树对应的中序遍历的位置在[inorderStart,index-1]
        node.leftChild=findLeftNode(preorderTraversal,preoderStart+1,preoderStart+index-inorderStart,inorderTraversal,inorderStart,index-1);
        node.rightChild=findLeftNode(preorderTraversal,preoderStart+index-1+inorderStart,preorderEnd,inorderTraversal,index+1,inorderlEnd);
        return node;
    }

    /**
     *
     * @param preorderTraversal 前序遍历
     * @param start 前序遍历的开始
     * @param end  前序遍历的结束
     * @param inorderTraversal 中序遍历数组
     * @param inorderStart 中序遍历的开始
     * @param inorederEnd 中序遍历的结束
     * @return
     */
    //   int[] inorderTraversal={10,20,25,30,50,60,80,85,90,100};
    //{50,10,20,25,30,60,80,85,90,100};
    // 到这里的的值为  start 1   end=4   inorderStart=0  inorederEnd=3

    // 到这里的 inorderTraversal={10，20，25，30}

    private static Node findLeftNode(int[] preorderTraversal, int start, int end, int[] inorderTraversal, int inorderStart, int inorederEnd) {
        // 开始位置大于结束位置说明已经没有需要处理的元素了
        if (start > end) {
            return null;
        }
        // 1、 到这里的 preorderTraversal={50，10，20，25，30}   start 1
        int  value=  preorderTraversal[start];
        // 那么这个值为 value=10
        // 在中序遍历中取找节点的角标
        int index=inorderStart;
        // 不断地查找到根节点的在中序遍历的角标
        while (index<=inorederEnd&&inorderTraversal[index]!=value){
            index++;
        }
        //查找的index的角标为0
        //构建 10的节点
        Node node = new Node(value);
        //左子树对应的前序遍历的位置在[preoderStart+1,preoderStart+index-inorderStart]
        //  左子树对应的中序遍历的位置在[inorderStart,index-1]
        node.leftChild=findLeftNode(preorderTraversal,start+1,start+index-inorderStart,inorderTraversal,inorderStart,index-1);

        return node;

    }

    /**
     * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二节树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
     *
     * @param preorder 前序遍历
     * @param ps       前序遍历的开始位置
     * @param pe       前序遍历的结束位置
     * @param inorder  中序遍历
     * @param is       中序遍历的开始位置
     * @param ie       中序遍历的结束位置
     * @return 树的根结点
     */
    public static Node construct(int[] preorder, int ps, int pe, int[] inorder, int is, int ie) {
        // 开始位置大于结束位置说明已经没有需要处理的元素了
        if (ps > pe) {
            return null;
        }
        // 取前序遍历的第一个数字，就是当前的根结点
        int value = preorder[ps];
        int index = is;
        // 在中序遍历的数组中找根结点的位置
        while (index <= ie && inorder[index] != value) {
            index++;
        }
        // 如果在整个中序遍历的数组中没有找到，说明输入的参数是不合法的，抛出异常
        if (index > ie) {
            throw new RuntimeException("Invalid input");
        }
        // 创建当前的根结点，并且为结点赋值
        Node node = new Node(value);
        // 递归构建当前根结点的左子树，左子树的元素个数：index-is+1个
        // 左子树对应的前序遍历的位置在[ps+1, ps+index-is]
        // 左子树对应的中序遍历的位置在[is, index-1]
        node.leftChild = construct(preorder, ps + 1, ps + index - is, inorder, is, index - 1);
        // 递归构建当前根结点的右子树，右子树的元素个数：ie-index个
        // 右子树对应的前序遍历的位置在[ps+index-is+1, pe]
        // 右子树对应的中序遍历的位置在[index+1, ie]
        node.rightChild = construct(preorder, ps + index - is + 1, pe, inorder, index + 1, ie);
        // 返回创建的根结点
        return node;
    }
}
