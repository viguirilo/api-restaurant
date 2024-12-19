package com.restaurant.api.rest.v1.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.restaurant.api.rest.v1.vo.StateRequestVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "state")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonRootName(value = "state")
public class State implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "id")
    private Long id;

    @Column(name = "name", length = 80, nullable = false)
    @JsonProperty(value = "name")
    private String name;

    @Column(name = "abbreviation", length = 5, nullable = false)
    @JsonProperty(value = "abbreviation")
    private String abbreviation;

    @Column(name = "country", length = 25, nullable = false)
    @JsonProperty(value = "country")
    private String country;

    public State(StateRequestVO stateRequestVO) {
        this.name = stateRequestVO.getName();
        this.abbreviation = stateRequestVO.getAbbreviation();
        this.country = stateRequestVO.getCountry();
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(name, state.name) && Objects.equals(abbreviation, state.abbreviation) && Objects.equals(country, state.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, abbreviation, country);
    }

}
