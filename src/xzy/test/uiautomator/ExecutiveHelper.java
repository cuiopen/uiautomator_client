package xzy.test.uiautomator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;

import android.graphics.Rect;
import android.os.RemoteException;
import android.util.Log;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

/**
 * 测试帮助类,主要保存通用方法
 * 
 * @author code by 心向东 <br>
 *         QQ:176560744
 * 
 */
public class ExecutiveHelper {
	public static int waitForExistTime = 30000;
	public static String shotPath = "/sdcard/test/";
	public static String casePath = "/sdcard/test/test.xml";
	// public static String InputMethod
	// ="jp.jun_nama.test.utf7ime/.Utf7ImeService";
	public static String InputMethod = "com.example.android.softkeyboard/.SoftKeyboard";
	public static UiDevice device;
	public static CommandUtil commandutil = new CommandUtil();
	//public static UiObject messageApp = null;
	public enum Side {
		UP, DOWN, LEFT, RIGHT
	}
	/**
	 * 通过xml节点实例化步骤
	 * 
	 * @param el
	 *            :xml节点
	 * @return 步骤实体对象
	 * @throws XPathExpressionException
	 */
	public static ExecutiveStep getTS(Element el)
			throws XPathExpressionException {

		ExecutiveStep ts;
		try {
			String className = el.getAttribute("name");
			Class<?> cl = Class.forName("xzy.test.uiautomator." + className);
			Constructor<?> constructor = cl
					.getDeclaredConstructor(new Class[] { Element.class });
			ts = (ExecutiveStep) constructor.newInstance(new Object[] { el });
		} catch (Exception e) {
			ts = new ExecutiveStep(el);
		}
		return ts;
	}

	/**
	 * 复制文件
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
	public static void copyFile(File sourceFile, File targetFile)
			throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			outBuff.flush();
		} finally {
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}

	/**
	 * 查找对象
	 * 
	 * @param executivestep
	 *            步骤对象
	 * @return UiObject对象
	 * @throws UiObjectNotFoundException
	 */
	public static UiObject AccessControl(ExecutiveStep exec)
			throws UiObjectNotFoundException {
		
		return AccessControl(exec,waitForExistTime);
	}
	
	
	
	public static UiObject AccessControl(ExecutiveStep exec,int timeout)
			throws UiObjectNotFoundException {
		String ExceptionMSG= "NoControlIsFound";
		
		if(exec.getNeglect())
		{
			timeout=2000;
			ExceptionMSG="NoControlIsNeglectFound";
		}
		
		
		UiScrollable noteList = new UiScrollable(new UiSelector().className(
				"android.widget.ListView").scrollable(true));
		
		
		UiObject messageApp = Obtain(exec);
		
		Boolean messageAppFlag =  messageApp.waitForExists(1000);
		Boolean noteListFlag;
		if(messageAppFlag){
			noteListFlag =false;
		}
		else{
			noteListFlag =  noteList.exists();
		}
		
		if(messageAppFlag)
		{
			return messageApp;
		}else
		{
			if(noteListFlag)
			{
				noteList.setAsVerticalList(); // 纵向
				if(noteList.scrollIntoView(messageApp))
					return messageApp;
				else
					throw new IllegalArgumentException(ExceptionMSG);
			}else
			{
				if( messageApp.waitForExists(timeout))
					return messageApp;
				else
					throw new IllegalArgumentException(ExceptionMSG);
			}
		}
		/*
		if(!messageApp.waitForExists(1000)){
				if(noteList.exists())
				{	
					if( messageApp.exists())  //对象和Listview重复存在,直接返回对象
						return messageApp;
					noteList.setAsVerticalList(); // 纵向
					if(noteList.scrollIntoView(messageApp))
						return messageApp;
					else
						throw new IllegalArgumentException(ExceptionMSG);
				}else
				{
					if( messageApp.waitForExists(timeout))
						return messageApp;
					else
						throw new IllegalArgumentException(ExceptionMSG);
				}
			
		}else{
			return messageApp;
		}
		
		*/
		
		
	
	}

