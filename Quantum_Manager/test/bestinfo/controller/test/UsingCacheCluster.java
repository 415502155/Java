package bestinfo.controller.test;

import java.net.URL;
import java.util.List;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class UsingCacheCluster {

    public static void main(String[] args) throws Exception {
        UsingCacheCluster uc = new UsingCacheCluster();

        URL url = uc.getClass().getResource("../../../bootstrap/ehcache.xml");
        if (url == null) {
            System.out.println("null");
            return;
        } else {
            System.out.println(url);
        }
        CacheManager manager = new CacheManager(url);
        Cache cache = manager.getCache("cosmosCache");
        int i = 0;
        int k = 10;
        while (true) {
            Thread.sleep(4500);
            cache.put(new Element("a:" + k, "a:" + k++));
            List<String> list = cache.getKeys();            
            System.out.print(i++ + "\t");
            for (String str : list) {
                System.out.print(str +"-"+cache.get(str).getObjectValue()+ "\t");
            }
            System.out.println();
            if(i > 2){
                break;
            }
        }
        List<String> list = cache.getKeys();   
        int j = 0;
        for (String s : list) {   
            cache.put(new Element("s", j++));
            System.out.println(s+"-"+cache.get(s).getObjectValue());
        }
        
    }

}
