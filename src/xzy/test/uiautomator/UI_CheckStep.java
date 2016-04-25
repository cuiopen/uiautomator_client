package xzy.test.uiautomator;

import java.util.Date;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;

/**
 * 检查点步骤,检查某个对象是否存在
 * 
 * @author code by 心向东 <br>
 *         QQ:176560744
 * 
 */
public class UI_CheckStep extends ExecutiveStep {
	private String checkMode;// 输入参数

	public String getCheckMode() {
		if (checkMode == null || checkMode.trim().isEmpty())
			return "found";
		else
			return checkMode;
	}

	public void setCheckMode(String advanced) {
		checkMode = advanced;
	}
	public UI_CheckStep() {
	}

	public UI_CheckStep(Element step) throws XPathExpressionException {
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setCheckMode((String) xpath.evaluate(
				"ParamBinding[@name='checkMode']/@value", step,
				XPathConstants.STRING));
	}

	@Override
	public void Excut(UiDevice device) throws Exception {
		System.out.println(" 运行检查步骤  ");
		UiObject object =null;
		
		if(this.getCheckMode().equalsIgnoreCase("notfound")){
			ExecutiveHelper.Screenshot(this);
			try{
			 object = ExecutiveHelper.AccessControl(this,1000);
			 this.step.setAttribute(
						"obj",
						"ClassName:" + object.getClassName() + ",text:"
								+ object.getText() + ",Desc:"
								+ object.getContentDescription());
			}catch(Exception e){
				return;
			}
			System.out.println(" "+ new Date());
			boolean a = object.waitUntilGone(ExecutiveHelper.waitForExistTime);
			System.out.println(" "+new Date());
			System.out.println(""+a);
			if(!a)
				throw new IllegalArgumentException("NoDisappear");
			else
				return;
			
		}else{
			
			object = ExecutiveHelper.AccessControl(this);
			ExecutiveHelper.Screenshot(this);
			this.step.setAttribute(
					"obj",
					"ClassName:" + object.getClassName() + ",text:"
							+ object.getText() + ",Desc:"
							+ object.getContentDescription());
		}
		
		
	}
}
