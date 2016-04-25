package xzy.test.uiautomator;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;

import com.android.uiautomator.core.UiDevice;

public class UI_KeyEvent extends ExecutiveStep {

	private int key; // 按键的参数

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public UI_KeyEvent() {

	}

	public UI_KeyEvent(Element step) throws XPathExpressionException {
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		String key = (String) xpath
				.evaluate("ParamBinding[@name='key']/@value", step,
						XPathConstants.STRING);
		if (key == null || key.trim().isEmpty())
			this.setKey(-1);
		else
			this.setKey(Integer.parseInt(key));
	}

	@Override
	public void Excut(UiDevice device) throws Exception {
		System.out.println(" 运行按键步骤参数" + this.getKey());
		if (this.getKey() == -1) {
			throw new IllegalArgumentException("NoParameter");
		}
		ExecutiveHelper.Screenshot(this);
		device.pressKeyCode(this.getKey());
	}
}
