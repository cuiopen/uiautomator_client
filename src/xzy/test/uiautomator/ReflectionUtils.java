package xzy.test.uiautomator;
import com.android.uiautomator.core.UiDevice;

import android.util.Log;
import android.view.InputEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;



public class ReflectionUtils {
	private static Field enableField(final Class<?> clazz, final String field)
			throws SecurityException, NoSuchFieldException {
		
		final Field fieldObject = clazz.getDeclaredField(field);
		fieldObject.setAccessible(true);
		return fieldObject;
	}

	private Object controller = null;
	private Object bridge = null;

	public ReflectionUtils() throws IllegalArgumentException,
			IllegalAccessException, SecurityException, NoSuchFieldException {
		final UiDevice device = UiDevice.getInstance();
		
		
		
		bridge = enableField(device.getClass(), "mUiAutomationBridge").get(
				device);
		Log.i("123", "bridge:"+bridge);
//		if (API_18) {
//			controller = enableField(bridge.getClass().getSuperclass(),
//					"mInteractionController").get(bridge);
//		} else {
			controller = enableField(bridge.getClass().getSuperclass(),
					"mInteractionController").get(bridge);
			Log.i("123", "controller:"+controller);
		//}
	}

	/*
	 * getAutomatorBridge is private so we access the bridge via reflection to
	 * use the touchDown / touchUp / touchMove methods.
	 */
	public Object getController() throws IllegalArgumentException,
			SecurityException {
		return controller;
	}

	public Object getBridge() {
		return bridge;
	}

	public Method getControllerMethod(final String name,
			final Class<?>... parameterTypes) throws NoSuchMethodException,
			SecurityException {
		return getMethod(controller.getClass(), name, parameterTypes);
	}

	public Method getMethodInjectInputEvent() throws NoSuchMethodException,
			SecurityException {
		Class bridgeClass = bridge.getClass();
//		if (API_18) {
//			bridgeClass = bridgeClass.getSuperclass();
//		}
		return getMethod(bridgeClass, "injectInputEvent", InputEvent.class,
				boolean.class);
	}

	public Method getMethod(final Class clazz, String name,
			final Class<?>... parameterTypes) throws NoSuchMethodException,
			SecurityException {
	
		final Method method = clazz.getDeclaredMethod(name, parameterTypes);

		method.setAccessible(true);
		return method;
	}

}
