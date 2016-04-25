package xzy.test.uiautomator;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;

import android.graphics.Rect;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;

/**
 * @param args
 */
public class UI_DropBox extends ExecutiveStep {
	private String DropBoxText;// 输入参数
	private String Advanced;// 输入参数

	public String getAdvanced() {
		if (Advanced == null || Advanced.trim().isEmpty())
			return null;
		else
			return Advanced;
	}

	public void setAdvanced(String advanced) {
		Advanced = advanced;
	}
	public String getDropBoxText() {
		return DropBoxText;
	}

	public void setDropBoxText(String dropBoxText) {
		DropBoxText = dropBoxText;
	}

	public UI_DropBox(Element step) throws XPathExpressionException {
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setDropBoxText((String) xpath.evaluate(
				"ParamBinding[@name='DropBoxText']/@value", step,
				XPathConstants.STRING));
		this.setAdvanced((String) xpath.evaluate(
				"ParamBinding[@name='Advanced']/@value", step,
				XPathConstants.STRING));
	}

	@Override
	public void Excut(UiDevice device) throws Exception {
		System.out.println(" 运行下拉框步骤  参数" + this.getDropBoxText());
		UiObject object = ExecutiveHelper.AccessControl(this);
		Rect Coordinate = object.getBounds();
		ExecutiveHelper.Screenshot(this);
		if (this.getAdvanced() != null) {
			ExecutiveHelper.SeniorClick(this.getAdvanced(), Coordinate);
		} else {
			object.clickAndWaitForNewWindow();
		}
		ExecutiveStep es = new ExecutiveStep();
		es.setText(this.getDropBoxText());
		UiObject object2 = ExecutiveHelper.AccessControl(es);
		object2.clickAndWaitForNewWindow();
	}


}
