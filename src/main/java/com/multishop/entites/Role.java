package com.multishop.entites;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Role implements Serializable{
	@Id
	private int id;
	private String name;
}
