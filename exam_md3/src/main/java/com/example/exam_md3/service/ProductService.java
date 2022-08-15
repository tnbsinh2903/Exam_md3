package com.example.exam_md3.service;

import com.example.exam_md3.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private String jdbcURL = "jdbc:mysql://localhost:3306/exam_md3?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456789";

    private static final String SELECT_ALL_PRODUCT = "select * from product ";
    private static final String SELECT_PRODUCT_BY_ID = "select id, name, price,quantity,color, description,idCate from product where id = ? ;";
    private static final String INSERT_PRODUCT = "insert into product (name,price,quantity,color,description,idCate) values (?,?,?,?,?,?);";
    private static final String DELETE_PRODUCT = "delete from product where id = ?";
    private static final String UPDATE_Product = "update product set name = ?, price = ? , quantity = ?,color = ?, description = ?, idCate = ?  where id = ?;";



    protected Connection connection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public List<Product> selectAllProduct() {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCT)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String color = rs.getString("color");
                String description = rs.getString("description");
                int idCate = rs.getInt("idCate");
                productList.add(new Product(id, name, price, quantity, color,description,idCate));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return productList;
    }

    public Product selectProductById(int id) {
        Product product = null;
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                double price = Double.parseDouble(rs.getString("price"));
                int quantity = Integer.parseInt(rs.getString("quantity"));
                String color = rs.getString("color");
                String description = rs.getString("description");
                int idCate = Integer.parseInt(rs.getString("idCate"));
                product = new Product(id,name, price, quantity,color,description,idCate);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    public void insertProduct(Product product) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getQuantity());
            statement.setString(4, product.getColor());
            statement.setString(5,product.getDescription());
            statement.setInt(6, product.getIdCate());
            statement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    public boolean deleteProduct(int id) throws SQLException {
        boolean delete ;
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT)) {
            statement.setInt(1, id);
            delete = statement.executeUpdate() > 0;
        }
        return delete ;
    }

    public boolean updateProduct(Product product) throws SQLException {
        boolean rowUpdate;
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_Product);) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getQuantity());
            statement.setString(4, product.getColor());
            statement.setString(5,product.getDescription());
            statement.setInt(6, product.getIdCate());
            statement.setInt(7, product.getId());
            rowUpdate = statement.executeUpdate() > 0;
        }
        return rowUpdate;
    }
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause " + t);
                    t = t.getCause();
                }
            }
        }
    }

}
