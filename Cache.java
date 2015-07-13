public interface Cache<K, V> {

    public V get(K key);

    public void setCacheTTL(long cacheTTL);

}
