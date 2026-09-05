package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "sale")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "sale_id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", length = 20)
    private DiscountType discountType;

    @Column(name = "discount_value")
    private BigDecimal discountValue;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "sale", fetch = FetchType.LAZY)
    private List<SaleItem> items = new ArrayList<>();

    public Sale(Organization org, Client client, BigDecimal subtotal, DiscountType discountType,
                BigDecimal discountValue, BigDecimal totalAmount) {
        this.org = org;
        this.client = client;
        this.subtotal = subtotal;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.totalAmount = totalAmount;
        this.createdAt = Instant.now();
    }
}
