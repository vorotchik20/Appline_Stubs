

import Logic.Model;
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

@WebServlet(urlPatterns = "/delete")
public class ServletDelete extends HttpServlet {
	Model model = Model.getInstance();
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	protected void doDelete (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("DELETE");
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
		if (model.getUserById(id)!=null){
			model.getList().remove(id);
			response.setContentType("application/json;charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.print("Пользователь с id=" + id + " удалён.");
			pw.print(gson.toJson(model.getList()));
		}else {
			response.setContentType("application/json;charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.print(gson.toJson("Пользователь не найден!"));
		}
	}
}
