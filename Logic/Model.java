package Logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Model implements Serializable {

	private static final Model instance = new Model();
	
	private final Map<Integer, User> model;
	
	public static Model getInstance() {
		return instance;
	}
	
	private Model() {
		model = new HashMap<Integer, User>();
	}
	
	public void add(int id, User user) {
		model.put(id, user);
	}
	
	public Map<Integer, User> getList(){
		return model;
	}

	public User getUserById(int id){
		return model.get(id);
	}

	public void deleteUserById(int id){

	}
}
