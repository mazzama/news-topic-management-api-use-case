package com.mazzama.learn.newstopic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "topics")
public class Topic  extends BaseEntity {
    private String name;
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "topics")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    private Set<News> news = new HashSet<>();
}
