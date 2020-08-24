package com.whiz.app.boot.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AUTHORITY_ID")
    @GenericGenerator(
        name = "SEQ_AUTHORITY_ID",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
            @Parameter(name = "sequence_name", value = "SEQ_AUTHORITY_ID"),
            @Parameter(name = "initial_value", value = "3"),
            @Parameter(name = "increment_size", value = "1")
        }
    )
    private Integer id;

    @NotNull
    private String name;

    @Override
    public String toString() {
        return "Authority#{" +
            "id=" + id +
            ",name=" + name +
            '}';
    }
}