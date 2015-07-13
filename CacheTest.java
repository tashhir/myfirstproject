public class CacheTest {

    public static void main(String[] args) {
        Cache<String, String> cache = new GuavaCache<String, String>();
        System.out.println(cache.get("Tashhir"));
        System.out.println(cache.get("Khan"));
        System.out.println(cache.get("Tashhir"));
    }
}
