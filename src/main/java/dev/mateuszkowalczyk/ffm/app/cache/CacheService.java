package dev.mateuszkowalczyk.ffm.app.cache;

public class CacheService {
    private static final CacheService instance = new CacheService();

    private CacheService () {
        CacheStructureChecker cacheStructureChecker = new CacheStructureChecker();
        cacheStructureChecker.check();
    }

    public static CacheService getInstance() {
        return instance;
    }
}
