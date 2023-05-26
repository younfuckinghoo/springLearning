package com.hy.druid.test;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Objects;

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
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i)+"\t\t\t\t");
            }
            System.out.println();
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {

                    String aNull = Objects.requireNonNullElse(resultSet.getObject(i), "null").toString();
                    if (aNull.length()==0){
                        aNull = "null";
                    }
                    System.out.print(aNull +"\t\t\t\t");
                }
                System.out.println();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
