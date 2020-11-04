package springintroex.springintro.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException, ParseException;
    List<String> findAllTitles();
    List<String> findAllAuthors();
}
