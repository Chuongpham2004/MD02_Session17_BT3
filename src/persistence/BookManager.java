package persistence;

import entity.Book;
import utils.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookManager {

    public boolean isDuplicate(String title, String author) {
        String sql = "SELECT COUNT(*) FROM books WHERE title = ? AND author = ?";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking for duplicate book: " + e.getMessage());
        }
        return false;
    }

    public boolean isIdExsit(int id) {
        String sql = "SELECT COUNT(*) FROM books WHERE id = ?";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking for existing ID: " + e.getMessage());
        }
        return false;
    }

    public boolean addBook(Book book) {
        if (isDuplicate(book.getTitle(), book.getAuthor())) {
            System.err.println("Error: Book with the same title and author already exists.");
            return false;
        }

        String sql = "CALL add_book(?, ?, ?, ?)";
        try (Connection conn = ConnectionDB.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, book.getTitle());
            cstmt.setString(2, book.getAuthor());
            cstmt.setInt(3, book.getPublishedYear());
            cstmt.setBigDecimal(4, new java.math.BigDecimal(book.getPrice()));
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding book: " + e.getMessage());
            return false;
        }
    }

    public boolean updateBook(int id, Book book) {
        if (!isIdExsit(id)) {
            System.out.println("Book with ID " + id + " does not exist.");
            return false;
        }
        String sql = "CALL update_book(?, ?, ?, ?,?)";
        try (Connection conn = ConnectionDB.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setInt(1, id);
            cstmt.setString(2, book.getTitle());
            cstmt.setString(3, book.getAuthor());
            cstmt.setInt(4, book.getPublishedYear());
            cstmt.setBigDecimal(5, new java.math.BigDecimal(book.getPrice()));
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error updating book: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteBook(int id) {
        if (!isIdExsit(id)) {
            System.out.println("Book with ID " + id + " does not exist.");
            return false;
        }
        String sql = "CALL delete_book(?)";
        try (Connection conn = ConnectionDB.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setInt(1, id);
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error deleting book: " + e.getMessage());
            return false;
        }
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE author = ?";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, author);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPublishedYear(resultSet.getInt("published_year"));
                book.setPrice(resultSet.getDouble("price"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Error finding books by author: " + e.getMessage());
        }
        return books;
    }

    public List<Book> listAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPublishedYear(rs.getInt("published_year"));
                book.setPrice(rs.getDouble("price"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Error listing all books: " + e.getMessage());
        }
        return books;
    }
}
