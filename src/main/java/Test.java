import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class Test {
    private static final HashMap<String, Method> pathMethods = new HashMap<>();

    static class MyHandler implements HttpHandler {
        public static void main(String[] args) throws Exception {
//            Class<Routes> routes = Routes.class;
//            System.out.println(Arrays.toString(routes.getDeclaredMethods()));
//            for (String key : pathMethods.keySet()) {
//                System.out.println(key);
//            }
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            createContext(server);
            server.setExecutor(null); // creates a default executor
            server.start();
        }

        public static void createContext(HttpServer server) {
            for (Method m : Routes.class.getDeclaredMethods()) {
//                System.out.println(m);
//                if (m.isAnnotationPresent(WebRoute.class)) {
//                System.out.println(m.getAnnotation(WebRoute.class));
                    WebRoute webRoute = m.getAnnotation(WebRoute.class);
//                    System.out.println(webRoute.path());
                    pathMethods.put(webRoute.path(), m);
                    server.createContext(webRoute.path(), new MyHandler());
//                }
            }
        }

        @Override
        public void handle(HttpExchange t) throws IOException {
                Method methodToExecute = pathMethods.get(t.getHttpContext().getPath());
//                System.out.println(methodToExecute);
                String response = "";
            try {
                response = (String) methodToExecute.invoke(new Routes());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();

        }
    }
}
