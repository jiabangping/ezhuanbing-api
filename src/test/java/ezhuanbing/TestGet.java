package ezhuanbing;

public class TestGet {
	public static void main(String[] args) {
			try {
				String r = WeixinBasicKit.sendGet("http://www.baidu.com");
				System.out.println(r);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
