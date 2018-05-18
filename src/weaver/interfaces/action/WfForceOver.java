package weaver.interfaces.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;

import weaver.common.StringUtil;
import weaver.conn.RecordSet;
import weaver.conn.RecordSetTrans;
import weaver.crm.Maint.CustomerInfoComInfo;
import weaver.fna.general.FnaCommon;
import weaver.general.BaseBean;
import weaver.general.GCONST;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.hrm.User;
import weaver.hrm.attendance.manager.HrmAttVacationManager;
import weaver.hrm.resource.ResourceComInfo;
import weaver.systeminfo.SystemEnv;
import weaver.workflow.msg.PoppupRemindInfoUtil;
import weaver.workflow.request.ComparatorUtilBean;
import weaver.workflow.request.RequestAddShareInfo;
import weaver.workflow.request.RequestNodeFlow;
import weaver.workflow.request.RequestOperationLogManager;
import weaver.workflow.request.RequestRemarkRight;
import weaver.workflow.request.SendMsgAndMail;
import weaver.workflow.request.SubWorkflowTriggerService;
import weaver.workflow.request.WFLinkInfo;
import weaver.workflow.request.WFUrgerManager;
import weaver.workflow.workflow.WFSubDataAggregation;

public class WfForceOver extends BaseBean {
	RecordSet rs;
	RecordSet rs1;
	private String remark;
	private String annexdocids;
	private String signdocids;
	private String signworkflowids;
	private int requestLogId;
	private SendMsgAndMail sendMsgAndMail = null;
	private RecordSetTrans rst = null;
	private String remarkLocation = "";

	public WfForceOver() {
		this.rs = new RecordSet();
		this.rs1 = new RecordSet();
		this.sendMsgAndMail = new SendMsgAndMail();
		this.rst = new RecordSetTrans();
		this.remark = "";
		this.annexdocids = "";
		this.signdocids = "";
		this.signworkflowids = "";
		this.requestLogId = 0;
	}

