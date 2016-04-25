package xzy.test.uiautomator;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;
import android.graphics.Rect;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;

public class UI_Location extends ExecutiveStep {

	private String LocationId;
	private String LocationDesc;
	private String LocationClassName;
	private String LocationText;
	private String LocationIndex;
	private String LocationcountIndex;
	private String WhetherClick;

	public String getWhetherClick() {
		if (WhetherClick == null || WhetherClick.trim().isEmpty())
			return null;
		return WhetherClick;
	}

	public void setWhetherClick(String whetherClick) {
		WhetherClick = whetherClick;
	}

	public String getLocationDesc() {
		return LocationDesc;
	}

	public void setLocationDesc(String locationDesc) {
		this.LocationDesc = locationDesc;
	}

	public String getLocationText() {
		return LocationText;
	}

	public void setLocationText(String locationText) {
		this.LocationText = locationText;
	}

	public String getLocationCountIndex() {
		return LocationcountIndex;
	}

	public void setLocationCountIndex(String LocationcountIndex) {
		this.LocationcountIndex = LocationcountIndex;
	}

	public String getLocationId() {
		return LocationId;
	}

	public void setLocationId(String locationId) {
		this.LocationId = locationId;
	}

	public String getLocationClassName() {
		return LocationClassName;
	}

	public void setLocationClassName(String locationClassName) {
		this.LocationClassName = locationClassName;
	}

	public String getLocationIndex() {
		return LocationIndex;
	}

	public void setLocationIndex(String locationIndex) {
		this.LocationIndex = locationIndex;
	}

	public UI_Location(Element step) throws XPathExpressionException {
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setLocationId((String) xpath.evaluate(
				"ParamBinding[@name='LocationId']/@value", step,
				XPathConstants.STRING));
		this.setLocationDesc((String) xpath.evaluate(
				"ParamBinding[@name='LocationDesc']/@value", step,
				XPathConstants.STRING));
		this.setLocationClassName((String) xpath.evaluate(
				"ParamBinding[@name='LocationClassName']/@value", step,
				XPathConstants.STRING));
		this.setLocationText((String) xpath.evaluate(
				"ParamBinding[@name='LocationText']/@value", step,
				XPathConstants.STRING));
		this.setLocationIndex((String) xpath.evaluate(
				"ParamBinding[@name='LocationIndex']/@value", step,
				XPathConstants.STRING));
		this.setLocationCountIndex((String) xpath.evaluate(
				"ParamBinding[@name='LocationcountIndex']/@value", step,
				XPathConstants.STRING));
		this.setWhetherClick((String) xpath.evaluate(
				"ParamBinding[@name='WhetherClick']/@value", step,
				XPathConstants.STRING));
	}

	@Override
	public void Excut(UiDevice device) throws Exception {

		int distance = 50;// 滑动屏幕边缘边距
		int moveLength;// 移动总距离
		int oneMoveLength;// 单次移动距离
		Rect moveObjPoint; // 需要移动的点
		Rect downObjPoint;// 定位点
		ExecutiveHelper.Side side;// 移动方向

		UiObject object1 = ExecutiveHelper.AccessControl(this);

		ExecutiveStep es = new ExecutiveStep();
		es.setClassName(this.getLocationClassName());
		es.setDesc(this.getLocationDesc());
		es.setId(this.getLocationId());
		es.setText(this.getLocationText());
		es.setIndex(this.getLocationIndex());
		es.setCountIndex(this.getLocationCountIndex());
		UiObject object2 = ExecutiveHelper.AccessControl(es);

		moveObjPoint = object1.getBounds();
		downObjPoint = object2.getBounds();
		System.out.println(moveObjPoint + "/" + moveObjPoint.centerX() + "|"
				+ moveObjPoint.centerY());
		System.out.println(downObjPoint + "/" + downObjPoint.centerX() + "|"
				+ downObjPoint.centerY());
		moveLength = moveObjPoint.centerY() - downObjPoint.centerY();
		if (moveLength > 0) {
			side = ExecutiveHelper.Side.UP;
			oneMoveLength = downObjPoint.centerY() - distance;
		} else {
			moveLength = 0 - moveLength;
			side = ExecutiveHelper.Side.DOWN;
			oneMoveLength = device.getDisplayHeight() - distance
					- downObjPoint.centerY();
		}

		while (moveLength > 15) {
			if (moveLength < oneMoveLength)
				oneMoveLength = moveLength;
			ExecutiveHelper.swipe(downObjPoint, side, oneMoveLength);
			moveLength = moveLength - oneMoveLength;
		}
		// if(!this.getWhetherClick().equals("false") ||
		// this.getWhetherClick()!=null)
		// device.click(downObjPoint.centerX(), downObjPoint.centerY());
		ExecutiveHelper.Screenshot(this);
	}
}
