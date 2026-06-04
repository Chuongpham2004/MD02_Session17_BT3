package entity;

public class Book {
    private int id;
    private String title;
    private String author;
    private int publishedYear;
    private double price;

    public Book() {
    }

    public Book( String title, String author, int publishedYear, double price) {
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("ID: %-4d | Tieu de: %-25s | Tac gia: %-20s | Nam xuat ban: %-5d | Gia: $%.2f",
                id, title, author, publishedYear, price);
    }
}