	/**
	 * 步骤对象转ui对象
	 * 
	 * @param executivestep
	 *            :步骤对象
	 * @return ui对象
	 */
	public static UiObject Obtain(ExecutiveStep exec) {
		// System.out.println("111 "+executivestep.getIndex()+"/"+executivestep.getDescription()+"/"+executivestep.getClassName());
		UiSelector uiseler = new UiSelector();
		if (exec.getId() != null && !"".equals(exec.getId()))
			uiseler = uiseler.resourceIdMatches(exec.getId());
		if (exec.getClassName() != null && !"".equals(exec.getClassName()))
			uiseler = uiseler.classNameMatches(exec.getClassName());
		if (exec.getText() != null && !"".equals(exec.getText()))
			uiseler = uiseler.textMatches(exec.getText());
		if (exec.getDesc() != null && !"".equals(exec.getDesc()))
			uiseler = uiseler.descriptionMatches(exec.getDesc());
		if (exec.getIndex() != null && !"".equals(exec.getIndex()))
			uiseler = uiseler.index(Integer.parseInt(exec.getIndex()));
		return new UiObject(uiseler.instance(exec.getCountIndex()));
	}

	/**
	 * 执行shell语句
	 * 
	 * @param command
	 *            :shell命令
	 */
	public static void Processing(String... command) {
		try {
			commandutil.executeCommand(command);
		} catch (Exception e) {
			System.out.println("命令执行报错误：" + e.getMessage());
		}
	}

	/**
	 * 快照
	 * 
	 * @param device
	 * 
	 */
	public static void Screenshot(ExecutiveStep step) {
		String s = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String shotName =step.getStepInt()+"-"+s + ".jpg";
		boolean aa = device.takeScreenshot(new File(shotPath + "/" + shotName));
		System.out.println(" 截屏结果：" + aa);
		if (aa)
			step.setPhoto(shotName);
		else
			step.setPhoto("截屏失败");
	}

	/**
	 * 点亮屏幕
	 * 
	 * @param device
	 */
	public static void BrightScreen() {
		try {
			device.wakeUp();
		} catch (RemoteException e) {
			System.out.println("点亮屏幕执行报错误：" + e.getMessage());
		}
	}

	/**
	 * 勾上输入法
	 * 
	 * @param device
	 */
	public static void HookInputMethod() {
		Processing("ime enable " + InputMethod, "ime set " + InputMethod);
		sleeps(100);
	}

	/**
	 * 去除输入法
	 * 
	 * @param device
	 */
	public static void RemoveInputMethod() {
		Processing("ime disable " + InputMethod);
		sleeps(2000);
	}

	public static void swipe(Rect rec, ExecutiveHelper.Side sd, int moneLength) {
		int monveX = 0, monveY = 0;
		if (sd == ExecutiveHelper.Side.UP) {
			monveX = rec.centerX();
			monveY = rec.centerY() - moneLength;
		} else if (sd == ExecutiveHelper.Side.DOWN) {
			monveX = rec.centerX();
			monveY = rec.centerY() + moneLength;
		}
		ExecutiveHelper.device.swipe(rec.centerX(), rec.centerY(), monveX, monveY, 55);
	}
	
	public static int[] SeniorClick(String Advanced, Rect Coordinate) {
		int x = Coordinate.centerX();
		int y = Coordinate.centerY();
		int z = -20;
		
		Advanced = Advanced.toString().toLowerCase();
		String ced[] = Advanced.split(" ");
		if (ced.length == 2)
			z = Integer.parseInt(ced[1]);
		if (Advanced.contains("left"))
			x = Coordinate.left - z;
		else if (Advanced.contains("right"))
			x = Coordinate.right + z;
		else if (Advanced.contains("down"))
			y = Coordinate.bottom + z;
		else if (Advanced.contains("up"))
			y = Coordinate.top - z;
		else
			throw new IllegalArgumentException("ParameterError");
		
		ExecutiveHelper.device.click(x, y);
		return new int []{x,y};
	}
	
	private static void sleeps(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
