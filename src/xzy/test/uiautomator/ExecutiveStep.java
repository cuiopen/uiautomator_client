package xzy.test.uiautomator;

import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import com.android.uiautomator.core.UiDevice;

/**
 * 测试步骤base对象,包含UiautomatorView中的基础属性
 * @author 
 * code by 心向东  <br>
 *
 */
public class ExecutiveStep{

	protected String Operation;// 操作步骤索引
	protected String StepDesc;// 步骤描述
	
	protected String id;// 控件ID
	protected String desc;// 标签
	protected String className;// class名字
	protected String text;//文本
	protected String index;
	protected String countIndex;//索引
	
	protected String neglect;//忽略
	protected String waitTime;// 等待时间
	protected String ResultStatic = "0";// 执行状态 0未执行 1执行成功 2执行失败 3报错
	protected String ResultMsg;// 执行结果
	protected Element step;
	protected String Photo="";
	protected String StepInt;

	/*
	public ExecutiveStep(Element step)  throws XPathExpressionException {

		this.step = step;
		XPath xpath = XPathFactory.newInstance().newXPath();

		this.setOperation((String) xpath.evaluate("@name", step,
				XPathConstants.STRING));
		
		this.setStepDesc((String) xpath.evaluate("@StepDesc", step,
				XPathConstants.STRING));
		
		this.setId((String) xpath.evaluate("ParamBinding[@name='id']/@value",
				step, XPathConstants.STRING));
		
		this.setDesc((String) xpath.evaluate(
				"ParamBinding[@name='desc']/@value", step,
				XPathConstants.STRING));
		
		this.setClassName((String) xpath.evaluate(
				"ParamBinding[@name='className']/@value", step,
				XPathConstants.STRING));
						
		this.setText((String) xpath.evaluate(
				"ParamBinding[@name='text']/@value", step,
				XPathConstants.STRING));

		this.setIndex((String) xpath.evaluate(
				"ParamBinding[@name='index']/@value", step,
				XPathConstants.STRING));
		
		this.setCountIndex((String) xpath.evaluate(
					"ParamBinding[@name='countIndex']/@value", step,
					XPathConstants.STRING));
		
		this.setWaitTime((String) xpath.evaluate(
					"ParamBinding[@name='waitTime']/@value", step,
					XPathConstants.STRING));
		
		this.setLocationText((String)xpath.evaluate(
				"ParamBinding[@name='LocationText']/@value", step,
				XPathConstants.STRING));
	}*/
	
	public ExecutiveStep(Element step)  throws XPathExpressionException {
		this.step = step;
		NodeList list2 = step.getElementsByTagName("ParamBinding");
		for (int i = 0; i < list2.getLength(); i++) {
			Element ent = (Element) list2.item(i);
			String name = ent.getAttribute("name");
			String value = ent.getAttribute("value");
			if(name.equalsIgnoreCase("name"))
				this.setOperation(value);
			else if(name.equalsIgnoreCase("StepDesc")) 
				this.setStepDesc(value);
			else if(name.equalsIgnoreCase("id")) 
				this.setId(value);
			else if(name.equalsIgnoreCase("desc")) 
				this.setDesc(value);
			else if(name.equalsIgnoreCase("className")) 
				this.setClassName(value);
			else if(name.equalsIgnoreCase("text")) 			
				this.setText(value);
			else if(name.equalsIgnoreCase("index")) 
				this.setIndex(value);
			else if(name.equalsIgnoreCase("countIndex")) 
				this.setCountIndex(value);
			else if(name.equalsIgnoreCase("waitTime")) 
				this.setWaitTime(value);
			else if(name.equalsIgnoreCase("neglect")) 
				this.setNeglect(value);
		}
			
			
//			ExecutiveHelper.Side test = ExecutiveHelper.Side.valueOf(name);
//			ExecutiveHelper.Side test = ExecutiveHelper.Side.valueOf(name);			
//			switch (test) {
//				case name:
//					this.setOperation(ent.getAttribute("value"));
//					break;
//				case StepDesc:
//					this.setStepDesc(ent.getAttribute("value"));
//					break;
//				case id:
//					this.setId(ent.getAttribute("value"));
//					break;
//				case desc:
//					this.setDesc(ent.getAttribute("value"));
//					break;
//				case className:
//					this.setClassName(ent.getAttribute("value"));
//					break;
//				case text:				
//					this.setText(ent.getAttribute("value"));
//					break;
//				case index:
//					this.setIndex(ent.getAttribute("value"));
//					break;
//				case countIndex:
//					this.setCountIndex(ent.getAttribute("value"));
//					break;
//				case waitTime:
//					this.setWaitTime(ent.getAttribute("value"));
//					break;
//				case LocationText:
//					this.setLocationText(ent.getAttribute("value"));
//					break;
//				default:
//					break;
//			}
//		}
	}

	
	public String getOperation() {
		return Operation;
	}
	public void setOperation(String operation) {
		Operation = operation;
	}
	public String getIndex() {
		if (index == null || index.trim().isEmpty())
			return null;
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public int getWaitTime() {
		if (waitTime == null || waitTime.trim().isEmpty())
			return 0;
		else
			return Integer.parseInt(waitTime);
	}
	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}
	public String getResultStatic() {
		return ResultStatic;
	}
	public void setResultStatic(String resultStatic) {
		ResultStatic = resultStatic;
	}
	public String getResultMsg() {
		return ResultMsg;
	}
	public void setResultMsg(String resultMsg) {
		ResultMsg = resultMsg;
	}

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String Photo) {
		this.Photo = Photo;
	}
	
	public String getStepDesc() {
		return StepDesc;
	}

	public void setStepDesc(String stepDesc) {
		StepDesc = stepDesc;
	}

	public String getId() {
		if (id == null || id.trim().isEmpty())
			return null;
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		if (desc == null || desc.trim().isEmpty())
			return null;
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Element getStep() {
		return step;
	}

	public void setStep(Element step) {
		this.step = step;
	}

	public int getCountIndex() {
		if (countIndex == null || countIndex.trim().isEmpty())
			return 0;
		else
			return Integer.parseInt(countIndex)-1;
	}

	public void setCountIndex(String countIndex) {
		this.countIndex = countIndex;
	}
	
	public void Excut(UiDevice device) throws Exception{
		
	}
	
	public String getStepInt() {
		return StepInt;
	}

	public void setStepInt(String stepInt) {
		StepInt = stepInt;
	}
	
	public boolean getNeglect() {
		if(neglect==null || neglect.isEmpty())
			return false;
		if(neglect.equalsIgnoreCase("true"))
			return true;
		return false;
	}


	public void setNeglect(String neglect) {
		this.neglect = neglect;
	}
	public ExecutiveStep() {

	}
	public void inputElement()  {
		this.step.setAttribute("ResultStatic", this.getResultStatic());
		this.step.setAttribute("ResultMsg", this.getResultMsg());
		this.step.setAttribute("Photo", this.getPhoto());
	}
	

}
