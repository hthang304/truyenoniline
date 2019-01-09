package online.hthang.truyenonline.service;

import online.hthang.truyenonline.entity.Category;

import java.util.List;
import java.util.Optional;

/**
 * @author Huy Thang
 */
public interface CategoryService {

    public List<Category> getCategoryMenu();

    public Optional<Category> getCategoryByID(Integer cID);
}
