// --== CS400 File Header Information ==--
// Name: <Richie Zhou>
// Team: <AG>
// TA: <Karan>
// Lecturer: <Gary Dahl>
// Notes to Grader: <optional extra notes>

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Red-Black Tree implementation with a Node inner class for representing
 * the nodes of the tree. Currently, this implements a Binary Search Tree that
 * we will turn into a red black tree by modifying the insert functionality.
 * In this activity, we will start with implementing rotations for the binary
 * search tree insert algorithm. You can use this class' insert method to build
 * a regular binary search tree, and its toString method to display a level-order
 * traversal of the tree.
 */
public class RedBlackTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

    /**
     * This class represents a node holding a single value within a binary tree
     * the parent, left, and right child references are always maintained.
     */
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
        public String toString() { // Helper method for toString
            String output = "["; // Display this node's data
            LinkedList<Node<T>> q = new LinkedList<>(); // For level-order
            q.add(this); // Start with this node
            while(!q.isEmpty()) { // Traverse each level
                Node<T> next = q.removeFirst();

                if(next.leftChild != null) { // Add left child to queue
                    q.add(next.leftChild); 
                }
                if(next.rightChild != null) { // Add right child to queue
                    q.add(next.rightChild);
                }

                output += next.data.toString(); // Append node data to output

                if(!q.isEmpty()) { // Add a comma if this is not the last node
                    output += ", "; 
                }
            }

            return output + "]";
        }
    }

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree

    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree. After  
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when the newNode and subtree contain
     *      equal data references
     */
    @Override
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if(data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
        if(root == null) { root = newNode; size++; root.isBlack=true; } // add first node to an empty tree
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

    /**
     * Recursive helper method to find the subtree with a null reference in the
     * position that the newNode should be inserted, and then extend this tree
     * by the newNode in that position.
     * @param newNode is the new node that is being added to this tree
     * @param subtree is the reference to a node within this tree which the 
     *      newNode should be inserted as a descenedent beneath
     * @return true is the value was inserted in subtree, false if not
     */
    private void enforceRBTreePropertiesAfterInsert(Node<T> cur) {
        if(!cur.parent.isBlack) { // If parent is red
            if(cur.parent.parent != null && cur.parent.isLeftChild() && cur.parent.parent.rightChild == null) { // If parent is left child and uncle is null
                if(cur.isLeftChild()) { // If cur is left child
                    rotate(cur.parent, cur.parent.parent);
                    cur.parent.isBlack = true;
                    cur.parent.rightChild.isBlack = false;
                } else { // if parent is right child
                    rotate(cur, cur.parent);
                    rotate(cur, cur.parent);
                    cur.isBlack = true;
                    cur.rightChild.isBlack = false;
                }
            } else if(cur.parent.parent != null && !cur.parent.isLeftChild() && cur.parent.parent.leftChild == null) { // If parent is right child and uncle is null
                if(!cur.isLeftChild()) { // If cur is right child
                    rotate(cur.parent, cur.parent.parent);
                    cur.parent.isBlack = true;
                    cur.parent.leftChild.isBlack = false;
                } else { // If cur is left child
                    rotate(cur, cur.parent);
                    rotate(cur, cur.parent);
                    cur.isBlack = true;
                    cur.leftChild.isBlack = false;
                }
            } else if(cur.parent.parent != null && cur.parent.isLeftChild() && cur.parent.parent.rightChild.isBlack) { // If parent is left child and uncle is black
                if(cur.isLeftChild()) { // If cur is left child
                    rotate(cur.parent, cur.parent.parent);
                    cur.parent.isBlack = true;
                    cur.parent.rightChild.isBlack = false;
                } else { // If cur is right child
                    rotate(cur, cur.parent);
                    rotate(cur, cur.parent);
                    cur.isBlack = true;
                    cur.rightChild.isBlack = false;
                }
            } else if(cur.parent.parent != null && !cur.parent.isLeftChild() && cur.parent.parent.leftChild.isBlack) { // If parent is right child and uncle is black
                if(!cur.isLeftChild()) { // If cur is right child
                    rotate(cur.parent, cur.parent.parent);
                    cur.parent.isBlack = true;
                    cur.parent.leftChild.isBlack = false;
                } else { // If cur is left child
                    rotate(cur,cur.parent);
                    rotate(cur,cur.parent);
                    cur.isBlack=true;
                    cur.leftChild.isBlack=false;
                }
            } else {
                assert cur.parent.parent != null;
                if(!cur.parent.parent.leftChild.isBlack && !cur.parent.parent.rightChild.isBlack) { // If parent is red and uncle is red
                    cur.parent.parent.isBlack = false;
                    cur.parent.parent.leftChild.isBlack = true;
                    cur.parent.parent.rightChild.isBlack = true;

                    if(!cur.parent.parent.equals(root)) { // If parent is not root
                        enforceRBTreePropertiesAfterInsert(cur.parent.parent);
                    }
                }
            }
        }
    }

    private boolean insertHelper(Node<T> newNode, Node<T> subtree) { // Recursive helper method to find the subtree with a null reference in the position that the newNode should be inserted, and then extend this tree by the newNode in that position.
        int compare = newNode.data.compareTo(subtree.data); // Compare the data within the newNode and the subtree root
        if(compare == 0) { // newNode and subtree contain equal data references
            return false;
        }
        else if(compare < 0) { // newNode should be inserted in the left subtree of subtree
            if(subtree.leftChild == null) { // Left subtree empty, add here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
            } else { // Continue recursive search for location to insert
                return insertHelper(newNode, subtree.leftChild);
            }
        } else { // newNode should be inserted in the right subtree of subtree
            if(subtree.rightChild == null) { // Right subtree empty, add here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);

                return true;
            } else { // Continue recursive search for location to insert
                return insertHelper(newNode, subtree.rightChild);
            }
        }
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * rightChild of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        Node<T> temple; // Temporary node reference used during rotation process
        if(parent.equals(child.parent) && child.equals(parent.leftChild)) { // If child is left child of parent
            if(parent.parent == null) { // If parent is root
                child.parent = null;
                temple = child.rightChild;
                child.rightChild = parent;
                parent.leftChild = temple;
                parent.parent = child;
                root = child;
            } else if(parent.isLeftChild()) { // If parent is left child
                parent.parent.leftChild = child;
                child.parent = parent.parent;
                temple = child.rightChild;
                child.rightChild = parent;
                parent.parent = child;
                parent.leftChild = temple;
            } else { // If parent is right child
                parent.parent.rightChild = child;
                child.parent = parent.parent;
                temple = child.rightChild;
                child.rightChild = parent;
                parent.parent = child;
                parent.leftChild = temple;
            }
        } else if(child.parent.equals(parent) && parent.rightChild.equals(child)) { // If child is right child of parent
            if(parent.parent == null) { // If parent is root
                child.parent = null;
                temple = child.leftChild;
                child.leftChild = parent;
                parent.parent = child;
                parent.rightChild = temple;
                root = child;
            } else if(parent.isLeftChild()) { // If parent is left child
                parent.parent.leftChild = child;
                child.parent = parent.parent;
                temple = child.leftChild;
                child.leftChild = parent;
                parent.parent = child;
                parent.rightChild = temple;
            } else { // If parent is right child
                parent.parent.rightChild = child;
                child.parent = parent.parent;
                temple = child.leftChild;
                child.leftChild = parent;
                parent.parent = child;
                parent.rightChild = temple;
            }
        } else { // If child and parent are not initially (pre-rotation) related that way
            throw new IllegalArgumentException("two given node have no relationship");
        }
    }

    /**
     * Get the size of the tree (its number of nodes).
     * @return the number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     * @return true of this.size() return 0, false if this.size() > 0
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Checks whether the tree contains the value *data*.
     * @param data the data value to test for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    @Override
    public boolean contains(T data) {
        // null references will not be stored within this tree
        if(data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");
        return this.containsHelper(data, root);
    }

    /**
     * Recursive helper method that recurses through the tree and looks
     * for the value *data*.
     * @param data the data value to look for
     * @param subtree the subtree to search through
     * @return true of the value is in the subtree, false if not
     */
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
