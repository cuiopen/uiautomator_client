package xzy.test.uiautomator;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;

import com.android.uiautomator.core.UiDevice;

public class UI_InitStep extends ExecutiveStep {

	private String PackageName;
	private String Activity;
	private boolean isClear;

	public String getPackageName() {
		if (PackageName != null && PackageName.trim().isEmpty())
			return null;
		return PackageName;
	}

	public void setPackageName(String packageName) {
		PackageName = packageName;
	}

	public String getActivity() {
		if (Activity != null && Activity.trim().isEmpty())
			return null;
		return Activity;
	}

	public void setActivity(String activity) {
		Activity = activity;
	}

	public boolean getIsClear() {
		return isClear;
	}

	public void setClear(boolean isClear) {
		this.isClear = isClear;
	}

	public UI_InitStep() {

	}

	public UI_InitStep(Element step) throws XPathExpressionException {
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setPackageName((String) xpath.evaluate(
				"ParamBinding[@name='PackageName']/@value", step,
				XPathConstants.STRING));
		this.setActivity((String) xpath.evaluate(
				"ParamBinding[@name='Activity']/@value", step,
				XPathConstants.STRING));
		String daXie = (String) xpath.evaluate(
				"ParamBinding[@name='isClear']/@value", step,
				XPathConstants.STRING);
		this.setClear(!daXie.toLowerCase().equals("false"));

	}

	@Override
	public void Excut(UiDevice device) throws Exception {
		System.out.println(" 运行打开程序步骤");
		if (this.getPackageName() == null || this.getActivity() == null) {
			// throw new IllegalArgumentException("NoParameter");
		} else {
			ExecutiveHelper.Screenshot(this);
			String command1 = "pm clear " + this.getPackageName();
			String command2 = "am start -n " + this.getPackageName().toString()
					+ "/" + this.getActivity().toString();
			String command3 = "am force-stop " + this.getPackageName();
			if (!getIsClear())
				ExecutiveHelper.Processing(command3, command2);
			else
				ExecutiveHelper.Processing(command1, command2);
		}
	}
}
