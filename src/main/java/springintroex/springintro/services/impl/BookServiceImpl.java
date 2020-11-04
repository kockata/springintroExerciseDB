package springintroex.springintro.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springintroex.springintro.entities.*;
import springintroex.springintro.repositories.AuthorRepository;
import springintroex.springintro.repositories.BookRepository;
import springintroex.springintro.repositories.CategoryRepository;
import springintroex.springintro.services.BookService;
import springintroex.springintro.util.FileUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final static String BOOKS_FILE_PATH = "D:\\Java-Tasks-Hibernate-Spring\\Hibernate\\fourthHibernateExercise\\intellijProject\\src\\main\\resources\\files\\books.txt";
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() != 0){
            return;
        }
        String[] books = this.fileUtil.fileContent(BOOKS_FILE_PATH);
        for (String s : books) {
            String[] params = s.split("\\s+");
            Book book = new Book();
            book.setAuthor(randomAuthor());

            EditionType editionType = EditionType.values()[Integer.parseInt(params[0])];
            book.setEditionType(editionType);

            LocalDate releaseDate = LocalDate.parse(params[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setReleaseDate(releaseDate);

            int copies = Integer.parseInt(params[2]);
            book.setCopies(copies);
            BigDecimal price = new BigDecimal(params[3]);
            book.setPrice(price);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(params[4])];
            book.setAgeRestriction(ageRestriction);

            StringBuilder titleBuilder = new StringBuilder();
            for (int i = 5; i <= params.length-1; i++) {
                titleBuilder.append(params[i]).append(" ");
            }
            titleBuilder.delete(titleBuilder.lastIndexOf(" "), titleBuilder.lastIndexOf(" "));
            String title = titleBuilder.toString();
            book.setTitle(title);

            book.setCategories(randomCategories());

            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<String> findAllTitles() {
        LocalDate releaseDate = LocalDate.parse("31/12/2000", DateTimeFormatter.ofPattern("d/M/yyyy"));
        return this.bookRepository
                .findAllByReleaseDateAfter(releaseDate)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllAuthors() {
        LocalDate releaseDate = LocalDate.parse("1/1/1990", DateTimeFormatter.ofPattern("d/M/yyyy"));
        return this.bookRepository.findAllByReleaseDateBefore(releaseDate)
                .stream()
                .map(b-> String.format("%s %s", b.getAuthor().getFirstName(), b.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    private Author randomAuthor() {
        Random random = new Random();
        int index = random.nextInt((int)this.authorRepository.count()) + 1;
        return this.authorRepository.getOne(index);
    }

    private Category randomCategory() {
        Random random = new Random();
        int index = random.nextInt((int)this.categoryRepository.count()) + 1;
        return this.categoryRepository.getOne(index);
    }

    private Set<Category> randomCategories(){
        Set<Category> categories = new HashSet<>();
        Random random = new Random();
        int index = random.nextInt((int) this.categoryRepository.count()) +1;
        for (int i = 0; i < index; i++) {
            categories.add(randomCategory());
        }
        return categories;
    }
}
