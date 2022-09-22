// --== CS400 File Header Information ==--
// Name: <Richie Zhou>
// Email: 
// Team: <AG>
// TA: <Karan>
// Lecturer: <Gary Dahl>

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRedBlackTree {

    @Test
    public void testAddingWithoutCollision() {
        try {
            RedBlackTree<Integer> test = new RedBlackTree<Integer>();
            test.insert(50);
            test.insert(30);
            assertEquals("[50, 30]",test.root.toString());
            assertEquals(true,test.root.isBlack);
            assertEquals(false,test.root.leftChild.isBlack);
        }catch(Exception e) {
            throw new IllegalArgumentException("This test have failed! There is an exception!");
        }
    }

    @Test
    public void testCollisionWithSiblingIsNull() {
        try {
            RedBlackTree<Integer> test = new RedBlackTree<Integer>();
            test.insert(50);
            test.insert(40);
            test.insert(30);
            assertEquals("[40, 30, 50]",test.root.toString());
            assertEquals(true,test.root.isBlack);
            assertEquals(false,test.root.leftChild.isBlack);
            assertEquals(false,test.root.rightChild.isBlack);
        }catch(Exception e) {
            throw new IllegalArgumentException("This test have failed! There is an exception!");
        }
    }

    @Test
    public void testCollisionWithSiblingIsBlack() {
        try {
            RedBlackTree<Integer> test = new RedBlackTree<Integer>();
            test.insert(50);
            test.insert(40);
            test.insert(30);
            test.root.rightChild.isBlack=true;
            test.insert(20);
            assertEquals("[30, 20, 40, 50]",test.root.toString());
            assertEquals(true,test.root.isBlack);
            assertEquals(false,test.root.leftChild.isBlack);
            assertEquals(false,test.root.rightChild.isBlack);
            assertEquals(true,test.root.rightChild.rightChild.isBlack);
        }catch(Exception e) {
            throw new IllegalArgumentException("This test have failed! There is an exception!");
        }
    }

    @Test
    public void testCollisionWithSiblingIsRed() {
        try {
            RedBlackTree<Integer> test = new RedBlackTree<Integer>();
            test.insert(50);
            test.insert(40);
            test.insert(30);
            test.insert(20);
            assertEquals("[40, 30, 50, 20]",test.root.toString());
            assertEquals(true,test.root.isBlack);
            assertEquals(true,test.root.leftChild.isBlack);
            assertEquals(true,test.root.rightChild.isBlack);
            assertEquals(false,test.root.leftChild.leftChild.isBlack);
        }catch(Exception e) {
            throw new IllegalArgumentException("This test have failed! There is an exception!");
        }
    }
}
