package com.mindScrub.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "post_table")
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
	@Enumerated(EnumType.STRING)
	private PostStatus status;
	
	private String blogImageName;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createAt;

	@Column(name = "updated_at")
	private LocalDateTime updateAt;

	@PreUpdate
	public void onUpdate() {
	    this.updateAt = LocalDateTime.now();
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;
}
