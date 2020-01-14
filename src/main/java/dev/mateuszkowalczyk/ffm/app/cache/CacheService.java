package dev.mateuszkowalczyk.ffm.app.cache;

public class CacheService {
    private static final CacheService instance = new CacheService();

    private CacheService () {
       this.check();
    }

    public static CacheService getInstance() {
        return instance;
    }

    public void check() {
        CacheStructureChecker cacheStructureChecker = new CacheStructureChecker();
        cacheStructureChecker.check();
    }
}
