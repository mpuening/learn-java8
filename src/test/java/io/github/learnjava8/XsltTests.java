package io.github.learnjava8;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;

public class XsltTests {

	@Test
	public void testSimpleTransform() throws TransformerException {
		Source xslt = new StreamSource(new File("src/test/resources/cd-collection-html.xsl"));
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xslt);

		Source text = new StreamSource(new File("src/test/resources/cd-collection.xml"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		transformer.transform(text, new StreamResult(baos));

		String output = new String(baos.toByteArray());
		assertTrue(output.contains("<td>Bob Dylan</td>"));
		assertTrue(output.contains("<td>Tom Petty</td>"));
		System.out.println(output);
	}
}
