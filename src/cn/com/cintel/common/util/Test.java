package cn.com.cintel.common.util;

public class Test {
	public static final String[] noFilters = new String[] { "login.do", "index.jsp" };

	public void testUrl() {
		String str4 = "manager/findmanagers.do";

		String temp = "true";
		for (String s : noFilters) {
			if (str4.indexOf(s) != -1) {
				temp = "false";
				break;
			}
		}
		System.out.println(temp);

	}

	public void testBase64() {
		System.out.println(PublicUtil.encodeBase64("测试ceshi111WWWwww"));
		System.out.println(PublicUtil.decodeBase64(PublicUtil.encodeBase64("测试ceshi111WWWwww")));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Test t = new Test();
		t.testBase64();
	}

}
