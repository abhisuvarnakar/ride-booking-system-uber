package com.codingshuttle.project.uber.uberApp.entities;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name = "t_rider")
@Builder
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double rating;

    public Rider() {
    }

    public Rider(User user, Double rating) {
        this.user = user;
        this.rating = rating;
    }

    public Rider(Long id, User user, Double rating) {
        this.id = id;
        this.user = user;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
