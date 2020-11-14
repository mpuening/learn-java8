package io.github.learnjava8;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;

import org.junit.Test;

import io.github.learnjava8.spi.ExampleServiceProvider;

public class ServiceProviderTests {

	@Test
	public void testLoadServiceProvider() {
		Set<String> results = new HashSet<>();

		ServiceLoader<ExampleServiceProvider> loader = ServiceLoader.load(ExampleServiceProvider.class);
		Iterator<ExampleServiceProvider> services = loader.iterator();
		while (services.hasNext()) {
			ExampleServiceProvider provider = services.next();
			String result = provider.configure();
			results.add(result);
		}
		
		assertEquals(2, results.size());
		assertTrue(results.contains("first"));
		assertTrue(results.contains("second"));
	}
}
