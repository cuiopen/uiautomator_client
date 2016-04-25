package xzy.test.uiautomator;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class Executive extends UiAutomatorTestCase {

	public Executive() {
		// 启用测试输入法
		ExecutiveHelper.HookInputMethod();
	}

	public void testDemo() {
		ExecutiveHelper.device = getUiDevice();
		ExecutiveHelper.BrightScreen();
		ExecutiveMain m = new ExecutiveMain(ExecutiveHelper.casePath);
		m.run(ExecutiveHelper.device);
		//ExecutiveHelper.RemoveInputMethod();
	}
}