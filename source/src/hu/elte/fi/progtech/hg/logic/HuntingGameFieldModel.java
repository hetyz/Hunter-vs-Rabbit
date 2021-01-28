package hu.elte.fi.progtech.hg.logic;

public class HuntingGameFieldModel {

    private FieldState fieldState;

    public HuntingGameFieldModel() {
        fieldState = FieldState.FIELD;
    }

    public void setFieldState(FieldState fieldState) {
        this.fieldState = fieldState;
    }

    public FieldState getFieldState() {
        return fieldState;
    }
}
