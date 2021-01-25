package Tree;

import java.util.List;

/**
 * 树 tree：是一些节点的集合
 * 如果集合不是空集，则树由跟节点以及0个到多个非空子树组成
 *
 * 树有n个节点以及n-1条边
 * 没有子节点的节点称为树叶
 *
 * @author asus
 */
public class Node {

    private Object element;

    private Node firstChild;

    private Node nex;

}
