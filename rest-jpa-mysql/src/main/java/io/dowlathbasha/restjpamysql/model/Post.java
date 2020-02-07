package io.dowlathbasha.restjpamysql.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {
  @Id
  @GeneratedValue
  private int postId;
  private String description;  
  @ManyToOne(fetch=FetchType.LAZY)
  @JsonIgnore
  private User user;
}
