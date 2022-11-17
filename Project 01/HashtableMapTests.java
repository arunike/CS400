// --== CS400 Project One File Header ==--
// Name: <Richie Zhou>
// Email: <Zhou469@wisc.edu>
// Team: <blue>
// Group: <AG>
// Lecturer: <Gary Dahl>
// Notes to Grader: <optional extra notes>

import java.util.NoSuchElementException;

public class HashtableMapTests {

    public static boolean test1() {
        HashtableMap map = new HashtableMap();
        map.put(1, "aaa");
        map.put(2, "bbb");
        map.put(3, "ccc");
        map.put(4, "ddd");

        if(map.put(3, "eee"))
            return false;
        if(!map.containsKey(1))
            return false;
        if(!map.containsKey(3))
            return false;
        if(map.containsKey(5))
            return false;
        if(!map.get(2).equals("bbb"))
            return false;
        try {
            map.get(5);
            return false;
        }catch(NoSuchElementException e) {
            //
        }
        return true;
    }

    public static boolean test2() {

        HashtableMap map = new HashtableMap(8);
        map.put(1, "aaa");
        map.put(2, "bbb");
        map.put(3, "ccc");
        map.put(4, "ddd");
        map.put(11, "AAA");
        map.put(12, "BBB");

        if(map.put(3, "eee"))
            return false;
        if(!map.containsKey(1))
            return false;
        if(!map.containsKey(11))
            return false;
        if(map.containsKey(5))
            return false;
        if(!map.get(12).equals("BBB"))
            return false;

        try {
            map.get(5);
            return false;
        }catch(NoSuchElementException e) {
            //
        }
        return true;
    }

    public static boolean test3() {

        HashtableMap map = new HashtableMap();
        map.put(1, "aaa");
        map.put(2, "bbb");
        map.put(3, "ccc");
        map.put(4, "ddd");
        map.put(11, "AAA");
        map.put(12, "BBB");

        if(!map.containsKey(1))
            return false;
        if(!map.containsKey(12))
            return false;
        if(!map.remove(1).equals("aaa"))
            return false;
        if(!map.remove(12).equals("BBB"))
            return false;
        if(map.remove(5)!=null)
            return false;
        if(map.containsKey(1))
            return false;
        if(map.containsKey(12))
            return false;

        try {
            map.get(1);
            return false;
        }catch(NoSuchElementException e) {
            //
        }
        return true;
    }

    public static boolean test4() {

        HashtableMap map = new HashtableMap();
        map.put(1, "aaa");
        map.put(2, "bbb");
        map.put(3, "ccc");
        map.put(4, "ddd");
        map.put(11, "AAA");
        map.put(12, "BBB");

        if(map.size()!=6)
            return false;

        map.remove(1);
        map.remove(12);

        if(map.size()!=4)
            return false;
        map.remove(5);
        if(map.size()!=4)
            return false;
        map.clear();
        if(map.size()!=0)
            return false;
        if(map.containsKey(2))
            return false;
            return true;
    }

    public static boolean test5() {

        HashtableMap map = new HashtableMap(10);
        map.put(1, "aaa");
        map.put(2, "bbb");
        map.put(3, "ccc");
        map.put(4, "ddd");

        if(!map.containsKey(1))
            return false;
        if(!map.remove(1).equals("aaa"))
            return false;
        if(map.containsKey(1))
            return false;
        if(!map.put(1,"aaa"))
            return false;
        if(!map.containsKey(1))
            return false;
            return true;
    }

    public static void main(String[] arg) {

        System.out.println("Test1: " + test1());
        System.out.println("Test2: " + test2());
        System.out.println("Test3: " + test3());
        System.out.println("Test4: " + test4());
        System.out.println("Test5: " + test5());
    }

}
