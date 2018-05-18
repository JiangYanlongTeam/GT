package weaver.interfaces.service.sjfw;

import java.util.ArrayList;
import java.util.List;

public class SJFWMoreModes {

	private String code = "0";
	private String msg ;
	private String count;
	private List<SJFWMoreMode> data = new ArrayList<SJFWMoreMode>();
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
	public List<SJFWMoreMode> getData() {
		return data;
	}
	public void setData(List<SJFWMoreMode> data) {
		this.data = data;
	}
}
