package weaver.interfaces.service.workflow;

import java.util.ArrayList;
import java.util.List;

public class WorkflowDataMode {

	private String code;
	private String msg;
	private String count;
	private List<WorkflowDataMode_Item> data = new ArrayList<WorkflowDataMode_Item>();
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
	public List<WorkflowDataMode_Item> getData() {
		return data;
	}
	public void setData(List<WorkflowDataMode_Item> data) {
		this.data = data;
	}
}