	public void doForceOver(ArrayList paramArrayList) {
		RecordSet localRecordSet = new RecordSet();
		int i = 0;
		String str1 = "select wfForceOverLogic from FnaSystemSet";
		localRecordSet.executeSql(str1);
		if (localRecordSet.next()) {
			i = Util.getIntValue(localRecordSet.getString("wfForceOverLogic"), 0);
		}

		String str2 = "";
		Hashtable localHashtable = new Hashtable();
		String str3 = "127.0.0.1";
		User localUser = new User(1);
		int j = -1;
		int k = -1;
		int l = -1;
		String str4 = "";
		String str5 = "";
		int i1 = -1;
		int i2 = -1;
		int i3 = -1;
		int i4 = 0;
		int i5 = -1;
		int i6 = -1;
		int i7 = -1;
		int i8 = -1;
		int i9 = -1;
		int i10 = -1;
		int i11 = -1;
		int i12 = localUser.getUID();
		int i13 = (localUser.getLogintype().equals("1")) ? 0 : 1;
		int i14 = 0;
		int i15 = 0;
		PoppupRemindInfoUtil localPoppupRemindInfoUtil = new PoppupRemindInfoUtil();
		boolean bool = false;

		String str6 = "";
		Calendar localCalendar = Calendar.getInstance();
		String str7 = new StringBuilder().append(Util.add0(localCalendar.get(1), 4)).append("-")
				.append(Util.add0(localCalendar.get(2) + 1, 2)).append("-").append(Util.add0(localCalendar.get(5), 2))
				.toString();

		String str8 = new StringBuilder().append(Util.add0(localCalendar.get(11), 2)).append(":")
				.append(Util.add0(localCalendar.get(12), 2)).append(":").append(Util.add0(localCalendar.get(13), 2))
				.toString();

		char c = Util.getSeparator();
		RequestRemarkRight localRequestRemarkRight = new RequestRemarkRight();
		for (int i16 = 0; i16 < paramArrayList.size(); ++i16) {
			str2 = (String) paramArrayList.get(i16);
			if (l < 0) {
				this.rs.executeSql(
						new StringBuilder().append("select currentnodeid from workflow_requestbase where requestid=")
								.append(str2).toString());
				if (this.rs.next()) {
					l = this.rs.getInt("currentnodeid");
				}
			}
			this.rs.executeProc("workflow_Requestbase_SByID", new StringBuilder().append(str2).append("").toString());
			if (this.rs.next()) {
				str5 = this.rs.getString("requestname");
				j = this.rs.getInt("currentnodeid");
				i9 = this.rs.getInt("requestlevel");
				i7 = this.rs.getInt("workflowid");
				i10 = Util.getIntValue(this.rs.getString("creater"), 0);
				i11 = Util.getIntValue(this.rs.getString("creatertype"), 0);
				k = Util.getIntValue(this.rs.getString("currentnodetype"), 0);
			}

			int i17 = -1;
			String str9 = "forceover";
			RequestOperationLogManager localRequestOperationLogManager = new RequestOperationLogManager(
					Util.getIntValue(str2), j, i17, localUser.getUID(), localUser.getType(), str7, str8, str9);

			localRequestOperationLogManager.flowTransStartBefore();

			this.rs.executeSql(new StringBuilder().append("select nodeid from workflow_flownode where workflowid = ")
					.append(i7).append(" and nodetype = 3").toString());

			if (this.rs.next()) {
				i5 = this.rs.getInt("nodeid");
				i6 = 3;
			}
			this.rs.executeSql(new StringBuilder().append("select workflowtype from workflow_base where id = ")
					.append(i7).toString());

			if (this.rs.next()) {
				i8 = this.rs.getInt("workflowtype");
			}

			this.rs.executeProc("workflow_Workflowbase_SByID", new StringBuilder().append("").append(i7).toString());
			if (this.rs.next()) {
				i2 = Util.getIntValue(this.rs.getString("isbill"), -1);
				i1 = Util.getIntValue(this.rs.getString("formid"), -1);
			}

			if (i2 == 1) {
				this.rs.executeSql(new StringBuilder().append("select tablename from workflow_bill where id = ")
						.append(i1).toString());

				if (this.rs.next()) {
					str4 = this.rs.getString("tablename");
				}
			}
			if ((i2 == 1) && (!("".equals(str4)))) {
				this.rs.executeSql(new StringBuilder().append("select id from ").append(str4)
						.append(" where requestid = ").append(str2).toString());

				if (this.rs.next()) {
					i3 = this.rs.getInt("id");
				}
			}

			if (i6 == 0) {
				i14 = 1;
			}

			int i18 = 0;
			String str10 = "0";

			if (this.rs.getDBType().equals("oracle")) {
				this.rs.executeSql(new StringBuilder()
						.append("select * from workflow_nodelink where wfrequestid is null and workflowid = ")
						.append(i7).append(" and destnodeid = ").append(i5)
						.append(" and ((isreject <>'1' and (dbms_lob.getlength(condition) is null or dbms_lob.getlength(condition) = 0)) or (isreject is null and condition is null)) order by nodepasstime,id")
						.toString());
			} else {
				this.rs.executeSql(new StringBuilder()
						.append("select * from workflow_nodelink where wfrequestid is null and workflowid = ")
						.append(i7).append(" and destnodeid = ").append(i5)
						.append(" and ((isreject <>'1' and condition is NOT null AND datalength(condition) = 0) or (isreject is null and condition is null)) order by nodepasstime,id")
						.toString());
			}

			if (this.rs.next()) {
				i18 = this.rs.getInt("nodeid");
				i15 = Util.getIntValue(this.rs.getString("isreject"), 0);
			}

			this.rs.executeSql(new StringBuilder().append("select * from workflow_flownode where workflowid = ")
					.append(i7).append(" and nodeid = ").append(i18).toString());

			if (this.rs.next()) {
				str10 = this.rs.getString("nodetype");
			}
			try {
				RequestNodeFlow localRequestNodeFlow = new RequestNodeFlow();
				ResourceComInfo localObject1 = new ResourceComInfo();

				if (WFSubDataAggregation.checkSubProcessSummary(Integer.parseInt(str2))) {
					String localObject2 = SubWorkflowTriggerService.getMainRequestId(Integer.parseInt(str2));
					if ((localObject2 != null) && (!(((String) localObject2).isEmpty()))) {
						WFSubDataAggregation.addMainRequestDetail((String) localObject2, str2, localUser);
					}

				}

				CustomerInfoComInfo localObject2 = new CustomerInfoComInfo();

				localRequestNodeFlow.setRequestid(Integer.parseInt(str2));
				localRequestNodeFlow.setNodeid(i18);
				localRequestNodeFlow.setNodetype(str10);
				localRequestNodeFlow.setWorkflowid(i7);
				localRequestNodeFlow.setUserid(i12);
				localRequestNodeFlow.setUsertype(i13);
				localRequestNodeFlow.setCreaterid(i10);
				localRequestNodeFlow.setCreatertype(i11);
				localRequestNodeFlow.setIsbill(i2);
				localRequestNodeFlow.setBillid(i3);
				localRequestNodeFlow.setBilltablename(str4);
				localRequestNodeFlow.setIsreject(i15);
				localRequestNodeFlow.setIsreopen(i14);
				localRequestNodeFlow.setForceOver(true);
				localRequestNodeFlow.setForceOverNodeId(i5);
				localRequestNodeFlow.setRecordSet(this.rs);
				bool = localRequestNodeFlow.getNextNodeOperator();

				if (bool) {
					localHashtable = localRequestNodeFlow.getOperators();
				} else {
					ArrayList localArrayList1 = new ArrayList();
					localArrayList1
							.add(new StringBuilder().append(i10).append("_").append(i11).append("_0").toString());
					localHashtable.put("1", localArrayList1);
					if (!(new StringBuilder().append(i10).append("_").append(i11).toString()
							.equals(new StringBuilder().append(i12).append("_").append(i13).toString()))) {
						localArrayList1 = new ArrayList();
						localArrayList1
								.add(new StringBuilder().append(i12).append("_0").append("_").append("-1").toString());
						localHashtable.put("2", localArrayList1);
					}

				}

				float f = 0.0F;
				String str12 = SystemEnv.getHtmlLabelName(18360, localUser.getLanguage());

				this.rs1.executeProc("workflow_NodeLink_SPasstime",
						new StringBuilder().append("").append(i5).append(c).append("0").toString());

				if (this.rs1.next()) {
					f = Util.getFloatValue(this.rs1.getString("nodepasstime"), -1.0F);
				}

				String str13 = new StringBuilder().append(" update workflow_requestbase set  lastnodeid = ").append(j)
						.append(" ,lastnodetype = '").append(k).append("' ,currentnodeid = ").append(i5)
						.append(" ,currentnodetype = '").append(i6).append("' ,status = '").append(str12).append("' ")
						.append(" ,passedgroups = 0").append(" ,totalgroups = ").append(localHashtable.size())
						.append(" ,lastoperator = ").append(i12).append(" ,lastoperatedate = '").append(str7)
						.append("' ").append(" ,lastoperatetime = '").append(str8).append("' ")
						.append(" ,lastoperatortype = ").append(i13).append(" ,nodepasstime = ").append(f)
						.append(" ,nodelefttime = ").append(f).append(" where requestid = ").append(str2).toString();

				this.rs1.executeSql(str13);

				WFLinkInfo localWFLinkInfo = new WFLinkInfo();
				int i19 = localWFLinkInfo.getNodeAttribute(i5);
				this.rs1.executeSql(new StringBuilder().append("delete from workflow_nownode where requestid=")
						.append(str2).toString());
				this.rs1.executeSql(new StringBuilder().append(
						"insert into workflow_nownode(requestid,nownodeid,nownodetype,nownodeattribute) values(")
						.append(str2).append(",").append(i5).append(",").append(i6).append(",").append(i19).append(")")
						.toString());

				String str14 = "";
				int i20 = 0;
				String str15 = "";
				String str16 = "";
				String str17 = "";
				String str18 = "";
				String str19 = "";
				String str20 = "";
				String str21 = "";
				String str22 = "";

				this.rs1.executeSql(new StringBuilder()
						.append("select userid ,usertype from  workflow_currentoperator   where requestid = ")
						.append(str2).append(" and nodeid = ").append(j)
						.append(" and isremark in ('0','1','8','9','7')").toString());

				while (this.rs1.next()) {
					localPoppupRemindInfoUtil.updatePoppupRemindInfo(this.rs1.getInt(1), 0,
							new StringBuilder().append("").append(this.rs1.getInt(2)).toString(),
							Integer.parseInt(str2));
				}

				this.rs1.executeSql(new StringBuilder()
						.append("update workflow_currentoperator set isremark = '2'  where requestid = ").append(str2)
						.append(" and nodeid = ").append(j).append(" and isremark in ('0','8','9','7')").toString());

				String str23 = "";
				this.rs1.executeSql(new StringBuilder().append(
						"select BeForwardid from workflow_Forward w1,workflow_currentoperator w2  where w1.requestid='")
						.append(str2)
						.append("' and w2.isremark='1' and w1.BeForwardid=w2.id and (w1.IsBeForwardPending=0 or (w1.IsBeForwardPending=1 and w2.viewtype=-2))")
						.toString());

				while (this.rs1.next()) {
					str23 = new StringBuilder().append(str23)
							.append(Util.null2String(this.rs1.getString("BeForwardid"))).append(",").toString();
				}
				if (!("".equals(str23))) {
					str23 = str23.substring(0, str23.length() - 1);
					this.rs1.execute(new StringBuilder()
							.append("update workflow_currentoperator set isremark = '2'  where requestid = ")
							.append(str2).append(" and id in (").append(str23).append(")").toString());
				}

				ArrayList localArrayList2 = new ArrayList();
				int i21 = 0;
				String str24 = "";
				TreeMap localTreeMap = new TreeMap(new ComparatorUtilBean());
				Enumeration localEnumeration = localHashtable.keys();
				Object localObject4;
				while (localEnumeration.hasMoreElements()) {
					String localObject3 = (String) localEnumeration.nextElement();
					localObject4 = (ArrayList) localHashtable.get(localObject3);

					localTreeMap.put(localObject3, localObject4);
				}
				Object localObject3 = localTreeMap.keySet().iterator();
				while (((Iterator) localObject3).hasNext()) {
					localObject4 = (String) ((Iterator) localObject3).next();
					ArrayList localArrayList3 = (ArrayList) localHashtable.get(localObject4);

					for (int i22 = 0; i22 < localArrayList3.size(); ++i22) {
						++i21;
						String str25 = (String) localArrayList3.get(i22);
						String[] arrayOfString = Util.TokenizerString2(str25, "_");

						String str26 = arrayOfString[0];
						String str27 = arrayOfString[1];
						int i23 = Util.getIntValue(arrayOfString[2], -1);
						i20 = 0;

						str14 = new StringBuilder().append(
								" select agentorbyagentid,agenttype from workflow_currentoperator where userid=")
								.append(str26).append(" and agenttype='2' and requestid=").append(str2)
								.append(" and nodeid=").append(l).toString();

						this.rs1.execute(str14);

						if (this.rs1.next()) {
							i20 = 1;
							str15 = String.valueOf(localUser.getUID());
							str26 = this.rs1.getString("agentorbyagentid");
							str20 = TimeUtil.getCurrentDateString();
							str21 = TimeUtil.getCurrentTimeString().substring(11, 19);

							str22 = this.rs1.getString("agenttype");
						}

						if (i20 != 0) {
							str6 = new StringBuilder().append("").append(str2).append(c).append(str26).append(c)
									.append((String) localObject4).append(c).append(i7).append(c).append(i8).append(c)
									.append(str27).append(c).append("2").append(c).append(i5).append(c).append(str15)
									.append(c).append("1").append(c).append(i21).append(c).append(i23).toString();

							this.rs1.executeProc("workflow_CurrentOperator_I", str6);

							str6 = new StringBuilder().append("").append(str2).append(c).append(str15).append(c)
									.append((String) localObject4).append(c).append(i7).append(c).append(i8).append(c)
									.append(str27).append(c).append("0").append(c).append(i5).append(c).append(str26)
									.append(c).append("2").append(c).append(i21).append(c).append(i23).toString();

							this.rs1.executeProc("workflow_CurrentOperator_I", str6);
						} else {
							str6 = new StringBuilder().append("").append(str2).append(c).append(str26).append(c)
									.append((String) localObject4).append(c).append(i7).append(c).append(i8).append(c)
									.append(str27).append(c).append("0").append(c).append(i5).append(c).append(-1)
									.append(c).append("0").append(c).append(i21).append(c).append(i23).toString();

							this.rs1.executeProc("workflow_CurrentOperator_I", str6);
						}

						if (i20 == 0) {
							if (str27.equals("0")) {
								str24 = new StringBuilder().append(str24)
										.append(Util.toScreen(((ResourceComInfo) localObject1).getResourcename(str26),
												localUser.getLanguage()))
										.append(",").toString();
							} else {
								str24 = new StringBuilder().append(str24)
										.append(Util.toScreen(
												((CustomerInfoComInfo) localObject2).getCustomerInfoname(str26),
												localUser.getLanguage()))
										.append(",").toString();
							}

						} else if (str27.equals("0"))
							str24 = new StringBuilder().append(str24)
									.append(Util.toScreen(((ResourceComInfo) localObject1).getResourcename(str26),
											localUser.getLanguage()))
									.append("->")
									.append(Util.toScreen(((ResourceComInfo) localObject1).getResourcename(str15),
											localUser.getLanguage()))
									.append(",").toString();
						HashMap localHashMap;
						if (i20 == 0) {
							localHashMap = new HashMap();
							localHashMap.put("userid",
									new StringBuilder().append("").append(Integer.parseInt(str26)).toString());
							localHashMap.put("type", "1");
							localHashMap.put("logintype", new StringBuilder().append("").append(str27).toString());
							localHashMap.put("requestid",
									new StringBuilder().append("").append(Integer.parseInt(str2)).toString());
							localHashMap.put("requestname", "");
							localHashMap.put("workflowid", "-1");
							localHashMap.put("creater", "");
							localArrayList2.add(localHashMap);
						} else {
							localHashMap = new HashMap();
							localHashMap.put("userid",
									new StringBuilder().append("").append(Integer.parseInt(str15)).toString());
							localHashMap.put("type", "1");
							localHashMap.put("logintype", new StringBuilder().append("").append(str27).toString());
							localHashMap.put("requestid",
									new StringBuilder().append("").append(Integer.parseInt(str2)).toString());
							localHashMap.put("requestname", "");
							localHashMap.put("workflowid", "-1");
							localHashMap.put("creater", "");
							localArrayList2.add(localHashMap);
						}
					}
				}

				try {
					localPoppupRemindInfoUtil.insertPoppupRemindInfo(localArrayList2);
				} catch (Exception localException2) {
					writeLog(localException2);
				}

				if (i6 == 3) {
					this.rs1.executeSql(new StringBuilder().append(
							"update  workflow_currentoperator  set isremark='4'  where isremark='0' and requestid = ")
							.append(str2).toString());

					this.rs1.executeSql(new StringBuilder()
							.append("update  workflow_currentoperator  set iscomplete=1  where requestid = ")
							.append(str2).toString());

					this.rst.setAutoCommit(false);

					this.sendMsgAndMail.sendMsg(this.rst, Integer.parseInt(str2), i5, localUser, "submit",
							new StringBuilder().append("").append(i6).toString());

					this.sendMsgAndMail.sendChats(this.rst, i7, Integer.parseInt(str2), i5, localUser, "submit",
							new StringBuilder().append("").append(i6).toString());

					this.rst.commit();
				}

				this.rs1.executeSql(new StringBuilder().append(
						"select agentorbyagentid, agenttype, showorder from workflow_currentoperator where userid = ")
						.append(localUser.getUID()).append(" and nodeid = ").append(j)
						.append(" and isremark in ('0','1','4','8','9','7') and requestid = ").append(str2).toString());

				if (this.rs1.next()) {
					i21 = this.rs1.getInt("showorder");
				}
				this.remark = Util.null2String(this.remark);

				str14 = new StringBuilder()
						.append("select agentorbyagentid,agenttype from workflow_currentoperator where userid=")
						.append(localUser.getUID()).append(" and agenttype='2' and requestid=").append(str2)
						.append(" and nodeid=").append(l).toString();

				this.rs1.execute(str14);
				if (this.rs1.next()) {
					i20 = 1;
					str15 = this.rs1.getString("agentorbyagentid");
					str22 = this.rs1.getString("agenttype");
				}
				str6 = new StringBuilder().append(str2).append("").append(c).append(i7).append("").append(c).append(j)
						.append("").append(c).append("e").append(c).append(str7).append(c).append(str8).append(c)
						.append(i12).append(c).append("").append(this.remark).append(c).append(str3).append(c)
						.append(i13).append(c).append("0").append(c).append(str24.trim()).append(c)
						.append((i20 != 0) ? str15 : "-1").append(c).append((i20 != 0) ? str22 : "0").append(c)
						.append(i21).append(c).append(this.annexdocids).append(c).append(this.requestLogId).append(c)
						.append(this.signdocids).append(c).append(this.signworkflowids).append(c)
						.append(this.remarkLocation).append(c).append('0').append(c).append(0).append(c).append(0)
						.toString();

				this.rs1.executeProc("workflow_RequestLog_Op", str6);

				new HrmAttVacationManager().handle(StringUtil.parseToInt(str2), i7, 2);
				try {
					FnaCommon localFnaCommon = new FnaCommon();
					localFnaCommon.doWfForceOver(Util.getIntValue(str2, 0), i, false);
				} catch (Exception localException3) {
					new BaseBean().writeLog(localException3);
				}

				try {
					RequestAddShareInfo localRequestAddShareInfo = new RequestAddShareInfo();
					localRequestAddShareInfo.setRequestid(Util.getIntValue(str2));
					localRequestAddShareInfo.SetWorkFlowID(i7);
					localRequestAddShareInfo.SetNowNodeID(j);
					localRequestAddShareInfo.SetNextNodeID(i5);
					localRequestAddShareInfo.setIsbill(i2);
					localRequestAddShareInfo.setUser(localUser);
					localRequestAddShareInfo.SetIsWorkFlow(1);
					localRequestAddShareInfo.setBillTableName(str4);
					localRequestAddShareInfo.setHaspassnode(true);
					localRequestAddShareInfo.addShareInfo();
				} catch (Exception localException4) {
				}
			} catch (Exception localException1) {
				writeLog(localException1);
			}

			String str11 = "";
			this.rs1.executeSql(new StringBuilder().append("select isfeedback from workflow_flownode where workflowid=")
					.append(i7).append(" and nodeid=").append(j).toString());
			if (this.rs1.next()) {
				str11 = Util.null2String(this.rs1.getString("isfeedback"));
			}
			Object localObject1 = Util.null2String(getPropValue(GCONST.getConfigFile(), "ecology.changestatus"));
			if ((!(((String) localObject1).equals(""))) && (str11.equals("1"))) {
				this.rs1.executeSql(new StringBuilder()
						.append("update workflow_currentoperator set viewtype =-1  where needwfback='1' and requestid=")
						.append(str2).append(" and viewtype=-2").toString());
			}

			localRequestRemarkRight.setRequestid(Util.getIntValue(str2, -1));
			localRequestRemarkRight.deleteAllRight();

			Object localObject2 = new WFUrgerManager();
			((WFUrgerManager) localObject2).deleteUrgerByRequestid(Integer.valueOf(str2).intValue());

			localRequestOperationLogManager.flowTransSubmitAfter();
		}
	}

