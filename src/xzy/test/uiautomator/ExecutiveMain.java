package xzy.test.uiautomator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import com.android.uiautomator.core.UiDevice;

/**
 * 测试案例主体对象,主入口
 * 
 * @author code by 心向东 <br>
 *         QQ:176560744
 * 
 */
public class ExecutiveMain {
	List<ExecutiveStep> step = new ArrayList<ExecutiveStep>();
	public ExecutiveStep textcass = null;
	private Document testDocument;
	private String resultFilePath;
	private String FilePath;

	public List<ExecutiveStep> getStep() {
		return step;
	}

	public void setStep(List<ExecutiveStep> step) {
		this.step = step;
	}

	/**
	 * 初始化测试案例
	 * 
	 * @param filename
	 *            测试案例文件路径
	 */
	public ExecutiveMain(String filename) {
		try {
//			System.out.println(new Date());
			iterateWholeXML(filename);
//			System.out.println(new Date());
		} catch (Exception e) {
			System.out.println(" 案例初始化报错误：" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void iterateWholeXML(String filename) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = factory.newDocumentBuilder();
		File caseFile = new File(filename);
		FilePath = caseFile.getParent();
		resultFilePath = FilePath + "/test_result.xml";
		File resultFile = new File(resultFilePath);
		resultFile.delete();
		ExecutiveHelper.copyFile(caseFile, resultFile);
		testDocument = db.parse(caseFile);
		Element caseElement = testDocument.getDocumentElement();
		NodeList list = caseElement.getElementsByTagName("Step");
		for (int i = 0; i < list.getLength(); i++) {
			Element element = (Element) list.item(i);
			textcass = ExecutiveHelper.getTS(element);
			step.add(textcass);
		}

		ExecutiveHelper.shotPath = FilePath;
		ExecutiveHelper.waitForExistTime = 30000;

	}

	/**
	 * 执行测试案例
	 * 
	 * @param device
	 *            Uiautomator的device对象
	 */
	public void run(UiDevice device) {
		int i=0;
//		Date now = null ,date = null;
//		String nows ,  dates;
		for (ExecutiveStep step : this.getStep()) {
//			nows = new SimpleDateFormat("HH:mm:ss").format(date =new Date());
			try {
				
				i++;
				step.setStepInt(""+i);
				step.Excut(device);
				
				
				Thread.sleep(step.getWaitTime() * 1000);
				
				step.setResultStatic("1");
				step.setResultMsg("执行成功");
			} catch (Exception e) {
//				 e.printStackTrace();
				System.out.println(" 案例执行报错误：" + e.getMessage());
				device.takeScreenshot(new File(FilePath + "/End.jpg"));
				if (e.getMessage().equals("NoControlIsFound")) {
					step.setResultStatic("2");
					step.setResultMsg("控件没有找到  执行不成功");
				} else if (e.getMessage().equals("NoDisappear")) {
					step.setResultStatic("2");
					step.setResultMsg("控件没有消失");
				} else if (e.getMessage().equals("NoParameter")) {
					step.setResultStatic("2");
					step.setResultMsg("无参数  执行不成功");
				} else if (e.getMessage().equals("ParameterError")) {
					step.setResultStatic("2");
					step.setResultMsg("输入参数错误  执行不成功");
				}else if (e.getMessage().equals("NoControlIsNeglectFound")) {
					ExecutiveHelper.Screenshot(step);
					step.setResultStatic("1");
					step.setResultMsg("忽略的步骤没有找到");
				} else {
					step.setResultStatic("3");
					step.setResultMsg(e.getMessage());
				}
				if (step.getPhoto() == null)
					step.setPhoto("End.png");
			}
			step.inputElement();
			this.creatXML();
//			&& !step.getNeglect()
//			dates =new SimpleDateFormat("HH:mm:ss").format(now =new Date());
//			System.out.println (" step:" +i+ " start time:" +nows+ " start over:" +dates+ " When used:" +name (now, date));
			if (!step.getResultStatic().equals("1") )
				break;
		}
	}

	public String name(Date now,Date date) {
		long l=now.getTime()-date.getTime();
		long day=l/(24*60*60*1000);
		long hour=(l/(60*60*1000)-day*24);
		long min=((l/(60*1000))-day*24*60-hour*60);
		long s=(l/1000-day*24*60*60-hour*60*60-min*60);
		return min+" Branch "+s+" Seconds";
	}
	
	/**
	 * 输出结果文件
	 */
	public void creatXML() {
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(testDocument);
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			PrintWriter pw = new PrintWriter(new FileOutputStream(
					resultFilePath));
			StreamResult result = new StreamResult(pw);
			transformer.transform(source, result);
		} catch (Exception e) {
			System.out.println(" 生成XML文件失败:" + e.getMessage());
			e.printStackTrace();
		}

	}
}
