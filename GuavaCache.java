import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class GuavaCache<K, V> implements Cache<K, V> {

    private static final long MAX_SIZE = 100;
    private long cacheTTL = MAX_SIZE;
    private final LoadingCache<K, V> cache;

    public GuavaCache() {
        cache = CacheBuilder.newBuilder().maximumSize(MAX_SIZE)
                .expireAfterWrite(cacheTTL, TimeUnit.HOURS)
                .build(new CacheLoader<K, V>() {
                    @Override
                    public V load(K key) throws Exception {
                        return createValue(key);
                    }
                });
    }

    @SuppressWarnings("unchecked")
    private V createValue(K key) {
        // This is where we need to put the logic to generate the value, either
        // by creating or by calling any service

        return (V) key.toString().toUpperCase();
    }

    @Override
    public V get(K key) {
        return cache.getUnchecked(key);
    }

    @Override
    public void setCacheTTL(long cacheTTL) {
        this.cacheTTL = cacheTTL;
    }
}