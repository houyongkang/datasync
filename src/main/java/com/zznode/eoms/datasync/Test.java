package com.zznode.eoms.datasync;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Test {
    public static void main(String[] args) {
        Properties properties = new Properties();
        InputStream inputStream = Object.class.getResourceAsStream("/config.cfg");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoadBean loadBean=new LoadBean();
        loadBean.load();

        List<String> tables = new ArrayList<String>(Arrays.asList(loadBean.getTablesTemp().split(",")));
        Oracle oracle = new Oracle();
        oracle.connection(loadBean.getOracleUrl(), loadBean.getOracleUser(), loadBean.getOraclePassword());
        Mysql mysql = new Mysql();
        PreparedStatement pstm = null;
        for (String table : tables) {
            ResultSet resultSet=oracle.select(table);
            mysql.insert(loadBean.getMysqlUrl(),loadBean.getMysqlUser(),loadBean.getMysqlPassword(),table,resultSet);
        }
        mysql.close();
        oracle.close();
    }
}



