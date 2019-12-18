package com.zznode.eoms.datasync;

/**
 * @Description
 * @Date 2019/12/18
 * @Author yongkang.hou
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadBean {
    private String oracleUrl;
    private String oracleUser;
    private String oraclePassword;
    private String mysqlUrl;
    private String mysqlUser;
    private String mysqlPassword;
    private String tablesTemp;

    public void load() {
        Properties properties = new Properties();
        InputStream inputStream = Object.class.getResourceAsStream("/config.cfg");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.oracleUrl = (String) properties.get("oracle_url");
        this.oracleUser = (String) properties.get("oracle_user");
        this.oraclePassword = (String) properties.get("oracle_password");
        this.mysqlUrl = (String) properties.get("mysql_url");
        this.mysqlUser = (String) properties.get("mysql_user");
        this.mysqlPassword = (String) properties.get("mysql_password");
        this.tablesTemp = (String) properties.get("tables");
    }

    public String getOracleUrl() {
        return oracleUrl;
    }

    public void setOracleUrl(String oracleUrl) {
        this.oracleUrl = oracleUrl;
    }

    public String getOracleUser() {
        return oracleUser;
    }

    public void setOracleUser(String oracleUser) {
        this.oracleUser = oracleUser;
    }

    public String getOraclePassword() {
        return oraclePassword;
    }

    public void setOraclePassword(String oraclePassword) {
        this.oraclePassword = oraclePassword;
    }

    public String getMysqlUrl() {
        return mysqlUrl;
    }

    public void setMysqlUrl(String mysqlUrl) {
        this.mysqlUrl = mysqlUrl;
    }

    public String getMysqlUser() {
        return mysqlUser;
    }

    public void setMysqlUser(String mysqlUser) {
        this.mysqlUser = mysqlUser;
    }

    public String getMysqlPassword() {
        return mysqlPassword;
    }

    public void setMysqlPassword(String mysqlPassword) {
        this.mysqlPassword = mysqlPassword;
    }

    public String getTablesTemp() {
        return tablesTemp;
    }

    public void setTablesTemp(String tablesTemp) {
        this.tablesTemp = tablesTemp;
    }
}
