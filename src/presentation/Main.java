package presentation;

import entity.Book;
import persistence.BookManager;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static BookManager bookManager = new BookManager();

    public static void main(String[] args) {
        int choice = -1;
        while (true) {
            System.out.println("\n==== HE THONG QUAN LY THU VIEN ====");
            System.out.println("1. Them sach moi");
            System.out.println("2. Cap nhat thong tin sach");
            System.out.println("3. Xoa sach");
            System.out.println("4. Tim kiem theo tac gia");
            System.out.println("5. Hien thi tat ca sach");
            System.out.println("0. Thoat");
            System.out.print("Nhap lua chon: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lua chon khong hop le. Vui long nhap lai.");
                continue;
            }

            switch (choice) {
                case 1:
                    handleAddBook();
                    break;
                case 2:
                    handleUpdateBook();
                    break;
                case 3:
                    handleDeleteBook();
                    break;
                case 4:
                    handleSearchByAuthor();
                    break;
                case 5:
                    handleDisplayAllBooks();
                    break;
                case 0:
                    System.out.println("Cam on ban da su dung");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lua chon khong hop le. Vui long nhap lai.");
                    break;
            }
        }
    }

    public static void handleAddBook() {
        System.out.println("\n==== THEM SACH MOI ====");
        try {
            System.out.println("Nhap tieu de: ");
            String title = scanner.nextLine();
            if (title.isEmpty()) {
                System.out.println("Error: Tieu de khong duoc de trong.");
                return;
            }

            System.out.println("Nhap ten tac gia: ");
            String author = scanner.nextLine();
            if (author.isEmpty()) {
                System.out.println("Error: Ten tac gia khong duoc de trong.");
                return;
            }

            System.out.println("Nhap nam xuat ban: ");
            int publishedYear = Integer.parseInt(scanner.nextLine().trim());
            System.out.println("Nhap gia: ");
            double price = Double.parseDouble(scanner.nextLine().trim());

            Book book = new Book(title, author, publishedYear, price);
            if (bookManager.addBook(book)) {
                System.out.println("=> Them sach thanh cong!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Nam xuat ban va gia phai la so. Vui long nhap lai.");
        }
    }

    public static void handleUpdateBook() {
        System.out.println("\n==== CAP NHAT THONG TIN SACH ====");
        try {
            System.out.println("Nhap ID sach can cap nhat: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.println("Nhap tieu de moi: ");
            String title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                System.out.println("Error: Tieu de khong duoc de trong.");
                return;
            }
            System.out.println("Nhap ten tac gia moi: ");
            String author = scanner.nextLine().trim();
            if (author.isEmpty()) {
                System.out.println("Error: Ten tac gia khong duoc de trong.");
                return;
            }
            System.out.println("Nhap nam xuat ban moi: ");
            int publishedYear = Integer.parseInt(scanner.nextLine().trim());
            System.out.println("Nhap gia moi: ");
            double price = Double.parseDouble(scanner.nextLine().trim());

            Book updateInfo = new Book(title, author, publishedYear, price);
            if (bookManager.updateBook(id, updateInfo)) {
                System.out.println("=> Cap nhat sach thanh cong!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: ID, Nam xuat ban va gia phai la so. Vui long nhap lai.");
        }
    }

    public static void handleDeleteBook() {
        System.out.println("\n==== XOA SACH ====");
        try {
            System.out.println("Nhap ID sach can xoa: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            if (bookManager.deleteBook(id)) {
                System.out.println("=> Xoa sach thanh cong!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: ID phai la so. Vui long nhap lai.");
        }
    }

    public static void handleSearchByAuthor() {
        System.out.println("\n==== TIM KIEM THEO TAC GIA ====");
        System.out.println("Nhap ten tac gia can tim: ");
        String author = scanner.nextLine().trim();
        if (author.isEmpty()) {
            System.out.println("Error: Ten tac gia khong duoc de trong.");
            return;
        }
        List<Book> foundBooks = bookManager.findBooksByAuthor(author);
        if (foundBooks.isEmpty()) {
            System.out.println("=> Khong tim thay sach nao cua tac gia " + author);
        } else {
            System.out.println("=> Tim thay " + foundBooks.size() + " sach cua tac gia " + author + ":");
            foundBooks.forEach(System.out::println);
        }
    }

    public static void handleDisplayAllBooks() {
        System.out.println("\n==== DANH SACH TAT CA SACH ====");
        List<Book> allBooks = bookManager.listAllBooks();
        if (allBooks.isEmpty()) {
            System.out.println("=> Khong co sach nao trong thu vien.");
        } else {
            allBooks.forEach(System.out::println);
        }
    }
}