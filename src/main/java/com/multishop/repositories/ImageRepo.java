package com.multishop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multishop.entites.Image;

public interface ImageRepo extends JpaRepository<Image, Integer> {

}
