package com.mindScrub.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "post-table")
@Data
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String title;
	
	private String url;
	
	@Column(nullable = false)
	private String shortDesc;
	
	@Lob
	@Column(nullable = false,columnDefinition = "LONGTEXT")
	private String content;
	
	@Column(nullable = false)
	private String status;
	
	private String blogImageName;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createAt;
	
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDateTime updateAt;
}
