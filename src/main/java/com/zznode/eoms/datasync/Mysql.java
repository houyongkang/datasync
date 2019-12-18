package com.zznode.eoms.datasync;

import org.apache.log4j.Logger;

import java.sql.*;

public class Mysql {
    private Connection conn = null;
    private Statement stmt = null;
    private static Logger log = Logger.getLogger(Mysql.class.getClass());

    /**
     * @param url
     * @param user
     * @param password
     * @param jdbcDriver
     * @return java.lang.Boolean
     * @Date 2019/12/17
     * @Description 连接数据库
     */
    public Connection connection(String url, String user, String password, String jdbcDriver) {
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(url, user, password);
            log.debug("Mysql 连接成功");
            stmt = conn.createStatement();
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
        return conn;
    }


    /**
     * @param
     * @return java.lang.Boolean
     * @Date 2019/12/17
     * @Description 关闭数据库连接
     */
    public Boolean close() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        log.debug("Mysql 关闭成功");
        return true;
    }

    /**
     * @param tableName
     * @return java.lang.Boolean
     * @Date 2019/12/17
     * @Description 清除表数据
     */
    public Boolean truncate(String tableName) {
        String sql = "truncate table " + tableName;
        try {
            stmt.executeUpdate(sql);
            log.info(tableName + "表已删除");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e);
            return false;
        }

    }

    public void insert(String mysqlUrl, String mysqlUser, String mysqlPassword, String tableName, ResultSet resultSet) {
        PreparedStatement pstm = null;
        try {
            this.connection(mysqlUrl, mysqlUser, mysqlPassword, "com.mysql.cj.jdbc.Driver");
            //拼接sql
            ResultSetMetaData metaData = resultSet.getMetaData();
            int fieldCount = metaData.getColumnCount();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("INSERT INTO " + tableName + " values(");
            for (int i = 1; i < fieldCount + 1; i++) {
                stringBuffer.append("?,");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            stringBuffer.append(")");
            String sql = stringBuffer.toString();
            System.out.println(sql);
            pstm = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            //批量提交
            int currentInsert = 0;
            this.truncate(tableName);
            resultSet.setFetchSize(2000);
            Long startTime = System.currentTimeMillis();
            while (resultSet.next()) {
                for (int i = 1; i < fieldCount + 1; i++) {
                    pstm.setString(i, resultSet.getString(i));
                }
                pstm.addBatch();
                if (currentInsert % 2000 == 0) {
                    pstm.executeBatch();
                }
                currentInsert++;
            }
            pstm.executeBatch();
            conn.commit();
            pstm.clearBatch();
            Long endTime = System.currentTimeMillis();
            System.out.println("用时：" + (endTime - startTime));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            this.close();
        }

    }

}


