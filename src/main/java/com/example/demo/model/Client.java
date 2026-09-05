package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "client")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "client_id", nullable = false)
    private UUID id;

    @Column(name = "username", length = Integer.MAX_VALUE)
    private String username;

    @Column(name = "mobile", nullable = false)
    private Long mobile;

    public Client(String username, Long mobile) {
        this.username = username;
        this.mobile = mobile;
    }

    /** Walk-in customer captured by mobile number only (e.g. at checkout), no name on file yet. */
    public Client(Long mobile) {
        this.mobile = mobile;
    }
}