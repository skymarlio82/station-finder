package com.whiz.app.boot.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Fullname {
    @NotNull
    @Size(min = 3, max = 30)
    String firstname;

    @NotNull
    @Size(min = 3, max = 30)
    String lastname;

    @Override
    public String toString() {
        return "Fullname#{" + firstname + ", " + lastname + "}";
    }
}