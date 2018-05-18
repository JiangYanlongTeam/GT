package weaver.interfaces.pdf;

public class Test {

	public static void main(String[] args) {
		FTPUtil f = new FTPUtil("10.254.45.4", 21, "admin", "123");
		System.out.println("登录："+f.login());
		System.out.println(f.downloadFile("/OAdest", "b2f331a2-51ab-11e8-a33d-40f2e92bf135.cebx", "/Users/wangshanshan/Desktop"));
	}
}
