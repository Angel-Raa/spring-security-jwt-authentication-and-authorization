package com.github.angel.raa.modules.service.implementation;

import com.github.angel.raa.modules.exception.CategoryNotFoundException;
import com.github.angel.raa.modules.exception.ProductNotFoundException;
import com.github.angel.raa.modules.persistence.dto.product.ProductAllListDto;
import com.github.angel.raa.modules.persistence.dto.product.ProductDto;
import com.github.angel.raa.modules.persistence.dto.product.ProductListDto;
import com.github.angel.raa.modules.utils.enums.ProductStatus;
import com.github.angel.raa.modules.persistence.mapper.ProductMapper;
import com.github.angel.raa.modules.persistence.models.Category;
import com.github.angel.raa.modules.persistence.models.Product;
import com.github.angel.raa.modules.persistence.repository.CategoryRepository;
import com.github.angel.raa.modules.persistence.repository.ProductRepository;
import com.github.angel.raa.modules.service.interfaces.ProductService;
import com.github.angel.raa.modules.utils.constants.Message;
import com.github.angel.raa.modules.utils.payload.ApisResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductListDto> findAll() {
        return repository.findAll()
                .stream()
                .map((dto) -> new ProductListDto(dto.getId(), dto.getName(), dto.getDescription(), dto.getPrice(), dto.getCreatedAt(), dto.getStatus()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductAllListDto> findAllAll() {
        return repository.findAll()
                .stream()
                .map((dto) -> new ProductAllListDto(dto.getName(), dto.getDescription(), dto.getPrice(), dto.getStatus(), dto.getCategory().getName()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductListDto> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map((dto) -> new ProductListDto(dto.getId(), dto.getName(), dto.getDescription(), dto.getPrice(), dto.getCreatedAt(), dto.getStatus()));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductListDto findById(Long productId) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(Message.PRODUCT_NOT_FOUND, 404, HttpStatus.NOT_FOUND, LocalDateTime.now()));
        return mapper.toEntity(product);
    }

    @Override
    @Transactional
    public ApisResponse saveProduct(ProductDto body) {
        Category category = categoryRepository.findById(body.category())
                .orElseThrow(( ) -> new CategoryNotFoundException(Message.CATEGORY_NOT_FOUND, 404, HttpStatus.NOT_FOUND, LocalDateTime.now()));
        Product product = mapper.toEntity(body);
        product.setCategory(category);
        repository.save(product);
        return new ApisResponse(Message.PRODUCT_SAVED_SUCCESSFULLY, 201, HttpStatus.CREATED, LocalDateTime.now());
    }

    @Override
    @Transactional
    public ApisResponse updateProduct(Long productId, ProductDto body) {
        Category category = categoryRepository.findById(body.category())
                .orElseThrow(( ) -> new CategoryNotFoundException(Message.CATEGORY_NOT_FOUND, 404, HttpStatus.NOT_FOUND, LocalDateTime.now()));
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(Message.PRODUCT_NOT_FOUND, 404, HttpStatus.NOT_FOUND, LocalDateTime.now()));
        product.setName(body.name());
        product.setDescription(body.description());
        product.setPrice(body.price());
        product.setStatus(ProductStatus.ACTIVE);
        product.setCategory(category);
        repository.save(product);
        return new ApisResponse(Message.PRODUCT_UPDATED_SUCCESSFULLY, 200, HttpStatus.OK, LocalDateTime.now());
    }

    @Override
    @Transactional
    public ApisResponse deleteProduct(Long productId) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(Message.PRODUCT_NOT_FOUND, 404, HttpStatus.NOT_FOUND, LocalDateTime.now()));
        repository.delete(product);
        return new ApisResponse(Message.PRODUCT_DELETED_SUCCESSFULLY, 200, HttpStatus.OK, LocalDateTime.now());
    }

    @Override
    @Transactional
    public ProductListDto disableOneById(Long productId) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(Message.PRODUCT_NOT_FOUND, 404, HttpStatus.NOT_FOUND, LocalDateTime.now()));
        product.setStatus(ProductStatus.INACTIVE);
        repository.save(product);
        return mapper.toEntity(product);
    }
}






