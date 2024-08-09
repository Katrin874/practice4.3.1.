public class Main {
    public static void main(String[] args) {
        LinearProbing<String, Integer> hashTable = new LinearProbing<>(16);
        hashTable.put("Apple", 1);
        hashTable.put("Banana", 2);
        hashTable.put("Orange", 3);
        hashTable.put("Grapes", 4);
        System.out.println("Value for 'Apple': " + hashTable.get("Apple"));
        System.out.println("Contains 'Banana': " + hashTable.contains("Banana"));
        hashTable.delete("Orange");
        System.out.println("Size of hash table: " + hashTable.size());
        System.out.println("Keys and values in the hash table:");
        for (String key : hashTable.keys()) {
            System.out.println("Key: " + key + ", Value: " + hashTable.get(key));
        }
    }
}