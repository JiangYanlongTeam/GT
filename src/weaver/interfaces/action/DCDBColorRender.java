package weaver.interfaces.action;

import java.util.Map;

import com.weaver.cssRenderHandler.CssDescriber;
import com.weaver.cssRenderHandler.CssRenderHandler;

import weaver.general.BaseBean;
import weaver.general.Util;

public class DCDBColorRender extends BaseBean implements CssRenderHandler {

	@Override
	public CssDescriber getCssDescriber(Map<String, String> paramMap, String paramString) {
		CssDescriber localCssDescriber = new CssDescriber();

		String sfcq = Util.null2String((String) paramMap.get("sfcq"));
		if ("1".equals(sfcq)) {
			localCssDescriber.addClass("e8_table_timeout");
		}
		localCssDescriber.addCss("background-repeat", "repeat-y");
		localCssDescriber.addCss("background-position", "right");
		return localCssDescriber;
	}

}
