package hu.elte.fi.progtech.tron.persistance.entity;

import java.io.Serializable;

public abstract class AbstractEntity implements Identifiable<Long>, Serializable {

    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

}
