// --== CS400 Project One File Header ==--
// Name: <Richie Zhou>
// Email: <Zhou469@wisc.edu>
// Team: <blue>
// Group: <AG>
// Lecturer: <Gary Dahl>
// Notes to Grader: <optional extra notes>

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class implements a basic Binary Search Tree that supports insert and get operations.
 * In addition, the implementation supports in-order traversals of the tree.
 * Each node of the tree stores a key, value pair.
 * 
 * @param <KeyType> The key type for this tree. Instances of this type are used to look up key, value pairs in the tree.
 * @param <ValueType> The value type for this tree. An instance of this type is stored in every node, along with a key.
 */
public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

    // This is the protected array that help store lots of lists
    protected LinkedList<Item<KeyType, ValueType>>[] table;

    /**
     * This is the constructor of Hashtable
     * 
     * @param capacity this is the length of the array to be created
     */
    @SuppressWarnings("unchecked")
    public HashtableMap(int capacity) {
        table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<Item<KeyType, ValueType>>();
        }
    }

    public HashtableMap() { // Default constructor
        this(20);
    }

    /**
     * This is the method that put items in the LinkedList
     * 
     * @param key   the key of the items
     * @param value the value of the items
     * @return true if it successfully put the items in the LinkedList
     */
    @Override
    public boolean put(KeyType key, ValueType value) {
        if (key == null || containsKey(key)) {
            return false;
        } else {
            table[code(key)].add(new Item<KeyType, ValueType>(key, value));
            if ((1.0 * size()) / table.length >= 0.75) {
                grow();
            }
            return true;
        }
    }

    // Private helper method that help the HashTable to grow dynamically
    @SuppressWarnings("unchecked")
    private void grow() { 
        LinkedList<Item<KeyType, ValueType>>[] old = table; // Store the old table
        table = new LinkedList[table.length * 2]; // Create a new table

        for (int t = 0; t < table.length; t++) {
            table[t] = new LinkedList<Item<KeyType, ValueType>>();
        }
        for (int i = 0; i < old.length; i++) { // Loop through the old table
            for (int j = 0; j < old[i].size(); j++) { // Loop through the LinkedList
                put(old[i].get(j).getKey(), old[i].get(j).getValue());
            }
        }
    }

    /**
     * This is the method that get the value of an item that has the given key
     * 
     * @param key this is the key that we are searching by
     * @return the Value of the items that has the given key with ValueType
     * @throws NoSuchElementException if there is no element with the given key in the table
     */
    @Override
    public ValueType get(KeyType key) throws NoSuchElementException {
        int a = code(key);
        for (int i = 0; i < table[a].size(); i++) {
            if (table[a].get(i).getKey().equals(key)) {
                return table[a].get(i).getValue();
            }
        }
        throw new NoSuchElementException("There is no element with that key in the table");
    }

    /**
     * This is the method that get the number of items stored in the table
     * 
     * @return the number of all the items in the table in type int
     */
    @Override
    public int size() {
        int a = 0;
        // use the size() method of LinkedList to calculate the total number of the Hash table
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null)
                a += table[i].size();
        }
        return a;
    }

    /**
     * This is the method that check whether there is an item with the given key
     * 
     * @param key the key to search by, this can help find the item
     * @return true if that item exist false otherwise
     */
    @Override
    public boolean containsKey(KeyType key) {
        boolean found = false;
        // first find the LinkedList that correspond to the hash code the same with that key
        for (int i = 0; i < table[code(key)].size(); i++) {
            // check each item in the list
            if (key.equals(table[code(key)].get(i).getKey())) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * This is the method that help remove the item with the given key and return the value of that
     * item
     * 
     * @param key the key of the to be removed item
     * @return the value of the item that we are going to remove
     */
    @Override
    public ValueType remove(KeyType key) {
        // if the item with the given key doesn't exist catch the exception throw by get() and
        // return null
        try {
            ValueType temp = get(key);
            // search the LinkedList correspond to the key
            for (int i = 0; i < table[code(key)].size(); i++) {
                if (key.equals(table[code(key)].get(i).getKey())) {
                    table[code(key)].remove(i);
                    break;
                }
            }
            return temp;
        } catch (NoSuchElementException nsee) {
            return null;
        }
    }

    @Override
    public void clear() { // Clear the table
        for (int i = 0; i < table.length; i++) { // Loop through the table
            table[i].clear();
        }
    }

    /**
     * This helper method help calculate the code of key
     * 
     * @param key the key of the item
     * @return a code
     */
    private int code(KeyType key) {
        return Math.abs(key.hashCode()) % table.length;
    }
}