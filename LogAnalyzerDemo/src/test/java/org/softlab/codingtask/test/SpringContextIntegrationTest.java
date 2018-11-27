/**
 * @author Paweł Włoch ©SoftLab
 * @date 23 lis 2018
 */
package org.softlab.codingtask.test;

import org.junit.Test;
import org.softlab.codingtask.Application;

public class SpringContextIntegrationTest {

	@Test
	public final void testMain() throws Exception {
		// put here a path to json file in ./test/resources
		Application.main(new String[] { "d:\\git_repos\\LogAnalyzerDemo\\src\\test\\resources\\test_data.json" });
	}

}
