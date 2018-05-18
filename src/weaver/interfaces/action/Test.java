package weaver.interfaces.action;

import weaver.docs.webservices.DocService;
import weaver.docs.webservices.DocServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jiangyanlong on 2018/5/18.
 */
public class Test {
    public static void main(String[] args) {
        System.out.println("this is a test java file");

        DocService service = new DocServiceImpl();
        try {
            service.getDocCount("11");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
