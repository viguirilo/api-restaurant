package com.restaurant.api.rest.v1.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.restaurant.api.rest.v1.vo.CityRequestVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "city")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonRootName(value = "city")
public class City implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    @JsonProperty(value = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id", nullable = false)
    @JsonProperty("state")
    private State state;

    public City(CityRequestVO cityRequestVO, State state) {
        this.name = cityRequestVO.getName();
        this.state = state;
    }

}
