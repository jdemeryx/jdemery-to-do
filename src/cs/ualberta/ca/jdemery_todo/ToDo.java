package cs.ualberta.ca.jdemery_todo;

public class ToDo {
	private String ToDoMsg;
	private Boolean isChecked;
	private Boolean isSelected;
	
	public ToDo(String msg) {
		super();
		ToDoMsg = msg;
	}
	
	public String getMsg() {
		return ToDoMsg;
	}
	
	public void setMsg(String msg) {
		this.ToDoMsg = msg;
	}
	
	@Override
	public String toString() {
		return ToDoMsg;
	}
	
	public void isChecked(Boolean bool) {
		isChecked = bool;
	}
	
	public void isSelected(Boolean bool) {
		isSelected = bool;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}
	
}
