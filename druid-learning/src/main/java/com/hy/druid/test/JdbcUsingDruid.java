package com.hy.druid.test;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class JdbcUsingDruid {

    private final DataSource dataSource;

    public JdbcUsingDruid(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void testDataSourceConnection(){
        try {
            Connection connection = this.dataSource.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from BASE_EMPLOYEE WHERE ID IN (?,?)");
            preparedStatement.setLong(1,97213l);
            preparedStatement.setLong(2,116821l);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()) {

//
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
