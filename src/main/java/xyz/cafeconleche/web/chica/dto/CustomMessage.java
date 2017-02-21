package xyz.cafeconleche.web.chica.dto;

public class CustomMessage {

	private int id;
	private String name;
	
	public CustomMessage() {
	}
	
	public CustomMessage(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
    public String toString() {
        return "CustomMessage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
	
}
