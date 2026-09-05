package com.example.demo.service.impl;

import com.example.demo.exception.EntryNotFoundException;
import com.example.demo.model.CatalogItem;
import com.example.demo.model.Client;
import com.example.demo.model.ClientOrganization;
import com.example.demo.model.DiscountType;
import com.example.demo.model.Organization;
import com.example.demo.model.Sale;
import com.example.demo.model.SaleItem;
import com.example.demo.repository.CatalogRepository;
import com.example.demo.repository.ClientOrganizationRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.SaleItemRepository;
import com.example.demo.repository.SaleRepository;
import com.example.demo.service.OrganizationService;
import com.example.demo.service.SalesService;
import com.example.demo.transfer.SaleItemSummary;
import com.example.demo.transfer.SaleSummary;
import com.example.demo.transfer.create.CreateSale;
import com.example.demo.transfer.create.CreateSaleItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {

    private final SaleRepository saleRepository;

    private final SaleItemRepository saleItemRepository;

    private final CatalogRepository catalogRepository;

    private final ClientRepository clientRepository;

    private final ClientOrganizationRepository clientOrganizationRepository;

    private final OrganizationService organizationService;

    @Override
    @Transactional
    public SaleSummary registerSale(CreateSale input) {
        Organization org = organizationService.getOrganization(input.getOrgID());
        Client client = resolveClient(input.getCustomerMobile(), org);

        BigDecimal subtotal = BigDecimal.ZERO;
        List<SaleItem> saleItems = new ArrayList<>();
        for (CreateSaleItem itemInput : input.getItems()) {
            CatalogItem catalogItem = catalogRepository.findById(itemInput.getCatalogItemID())
                    .orElseThrow(() -> new EntryNotFoundException("Catalog Item Not Found"));

            BigDecimal effectivePrice = itemInput.getDiscountPrice() != null
                    ? itemInput.getDiscountPrice()
                    : itemInput.getPrice();
            subtotal = subtotal.add(effectivePrice.multiply(BigDecimal.valueOf(itemInput.getQty())));

            saleItems.add(new SaleItem(null, catalogItem, itemInput.getQty(), itemInput.getPrice(), itemInput.getDiscountPrice()));
        }

        BigDecimal discountAmount = computeDiscountAmount(subtotal, input.getDiscountType(), input.getDiscountValue());
        BigDecimal totalAmount = subtotal.subtract(discountAmount);

        Sale sale = saleRepository.save(new Sale(
                org, client, subtotal, input.getDiscountType(), input.getDiscountValue(), totalAmount
        ));
        saleItems.forEach(saleItem -> saleItem.setSale(sale));
        saleItemRepository.saveAll(saleItems);

        return toSummary(sale, saleItems);
    }

    /** Whole-sale discount, clamped so it can never take the total below zero. */
    private BigDecimal computeDiscountAmount(BigDecimal subtotal, DiscountType discountType, BigDecimal discountValue) {
        if (discountType == null || discountValue == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal amount = discountType == DiscountType.PERCENTAGE
                ? subtotal.multiply(discountValue).divide(BigDecimal.valueOf(100))
                : discountValue;
        if (amount.compareTo(BigDecimal.ZERO) < 0) return BigDecimal.ZERO;
        return amount.min(subtotal);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SaleSummary> getAllSales(UUID orgId, Pageable pageable) {
        return saleRepository.findAllByOrg_Id(orgId, pageable).map(this::toSummary);
    }

    @Override
    @Transactional(readOnly = true)
    public long countSales(UUID orgId) {
        return saleRepository.countByOrg_Id(orgId);
    }

    /** Finds the customer by mobile number within this organization, or creates one (linking it to the org) if none exists yet. */
    private Client resolveClient(Long customerMobile, Organization org) {
        if (customerMobile == null) {
            return null;
        }

        Client client = clientRepository.findClientByMobile(customerMobile)
                .orElseGet(() -> clientRepository.save(new Client(customerMobile)));

        boolean alreadyLinkedToOrg = clientOrganizationRepository.findAllByClient_Id(client.getId()).stream()
                .anyMatch(clientOrganization -> clientOrganization.getOrg().getId().equals(org.getId()));
        if (!alreadyLinkedToOrg) {
            clientOrganizationRepository.save(new ClientOrganization(client, org));
        }

        return client;
    }

    private SaleSummary toSummary(Sale sale) {
        return toSummary(sale, sale.getItems());
    }

    private SaleSummary toSummary(Sale sale, List<SaleItem> saleItems) {
        List<SaleItemSummary> itemSummaries = saleItems.stream()
                .map(saleItem -> new SaleItemSummary(
                        saleItem.getCatalogItem().getId(),
                        saleItem.getCatalogItem().getItem().getItemName(),
                        saleItem.getQty(),
                        saleItem.getPrice(),
                        saleItem.getDiscountPrice()
                ))
                .toList();

        return new SaleSummary(
                sale.getId(),
                sale.getOrg().getId(),
                sale.getClient() == null ? null : sale.getClient().getId(),
                sale.getClient() == null ? null : sale.getClient().getMobile(),
                sale.getSubtotal(),
                sale.getDiscountType(),
                sale.getDiscountValue(),
                sale.getTotalAmount(),
                sale.getCreatedAt(),
                itemSummaries
        );
    }
}
