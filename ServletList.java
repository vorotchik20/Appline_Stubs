

import Logic.Model;
import Logic.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/get")
public class ServletList extends HttpServlet {
	Model model = Model.getInstance();
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET");
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

		int id = jObj.get("id").getAsInt();
		if (id == 0){
			response.setContentType("application/json;charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.print(gson.toJson(model.getList()));
		}else if (id > 0 && id < model.getList().size()){
			response.setContentType("application/json;charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.print(gson.toJson(model.getUserById(id)));
		}else {
			response.setContentType("application/json;charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.print(gson.toJson("Пользователь не найден!"));
		}
	}
}
