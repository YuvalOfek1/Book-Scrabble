package SecondMileStone;


public class Dictionary {

    private CacheManager exists;
    private CacheManager notExists;
    private BloomFilter bloomFilter;
    String[] fileNames;

    public Dictionary(String...fileNames){
        this.fileNames = fileNames.clone();
        exists = new CacheManager(400, new LRU());
        notExists = new CacheManager(100, new LFU());
        bloomFilter = new BloomFilter(256, "MD5", "SHA1");
        for(String file : fileNames){
            bloomFilter.add(file);
        }
    }

    public boolean query(String word){
        if(exists.query(word)){
            return true;
        }
        else if(notExists.query(word)){
            return true;
        }
        else if(bloomFilter.contains(word)){
            exists.add(word);
            return true;
        }

        return challenge(word);
    }

    public boolean challenge(String word){
        if(IOSearcher.search(word, fileNames)){
            exists.add(word);
            return true;
        }
        notExists.add(word);
        return false;

    }

}
