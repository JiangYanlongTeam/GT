package weaver.interfaces.service.workflow;

import java.util.ArrayList;
import java.util.List;

public class TodoWorkflowModes {

	private String code = "0";
	private String msg;
	private String count;
	private List<TodoWorkflowMode> data = new ArrayList<TodoWorkflowMode>();
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<TodoWorkflowMode> getData() {
		return data;
	}
	public void setData(List<TodoWorkflowMode> data) {
		this.data = data;
	}
}
