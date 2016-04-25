package xzy.test.uiautomator;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;

import android.graphics.Rect;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;

public class UI_SwipStep extends ExecutiveStep {// 滑动

	private String Direction;// 滑动
	private String PiontXY;// 输入参数
	private int Width;// 宽度
	private int Height;// 高度

	public String getPiontXY() {
		return PiontXY;
	}

	public void setPiontXY(String PiontXY) {
		this.PiontXY = PiontXY;
	}

	public String getDirection() {
		if (Direction == null || Direction.trim().isEmpty())
			return "up";
		return Direction;
	}

	public void setDirection(String direction) {
		Direction = direction;
	}

	public UI_SwipStep() {

	}

	public UI_SwipStep(Element step) throws XPathExpressionException {
		super(step);
		XPath xpath = XPathFactory.newInstance().newXPath();
		this.setDirection((String) xpath.evaluate(
				"ParamBinding[@name='Direction']/@value", step,
				XPathConstants.STRING));
		this.setPiontXY((String) xpath.evaluate(
				"ParamBinding[@name='PiontXY']/@value", step,
				XPathConstants.STRING));
	}

	@Override
	public void Excut(UiDevice device) throws Exception {
		Width = device.getDisplayWidth();// 显示宽度
		Height = device.getDisplayHeight();// 显示高度
		UiObject object = null;
		Rect Coordinate = null;
		String a[] = this.getPiontXY().split(",");
		System.out.println(" 运行滑动步骤  参数: "+ this.getDirection() + "/" + this.getPiontXY() + "/"+ Width + "/" + Height);
		ExecutiveHelper.Screenshot(this);
		int startx = Width / 2, starty = Height / 2, endx = Width / 2, endy = Height / 2, steps= 55;
		
		if(a.length == 5 || a.length == 4){
			startx = convert(a[0], "Width");
			starty = convert(a[1], "Height");
			endx = convert(a[2], "Width");
			endy = convert(a[3], "Height");
			if (a.length == 5){
				steps =	convert(a[4], "steps");
			}
		}else if(this.getClassName() != null || this.getDesc() != null || this.getId() != null || this.getIndex() != null || this.getText() != null){
			object = ExecutiveHelper.AccessControl(this);
			Coordinate = object.getBounds();
			startx = Coordinate.centerX();
			starty = Coordinate.centerY();
			if (this.getDirection().equalsIgnoreCase("right")){// --->左
				endx = Width - 10;
				endy =  Coordinate.centerY();
			}else if (this.getDirection().equalsIgnoreCase("left")){// <---右
				endx = 10;
				endy = Coordinate.centerY();
			}else if (this.getDirection().equalsIgnoreCase("up")){// 上
				endx = Coordinate.centerX();
				endy =  10;
			}else if (this.getDirection().equalsIgnoreCase("down")){// 下
				endx = Coordinate.centerX();
				endy = Height - 10;
			}else if (this.getDirection().equalsIgnoreCase("Oblique")){// 斜左上
				endx =  Width - 10;
				endy =  Height - 10;
			}
		}else{
			if (this.getDirection().equalsIgnoreCase("right")){// --->左
				endx = Width - 10;
				endy =  Height / 2;
			}else if (this.getDirection().equalsIgnoreCase("left")){// <---右
				endx = 10;
				endy =  Height / 2;
			}else if (this.getDirection().equalsIgnoreCase("up")){// 上
				endx = Width / 2;
				endy =  10;
			}else if (this.getDirection().equalsIgnoreCase("down")){// 下
				endx = Width / 2;
				endy = Height - 10;
			}else if (this.getDirection().equalsIgnoreCase("Oblique")){// 斜左上
				endx =  Width - 10;
				endy =  Height - 10;
			}
		}
//		System.out.println(startx+"/"+starty+"/"+endx+"/"+endy+"/"+steps);
		device.swipe(startx, starty, endx, endy, steps);
	}

	public int convert(String... Coordinate) {
		if (Coordinate[0].indexOf("%") != -1) {
			double dinate = (double) Integer.parseInt(Coordinate[0].replace(
					"%", "").toString()) / 100;
			if (Coordinate[1].equalsIgnoreCase("Width"))
				return (int) (Width * dinate);
			else if (Coordinate[1].equalsIgnoreCase("Height"))
				return (int) (Height * dinate);
			else
				throw new IllegalArgumentException("ParameterError");
		}
		return Integer.parseInt(Coordinate[0]);
	}
}