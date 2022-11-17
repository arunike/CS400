// --== CS400 Project One File Header ==--
// Name: <Chris Cai>
// CSL Username: <ccai>
// Email: <lcai54@wisc.edu>
// Lecture #: <002 @1:00pm>
// Notes to Grader: <any optional extra notes to your grader>
/**
 * This is my helper class that manage the behavior of Item
 */
public class Item<KeyType, ValueType> {
    private KeyType key;
    private ValueType value;

    /**
     * This is the constructor of the Item
     * 
     * @param key   the key of the Item
     * @param value the value of the Item
     */
    public Item(KeyType key, ValueType value) {
        this.key = key;
        this.value = value;
    }

    /**
     * get the key of the item
     * 
     * @return the key of the item
     */
    public KeyType getKey() {
        return this.key;
    }

    /**
     * get the value of the item
     * 
     * @return the value of the item
     */
    public ValueType getValue() {
        return this.value;
    }
}
