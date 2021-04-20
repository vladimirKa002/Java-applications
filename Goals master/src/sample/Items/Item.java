package sample.Items;

import java.time.LocalDate;

public abstract class Item {
    protected String text;
    protected String text_additional_info;
    protected LocalDate deadline;

    public String getText() {
        return text;
    }

    public String getText_additional_info() {
        return text_additional_info;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
}
