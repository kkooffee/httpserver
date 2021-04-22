import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


import java.io.Console;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class Handler implements HttpHandler {
    private Converter converter;
    private Gson gson;

    public Handler(String uri) throws FileNotFoundException {
        this.converter =new Converter( URI.create(uri));
        this.gson=new Gson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestParamValue=null;
        try{
            if("POST".equals(exchange.getRequestMethod())) {
                requestParamValue = handlePostRequest(exchange);
            }
            handleResponse(exchange,200,requestParamValue);
        } catch (NotFoundException exception){
            handleResponse(exchange,404,"Not Found");
        } catch (BadRequestException exception){
            handleResponse(exchange,400,"Bad Request");
        }
    }

    private String handlePostRequest(HttpExchange exchange) throws NotFoundException, BadRequestException {
        try{
            var request=gson.fromJson(new String(exchange.getRequestBody().readAllBytes()),Request.class);
            return converter.executeRequest(request);
        } catch (Exception e){
            throw new BadRequestException();
        }
    }

    private void handleResponse(HttpExchange httpExchange, int rCode, String requestParamValue)  throws  IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder.append("")
                .append("")
                .append(requestParamValue)
                .append("")
                .append("");

        // encode HTML content
        String htmlResponse = htmlBuilder.toString();

        // this line is a must
        httpExchange.sendResponseHeaders(rCode, htmlResponse.length());

        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
