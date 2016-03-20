/*
 *
 * This file is part of the XiPKI project.
 * Copyright (c) 2013 - 2016 Lijun Liao
 * Author: Lijun Liao
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License version 3
 * as published by the Free Software Foundation with the addition of the
 * following permission added to Section 15 as permitted in Section 7(a):
 *
 * FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
 * THE AUTHOR LIJUN LIAO. LIJUN LIAO DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
 * OF THIRD PARTY RIGHTS.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * The interactive user interfaces in modified source and object code versions
 * of this program must display Appropriate Legal Notices, as required under
 * Section 5 of the GNU Affero General Public License.
 *
 * You can be released from the requirements of the license by purchasing
 * a commercial license. Buying such a license is mandatory as soon as you
 * develop commercial activities involving the XiPKI software without
 * disclosing the source code of your own applications.
 *
 * For more information, please contact Lijun Liao at this
 * address: lijun.liao@gmail.com
 */

package org.xipki.commons.dbtool;

import java.util.Objects;
import java.util.Properties;

import org.xipki.commons.password.api.PasswordResolver;
import org.xipki.commons.password.api.PasswordResolverException;

/**
 * @author Lijun Liao
 * @since 2.0.0
 */

public class LiquibaseDatabaseConf {

    private final String driver;

    private final String username;

    private final String password;

    private final String url;

    private final String schema;

    public LiquibaseDatabaseConf(
            final String driver,
            final String username,
            final String password,
            final String url,
            final String schema) {
        this.driver = Objects.requireNonNull(driver, "driver must not be null");
        this.username = Objects.requireNonNull(username, "username must not be null");
        this.password = password;
        this.url = Objects.requireNonNull(url, "url must not be null");
        this.schema = schema;
    }

    public String getDriver() {
        return driver;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getSchema() {
        return schema;
    }

    public static LiquibaseDatabaseConf getInstance(
            final Properties dbProps,
            final PasswordResolver passwordResolver)
    throws PasswordResolverException {
        Objects.requireNonNull(dbProps, "dbProps must no be null");

        String driverClassName;
        String url;
        String schema = null;
        String user;
        String password;

        String datasourceClassName = dbProps.getProperty("datasourceClassName");
        if (datasourceClassName != null) {
            user = dbProps.getProperty("datasource.user");
            password = dbProps.getProperty("datasource.password");

            StringBuilder urlBuilder = new StringBuilder();

            datasourceClassName = datasourceClassName.toLowerCase();
            if (datasourceClassName.contains("org.h2.")) {
                driverClassName = "org.h2.Driver";
                urlBuilder.append(dbProps.getProperty("datasource.url"));
            } else if (datasourceClassName.contains("mysql.")) {
                driverClassName = "com.mysql.jdbc.Driver";
                urlBuilder.append("jdbc:mysql://");
                urlBuilder.append(dbProps.getProperty("datasource.serverName"));
                urlBuilder.append(":");
                urlBuilder.append(dbProps.getProperty("datasource.port"));
                urlBuilder.append("/");
                urlBuilder.append(dbProps.getProperty("datasource.databaseName"));
            } else if (datasourceClassName.contains("oracle.")) {
                driverClassName = "oracle.jdbc.driver.OracleDriver";
                String str = dbProps.getProperty("datasource.URL");
                if (str != null && !str.isEmpty()) {
                    urlBuilder.append(str);
                } else {
                    urlBuilder.append("jdbc:oracle:thin:@");
                    urlBuilder.append(dbProps.getProperty("datasource.serverName"));
                    urlBuilder.append(":");
                    urlBuilder.append(dbProps.getProperty("datasource.portNumber"));
                    urlBuilder.append(":");
                    urlBuilder.append(dbProps.getProperty("datasource.databaseName"));
                }
            } else if (datasourceClassName.contains("com.ibm.db2.")) {
                driverClassName = "com.ibm.db2.jcc.DB2Driver";
                schema = dbProps.getProperty("datasource.currentSchema");

                urlBuilder.append("jdbc:db2://");
                urlBuilder.append(dbProps.getProperty("datasource.serverName"));
                urlBuilder.append(":");
                urlBuilder.append(dbProps.getProperty("datasource.portNumber"));
                urlBuilder.append("/");
                urlBuilder.append(dbProps.getProperty("datasource.databaseName"));
            } else if (datasourceClassName.contains("postgresql.")
                    || datasourceClassName.contains("impossibl.postgres.")) {
                String serverName;
                String portNumber;
                String databaseName;
                if (datasourceClassName.contains("postgresql.")) {
                    serverName = dbProps.getProperty("datasource.serverName");
                    portNumber = dbProps.getProperty("datasource.portNumber");
                    databaseName = dbProps.getProperty("datasource.databaseName");
                } else {
                    serverName = dbProps.getProperty("datasource.host");
                    portNumber = dbProps.getProperty("datasource.port");
                    databaseName = dbProps.getProperty("datasource.database");
                }
                driverClassName = "org.postgresql.Driver";
                urlBuilder.append("jdbc:postgresql://");
                urlBuilder.append(serverName)
                    .append(":")
                    .append(portNumber)
                    .append("/")
                    .append(databaseName);
            } else if (datasourceClassName.contains("hsqldb.")) {
                driverClassName = "org.hsqldb.jdbc.JDBCDriver";
                urlBuilder.append(dbProps.getProperty("datasource.url"));
            } else {
                throw new IllegalArgumentException(
                        "unsupported datasbase type " + datasourceClassName);
            }

            url = urlBuilder.toString();
        } else if (dbProps.containsKey("driverClassName")
                || dbProps.containsKey("db.driverClassName")) {
            if (dbProps.containsKey("driverClassName")) {
                driverClassName = dbProps.getProperty("driverClassName");
                user = dbProps.getProperty("username");
                password = dbProps.getProperty("password");
                url = dbProps.getProperty("jdbcUrl");
            } else {
                driverClassName = dbProps.getProperty("db.driverClassName");
                user = dbProps.getProperty("db.username");
                password = dbProps.getProperty("db.password");
                url = dbProps.getProperty("db.url");
            }

            if (startsWithIgnoreCase(url, "jdbc:db2:")) {
                String sep = ":currentSchema=";
                int idx = url.indexOf(sep);
                if (idx != 1) {
                    schema = url.substring(idx + sep.length());
                    if (schema.endsWith(";")) {
                        schema = schema.substring(0, schema.length() - 1);
                    }
                    schema = schema.toUpperCase();
                    url = url.substring(0, idx);
                }
            }
        } else {
            throw new IllegalArgumentException("unsupported configuration");
        }

        if (passwordResolver != null && (password != null && !password.isEmpty())) {
            password = new String(passwordResolver.resolvePassword(password));
        }

        return new LiquibaseDatabaseConf(driverClassName, user, password, url, schema);
    } // method getInstance

    public static boolean isNotBlank(
            final String str) {
        return str != null && !str.isEmpty();
    }

    private static boolean startsWithIgnoreCase(
            final String str,
            final String prefix) {
        if (str.length() < prefix.length()) {
            return false;
        }

        return prefix.equalsIgnoreCase(str.substring(0, prefix.length()));
    }
}