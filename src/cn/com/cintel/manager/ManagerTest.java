package cn.com.cintel.manager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-servlet.xml" })
public class ManagerTest {
	@Autowired
	private ManagerDao mdao;

	@Autowired
	private ManagerService mservice;

	@Test
	public void testLogin() {
		Assert.assertTrue(mservice.login("clw").size() == 1);
	}

	public void testDel() {
		Assert.assertTrue(mdao.delManagerById("3,8") == 2);
	}

}
