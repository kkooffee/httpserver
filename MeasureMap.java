import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class MeasureMap {
    private Map<String,Measure> measureMap;

    public MeasureMap(URI uri) throws FileNotFoundException {
        this.measureMap = buildMapFromFile(uri);
    }
//поиск пропорции между единицами измерений
    public Double runMapping(String left,String right){
        var measure = measureMap.get(left);
        return measure.getValue(right.trim(),new HashSet<String>(),measureMap);
    }
//получение отображения всех валидных записей из файла
    private Map<String,Measure> buildMapFromFile(URI uri) throws FileNotFoundException {
        var map =new HashMap<String,Measure>();
        var scanner= new Scanner(new FileInputStream(uri.getPath()));
        while (scanner.hasNext()){
            var mass=scanner.nextLine().split(",");
            if(mass.length!=3)continue;
            var s_name=mass[0];
            var t_name=mass[1];
            var value=Double.parseDouble(mass[2]);

            if(!map.containsKey(s_name))map.put(s_name, new Measure().setLink(s_name,1.0));
            map.get(s_name).setLink(t_name,value);

            if(!map.containsKey(t_name)) map.put(t_name, new Measure().setLink(t_name,1.0));
            map.get(t_name).setLink(s_name,1/value) ;
        }
        return map;
    }
}
