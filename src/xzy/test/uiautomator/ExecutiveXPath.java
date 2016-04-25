package xzy.test.uiautomator;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * 后期扩展的xpath,暂时没用
 * 
 * @author code by 心向东 <br>
 *         QQ:176560744
 * 
 */
public class ExecutiveXPath extends CommandUtil {

	public int[] AccessControl(String className, String description,
			String text, String Id, int index) throws InterruptedException {
		long beginTime = System.currentTimeMillis();// 开始时间
		long nowTime = 0;
	
		int[] a;
		do {
			xpath();
			Thread.sleep(1000);
			a = iterateWholeXML(className, description, text, Id, index);
			if (a != null)
				break;
			
			nowTime = System.currentTimeMillis();
//			System.out.println(" 获取控件结果：  " + a[0] + " / 第" + i + "次 获取" + a[1]);
		} while ((nowTime - beginTime) <= 30000);
		return a;
	}

	public void xpath() {
		String xpath[] = { "uiautomator dump /sdcard/test/uidump.xml" };
		Processing(xpath);

	}

	public int[] iterateWholeXML(String className, String description,
			String text, String Id, int index) {
		String filename = "/sdcard/test/uidump.xml";
		String s = "//node[";
		if (className.equals(""))
			s = s + "@class ";
		else
			s = s + "@class='" + className + "'";
		if (!description.equals(""))
			s = s + " and @content-desc='" + description + "'";
		if (!text.equals(""))
			s = s + " and @text='" + text + "'";
		if (!Id.equals(""))
			s = s + " and @resource-id='" + Id + "'";
		s = s + "]";
		System.out.println(s);
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder db = factory.newDocumentBuilder();
			File caseFile = new File(filename);
			Document testDocument;
			testDocument = db.parse(caseFile);
			XPath xpath = XPathFactory.newInstance().newXPath();
			Element caseElement = testDocument.getDocumentElement();
			NodeList nodes = (NodeList) xpath.evaluate(s, caseElement,
					XPathConstants.NODESET);
			System.out.println(index + "/" + nodes.getLength());
			if (index <= nodes.getLength())
				return OperationString((String) xpath.evaluate("@bounds",
						nodes.item(index), XPathConstants.STRING));
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
		return null;
	}

	public int[] OperationString(String s) {
		s = s.replace("][", ",");
		s = s.replace("[", "");
		s = s.replace("]", "");
		String a[] = s.split(",");
		int x = (Integer.parseInt(a[0]) + Integer.parseInt(a[2])) / 2;
		int y = (Integer.parseInt(a[1]) + Integer.parseInt(a[3])) / 2;
		int xy[] = { x, y };
		return xy;
	}

	// adb shell /system/bin/uiautomator dump --compressed
	// /data/local/tmp/uidump.xml
	public void Processing(String[] command) {
		try {
			executeCommand(command);
		} catch (Exception e) {
			System.out.println(" 命令执行报错误：" + e.getMessage());
		}
	}

}
