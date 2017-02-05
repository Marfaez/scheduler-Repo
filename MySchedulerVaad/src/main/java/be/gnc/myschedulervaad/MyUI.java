package be.gnc.myschedulervaad;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;

/**
 *gnc class git test 4
 */
@Theme("mytheme")
@Widgetset("be.gnc.myschedulervaad.MyAppWidgetset")
public class MyUI extends UI {

    private CustomerService customerService = CustomerService.getInstance();
    private TextField filterText = new TextField();

    private Grid customerGrid = new Grid();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
        clearFilterTextBtn.setDescription("Clear the current filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterText.clear();
            updateList();
        });

        CssLayout filtering = new CssLayout();
        filtering.addComponents(filterText, clearFilterTextBtn);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        layout.addComponent(filtering);

        customerGrid.setColumns("firstName", "lastName", "email");
        layout.addComponent(customerGrid);
        updateList();

        layout.setMargin(true);
        setContent(layout);

        filterText.setInputPrompt("filter by name...");
        filterText.addTextChangeListener(e -> {
            customerGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class,
                    customerService.findAll(e.getText())));
        });

        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                layout.addComponent(new Label("Thank you for clicking"));
            }
        });
        layout.addComponent(button);

    }

    private void updateList() {
        List<Customer> customers = customerService.findAll();
        customerGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customers));
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
