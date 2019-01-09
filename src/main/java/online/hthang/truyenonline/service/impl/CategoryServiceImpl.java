package online.hthang.truyenonline.service.impl;

import online.hthang.truyenonline.entity.Category;
import online.hthang.truyenonline.repository.CategoryRepository;
import online.hthang.truyenonline.service.CategoryService;
import online.hthang.truyenonline.utils.ConstantsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Huy Thang
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategoryMenu() {
        return categoryRepository.findByCStatus(ConstantsUtils.STATUS_ACTIVED);
    }

    @Override
    public Optional<Category> getCategoryByID(Integer cID) {
        return categoryRepository.findByCIDAndCStatus(cID, ConstantsUtils.STATUS_ACTIVED);
    }

}
