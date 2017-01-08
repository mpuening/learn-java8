package io.github.learnjava8;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import io.github.learnjava8.LambdaTests.Reversable;

public class StreamTests {

	private List<String> names = Collections
			.unmodifiableList(Arrays.asList("Ben", "", "Al", "Kim", "Jeff", "", "Susan"));

	// Alternative way to create a stream
	// private Stream<String> altNames = Stream.of("Ben", "", "Al", "Kim",
	// "Jeff", "", "Susan");

	@Test
	public void testAllMatchGivenCondition() {
		// All are not null
		assertTrue(names.stream().allMatch(name -> name != null));

		// But not all are empty
		assertFalse(names.stream().allMatch(name -> !name.isEmpty()));

		// All are less than 10 chars in length
		assertTrue(names.stream().allMatch(name -> name.length() < 10));

		// But not all are greater than 3
		assertFalse(names.stream().allMatch(name -> name.length() > 3));
	}

	@Test
	public void testAnyMatchGivenCondition() {
		// There is a name that is less than 3 chars in less
		assertTrue(names.stream().anyMatch(name -> name.length() < 3));

		// But not one that is greater than 10 chars in length
		assertFalse(names.stream().anyMatch(name -> name.length() > 10));
	}

	@Test
	public void testFilteringAList() {
		List<String> nonEmptyNames = names.stream().filter(name -> !name.isEmpty()).collect(Collectors.toList());
		assertEquals(5, nonEmptyNames.size());
	}

	@Test
	public void testJoiningItemsInAList() {
		String joined = names.stream().filter(name -> !name.isEmpty()).collect(Collectors.joining());
		assertEquals("BenAlKimJeffSusan", joined);

		joined = names.stream().filter(name -> !name.isEmpty()).collect(Collectors.joining(", "));
		assertEquals("Ben, Al, Kim, Jeff, Susan", joined);
	}

	@Test
	public void testCountingItemsInAList() {
		long count = names.stream().count();
		assertEquals(7, count);

		count = names.stream().filter(name -> !name.isEmpty()).collect(Collectors.counting());
		assertEquals(5, count);

		// A goofy map reduce way
		IntSummaryStatistics stats = names.stream().filter(name -> !name.isEmpty()).mapToInt(name -> 1)
				.summaryStatistics();
		assertEquals(5, stats.getCount());
	}

	@Test
	public void testSummingItemsInAList() {
		IntSummaryStatistics stats = names.stream().filter(name -> !name.isEmpty())
				.collect(Collectors.summarizingInt(name -> name.length()));
		assertEquals(17, stats.getSum());
		assertEquals(2, stats.getMin());
		assertEquals(5, stats.getMax());
		assertEquals(3.4, stats.getAverage(), 0.01);

		// Reference method name to call
		stats = names.stream().filter(name -> !name.isEmpty()).collect(Collectors.summarizingInt(String::length));
		assertEquals(17, stats.getSum());
		assertEquals(2, stats.getMin());
		assertEquals(5, stats.getMax());
		assertEquals(3.4, stats.getAverage(), 0.01);
	}

	@Test
	public void testAveragingItemsInAList() {
		double average = names.stream().filter(name -> !name.isEmpty())
				.collect(Collectors.averagingInt(name -> name.length()));
		assertEquals(3.4, average, 0.01);

		// Reference method name to call
		average = names.stream().filter(name -> !name.isEmpty()).collect(Collectors.averagingInt(String::length));
		assertEquals(3.4, average, 0.01);
	}

	@Test
	public void testDistinctFiltering() {
		List<String> distinctNames = names.stream().distinct().collect(Collectors.toList());
		assertEquals(6, distinctNames.size());
	}

	@Test
	public void testConvertListToMap() {
		Map<String, Integer> nameToLengthMap = names.stream().filter(name -> !name.isEmpty())
				.collect(Collectors.toMap(name -> name, name -> name.length()));
		assertEquals(5, nameToLengthMap.entrySet().size());
		assertEquals(4, nameToLengthMap.get("Jeff").intValue());

		// Another example using identity (itself), and lambda expression
		Reversable reverser = value -> new StringBuilder(value).reverse().toString();
		Map<String, String> namesToReverse = names.stream().filter(name -> !name.isEmpty())
				.collect(Collectors.toMap(Function.identity(), reverser::reverse));
		assertEquals(5, namesToReverse.entrySet().size());
		assertEquals("ffeJ", namesToReverse.get("Jeff"));
	}

	@Test
	public void testFindAnyName() {
		String foundName = names.stream().filter(name -> name.length() == 3).findAny().orElse("Name Not Found");
		assertTrue("Kim".equals(foundName) || "Ben".equals(foundName));
	}

	@Test
	public void testFindFirstName() {
		String foundName = names.stream().filter(name -> name.length() == 3).findAny().orElse("Name Not Found");
		assertEquals("Ben", foundName);
	}

	@Test
	public void testFindFirstNameOptionallyAbsent() {
		Optional<String> foundName = names.stream().filter(name -> name.length() == 8).findAny();
		assertEquals(Optional.empty(), foundName);
		assertEquals("Name Not Found", foundName.orElse("Name Not Found"));
	}

	@Test
	public void testForEach() {
		names.stream().forEach(name -> System.out.println("Name: " + name));
	}

	@Test
	public void testForEachOrdered() {
		names.stream().forEachOrdered(name -> System.out.println("Name: " + name));
	}

	@Test
	public void testSortList() {
		Stream<String> sorted = names.stream().filter(name -> !name.isEmpty()).sorted();
		assertEquals("Al", sorted.findFirst().orElse("Name Not Found"));
	}

	@Test
	public void testFirstName() {
		String firstName = names.stream().filter(name -> !name.isEmpty()).min(String::compareToIgnoreCase)
				.orElse("Name Not Found");
		assertEquals("Al", firstName);
	}

	@Test
	public void testLastName() {
		String lastName = names.stream().max(String::compareToIgnoreCase).orElse("Name Not Found");
		assertEquals("Susan", lastName);
	}

	@Test
	public void testMapToListOfFirstInitials() {
		List<Character> initials = names.stream().filter(name -> !name.isEmpty()).map(name -> name.charAt(0))
				.collect(Collectors.toList());
		// B for Ben
		assertEquals('B', initials.get(0).charValue());
	}

	@Test
	public void testFlattenMap() {
		// Strange, char[] doesn't seem to be supported in the stream API.
		// So this is a really bad example
		Stream<Character> uniqueCharacters = names.stream().filter(name -> !name.isEmpty())
				.map(name -> name.toCharArray()).flatMap(charArray -> {
					// Should be charArray.stream()
					List<Character> characters = new ArrayList<>();
					for (char ch : charArray) {
						characters.add(Character.valueOf(ch));
					}
					return characters.stream();
				}).collect(Collectors.toList()).stream().distinct();

		assertEquals(14, uniqueCharacters.count());
	}

	@Test
	public void testMapReduce() {
		int totalCharacters = names.stream().mapToInt(name -> name.length()).reduce((first, second) -> first + second)
				.orElse(0);
		assertEquals(17, totalCharacters);
	}
}