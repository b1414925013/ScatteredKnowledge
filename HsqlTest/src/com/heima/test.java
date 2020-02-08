/**
 * @Title:  test.java
 * @Package com.heima
 * @Description:    描述
 * @author: bianjianfneg
 * @date:   2020年1月13日 下午9:10:45
 * @version V1.0
 */
package com.heima;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @ClassName:  test
 * @Description:TODO(描述这个类的作用)
 * @author: bianjianfeng
 * @date:   2020年1月13日 下午9:10:45
 */
public class test {
    public static void main(String args[]) {
        test2();

    }

    /**
     * @Title: test2
     * @Description: 链接本地文件的数据库
     * @return: void
     * @throws
     */
    private static void test2() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            final Connection conn =
                DriverManager.getConnection("jdbc:hsqldb:file:D:\\testhsqldb\\dbname", "root", "root");
            System.out.println("conn = " + conn);

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @Title: test1
     * @Description: 纯内存数据库
     * @return: void
     * @throws
     */
    public static void test1() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            final Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:ceshidb", "root", "root");
            System.out.println("conn = " + conn);

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
