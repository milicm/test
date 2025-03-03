package com.production.app.controller;

import com.production.app.dto.ProductDTO;
import com.production.app.model.Production;
import com.production.app.model.ProductionInventory;
import com.production.app.service.ProductionService;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Milos
 */
@RestController
@RequestMapping("/productions")
public class ProductionController {

    private final ProductionService productionService;

    private final RestTemplate restTemplate;

    @Value("${app.service.url}")
    private String appServiceUrl;

    public ProductionController(ProductionService productionService, RestTemplate restTemplate) {
        this.productionService = productionService;
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public Production createProduction(@RequestBody ProductionRequest request) {
        return productionService.createProduction(request.getProduction(), request.getInventories());
    }

    @GetMapping("/inventory-usage/{inventoryId}")
    public int getTotalInventoryUsage(@PathVariable Long inventoryId) {
        return productionService.getTotalInventoryUsage(inventoryId);
    }

    @GetMapping("/product-produced/{productId}")
    public int getTotalProductsProduced(@PathVariable Long productId) {
        return productionService.getTotalProductsProduced(productId);
    }

    @GetMapping("/report")
    public List<ProductDTO> report() {
        ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(
                appServiceUrl + "/products/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDTO>>() {
        }
        );
        return response.getBody();
    }

}

@Data
class ProductionRequest {

    private Production production;
    private List<ProductionInventory> inventories;
}
