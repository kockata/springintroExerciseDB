package springintroex.springintro.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springintroex.springintro.entities.Author;
import springintroex.springintro.repositories.AuthorRepository;
import springintroex.springintro.services.AuthorService;
import springintroex.springintro.util.FileUtil;

import java.io.IOException;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final static String AUTHOR_PATH = "D:\\Java-Tasks-Hibernate-Spring\\Hibernate\\fourthHibernateExercise\\intellijProject\\src\\main\\resources\\files\\authors.txt";
    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;


    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() != 0) {
            return;
        }
        String[] authors = this.fileUtil.fileContent(AUTHOR_PATH);
        for (String s : authors) {
            String[] params = s.split("\\s+");
            Author author = new Author();
            author.setFirstName(params[0]);
            author.setLastName(params[1]);
            this.authorRepository.saveAndFlush(author);
        }
    }
}
