package de.hska.awp.palaver2.util;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PopUp extends UI {
    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Popup Window");
        
        // Have some content for it
        VerticalLayout content = new VerticalLayout();
        Label label = new Label("I just popped up to say hi!");
        label.setSizeUndefined();
        content.addComponent(label);
        content.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
        content.setSizeFull();
        setContent(content);
    }
}
