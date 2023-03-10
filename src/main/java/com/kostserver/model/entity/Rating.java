package com.kostserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kostserver.model.entity.BaseEntity;
import com.kostserver.model.entity.RoomKost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tbl_rating")
public class Rating extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rating;
    private String ulasan;

    private Boolean anonym;

    @JsonIgnore
    @ManyToOne
    private Account account;

    @JsonIgnore
    @ManyToOne
    private RoomKost roomKost;
}
