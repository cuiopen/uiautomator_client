package xzy.test.uiautomator;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.lang.reflect.Method;
import org.w3c.dom.Element;

import android.graphics.Rect;

import android.util.Log;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;

/**
 * 点击步骤,点击某个对象
 * 
 * @author code by 心向东 <br>
 *         QQ:176560744
 * 
 */
public class UI_ClickStep extends ExecutiveStep {

	private String PiontXY;// 输入参数
	private String Advanced;// 输入参数
	private String LongClickTime;

	public String getAdvanced() {
		if (Advanced == null || Advanced.trim().isEmpty())
			return null;
		else
			return Advanced;
	}

	public void setAdvanced(String advanced) {
		Advanced = advanced;
	}

	public String getPiontXY() {
		return PiontXY;
	}

	public void setPiontXY(String PiontXY) {
		this.PiontXY = PiontXY; 
	}

	public int getLongClickTime() {
		if (LongClickTime == null || LongClickTime.trim().isEmpty())
			return 0;
		else
			return Integer.parseInt(LongClickTime);
	}

	public void setLongClickTime(String LongClickTime) {
		this.LongClickTime = LongClickTime;
	}

	public UI_ClickStep() {
	}

	public UI_ClickStep(Element step) throws XPathExpressionException {
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setPiontXY((String) xpath.evaluate(
				"ParamBinding[@name='PiontXY']/@value", step,
				XPathConstants.STRING));
		this.setAdvanced((String) xpath.evaluate(
				"ParamBinding[@name='Advanced']/@value", step,
				XPathConstants.STRING));
		this.setLongClickTime((String) xpath.evaluate(
				"ParamBinding[@name='LongClickTime']/@value", step,
				XPathConstants.STRING));

	}

	@Override
	public void Excut(UiDevice device) throws Exception {
		System.out.println(" 运行点击步骤  " + this.getClassName() + "/"
				+ this.getDesc() + "/" + this.getText() + "/" + this.getId()
				+ "/" + this.getIndex());

		String a[] = this.getPiontXY().split(",");
		Log.i("123", "运行点击");
		UiObject object = ExecutiveHelper.AccessControl(this);
		Rect Coordinate = object.getBounds();
		ExecutiveHelper.Screenshot(this);

		int clickXY[] = { Coordinate.centerX(), Coordinate.centerY() };
		boolean Continuous = false;

		if (a.length == 2) {
			int x = getPoint(device.getDisplayWidth(), a[0]);
			int y = getPoint(device.getDisplayHeight(), a[1]);
			if (this.getLongClickTime() > 0) {
				ReflectionUtils utils = new ReflectionUtils();

				longClick(clickXY[0], clickXY[1], this.getLongClickTime()*1000);

			}else {
				device.click(x, y);
			}
		} else if (this.getAdvanced() != null) {
			String x[] = this.getAdvanced().toLowerCase().split(",");
			
			for (int i = 0; i < x.length; i++) {
				if (x[i].equals("untilnotfound")) {
					Continuous = true;
				}else if(x[i].equals("doubleclick")){
					device.click(clickXY[0], clickXY[1]);
					Thread.sleep(100);
					device.click(clickXY[0], clickXY[1]);
				}
				else {
					clickXY = ExecutiveHelper.SeniorClick(x[i].toString(),
							Coordinate);
				}
			}
			
		} else {
			// object.clickAndWaitForNewWindow();
			// object.longClick();
			if (this.getLongClickTime() > 0) {
				ReflectionUtils utils = new ReflectionUtils();
				//长按
				longClick(clickXY[0], clickXY[1], this.getLongClickTime()*1000);

			} 
			
			else {
				object.clickAndWaitForNewWindow();
			}

		}

		if (Continuous) {
			for (int i = 0; i < 4; i++) {
				if (object.exists()) {
					device.click(clickXY[0], clickXY[1]);
					Thread.sleep(2000);
				} else
					break;
			}
		}
		System.out.println(" 点击坐标:" + Coordinate.centerX() + "/"
				+ Coordinate.centerY());
	}

	public void longClick(int x, int y, int time) throws Exception {
		ReflectionUtils utils = new ReflectionUtils();

		Method touchDown = utils.getControllerMethod("touchDown", int.class,
				int.class);

		Method touchUp = utils.getControllerMethod("touchUp", int.class,
				int.class);

		touchDown.invoke(utils.getController(), x, y);
		Thread.sleep(time);
		touchUp.invoke(utils.getController(), x, y);
	}

	public int getPoint(int length, String point) {
		if (point.contains("%")) {
			return (int) (length * ((Float.parseFloat(point.replace("%", "")
					.trim())) / 100));
		} else {
			return (int) Float.parseFloat(point.trim());
		}
	}

}
