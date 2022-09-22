// --== CS400 File Header Information ==--
// Name: <Richie Zhou>
// Email: 
// Team: <AG>
// TA: <Karan>
// Lecturer: <Gary Dahl>

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Stack;

public class RedBlackTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

    protected static class Node<T> {
        public boolean isBlack = false;
        public T data;
        public Node<T> parent; // null for root node
        public Node<T> leftChild;
        public Node<T> rightChild;
        public Node(T data) { this.data = data; }
        public boolean isLeftChild() {
            return parent != null && parent.leftChild == this;
        }

        @Override
        public String toString() {
            String output = "[";
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this);
            while(!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if(next.leftChild != null) q.add(next.leftChild);
                if(next.rightChild != null) q.add(next.rightChild);
                output += next.data.toString();
                if(!q.isEmpty()) output += ", ";
            }
            return output + "]";
        }
    }

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree


    @Override
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if(data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
        if(root == null) { root = newNode; size++; root.isBlack=true;
        } // add first node to an empty tree
        else{
            boolean returnValue = insertHelper(newNode,root); // recursively insert into subtree
            if (returnValue) {
                size++;
                root.isBlack=true;
            }
            else throw new IllegalArgumentException("This RedBlackTree already contains that value.");
        }
        return true;
    }

    private void enforceRBTreePropertiesAfterInsert(Node<T> cur) {
        if(!cur.parent.isBlack) {// if there is a collision
            if(cur.parent.parent!=null&&cur.parent.isLeftChild()&&cur.parent.parent.rightChild==null) {
                if(cur.isLeftChild()) {
                    rotate(cur.parent,cur.parent.parent);
                    cur.parent.isBlack=true;
                    cur.parent.rightChild.isBlack=false;
                }else {
                    rotate(cur,cur.parent);
                    rotate(cur,cur.parent);
                    cur.isBlack=true;
                    cur.rightChild.isBlack=false;
                }//case 2, uncle is null
            }else if(cur.parent.parent!=null&&!cur.parent.isLeftChild()&&cur.parent.parent.leftChild==null) {
                if(!cur.isLeftChild()) {
                    rotate(cur.parent,cur.parent.parent);
                    cur.parent.isBlack=true;
                    cur.parent.leftChild.isBlack=false;
                }else {
                    rotate(cur,cur.parent);
                    rotate(cur,cur.parent);
                    cur.isBlack=true;
                    cur.leftChild.isBlack=false;
                }
            }else if(cur.parent.parent!=null&&cur.parent.isLeftChild()&&cur.parent.parent.rightChild.isBlack) {
                if(cur.isLeftChild()) {
                    rotate(cur.parent,cur.parent.parent);
                    cur.parent.isBlack=true;
                    cur.parent.rightChild.isBlack=false;
                }else {
                    rotate(cur,cur.parent);
                    rotate(cur,cur.parent);
                    cur.isBlack=true;
                    cur.rightChild.isBlack=false;
                }//case 3 , uncle is black
            }else if(cur.parent.parent!=null&&!cur.parent.isLeftChild()&&cur.parent.parent.leftChild.isBlack){
                if(!cur.isLeftChild()) {
                    rotate(cur.parent,cur.parent.parent);
                    cur.parent.isBlack=true;
                    cur.parent.leftChild.isBlack=false;
                }else {
                    rotate(cur,cur.parent);
                    rotate(cur,cur.parent);
                    cur.isBlack=true;
                    cur.leftChild.isBlack=false;
                }
            }else {
                assert cur.parent.parent != null;
                if(!cur.parent.parent.leftChild.isBlack&&!cur.parent.parent.rightChild.isBlack){
                    cur.parent.parent.isBlack=false;
                    cur.parent.parent.leftChild.isBlack=true;
                    cur.parent.parent.rightChild.isBlack=true;
                    if(!cur.parent.parent.equals(root)) {
                        enforceRBTreePropertiesAfterInsert(cur.parent.parent);//recursive calling
                    }	//case 4 uncle is red
                }
            }
        }
    }

    private boolean insertHelper(Node<T> newNode, Node<T> subtree) {
        int compare = newNode.data.compareTo(subtree.data);
        // do not allow duplicate values to be stored within this tree
        if(compare == 0) return false;

            // store newNode within left subtree of subtree
        else if(compare < 0) {
            if(subtree.leftChild == null) { // left subtree empty, add here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
                // Otherwise, continue recursive search for location to insert
            } else return insertHelper(newNode, subtree.leftChild);
        }

        // store newNode within the right subtree of subtree
        else {
            if(subtree.rightChild == null) { // right subtree empty, add here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
                // Otherwise, continue recursive search for location to insert
            } else return insertHelper(newNode, subtree.rightChild);
        }
    }

    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        Node<T> temple;
        if(parent.equals(child.parent)&&child.equals(parent.leftChild)) {
            if(parent.parent==null) {
                child.parent=null;
                temple=child.rightChild;
                child.rightChild=parent;
                parent.leftChild=temple;
                parent.parent=child;
                root=child;
            }else if(parent.isLeftChild()) {
                parent.parent.leftChild=child;
                child.parent=parent.parent;
                temple=child.rightChild;
                child.rightChild=parent;
                parent.parent=child;
                parent.leftChild=temple;
            }else {
                parent.parent.rightChild=child;
                child.parent=parent.parent;
                temple=child.rightChild;
                child.rightChild=parent;
                parent.parent=child;
                parent.leftChild=temple;
            }
        }else if(child.parent.equals(parent)&&parent.rightChild.equals(child)) {
            if(parent.parent==null) {
                child.parent=null;
                temple=child.leftChild;
                child.leftChild=parent;
                parent.parent=child;
                parent.rightChild=temple;
                root=child;
            }else if(parent.isLeftChild()) {
                parent.parent.leftChild=child;
                child.parent=parent.parent;
                temple=child.leftChild;
                child.leftChild=parent;
                parent.parent=child;
                parent.rightChild=temple;
            }else {
                parent.parent.rightChild=child;
                child.parent=parent.parent;
                temple=child.leftChild;
                child.leftChild=parent;
                parent.parent=child;
                parent.rightChild=temple;
            }
        }else {
            throw new IllegalArgumentException("two given node have no relationship");
        }

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(T data) {
        // null references will not be stored within this tree
        if(data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");
        return this.containsHelper(data, root);
    }

    private boolean containsHelper(T data, Node<T> subtree) {
        if (subtree == null) {
            // we are at a null child, value is not in tree
            return false;
        } else {
            int compare = data.compareTo(subtree.data);
            if (compare < 0) {
                // go left in the tree
                return containsHelper(data, subtree.leftChild);
            } else if (compare > 0) {
                // go right in the tree
                return containsHelper(data, subtree.rightChild);
            } else {
                // we found it :)
                return true;
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        // use an anonymous class here that implements the Iterator interface
        // we create a new on-off object of this class everytime the iterator
        // method is called
        return new Iterator<>() {
            // a stack and current reference store the progress of the traversal
            // so that we can return one value at a time with the Iterator
            Stack<Node<T>> stack = null;
            Node<T> current = root;

            public T next() {
                // if stack == null, we need to initialize the stack and current element
                if (stack == null) {
                    stack = new Stack<>();
                    current = root;
                }
                // go left as far as possible in the sub tree we are in until we hit a null
                // leaf (current is null), pushing all the nodes we fund on our way onto the
                // stack to process later
                while (current != null) {
                    stack.push(current);
                    current = current.leftChild;
                }
                // as long as the stack is not empty, we haven't finished the traversal yet;
                // take the next element from the stack and return it, then start to step down
                // its right subtree (set its right sub tree to current)
                if (!stack.isEmpty()) {
                    Node<T> processedNode = stack.pop();
                    current = processedNode.rightChild;
                    return processedNode.data;
                } else {
                    // if the stack is empty, we are done with our traversal
                    throw new NoSuchElementException("There are no more elements in the tree");
                }

            }

            public boolean hasNext() {
                // return true if we either still have a current reference, or the stack
                // is not empty yet
                return !(current == null && (stack == null || stack.isEmpty()));
            }

        };
    }

    @Override
    public String toString() {
        // use the inorder Iterator that we get by calling the iterator method above
        // to generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        Iterator<T> treeNodeIterator = this.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        if (treeNodeIterator.hasNext())
            sb.append(treeNodeIterator.next());
        while (treeNodeIterator.hasNext()) {
            T data = treeNodeIterator.next();
            sb.append(", ");
            sb.append(data.toString());
        }
        sb.append(" ]");
        return sb.toString();
    }
}
