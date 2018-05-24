package weaver.interfaces.service.log;

import java.util.HashMap;
import java.util.Map;

public class ServiceLogInfo {

    /**
     * 表单建模自定义字段，key为数据库字段名，value为对应数据库字段值
     */
    private Map<String,String> map = new HashMap<String,String>();

    /**
     * 日志监控配置文件名称
     */
    private String PROP_FILENAME;
    /**
     * 日志监控配置文件中对应的key
     */
    private String PROP_COLUMN_KEY;
    /**
     * 表单建模创建日期，格式为yyyy-MM-dd
     */
    private String modedatacreatedate;
    /**
     * 表单建模创建时间，格式为HH:mm:ss
     */
    private String modedatacreatetime;

    /**
     * 建模创建人，默认为1
     */
    private String modedatacreater = "1";

    /**
     * 表单建模创建类型，默认为0
     */
    private String modedatacreatertype = "0";

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public String getModedatacreater() {
        return modedatacreater;
    }

    public void setModedatacreater(String modedatacreater) {
        this.modedatacreater = modedatacreater;
    }

    public String getModedatacreatertype() {
        return modedatacreatertype;
    }

    public void setModedatacreatertype(String modedatacreatertype) {
        this.modedatacreatertype = modedatacreatertype;
    }

    public String getPROP_FILENAME() {
        return PROP_FILENAME;
    }

    public void setPROP_FILENAME(String PROP_FILENAME) {
        this.PROP_FILENAME = PROP_FILENAME;
    }

    public String getPROP_COLUMN_KEY() {
        return PROP_COLUMN_KEY;
    }

    public void setPROP_COLUMN_KEY(String PROP_COLUMN_KEY) {
        this.PROP_COLUMN_KEY = PROP_COLUMN_KEY;
    }

    public String getModedatacreatedate() {
        return modedatacreatedate;
    }

    public void setModedatacreatedate(String modedatacreatedate) {
        this.modedatacreatedate = modedatacreatedate;
    }

    public String getModedatacreatetime() {
        return modedatacreatetime;
    }

    public void setModedatacreatetime(String modedatacreatetime) {
        this.modedatacreatetime = modedatacreatetime;
    }
}
