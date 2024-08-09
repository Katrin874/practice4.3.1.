import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinearProbing<K, V>{
    private static final int INIT_CAPACITY = 16;
    private double loadFactor = 0.5;
    private int threshold;
    private int countOfItems;
    private int capacity;
    private K[] keys;
    private V[] values;


    public LinearProbing(int capacity) {
        this.capacity = capacity;
        this.threshold = (int)(capacity * loadFactor);
        this.keys = (K[]) new Object[capacity];
        this.values = (V[])new Object[capacity];
    }
    public int size(){
        return countOfItems;
    }
    public boolean isEmpty(){
        return size() == 0;
    }
    public boolean contains(K key){
        return get(key) != null;
    }
    public int hash(K key){
        return key.hashCode() < 0? key.hashCode() * -1 % capacity : key.hashCode() % capacity;
    }
    public void put(K key, V value){
        if (key == null) throw new IllegalArgumentException();
        if(countOfItems >= threshold){
            resize(2 * capacity);
        }
        int index = getBucketIndex(key);
        if(keys[index] != null){
            values[index] = value;
            return;
        }
        keys[index] = key;
        values[index] = value;
        countOfItems++;

    }
    public V get(K key){
        if(key == null) throw new IllegalArgumentException();
        int index = getBucketIndex(key);
        if (keys[index] != null){
            return  values[index];
        }
        return null;
    }
    private int getBucketIndex(K key){
        int index = hash(key);
        while (keys[index] != null){
            if (keys[index].equals(key)){
                return index;
            }
            index = (index + 1) % capacity;
        }
        return index;
    }
    private void resize(int newCapacity){
      LinearProbing<K,V> temp = new LinearProbing<>(newCapacity);
      for(int i =0; i < capacity; i++){
          if(keys[i] != null){
              temp.put(keys[i], values[i]);
          }
      }
      keys = temp.keys;
      values = temp.values;
      capacity = temp.capacity;
      threshold = (int)(capacity * loadFactor);
    }
    public void delete(K key){
        if(key == null) throw new IllegalArgumentException();
        if (!contains(key)) return;
        int index = getBucketIndex(key);
        while (!key.equals(keys[index])){
            index = (index + 1) % capacity;
        }
        keys[index] = null;
        values[index] = null;
        countOfItems--;
        if (countOfItems > 0 && countOfItems <= threshold / 4){
            resize(capacity / 2);
        }
    }
    public Iterable<K> keys() {
        return new Iterable<K>() {
            @Override
            public Iterator<K> iterator() {
                return new Iterator<K>() {
                    private int currentIndex = 0;

                    @Override
                    public boolean hasNext() {
                        while (currentIndex < capacity && keys[currentIndex] == null) {
                            currentIndex++;
                        }
                        return currentIndex < capacity;
                    }

                    @Override
                    public K next() {
                        if (!hasNext()) throw new NoSuchElementException();
                        return keys[currentIndex++];
                    }
                };
            }
        };
    }

}
