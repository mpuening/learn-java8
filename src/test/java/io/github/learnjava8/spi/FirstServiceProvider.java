package io.github.learnjava8.spi;

public class FirstServiceProvider implements ExampleServiceProvider {

	@Override
	public String configure() {
		return "first";
	}

}
