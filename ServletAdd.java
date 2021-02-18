import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import Logic.Model;
import Logic.User;

@WebServlet(urlPatterns = "/add")
public class ServletAdd extends HttpServlet {
	private AtomicInteger counter = new AtomicInteger(0);
	Model model = Model.getInstance();
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ADD");
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

		String name = jObj.get("name").getAsString();
		String surname = jObj.get("surname").getAsString();
		double salary = jObj.get("salary").getAsDouble();

		User user = new User(name, surname, salary);

		model.add(counter.getAndIncrement(), user);

		response.setContentType("application/json;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(gson.toJson(model.getList()));
	}
}
