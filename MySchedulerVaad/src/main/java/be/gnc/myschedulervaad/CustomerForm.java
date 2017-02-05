/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.gnc.myschedulervaad;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;

/**
 *
 * @author alex
 */
public class CustomerForm extends com.vaadin.ui.FormLayout{

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField email = new TextField("Email");
    private NativeSelect status = new NativeSelect("Status");
    private PopupDateField birthdate = new PopupDateField("Birthday");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private CustomerService service = CustomerService.getInstance();
    private Customer customer;
    private MyUI myUI;

    public CustomerForm(MyUI myUI) {
        this.myUI = myUI;

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        buttons.setSpacing(true);
        addComponents(firstName, lastName, email, status, birthdate, buttons);
        
        status.addItems(CustomerStatus.values());
    }

}
