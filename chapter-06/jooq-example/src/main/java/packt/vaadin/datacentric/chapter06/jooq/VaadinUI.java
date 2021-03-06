package packt.vaadin.datacentric.chapter06.jooq;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import packt.vaadin.chapter06.jooq.public_.tables.records.MessagesRecord;

/**
 * @author Alejandro Duarte
 */
public class VaadinUI extends UI {

    private Grid<MessagesRecord> grid;
    private TextField textField;
    private Button button;

    @Override
    protected void init(VaadinRequest request) {
        initLayout();
        initBehavior();
    }

    private void initLayout() {
        grid = new Grid<>(MessagesRecord.class);
        grid.setSizeFull();
        grid.setColumns("id", "content");
        grid.getColumn("id").setWidth(100);

        textField = new TextField();
        textField.setPlaceholder("Enter a new message...");
        textField.setSizeFull();

        button = new Button("Save");

        HorizontalLayout formLayout = new HorizontalLayout(textField, button);
        formLayout.setWidth("100%");
        formLayout.setExpandRatio(textField, 1);

        VerticalLayout layout = new VerticalLayout(grid, formLayout);
        layout.setWidth("600px");
        setContent(layout);
    }

    private void initBehavior() {
        button.addClickListener(e -> saveCurrentMessage());
        update();
    }

    private void update() {
        grid.setItems(MessageRepository.findAll());
        textField.clear();
        textField.focus();
    }

    private void saveCurrentMessage() {
        MessagesRecord message = new MessagesRecord();
        message.setContent(textField.getValue());
        MessageRepository.save(message);

        update();
        grid.select(message);
        grid.scrollToEnd();
    }

}
