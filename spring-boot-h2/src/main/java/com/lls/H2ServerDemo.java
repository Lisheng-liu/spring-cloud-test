package com.lls;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.Server;

public class H2ServerDemo {
    private Server server;
    private String port = "9092";
    private String dbDir = "./h2db/sample";
    private String user = "zhoujiang";
    private String password = "123456";

    public void startServer() {
        try {
            System.out.println("正在启动...");
            server = Server.createTcpServer(
                    new String[] { "-tcpPort", port }).start();
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
            Connection conn = DriverManager.getConnection("jdbc:h2:" + dbDir,
                    user, password);
            Statement stat = conn.createStatement();
// insert data
            stat.execute("CREATE TABLE TEST_1(NAME VARCHAR)");
            stat.execute("INSERT TEST_1 TEST VALUES('Hello World')");
// use data
            ResultSet result = stat.executeQuery("select name from TEST_1 ");
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

    public static void main(String[] args) {
        H2ServerDemo h2 = new H2ServerDemo();
        h2.startServer();
        h2.useH2();
//        h2.stopServer();
//        System.out.println("==END==");
    }
}
