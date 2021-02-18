

import Logic.Model;
import Logic.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/update")
public class ServletUpdate extends HttpServlet {
	Model model = Model.getInstance();
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("UPDATE");
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
		String name = jObj.get("name").getAsString();
		String surname = jObj.get("surname").getAsString();
		double salary = jObj.get("salary").getAsDouble();

		User user = new User(name, surname, salary);

		if (model.getUserById(id)!=null){
			model.add(id, user);
			response.setContentType("application/json;charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.print("Пользователь с id=" + id + " обновлён.");
			pw.print(gson.toJson(model.getUserById(id)));
		}else {
			response.setContentType("application/json;charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.print(gson.toJson("Пользователь не найден!"));
		}
	}
}
