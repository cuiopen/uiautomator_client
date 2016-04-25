package xzy.test.uiautomator;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.xpath.XPathExpressionException;

import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;

import org.w3c.dom.Element;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

public class UI_ShenTai1 extends ExecutiveStep {
	
	UiObject object = null;
	
	public UI_ShenTai1(Element step) throws XPathExpressionException {
		super(step);
//		XPath xpath = XPathFactory.newInstance().newXPath();
//		this.setLocationId((String) xpath.evaluate(
//				"ParamBinding[@name='LocationId']/@value", step,
//				kkXPathConstants.STRING));
		
	}
	
	@Override
	public void Excut(UiDevice device) throws Exception {
		System.out.println(" 时间开始:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		General(".您是被保险人的.*", "right 50");
		GeneralText("其他");
		General(".投保人姓名.*", "right 50","张大保");
		General(".证件类型.*", "right 50");
		GeneralText("护照");
		General(".性别：", "right 50");
		GeneralText("男");
		General(".婚姻状况：", "left 30");
		GeneralEdit(0, "0000004");
		GeneralText(".*0000004.*",1);
		General(".出生日期：", "right 50");
		GeneralId("android:id/numberpicker_input","1968");
		GeneralId("android:id/numberpicker_input",1,"7");
		GeneralId("android:id/numberpicker_input",2,"7");
		GeneralText("确定");
		General(".户籍所在地：", "right 50");
		GeneralText("北京市");
		GeneralText("北京市");
		General(".手机号码：", "right 50","13585748956");
		General(".联系地址邮编：", "right 50","570000");
		General(".*联系地址：", "right 50");
		GeneralText("海南省");
		General("城市");
		GeneralText("海口市");
		GeneralText("秀英区");
		GeneralEdit(11, "上海南路322号");
		General(".婚姻状况：", "right 50");
		GeneralText("已婚");
		General(".证件号码：", "right 50","6582187254");
		General("下一步 Link");
		General("下一步 Link");
		System.out.println(" 时间结束:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

	public void General(String desc) throws UiObjectNotFoundException {
		object = new UiObject(new UiSelector().descriptionMatches(desc));
		object.click();
		System.out.println(" 时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
	
	public void General(String desc,String Advanced) throws UiObjectNotFoundException {
		object = new UiObject(new UiSelector().descriptionMatches(desc));
		ExecutiveHelper.SeniorClick(Advanced, object.getBounds());
		System.out.println(" 时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
	
	public void General(String desc,String Advanced,String input) throws UiObjectNotFoundException {
		object = new UiObject(new UiSelector().descriptionMatches(desc));
		ExecutiveHelper.SeniorClick(Advanced, object.getBounds());
		ExecutiveHelper.Processing("input text "+ Utf7ImeHelper.e(input));
		System.out.println(" 时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
	
	public void GeneralText(String text) throws UiObjectNotFoundException {
		object = new UiObject(new UiSelector().text(text));
		object.click();
		System.out.println(" 时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
	
	public void GeneralText(String text,int instance) throws UiObjectNotFoundException {
		object = new UiObject(new UiSelector().text(text).instance(instance));
		object.click();
		System.out.println(" 时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
	
	public void GeneralId(String id) throws UiObjectNotFoundException {
		object = new UiObject(new UiSelector().resourceIdMatches(id));
		object.click();
		System.out.println(" 时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
	
	public void GeneralId(String id, String input) throws UiObjectNotFoundException {
		object = new UiObject(new UiSelector().resourceIdMatches(id));
		object.click();
		ExecutiveHelper.Processing("input text "+ Utf7ImeHelper.e(input));
		System.out.println(" 时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
	
	public void GeneralId(String id,int countIndex, String input) throws UiObjectNotFoundException {
		object = new UiObject(new UiSelector().resourceIdMatches(id).instance(countIndex));
		object.click();
		ExecutiveHelper.Processing("input text "+ Utf7ImeHelper.e(input));
		System.out.println(" 时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
	
	public void GeneralEdit(int countIndex, String input) throws UiObjectNotFoundException {
		object = new UiObject(new UiSelector().className("android.widget.EditText").instance(countIndex));
		object.click();
		ExecutiveHelper.Processing("input text "+ Utf7ImeHelper.e(input));
		System.out.println(" 时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
}
