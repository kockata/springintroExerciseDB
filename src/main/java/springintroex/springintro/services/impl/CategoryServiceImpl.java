package springintroex.springintro.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springintroex.springintro.entities.Category;
import springintroex.springintro.repositories.CategoryRepository;
import springintroex.springintro.services.CategoryService;
import springintroex.springintro.util.FileUtil;
import java.io.IOException;

@Service

public class CategoryServiceImpl implements CategoryService {

    private final static String CATEGORY_FILE_PATH = "D:\\Java-Tasks-Hibernate-Spring\\Hibernate\\fourthHibernateExercise\\intellijProject\\src\\main\\resources\\files\\categories.txt";
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedCategory() throws IOException {
        if (this.categoryRepository.count() != 0) {
            return;
        }
        String[] categories = this.fileUtil.fileContent(CATEGORY_FILE_PATH);

        for (String s : categories) {
            Category category = new Category();
            category.setName(s);
            this.categoryRepository.saveAndFlush(category);
        }
    }
}
