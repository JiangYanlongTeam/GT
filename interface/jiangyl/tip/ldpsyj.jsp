<%
	weaver.hrm.User user = new weaver.hrm.HrmUserVarify().getUser (request , response) ;
	weaver.general.BaseBean bean = new weaver.general.BaseBean();
	String IP = bean.getPropValue("SYSIP", "SYSIP");
	String currentDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
	String currentTime = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date());
	String gdtxr = new weaver.general.Util().null2String(request.getParameter("gdtxr"));
	String ssbm = new weaver.general.Util().null2String(request.getParameter("ssbm"));
	String ssbm1 = new weaver.general.Util().null2String(request.getParameter("ssbm1"));
	String ssbm2 = new weaver.general.Util().null2String(request.getParameter("ssbm2"));
	String requestid = new weaver.general.Util().null2String(request.getParameter("requestid"));
	String requestname = new weaver.general.Util().null2String(request.getParameter("reqName"));
	String qzyj = new weaver.general.Util().null2String(request.getParameter("qzyj"));
	if("".equals(qzyj)) {
		out.println("{\"success\":\"插入成功\"}");
		return;
	}
	if("".equals(requestid)) {
		out.println("{\"success\":\"插入成功\"}");
		return;
	}
	weaver.hrm.resource.ResourceComInfo resource = null;
	try {
		resource = new weaver.hrm.resource.ResourceComInfo();
	} catch (java.lang.Exception e) {
		e.printStackTrace();
	}
	weaver.interfaces.action.CreateDCDBCQTXAuto createDCDB = new weaver.interfaces.action.CreateDCDBCQTXAuto();
	String _gdtxrSQL = "select resourceid from HRMROLEMEMBERS where roleid = 281";
	weaver.conn.RecordSet rs = new weaver.conn.RecordSet();
	rs.execute(_gdtxrSQL);
	java.util.List<String> _gdtxrList = new java.util.ArrayList<String>();
	while(rs.next()){
		String _resourceid = new weaver.general.Util().null2String(rs.getString("resourceid"));
		_gdtxrList.add(_resourceid);
	}
	for(String gd : _gdtxrList) {
		if(!gd.equals("")){
			String loginName = resource.getLoginID(gd);
			String addr = ""+IP+"/interface/portal/portal.jsp?typeid=flow-" + requestid + "";
			String typeid = "3";
			String flag = "1";
			bean.writeLog("loginName:"+loginName+";" + "requestid:"+requestid + ";" + "addr:"+addr + ";" + "requestname:"+requestname + ";" + "typeid:"+typeid+";");
			createDCDB.insertRecordToLogTable(loginName, requestid, addr, requestname, typeid, flag, currentDate, currentTime);
		}
	}
	java.lang.StringBuffer _bmString = new java.lang.StringBuffer();
	_bmString.append(ssbm);
	_bmString.append(",");
	_bmString.append(ssbm1);
	_bmString.append(",");
	_bmString.append(ssbm2);
	String _SSBM = _bmString.toString();
	String[] ssbms = _SSBM.split(",");
	for(String s : ssbms) {
		if("".equals(s)){
			continue;
		}
		java.util.List<String> hrmList = createDCDB.getHrmID(s);
		java.util.List<String> nqList = createDCDB.getNQHrmID(s);
		java.util.Map<String,String> hrmMap = new java.util.HashMap<String,String>();
		java.lang.StringBuffer sb = new java.lang.StringBuffer(",");
		for(String hrm : _gdtxrList) {
			hrmMap.put(hrm, hrm);
		}
		for(String hrm : hrmList) {
			hrmMap.put(hrm, hrm);
		}
		for(String hrm : nqList) {
			hrmMap.put(hrm, hrm);
		}
		for(String gd : _gdtxrList) {
			hrmMap.remove(gd);
		}
		for(java.util.Map.Entry<String,String> entry : hrmMap.entrySet()) {
			String key = entry.getKey();
			sb.append(key + ",");
		}

		String sbStri = sb.toString();
		sbStri = sbStri.substring(1, sbStri.length()-1);
		
		String[] sjrs = sbStri.split(",");
		for(int i = 0; i < sjrs.length; i++) {
			if(!sjrs[i].equals("")){
				String loginName = resource.getLoginID(sjrs[i]);
				String addr = ""+IP+"/interface/portal/portal.jsp?typeid=flow-" + requestid + "";
				String typeid = "3";
				String flag = "1";
				bean.writeLog("loginName:"+loginName+";" + "requestid:"+requestid + ";" + "addr:"+addr + ";" + "requestname:"+requestname + ";" + "typeid:"+typeid+";");
				createDCDB.insertRecordToLogTable(loginName, requestid, addr, requestname, typeid, flag, currentDate, currentTime);
			}
		}
	}
	String[] sjrs = gdtxr.split(",");
	for(int i = 0; i < sjrs.length; i++) {
		if(!sjrs[i].equals("")){
			String loginName = resource.getLoginID(sjrs[i]);
			String addr = ""+IP+"/interface/portal/portal.jsp?typeid=flow-" + requestid + "";
			String typeid = "3";
			String flag = "1";
			bean.writeLog("loginName:"+loginName+";" + "requestid:"+requestid + ";" + "addr:"+addr + ";" + "requestname:"+requestname + ";" + "typeid:"+typeid+";");
			createDCDB.insertRecordToLogTable(loginName, requestid, addr, requestname, typeid, flag, currentDate, currentTime);
		}
	}
	out.println("{\"success\":\"插入成功\"}");
%>