package com.multishop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multishop.entites.Status;

public interface StatusRepo extends JpaRepository<Status, Integer> {

}
