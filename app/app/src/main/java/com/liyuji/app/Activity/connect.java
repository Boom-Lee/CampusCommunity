package com.liyuji.app.Activity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class connect {
    public static Connection getConnection(String dbName) throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //加载驱动
            String ip = "8.136.180.205";
            conn =(Connection) DriverManager.getConnection(
                    "jdbc:mysql://" + ip + ":3306/" + dbName,
                    "campus_community", "123456");
            MainActivity.conn_on=1;//用于向主函数传参，判断连接是否成功
        }catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            MainActivity.conn_on=2;//用于向主函数传参，判断连接是否成功
        }
        return conn;//返回Connection型变量conn用于后续连接
    }
    public static int insertIntoData(final String username, final String password) throws SQLException {//增加数据
        Connection  conn = null;
        conn = getConnection("campus_community");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "insert INTO admin (admin_name,admin_password)VALUES('"+username+"','"+password+"')";//把用户名和密码插入到数据库中
        return stmt.executeUpdate(sql);
        //执行DML语句，返回受影响的记录条数
    }
    public static String querycol(final String id) throws SQLException {//读取某一行
        //加载数据库驱动
        String a;
        Connection  conn = null;
        conn = getConnection("campus_community");
        //使用DriverManager获取数据库连接
        Statement  stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        ResultSet rs =stmt.executeQuery(
                "select admin_password from admin where admin_name='"+id+"'");//从数据库中查询用户名对应的密码并返回
        rs.first();
        a=rs.getString(1);
        rs.close();
        return a;
        //把查询结果输出来
    }
}
