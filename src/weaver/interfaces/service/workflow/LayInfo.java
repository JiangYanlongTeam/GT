package weaver.interfaces.service.workflow;

import java.util.ArrayList;
import java.util.List;

public class LayInfo {
    private String code;
    private String message;
    private int count;
    private List<LayDetail> data = new ArrayList<LayDetail>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<LayDetail> getData() {
        return data;
    }

    public void setData(List<LayDetail> data) {
        this.data = data;
    }
}
