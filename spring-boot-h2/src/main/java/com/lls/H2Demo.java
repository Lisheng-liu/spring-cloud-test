package com.lls;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.h2.tools.Server;

public class H2Demo {
    private Server server;

    private String port = "8082";
    private static String sourceURL1 = "jdbc:h2:mem:h2db";
    //jdbc:h2:tcp://localhost/mem:gacl
    private static String sourceURL2 = "jdbc:h2:tcp://localhost:8082/mem:h2db";
//    private static String sourceURL2 = "jdbc:h2:tcp://localhost:8082/~/data/h2";

    private String user = "shorturl";
    private String password = "123456";


    public static void main(String[] args) {
        new H2Demo().useH2i();
//        new H2Demo().useH2();
    }


    public void startServer() {
        try {
            System.out.println("正在启动h2...");
            server = Server.createTcpServer(
                    new String[]{"-tcpPort", port,"-webAllowOthers","-web"}).start();
//            server = Server.createWebServer(new String[]{"-webAllowOthers","-tcpPort", port}).start();
        } catch (SQLException e) {
            System.out.println("启动h2出错：" + e.toString());
// TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void stopServer() {
        if (server != null) {
            System.out.println("正在关闭h2...");
            server.stop();
            System.out.println("关闭成功.");
        }
    }

    public void useH2() {
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(sourceURL2, user, password);
            Statement stat = conn.createStatement();
// insert data
            stat.execute("CREATE  Table TEST(NAME VARCHAR)");
            stat.execute("INSERT INTO TEST VALUES('Hello World')");
//stat.execute("delete mappedURL");

// use data
            ResultSet result = stat.executeQuery("select name from test ");
            int i = 1;
            while (result.next()) {
                System.out.println(i++ + ":" + result.getString("name"));
            }
            result.close();
            stat.close();
            conn.close();
        } catch (Exception e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void useH2i() {
        try {
            Class.forName("org.h2.Driver");
//Connection conn = DriverManager.getConnection("jdbc:h2:" + dbDir+";AUTO_SERVER=TRUE;MVCC=TRUE",user, password);
            Connection conn = DriverManager.getConnection(sourceURL2, user, password);
            Statement stat = conn.createStatement();
// use data
            ResultSet result = stat.executeQuery("select name from test");
            int i = 1;
            while (result.next()) {
                System.out.println(i++ + ":" + result.getString("name"));
            }
            result.close();
            stat.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
