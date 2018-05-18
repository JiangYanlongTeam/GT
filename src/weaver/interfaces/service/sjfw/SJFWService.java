package weaver.interfaces.service.sjfw;

public interface SJFWService {

	/**
	 * 市局发文（局内发文传阅）
	 * 
	 * @param num
	 * @param loginname
	 * @return
	 */
	public String sjfw(int num, String loginname);
	
	/**
	 * 市局发文（局内发文传阅）点击更多
	 * 
	 * @param page
	 * @param limit
	 * @param handle
	 * @param type
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param loginname
	 * @return
	 */
	public String sfjwMore(int page, int limit, String handle, String type, String name, String startDate, String endDate, String loginname);
}
