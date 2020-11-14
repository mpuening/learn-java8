package io.github.learnjava8.spi;

public class SecondServiceProvider implements ExampleServiceProvider {

	@Override
	public String configure() {
		return "second";
	}

}
