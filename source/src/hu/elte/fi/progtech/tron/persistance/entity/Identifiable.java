package hu.elte.fi.progtech.tron.persistance.entity;

public interface Identifiable <T extends Number> {
    T getId();
    void setId(T id);
}
