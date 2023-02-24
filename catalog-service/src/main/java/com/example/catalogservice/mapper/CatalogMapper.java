package com.example.catalogservice.mapper;

import com.example.catalogservice.dto.CatalogDto;
import com.example.catalogservice.entity.CatalogEntity;
import org.springframework.stereotype.Component;

@Component
public class CatalogMapper {

    public CatalogDto toDto(CatalogEntity catalogEntity) {
        return CatalogDto.builder()
                .productId(catalogEntity.getId())
                .productName(catalogEntity.getName())
                .unitPrice(catalogEntity.getUnitPrice())
                .stock(catalogEntity.getStock())
                .build();
    }
}
