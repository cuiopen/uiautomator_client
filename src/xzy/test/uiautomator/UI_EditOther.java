package xzy.test.uiautomator;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;
import org.w3c.dom.Element;

import android.graphics.Rect;


import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

/**
 * 输入步骤,找到控件并进行输入
 * 
 * @author code by 心向东 <br>
 *         QQ:176560744
 * 
 */
public class UI_EditOther extends ExecutiveStep {

	private String inputText;// 输入参数
	private String Advanced;// 

	public String getAdvanced() {
		if (Advanced == null || Advanced.trim().isEmpty())
			return null;
		else
			return Advanced;
	}

	public void setAdvanced(String advanced) {
		Advanced = advanced;
	}
	public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}

	public UI_EditOther(Element step) throws XPathExpressionException {
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setInputText((String) xpath.evaluate(
				"ParamBinding[@name='inputText']/@value", step,
				XPathConstants.STRING));
		this.setAdvanced((String) xpath.evaluate(
				"ParamBinding[@name='Advanced']/@value", step,
				XPathConstants.STRING));
	}

	@Override
	public void Excut(UiDevice device) throws Exception {
		System.out.println(" 运行输入步骤  参数" + this.getInputText());
		UiObject object = ExecutiveHelper.AccessControl(this);
		Rect Coordinate = object.getBounds();
		ExecutiveHelper.Screenshot(this);
		if (this.getAdvanced() != null) {
			int clickXY[] = ExecutiveHelper.SeniorClick(this.getAdvanced(), Coordinate);
			Search(object, clickXY);
		} else {
			object.click();
			Clear(object);
		}
		ExecutiveHelper.Processing("input text "
				+ Utf7ImeHelper.e(this.getInputText()));

	}
	
	public void Search(UiObject object,int clickXY[]) throws UiObjectNotFoundException {
		for (int i = 0; i < 100; i++) {
//			UiObject object2 = object.getFromParent(new UiSelector().instance(i).childSelector(new UiSelector().className("android.widget.EditText")));
			UiObject object2 = new UiObject(new UiSelector().className("android.widget.EditText").instance(i));
			if(!object2.exists())
				return;
			Rect inate = object2.getBounds();
//			System.out.println(inate.left+"/"+inate.right+"/"+inate.top+"/"+inate.bottom);
			if(clickXY[0] > inate.left && clickXY[0] < inate.right && clickXY[1] > inate.top && clickXY[1] < inate.bottom ){
				Clear(object2);
				return;
			}
		}
	}
	
	public void Clear(UiObject object) throws UiObjectNotFoundException {
		int temp = Math.max(object.getText().toString().length(), object.getContentDescription().toString().length());
		for (int i = 0; i < temp; i++) {
			ExecutiveHelper.device.pressKeyCode(112);
			ExecutiveHelper.device.pressKeyCode(67);
		}
	}
}
