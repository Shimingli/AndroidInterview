package com.android.interview.algorithm.tree;

import android.text.TextUtils;

/**
 * author： Created by shiming on 2018/8/23 16:35
 * mailbox：lamshiming@sina.com
 */

public class BinaryTree implements Tree {
    //表示根节点
    private Node root;

    //查找节点
    public Node find(int key) {
        // 以根节点开始
        Node current = root;
        // 知道找到了当前的 current的等于 null
        while (current != null) {
            if (current.data > key) {//当前值比查找值大，搜索左子树
                current = current.leftChild;
            } else if (current.data < key) {//当前值比查找值小，搜索右子树
                current = current.rightChild;
            } else {
                return current;
            }
        }
        return null;//遍历完整个树没找到，返回null
    }

    //插入节点
    public boolean insert(int data) {
        Node newNode = new Node(data);
        if (root == null) {//当前树为空树，没有任何节点
            root = newNode;
            return true;
        } else {
            Node current = root;
            Node parentNode = null;
            while (current != null) {
                parentNode = current;
                if (current.data > newNode.data) {//当前值比插入值大，搜索左子节点
                    current = current.leftChild;
                    if (current == null) {//左子节点为空，直接将新值插入到该节点
                        parentNode.leftChild = newNode;
                        return true;
                    }
                } else {
                    current = current.rightChild;
                    if (current == null) {//右子节点为空，直接将新值插入到该节点
                        parentNode.rightChild = newNode;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * 1、传入根节点 值为50 ，查找50的左节点为20不为null，再次查找20的左节点，为10，也不为null，然后在查找10的左节点，为null ，输出10，第一个节点输出完成为 10
     * 2、接下来，查找的10的右节点，为null，不进入输出语句
     *  3、这下输出语句20，查找20右节点，查找到值为30，第一步查找的值为左节点，查找到的值为25，查找右节点为null
     *  4、到这里输出的结果为  10  20 25 30
     *  5、这样子50左节点就全部查找完了
     *  6、接下来查找50的右节点，查找右节点80，然后查找80的左节点，查找到的值为60,60没有右节点，继续查找80的右节点，到这里 输出的结果10 20 25 30 50 60 80
     *  7、查找80右节点，查到到值为90，接着查找90的左节点，查到到的值85，接着85的左右节点为null，返回直接查找90的右节点，查找到了100，
     *  8、最终输出的结果，10 20 25 30 50 60 80 85 90 100
     */
    //中序遍历   左子树 ---》根节点----》 右子树
    //在二叉树中，中序遍历首先遍历左子树，然后访问根结点，最后遍历右子树。
    public void infixOrder(Node current) {
        if (current != null) {
            infixOrder(current.leftChild);
            System.out.print(current.data + " ");
            infixOrder(current.rightChild);
        }
    }
    public void infixOrderDemo(String str,Node current) {
        if (current != null) {

            System.out.println("这个值是什么啊 Node"+current.data+"  是那边啊 "+str);
            infixOrderDemo("左",current.leftChild);
          //  System.out.print(current.data + " ");
            infixOrderDemo("右",current.rightChild);
        }
    }
    /**
     * 1、根节点50，查找左节点，找到20，然后找到10，输出10，然后找到25 ，然后30 。到这里输出的结果是 50 10 20 25 30
     * 2、查找根节点的50的右节点，然后找出80，找出80的左节点60.接着80，查找80的右节点95，输出85 然后 90 ，最后输出100
     * 3、最终的输出的结果50 10 20 25 30 60 80 85 90 100
     *  @param current
     */
    //前序遍历
    // 前序遍历首先访问根结点然后遍历左子树，最后遍历右子树。
    // 根节点>>左子树>>右子树
    public void preOrder(Node current) {
        if (current != null) {
            System.out.print(current.data + " ");
            infixOrder(current.leftChild);
            infixOrder(current.rightChild);
        }
    }

    /**
     * 1、根节点50，查找左节点20，在继续查找20的左节点10,10没有左右节点，打印10,然后查找到20的右节点30,30继续查找到25，第一次输出为 10 20 25 30
     * 3、最终输出
     * 10 20 25 30 60 80 85 90 100 50
     * @param current
     */
    //后序遍历
    //在二叉树中，先左后右再根，即首先遍历左子树，然后遍历右子树，最后访问根结点。
    public void postOrder(Node current) {
        if (current != null) {
            infixOrder(current.leftChild);
            infixOrder(current.rightChild);
            System.out.print(current.data + " ");
        }
    }

    //找到最大值
    public Node findMax() {
        Node current = root;
        Node maxNode = current;
        while (current != null) {
            maxNode = current;
            current = current.rightChild;
        }
        return maxNode;
    }

    //找到最小值
    public Node findMin() {
        Node current = root;
        Node minNode = current;
        while (current != null) {
            minNode = current;
            current = current.leftChild;
        }
        return minNode;
    }

    @Override
    public boolean delete(int key) {
        Node current = root;
        Node parent = root;
        boolean isLeftChild = false;
        //查找删除值，找不到直接返回false
        while (current.data != key) {
            parent = current;
            if (current.data > key) {
                isLeftChild = true;
                current = current.leftChild;
            } else {
                isLeftChild = false;
                current = current.rightChild;
            }
            if (current == null) {
                return false;
            }
        }
        //如果当前节点没有子节点
        if (current.leftChild == null && current.rightChild == null) {
            if (current == root) {
                root = null;
            } else if (isLeftChild) {
                parent.leftChild = null;
            } else {
                parent.rightChild = null;
            }
            return true;

            //当前节点有一个子节点，右子节点
        } else if (current.leftChild == null && current.rightChild != null) {
            if (current == root) {
                root = current.rightChild;
            } else if (isLeftChild) {
                parent.leftChild = current.rightChild;
            } else {
                parent.rightChild = current.rightChild;
            }
            return true;
            //当前节点有一个子节点，左子节点
        } else if (current.leftChild != null && current.rightChild == null) {
            if (current == root) {
                root = current.leftChild;
            } else if (isLeftChild) {
                parent.leftChild = current.leftChild;
            } else {
                parent.rightChild = current.leftChild;
            }
            return true;
        } else {
            //当前节点存在两个子节点
            Node successor = getSuccessor(current);
            if (current == root) {
                successor = root;
            } else if (isLeftChild) {
                parent.leftChild = successor;
            } else {
                parent.rightChild = successor;
            }
            successor.leftChild = current.leftChild;
        }
        return false;

    }

    public Node getSuccessor(Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild;
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.leftChild;
        }
        //后继节点不是删除节点的右子节点，将后继节点替换删除节点
        if (successor != delNode.rightChild) {
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }
}