	public boolean isOver(int paramInt) {
		boolean flag = false;
		this.rs = new RecordSet();
		this.rs.executeSql(
				new StringBuilder().append("select currentnodetype from workflow_requestbase where requestid = ")
						.append(paramInt).toString());

		if ((this.rs.next()) && ("3".equals(this.rs.getString("currentnodetype")))) {
			flag = true;
		}

		return flag;
	}

	public boolean isNodeOperator(int paramInt1, int paramInt2) {
		boolean flag = false;
		this.rs = new RecordSet();

		this.rs.executeSql(new StringBuilder().append("select * from workflow_currentoperator where requestid = ")
				.append(paramInt1)
				.append(" and isremark in ('0','2','7') and nodeid in(select nownodeid from workflow_nownode where requestid=")
				.append(paramInt1).append(") and userid = ").append(paramInt2).toString());

		if (this.rs.next()) {
			flag = true;
		}
		return flag;
	}

	public boolean isNodeOperator(int paramInt1, int paramInt2, int paramInt3) {
		boolean flag = false;
		this.rs = new RecordSet();
		this.rs.executeSql(new StringBuilder().append("select * from workflow_currentoperator where requestid = ")
				.append(paramInt1).append(" and isremark in ('0','2') and nodeid = ").append(paramInt2)
				.append(" and userid = ").append(paramInt3).toString());

		if (this.rs.next()) {
			flag = true;
		}
		return flag;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String paramString) {
		this.remark = paramString;
	}

	public String getAnnexdocids() {
		return this.annexdocids;
	}

	public void setAnnexdocids(String paramString) {
		this.annexdocids = paramString;
	}

	public String getSigndocids() {
		return this.signdocids;
	}

	public void setSigndocids(String paramString) {
		this.signdocids = paramString;
	}

	public String getSignworkflowids() {
		return this.signworkflowids;
	}

	public void setSignworkflowids(String paramString) {
		this.signworkflowids = paramString;
	}

	public int getRequestLogId() {
		return this.requestLogId;
	}

	public void setRequestLogId(int paramInt) {
		this.requestLogId = paramInt;
	}

	public String getRemarkLocation() {
		return this.remarkLocation;
	}

	public void setRemarkLocation(String paramString) {
		this.remarkLocation = paramString;
	}
}