package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.elastic.repository.ElasticBoardRepository;
import com.nhnacademy.marketgg.server.elastic.repository.ElasticProductRepository;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.exception.categorization.CategorizationNotFoundException;
import com.nhnacademy.marketgg.server.exception.category.CategoryNotFoundException;
import com.nhnacademy.marketgg.server.repository.categorization.CategorizationRepository;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.customerservicepost.CustomerServicePostRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import com.nhnacademy.marketgg.server.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategorizationRepository categorizationRepository;
    private final ElasticProductRepository elasticProductRepository;
    private final ElasticBoardRepository elasticBoardRepository;
    private final ProductRepository productRepository;
    private final CustomerServicePostRepository customerServicePostRepository;

    @Transactional
    @Override
    public void createCategory(final CategoryCreateRequest createRequest) {
        Categorization categorization =
                categorizationRepository.findById(createRequest.getCategorizationCode())
                                        .orElseThrow(CategorizationNotFoundException::new);

        Category category = new Category(createRequest, categorization);

        categoryRepository.save(category);
    }

    @Override
    public CategoryRetrieveResponse retrieveCategory(final String id) {
        return categoryRepository.findByCode(id);
    }

    @Override
    public List<CategoryRetrieveResponse> retrieveCategoriesByCategorization(
            String categorizationId) {
        return categoryRepository.findByCategorizationCode(categorizationId);
    }

    @Override
    public List<CategoryRetrieveResponse> retrieveCategories() {
        return categoryRepository.findAllCategories();
    }

    @Transactional
    @Override
    public void updateCategory(final String id, final CategoryUpdateRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                                              .orElseThrow(CategoryNotFoundException::new);

        category.updateCategory(categoryRequest);

        categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void deleteCategory(final String id) {
        Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);

        if (productRepository.existsByCategory(category) ||
                customerServicePostRepository.existsByCategory(category)) {
            return;
        }
        categoryRepository.delete(category);
        elasticProductRepository.deleteAllByCategoryCode(id);
        elasticBoardRepository.deleteAllByCategoryCode(id);
    }

}
