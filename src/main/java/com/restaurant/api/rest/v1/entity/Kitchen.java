package com.restaurant.api.rest.v1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.restaurant.api.rest.v1.vo.KitchenRequestVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "kitchen")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonRootName(value = "kitchen")
public class Kitchen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "id")
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    @JsonProperty(value = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "kitchen")
    private List<Restaurant> restaurants = new ArrayList<>();

    public Kitchen(KitchenRequestVO kitchenRequestVO) {
        this.name = kitchenRequestVO.getName().trim().replaceAll("\\s+", " ");
    }

    @Override
    public String toString() {
        return "Kitchen{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kitchen kitchen = (Kitchen) o;
        return Objects.equals(name, kitchen.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

}
