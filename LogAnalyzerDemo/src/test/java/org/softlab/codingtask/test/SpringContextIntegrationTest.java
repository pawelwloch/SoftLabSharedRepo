/**
 * @author Paweł Włoch ©SoftLab
 * @date 23 lis 2018
 */
package org.softlab.codingtask.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.softlab.codingtask.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SpringContextIntegrationTest {

	@Autowired
	private ResourceLoader resourceLoader;

	private final static String JSON_TEST_FILE = "classpath:test_data.json";

	@Test
	public final void testMain() throws Exception {
		String path2JsonTestFile = resourceLoader.getResource(JSON_TEST_FILE).getFile().getPath();
		Application.main(new String[] { path2JsonTestFile });
	}

}
