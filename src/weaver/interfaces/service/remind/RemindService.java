package weaver.interfaces.service.remind;

public interface RemindService {

	public String remind(String loginname, String type);
	
	public String exists(String loginname, String type);
}
