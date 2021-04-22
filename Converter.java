import java.io.FileNotFoundException;
import java.net.URI;
import java.util.*;

public class Converter {
    private MeasureMap measureMap;
    List leftExpression=new ArrayList<String>();
    List rightExpression = new ArrayList<String>();

    public Converter(URI uri) throws FileNotFoundException {
        this.measureMap = new MeasureMap(uri);
    }

    public String executeRequest(Request request) throws NotFoundException {
        Double result=1.0;
        parseRequest(request);

        for (var i:leftExpression){
            result*=findMeasureFor( (String) i);
        }

        return String.valueOf(result);
    }
//построение выражения на основе запроса
    private void parseRequest(Request request){
        leftExpression.clear();
        rightExpression.clear();
        var in=request.from.trim().split("/");
        var out=request.to.trim().split("/");

        if(in.length!=0) leftExpression.addAll(Arrays.asList(in[0].trim().replace('*','`').split("`")));
        if(out.length==2) leftExpression.addAll(Arrays.asList(out[1].trim().replace('*','`').split("`")));
        if(out.length!=0) rightExpression.addAll(Arrays.asList(out[0].trim().replace('*','`').split("`")));
        if(in.length==2) rightExpression.addAll(Arrays.asList(in[1].trim().replace('*','`').split("`")));
    }
    private Double findMeasureFor(String name) throws NotFoundException {
        Double findResult;
        for (var i:rightExpression) {

            findResult=measureMap.runMapping(name.trim(),(String) i);
            if(findResult!=0.0){
                rightExpression.remove(i);
                return findResult;
            }
        }
        throw new NotFoundException();
    }
}
