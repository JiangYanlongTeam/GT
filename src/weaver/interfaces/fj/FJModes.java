package weaver.interfaces.fj;

import java.util.ArrayList;
import java.util.List;

public class FJModes {

	private String code = "0";
	private String msg;
	private String count;
	private List<FJMode> data = new ArrayList<FJMode>();
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
	public List<FJMode> getData() {
		return data;
	}
	public void setData(List<FJMode> data) {
		this.data = data;
	}
}
