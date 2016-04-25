package xzy.test.uiautomator;

import java.util.regex.Pattern;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;


import org.w3c.dom.Element;
import android.util.Log;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;

/**
 * 输入步骤,找到控件并进行输入
 * 
 * @author code by 心向东 <br>
 *         QQ:176560744 UI_DropBox
 */
public class UI_EditStep extends ExecutiveStep {

	private String inputText;// 输入参数
	private String type;
	public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}

	@Override
	public String getClassName() {
		return "android.widget.EditText";
	}
	
	public int getType() {
		if(type == null || type.trim().isEmpty()){
			return 0 ;
		}
		return Integer.parseInt(type);
	}

	public void setType(String type) {
		this.type = type;
	}

	public UI_EditStep(Element step) throws XPathExpressionException {
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setInputText((String) xpath.evaluate(
				"ParamBinding[@name='inputText']/@value", step,
				XPathConstants.STRING));
		// String classname = (String) xpath.evaluate(
		// "ParamBinding[@name='className']/@value", step,
		// XPathConstants.STRING);
		// if (classname == null || classname.trim().isEmpty())
		// this.setClassName("android.widget.EditText");
		// else
		// this.setClassName(classname);
		this.setType((String) xpath.evaluate(
				"ParamBinding[@name='type']/@value", step,
				XPathConstants.STRING));
	}

	@Override
	public void Excut(UiDevice device) throws Exception {
		
		Log.i("123", "准备输入");
		System.out.println(" 运行输入步骤  参数" + this.getInputText());

		ExecutiveHelper.Screenshot(this);
		// System.out.println(this.getCountIndex() +"/"+this.getDesc()+"/"+
		// this.getId()+"/"+this.getIndex());
		if (!this.countIndex.trim().isEmpty() || this.getDesc() != null
				|| this.getId() != null || this.getIndex() != null) {

			UiObject object = ExecutiveHelper.AccessControl(this);
			object.click();

			int temp = Math.max(object.getText().toString().length(), object
					.getContentDescription().toString().length());
			for (int i = 0; i < temp; i++) {
				device.pressKeyCode(112);
				device.pressKeyCode(67);
			}
		}
		
		String str = this.getInputText();
		
			if(this.getType() == 0){
				Log.i("123", "正常输入");
				if(isNumeric(str)){
					ExecutiveHelper.Processing("input text " + str);
				}else{
					ExecutiveHelper.Processing("input text "
							+ Utf7ImeHelper.e(str));
				}
				
			}else if(this.getType() == 1){
				Log.i("123", "切换系统输入法");
				if(isNumeric(str)){
					ExecutiveHelper.RemoveInputMethod();
					ExecutiveHelper.Processing("input text " + str);
					ExecutiveHelper.HookInputMethod();
				}else{					
					ExecutiveHelper.Processing("input text "+ Utf7ImeHelper.e(str));	
				}
				
			}else if(this.getType() == 2){
				Log.i("123", "间隔0.5秒输入");
				for (int i = 0; i < str.length(); i++) {
					Thread.sleep(500);
					if(isNumeric(str)){
						ExecutiveHelper.Processing("input text " + str.charAt(i));
					}else{
						ExecutiveHelper.Processing("input text "
								+ Utf7ImeHelper.e(str.charAt(i)+""));
					}				
				}	
			}
		}
		
		

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	
	}

	// if(isNumeric(this.getInputText())){
	// //ExecutiveHelper.RemoveInputMethod();
	// //object.clearTextField();
	// //device.pressKeyCode(67);
	// char[] myChar=this.getInputText().toCharArray();
	// for (char a :myChar){
	// System.out.println(" "+Integer.parseInt(String.valueOf(a)));
	// device.pressKeyCode(Integer.parseInt(String.valueOf(a))+7);
	// }
	// //ExecutiveHelper.HookInputMethod();
	// }else{
	// System.out.println("input text "+Utf7ImeHelper.e(this.getInputText()));

	// object.setText(Utf7ImeHelper.e(this.getInputText()));
	// }
}
