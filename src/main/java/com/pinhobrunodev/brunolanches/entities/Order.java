package com.pinhobrunodev.brunolanches.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinhobrunodev.brunolanches.entities.enums.OrderStatus;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime moment;
    private OrderStatus status;
    private Boolean isInProgress;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;
    @ManyToOne
    @JoinColumn(name = "driver_id")
    public Driver driver;
    @ManyToMany
    @JoinTable(name = "tb_order_item", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> items = new HashSet<>();

    public Order() {
    }


    public Boolean getInProgress() {
        return isInProgress;
    }

    public void setInProgress(Boolean inProgress) {
        isInProgress = inProgress;
    }

    public Set<Product> getItems() {
        return items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }


    public LocalDateTime getMoment() {
        return moment;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @PrePersist
    public void prePersist() {
        this.moment = LocalDateTime.now();
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
