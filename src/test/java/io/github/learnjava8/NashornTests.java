package io.github.learnjava8;

import static org.junit.Assert.assertEquals;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;

public class NashornTests {

	@Test
	public void testInvokingJavaScript() throws ScriptException {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		Object result = engine.eval("print('Hello World!');");
		assertEquals(null, result);
	}

	@Test
	public void testInvokingJavaScriptFunction() throws ScriptException, NoSuchMethodException {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		Object result = engine.eval("var add = function(a, b) { return a + b; };");
		assertEquals(null, result);
		
		Invocable javaScript = (Invocable) engine;
		result = javaScript.invokeFunction("add", 5, 7);
		assertEquals(12.0, result);
	}
}
