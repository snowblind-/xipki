// #THIRDPARTY# Spring Framework

/*
 * Copyright 2002-2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.xipki.commons.datasource.springframework.jdbc;

import java.sql.SQLException;

import org.xipki.commons.datasource.springframework.dao.UncategorizedDataAccessException;

/**
 *
 * Exception thrown when we can't classify a SQLException into
 * one of our generic data access exceptions.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 */
@SuppressWarnings("serial")
public class UncategorizedSqlException extends UncategorizedDataAccessException {

    /**
     * SQL that led to the problem.
     */
    private final String sql;

    /**
     * Constructor for UncategorizedSQLException.
     * @param task name of current task
     * @param sql the offending SQL statement
     * @param ex the root cause
     */
    public UncategorizedSqlException(
            final String sql,
            final SQLException ex) {
        super("uncategorized SQLException for SQL [" + sql + "]; SQL state ["
                + ex.getSQLState() + "]; error code [" + ex.getErrorCode() + "]; "
                + ex.getMessage(), ex);
        this.sql = sql;
    }

    /**
     * Return the underlying SQLException.
     */
    public SQLException getSqlException() {
        return (SQLException) getCause();
    }

    /**
     * Return the SQL that led to the problem.
     */
    public String getSql() {
        return this.sql;
    }

}