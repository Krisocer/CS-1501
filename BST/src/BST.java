
public class BST<T extends Comparable<T>> implements BST_Inter<T>
{

    private BTNode<T> root = null;


    public void put(T key) {
        BTNode<T> cur = root;
        this.put(cur, key);
    }
    private void put(BTNode<T> cur, T key) {
        BTNode<T> newNode = new BTNode<>(key);
        if (cur == null) {
            root = newNode; //put if root is null
            return;
        }
        if (key.compareTo(cur.getKey()) > 0) { //find the position of newNode in right branch
            if (cur.getRight() != null) {
                cur = cur.getRight();
                this.put(cur, key); //recursion
            } else {
                cur.setRight(newNode); //put the key in the null
            }
        } else if (key.compareTo(cur.getKey()) < 0) { //find the position of newNode in left branch
            if (cur.getLeft() != null) {
                cur = cur.getLeft();
                this.put(cur, key);// recursion
            } else {
                cur.setLeft(newNode); //put the key in the null
            }
        }
    }


    public boolean contains(T key) {
        BTNode<T> cur = root;
        while (cur != null) {
            if (key.compareTo(cur.getKey()) > 0) {
                cur = cur.getRight();
            } else if (key.compareTo(cur.getKey()) < 0) {
                cur = cur.getLeft();
            } else {
                return true;
            }
        }
        return false;
    }

    public void delete(T key) {
        if (!this.contains(key)) {
            System.out.println("key is not in the tree");
            return;
        }
        BTNode<T> cur = root;
        BTNode<T> parent = null;

        while (true) {
            if (key.compareTo(cur.getKey()) > 0) {
                parent = cur;
                cur = cur.getRight();
            } else if (key.compareTo(cur.getKey()) < 0) {
                parent = cur;
                cur = cur.getLeft();
            } else if (key.compareTo(cur.getKey()) == 0) {
                break;
            }
        }

        //first condition: getright and getleft are both null
        if (cur.getRight() == null && cur.getLeft() == null) {
            if (cur == root) {
                root = null;
            } else if (parent.getLeft() == cur) {
                parent.setLeft(null);
            } else if (parent.getRight() == cur) {
                parent.setRight(null);
            }
        }

        //second condition: one of the left and right is null
        else if (cur.getRight() == null || cur.getLeft() == null) {
            if (cur == root && cur.getLeft() != null) {
                root = cur.getLeft();
            } else if (cur == root && cur.getRight() != null) {
                root = cur.getRight();
            } else if (cur != root && cur.getLeft() != null && parent.getLeft() == cur) {
                parent.setLeft(cur.getLeft());
            } else if (cur != root && cur.getRight() != null && parent.getLeft() == cur) {
                parent.setLeft(cur.getRight());
            } else if (cur != root && cur.getLeft() != null && parent.getRight() == cur) {
                parent.setRight(cur.getLeft());
            } else if (cur != root && cur.getRight() != null && parent.getRight() == cur) {
                parent.setRight(cur.getRight());
            }
        }

        //Third condition: both of the left and right is null
        else if (cur.getLeft() != null && cur.getRight() != null) {
            BTNode<T> successor = getSuccessor(cur);
            if (cur == root) {
                root = successor;
            } else if (cur != root && parent.getLeft() == cur) {
                parent.setLeft(successor);
            } else if (cur != root && parent.getRight() == cur) {
                parent.setRight(successor);
            }
        }
    }
    // need to get the successor
    private BTNode<T> getSuccessor(BTNode<T> delNode) {
        BTNode<T> successor = delNode;
        BTNode<T> successorParent = null;
        BTNode<T> current = delNode.getRight();

        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.getLeft();
        }

        //if successor is not the right side of the delnode
        if (successor != delNode.getRight()) {
            successorParent.setLeft(successor.getRight());
            successor.setRight(delNode.getRight());
        }
        successor.setLeft(delNode.getLeft());

        return successor;
    }

    public int height() {
        BTNode<T> cur = root;
        return height(cur);
    }
    private int height(BTNode<T> curr) {
        if(curr == null){
            return 0;
        }
        int lHeight = height(curr.getLeft());
        int rHeight = height(curr.getRight());
        return Math.max(lHeight, rHeight) + 1;
    }


    public boolean isBalanced() {
        BTNode<T> cur = root;
        return isBalanced(cur);
    }
    private boolean isBalanced(BTNode<T> cur) {
        if (cur == null) {
            return true;
        }
        isBalanced(cur.getLeft());
        isBalanced(cur.getRight());
        int lh = height(cur.getLeft());
        int rh = height(cur.getRight());

        int dif = Math.abs(lh - rh);
        return dif <= 1;
    }

    public String inOrderTraversal() {
        BTNode<T> cur = root;
        String res = this.inOrderTraversal(cur);
        return res.substring(0,res.length()-1);
    }
    private String inOrderTraversal(BTNode<T> cur) {
        if (cur == null) {
            return "";
        }
        String s = "";
        s += inOrderTraversal(cur.getLeft());
        s += cur.getKey() + ":";
        s += inOrderTraversal(cur.getRight());
        return s;
    }

    public String serialize() {
        BTNode<T> cur = root;
        String res = this.serialize(cur);
        return "R" + res.substring(1);
    }
    private String serialize(BTNode<T> cur) {
        String s = "";
        if (cur == null) {
            return "";
        }
        if (cur.getLeft() == null && cur.getRight() == null) { //base case: leaf node, return L(key)
            return "L" + "(" + (cur.getKey()) + ")";
        }
        if (cur.getLeft() == null || cur.getRight() == null) { //right or left has just one null
            if (cur.getLeft() == null) { //left is null, print root -> null -> right
                s += "I" + "(" + (cur.getKey()) + ")" + "," + "X(NULL)" + "," + serialize(cur.getRight());
            }
            if (cur.getRight() == null) { //right is null, print root -> left -> null
                s += "I" + "(" + (cur.getKey()) + ")" + "," + serialize(cur.getLeft()) + "," + "X(NULL)";
            }
        }
        if (cur.getLeft() != null && cur.getRight() != null) { //right and left are not null, print root -> left ->right
            s += "I" + "(" + (cur.getKey()) + ")" + "," + serialize(cur.getLeft())+ "," + serialize(cur.getRight());
        }
        return s;
    }

    public BST_Inter reverse(){
        BST<T> copyTree = new BST<>();
        copyTree.root = copy(this.root);
        return copyTree;
    }
    private BTNode<T> copy(BTNode<T> treeRoot) {
        if(treeRoot == null) {
            return null;
        }
        BTNode<T> newNode = new BTNode<>(treeRoot.getKey());
        newNode.setLeft(copy(treeRoot.getRight()));
        newNode.setRight(copy(treeRoot.getLeft()));
        return newNode;
    }

}
