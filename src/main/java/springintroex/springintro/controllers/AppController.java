package springintroex.springintro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import springintroex.springintro.services.AuthorService;
import springintroex.springintro.services.BookService;
import springintroex.springintro.services.CategoryService;

import java.util.List;

@Controller
public class AppController implements CommandLineRunner {
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    @Autowired
    public AppController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.authorService.seedAuthors();
        this.categoryService.seedCategory();
        this.bookService.seedBooks();
        //allTitlesAfterYear2000();
        this.allAuthors();
    }

    private void allTitlesAfterYear2000() {
        List<String> titles = this.bookService.findAllTitles();
        titles.forEach(System.out::println);
    }

    private void allAuthors (){
        List<String> authors = this.bookService.findAllAuthors();
        authors.forEach(System.out::println);
    }
}
