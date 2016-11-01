package ezhuanbing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestResultSet {
	static Connection conn = null;
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
			 System.out.println(conn);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	static Connection getConn() {
		return conn;
	}
/**
ResultSet结果集并不仅仅具有存储的功能，他同时还具有操纵数据的功能，可能完成对数据的更新等。
 结果集读取数据的方法主要是getXXX(), getString()可以返回所有列的值，
 
结果集特点分4类
结果集都是由statement执行后产生的，结果集的特点取决于Statement

3种Statement
1、无参数的基本 ResultSet和Statement
最基本的ResultSet的作用就是完成了查询结果的功能，而且只能读取一次，不能濑户的滚动读取
因为这种结果集不支持滚动，所以只能使用它的next()方法，逐个的去读取数据

2、可滚动的ResultSet类型
	逐个类型支持前后滚动取得记录next()、previous()、回到第一行first() 同时还支持要去的ResultSet中的第几行absolute(int n)
	以及移动到相对当前行的第几行relative(int n)
	要实现这样的结果集在创建Statement时使用如下的方法：
	Statement stmt = conn.createStatement(int resultSetType,int resultSetConcurrency) concurrency同时并发产生
	ResultSet rs = stmt.executeQuery(sqlStr)
	
	其中第一个参数的意义：是这是ResultSet对象的类型可滚动，或者不可滚动
	ResultSet.TYPE_FORWARD_ONLY 只能向前滚动
	ResultSet.TYPE_SCROLL_INSENSITIVE(type_scroll_insensitive不敏感)
	ResultSet.TYPE_SCROLL_SENSITIVE(type_scroll_sensitive敏感) 
	后面这个2个都能实现任意的前后滚动，使用各种移动的ResultSet指针的方法，区别在于前者对于修改不敏感，后者对于修改敏感
	type_scroll_sensitive仅针对已经取出来的记录的更改(update/delete)敏感，对新增（insert）不敏感
	
	第2个参数(ReulstSetConcurency)的意义：设置ResultSet对象能够被修改，取值如下：
	ResultSet.CONCUR_READ_ONLY(concur_read_only)只读
	ResultSet.CONCUR_UPDATEABLE(concur_updateable)可修改
	
	
3、可更新的ResultSet
	这样的ResultSet对象可以完成对数据库中表的修改，但是ResultSet只是相当于数据库中表的视图，所以并不是素有的ResultSet只要设置了可更新就可以完成更新的。
	能够完成更新的ResultSet的SQL语句必须具备如下的属性：
	a:只引用了单个表
	b:不含有join或者group by子句
	c:那些列中要包含主关键字
	
*/
	

	
	
	public static void main(String[] args) {
		try {
			java.sql.Statement stmt =  conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			String sql = "select * from test where id = 1";
			
			ResultSet rs = stmt.executeQuery(sql);
			rs.first();
			rs.updateString(1, "你妹");
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
