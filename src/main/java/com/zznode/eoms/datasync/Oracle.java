package com.zznode.eoms.datasync;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Oracle {
    private Connection conn = null;
    private PreparedStatement pre = null;
    private static Logger log = Logger.getLogger(Oracle.class.getClass());

    /**
     * @param url
     * @param user
     * @param password
     * @return java.lang.Boolean
     * @Date 2019/12/17
     * @Description 连接数据库
     */
    Boolean connection(String url, String user, String password) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
            conn = DriverManager.getConnection(url, user, password);// 获取连接
            log.debug("Oracle 连接成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            return false;
        }
        return true;
    }

    /**
     * @param
     * @return java.lang.Boolean
     * @Date 2019/12/17
     * @Description 关闭连接
     */
    Boolean close() {
        try {
            if (null != conn) {
                conn.close();
            }
            if (null != pre) {
                pre.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.debug("Oracle 关闭成功");
        return true;
    }


    /**
     * @param table
     * @return java.util.List<java.sql.ResultSet>
     * @Date 2019/12/17
     * @Description 获取结果集
     */
    ResultSet select(String table) {
        String sql = "select * from " + table;
        ResultSet result = null;
        try {
            pre = conn.prepareStatement(sql);
            result = pre.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e);
        }
        return result;
    }

    String formatFieldVlaue(ResultSet result) {
        String fields = "";
        try {
            ResultSetMetaData metaData = result.getMetaData();
            int fieldCount = metaData.getColumnCount();
            for (int i = 1; i < fieldCount + 1; i++) {
                if (i == fieldCount) {
                    fields = fields + "'" + result.getString(i) + "'";
                } else {
                    fields = fields + "'" + result.getString(i) + "',";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  fields;
    }
}
