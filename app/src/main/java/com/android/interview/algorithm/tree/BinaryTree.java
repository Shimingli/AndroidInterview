package com.android.interview.algorithm.tree;

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

    //中序遍历   左子树 ---》根节点----》 右子树
    public void infixOrder(Node current) {
        if (current != null) {
            infixOrder(current.leftChild);
            System.out.print(current.data + " ");
            infixOrder(current.rightChild);
        }
    }

    //前序遍历
    public void preOrder(Node current) {
        if (current != null) {
            System.out.print(current.data + " ");
            infixOrder(current.leftChild);
            infixOrder(current.rightChild);
        }
    }

    //后序遍历
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