// --== CS400 Project One File Header ==--
// Name: <Richie Zhou>
// Email: 
// Team: <blue>
// Group: <AG>
// Lecturer: <Gary Dahl>

import java.util.NoSuchElementException;
import java.util.LinkedList;

public class HashtableMap <KeyType, ValueType> {

    public int capacity;
    public int num = 0;
    int i, j;
    LinkedList<Node>[] map;

    public HashtableMap(int capacity) {

        this.capacity = capacity;
        map = (LinkedList<Node>[]) new LinkedList[capacity];
        for(i=0; i < capacity; i++)
            map[i] = new LinkedList<Node>();
    }

    public HashtableMap() {

        // with default capacity = 20
        capacity = 20;
        map= (LinkedList<Node>[]) new LinkedList[20];
        for(i = 0; i < capacity; i++) {
            map[i] = new LinkedList<>();
        }
    }

    public boolean put(KeyType key, ValueType value) {

        if(key == null) return false; //false if key is null
        if(this.containsKey(key)) return false; // check existence
        Node node = new Node(key,value); // create the Node
        int hashcode = Math.abs(key.hashCode()) % capacity;	//get hashcode
        map[hashcode].addFirst(node); //add the Node into LinkedList
        this.num++; //renew the number of pair
        if((double)num / (double)capacity >= 0.8f) {
            resize(); //check load factor,
        }
        return true;
    }

    public ValueType get(KeyType key) throws NoSuchElementException {

        if (!this.containsKey(key))
            throw new NoSuchElementException("NoSuchElement!");
        //check existence
        int hashcode = Math.abs(key.hashCode()) % capacity; //get hashcode
        int size = map[hashcode].size(); //get size of LinkedList
        for (i = 0; i < size; i++) { //iterate
            if (map[hashcode].get(i).getKey().equals(key))
                return (ValueType) map[hashcode].get(i).getValue();
        }
        return null;
    }

    public int size() {

        return this.num;
    }

    public boolean containsKey(KeyType key) {

        int hashcode=Math.abs(key.hashCode()) % capacity; //get hashcode
        if(map[hashcode].size() ==0)
            return false; //if LinkedList is empty
        int size = map[hashcode].size(); //get size
        for(i = 0;i < size; i++) {//iterate
            if(map[hashcode].get(i).getKey().equals(key))
                return true;
        }
        return false;
    }

    public ValueType remove(KeyType key) {

        int hashcode=Math.abs(key.hashCode()) % capacity; //get hashcode
        if(!this.containsKey(key)) return null; //check existence
        ValueType value = this.get(key);
        int index = 0;
        int size = map[hashcode].size(); //get size
        for(i = 0; i < size; i++) { //iterate
            if(map[hashcode].get(i).getKey().equals(key))
                break;
            index++;
        }
        map[hashcode].remove(index); //remove
        num--; //renew the number
        return value;

    }

    public void clear() {

        num = 0; //renew number
        for(i = 0; i < capacity; i++) {
            map[i].clear(); //renew LinkedList
        }
    }

    public void resize() {

        Node[] list = new Node[num]; //save all Node
        int index = 0;
        for(i = 0; i < capacity; i++) {
            for(j = 0; j < map[i].size(); j++) {
                list[index] = map[i].get(j);
                index++;
            }
        }

        capacity = capacity * 2; //double capacity
        num = 0; //renew number
        map= (LinkedList<Node>[]) new LinkedList[capacity];
        for(i = 0; i < capacity; i++) {
            map[i] = new LinkedList<>();
        }
        //initialize map and LinkedList
        i = 0;
    }
}

class Node <KeyType,ValueType> {

    private KeyType key;
    private ValueType value;

    public Node(KeyType key, ValueType value) {

        this.key=key;
        this.value=value;
    }

    public Node(KeyType key) {

        this.key=key;
    }

    public void setKey(KeyType key) {

        this.key=key;
    }

    public void setValue(ValueType value) {

        this.value=value;
    }

    public KeyType getKey() {

        return this.key;
    }

    public ValueType getValue() {

        return this.value;
    }

    public boolean equals(Node n) {

        return this.getKey().equals(n.getKey());
    }
}
