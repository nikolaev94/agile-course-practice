package ru.unn.agile.Complex.viewmodel;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.Complex.model.Complex;
import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    private final StringProperty firstReal = new SimpleStringProperty();
    private final StringProperty firstImaginary = new SimpleStringProperty();
    private final StringProperty secondReal = new SimpleStringProperty();
    private final StringProperty secondImaginary = new SimpleStringProperty();
    private final StringProperty result = new SimpleStringProperty();
    private final StringProperty errors = new SimpleStringProperty();
    private final ObjectProperty<Operation> operation = new SimpleObjectProperty<Operation>();
    private final BooleanProperty canCalculate = new SimpleBooleanProperty();
    private final List<ListenerOfChangedValue> listOfListenersOfChanedValues = new ArrayList<>();

    public ViewModel() {
        firstReal.set("");
        firstImaginary.set("");
        secondReal.set("");
        secondImaginary.set("");
        result.set("");
        errors.set("");
        operation.set(Operation.ADD);
        canCalculate.set(false);

        final List<StringProperty> fields = new ArrayList<StringProperty>();
        fields.add(firstReal);
        fields.add(firstImaginary);
        fields.add(secondReal);
        fields.add(secondImaginary);
        for (StringProperty field : fields) {
            final ListenerOfChangedValue listener = new ListenerOfChangedValue();
            field.addListener(listener);
            listOfListenersOfChanedValues.add(listener);
        }
    }

    public StringProperty getFirstRealProperty() {
        return firstReal;
    }
    public StringProperty getFirstImaginaryProperty() {
        return firstImaginary;
    }
    public StringProperty getSecondRealProperty() {
        return secondReal;
    }
    public StringProperty getSecondImaginaryProperty() {
        return secondImaginary;
    }
    public StringProperty getResultProperty() {
        return result;
    }
    public StringProperty getErrorsProperty() {
        return errors;
    }
    public ObjectProperty<Operation> getOperationProperty() {
        return operation;
    }
    public BooleanProperty canCalculateProperty() {
        return canCalculate;
    }
    public void calculate() {
        if (!canCalculate.get()) {
            return;
        }
        double tmpReal = Double.parseDouble(firstReal.get());
        double tmpImaginary = Double.parseDouble(firstImaginary.get());
        Complex first = new Complex(tmpReal, tmpImaginary);

        tmpReal = Double.parseDouble(secondReal.get());
        tmpImaginary = Double.parseDouble(secondImaginary.get());
        Complex second = new Complex(tmpReal, tmpImaginary);

        switch (operation.get()) {
            case ADD:
                result.set(first.add(second).toString());
                break;
            case SUBTRACT:
                result.set(first.subtract(second).toString());
                break;
            case MULTIPLY:
                result.set(first.multiply(second).toString());
                break;
            case DIVIDE:
                try {
                    result.set(first.divide(second).toString());
                } catch (IllegalArgumentException e) {
                    errors.set("Divider can't be zero!");
                }
                break;
            default:
                break;
        }
    }
    private void refreshInputErrors() {
        errors.set("");
        canCalculate.set(true);
        try {
            if (firstReal.get().isEmpty()) {
                canCalculate.set(false);
            } else {
                Double.parseDouble(firstReal.get());
            }
            if (firstImaginary.get().isEmpty()) {
                canCalculate.set(false);
            } else {
                Double.parseDouble(firstImaginary.get());
            }
            if (secondReal.get().isEmpty()) {
                canCalculate.set(false);
            } else {
                Double.parseDouble(secondReal.get());
            }
            if (secondImaginary.get().isEmpty()) {
                canCalculate.set(false);
            } else {
                Double.parseDouble(secondImaginary.get());
            }
        } catch (NumberFormatException e) {
            canCalculate.set(false);
            errors.set("Invalid format!");
        }
    }
    private class ListenerOfChangedValue implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            refreshInputErrors();
        }
    }
}
