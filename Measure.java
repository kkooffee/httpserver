import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Measure {
    //private String name;
    private Map<String,Double> links;
    public Map<String, Double> getLinks() {
        return links;
    }
    Measure(){
        //this.name=name;
        links=new HashMap<String, Double>();
    }

    public Measure setLink(String key, double value){
        links.put(key,value);
        return this;
    }

    public Double getValue(String key, Set<String> offSet,Map<String,Measure> measureMap){
        offSet.add(key);
        if(links.containsKey(key))return links.get(key);
        for (var link:links.keySet()) {
            if(!offSet.contains(link)){
                var measure=measureMap.get(link);
                var result=links.get(link);
                var get=measure.getValue(key,offSet,measureMap);
                if(get==0)continue;;
                return result*get;//measure.getValue(key,offSet,measureMap);
            }
        }
        return 0.0;
    }
}
