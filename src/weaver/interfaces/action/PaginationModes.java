package weaver.interfaces.action;

import java.util.List;

public class PaginationModes {

	private String total;
	private List<PaginationMode_Item> rows;
	private List<PaginationMode_Row> colspan;
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public List<PaginationMode_Item> getRows() {
		return rows;
	}
	public void setRows(List<PaginationMode_Item> rows) {
		this.rows = rows;
	}
	public List<PaginationMode_Row> getColspan() {
		return colspan;
	}
	public void setColspan(List<PaginationMode_Row> colspan) {
		this.colspan = colspan;
	}
}
