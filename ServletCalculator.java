import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/ServletCalculator")
public class ServletCalculator extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        StringBuffer jb = new StringBuffer();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while((line = reader.readLine())!=null) {
                jb.append(line);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        JsonObject jObj = gson.fromJson(String.valueOf(jb), JsonObject.class);
        request.setCharacterEncoding("UTF-8");

        int a = jObj.get("a").getAsInt();
        int b = jObj.get("b").getAsInt();
        String math = jObj.get("math").getAsString();
        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();


        if (math.equals("+")){
            int result = a + b;
            pw.print("{\"result\": " + result + "}");
        }else if (math.equals("-")){
            int result = a - b;
            pw.print("{\"result\": " + result + "}");
        }else if (math.equals("*")){
            int result = a * b;
            pw.print("{\"result\": " + result + "}");
        }else if (math.equals("/")){
            int result = a / b;
            pw.print("{\"result\": " + result + "}");
        }else{
            pw.print("Операция не распознана");
        }

    }
}
