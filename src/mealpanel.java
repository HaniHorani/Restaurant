package src;

import src.models.Meal;
import src.models.Order;
import src.models.OrderDetail;
import src.models.Component;

import javax.imageio.IIOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class mealpanel extends JPanel {
    private DefaultListModel<String> mealListModel;
    private JList<String> mealList;
    private DefaultListModel<String> componentlistmodel;
    private JList<String> componentList;

    public mealpanel() {
        setLayout(new BorderLayout());

        mealListModel = new DefaultListModel<>();
        mealList = new JList<>(mealListModel);
        JScrollPane mealScrollPane = new JScrollPane(mealList);

        componentlistmodel = new DefaultListModel<>();
        componentList = new JList<>(componentlistmodel);
        JScrollPane componentscrollpane = new JScrollPane(componentList);

        JButton addMealButton = new JButton("Add meal");
        JButton editMealButton = new JButton("Edit meal");
        JButton detailMealButton = new JButton("Show Details");
        JButton deleteMealButton = new JButton("Delete meal");

        JButton addcomponentbutton = new JButton("Add component");
        JButton editcomponentbutton = new JButton("Edit component");
        JButton deletecomponentbutton = new JButton("Delete component");

        addMealButton.setFocusable(false);
        editMealButton.setFocusable(false);
        detailMealButton.setFocusable(false);
        deleteMealButton.setFocusable(false);
        addcomponentbutton.setFocusable(false);
        editcomponentbutton.setFocusable(false);
        deletecomponentbutton.setFocusable(false);

        Dimension buttonSize = new Dimension(mealList.getPreferredSize().width, 40);
        addMealButton.setPreferredSize(buttonSize);
        editMealButton.setPreferredSize(buttonSize);
        detailMealButton.setPreferredSize(buttonSize);
        deleteMealButton.setPreferredSize(buttonSize);
        addcomponentbutton.setPreferredSize(buttonSize);
        editcomponentbutton.setPreferredSize(buttonSize);
        deletecomponentbutton.setPreferredSize(buttonSize);

        addMealButton.addActionListener(e -> {
            String mealName = JOptionPane.showInputDialog(this, "Enter the meal name:");
            if (mealName != null && !mealName.isEmpty()) {
                for (int i = 0; i < mealName.length(); i++) {
                    if(!Character.isLetter(mealName.charAt(i)) && !Character.isWhitespace(mealName.charAt(i))){
                        JOptionPane.showMessageDialog(this, "Meal name can only contain letters.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                String priceInput = JOptionPane.showInputDialog(this, "Enter the meal price:");
                if (priceInput == null || priceInput.isEmpty()) return;
                if (!priceInput.matches("\\d+")) {
                    if(Double.parseDouble(priceInput)<0){
                        JOptionPane.showMessageDialog(this, "Price can't be negative", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    JOptionPane.showMessageDialog(this, "Please enter number only", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    double dprice = Double.parseDouble(priceInput);
                    long lprice = (long) dprice;
                    JFrame temp = (JFrame) SwingUtilities.getWindowAncestor(this);
                    CheckboxDialog selection = new CheckboxDialog(temp, Component.loadFromFile());
                    selection.setVisible(true);
                    List<String> selectedItems = selection.getSelectedItems();
                    if (selectedItems.isEmpty()) {
                        return;
                    }
                    List<Component> mealComponents = new ArrayList<>();
                    for (String selectedItem : selectedItems) {
                        Component component = new Component(selectedItem);
                        mealComponents.add(component);
                    }
                    Meal meal = new Meal(mealName,lprice,mealComponents);
                    if (Meal.check(meal)) {
                        List<Meal> mealList1 = Meal.loadFromFile();
                        mealList1.add(meal);
                        Meal.saveToFile(mealList1);
                        updateAll();
                    }
                    else {
                        JOptionPane.showMessageDialog(this,
                                "Meal already exists.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException | IIOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid price input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        editMealButton.addActionListener(e -> {
            int selectedIndex = mealList.getSelectedIndex();
            if (selectedIndex != -1) {
                String tablename = mealListModel.getElementAt(selectedIndex);
                String oldname = tablename.split("-")[0].trim();
                //chech the male is pending in order list
                try {
                    List<Meal> mealList1 = Meal.loadFromFile();
                    for (Meal meal : mealList1) {
                        if (meal.name.equals(oldname)){
                            List<Order> orderList = Order.loadFromFile();
                            for (Order order : orderList) {
                                if(order.getOrderStatus() == Order.OrderStatus.PENDING){
                                    List<OrderDetail> detailList =order.getOrderDetails();
                                    for (OrderDetail detail : detailList) {
                                        if (detail.meal.name.equals(meal.name)) {
                                            JOptionPane.showMessageDialog(this, "Cannot edit meal as it is used in an order.", "Error", JOptionPane.ERROR_MESSAGE);
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }catch (IOException | ClassNotFoundException ex){
                    throw new RuntimeException(ex);
                }
                String oldprice = tablename.split("-")[1].trim();
                oldprice = oldprice.substring(1, oldprice.length());
                String mealName = JOptionPane.showInputDialog(this, "Enter the meal name:",oldname);
                if (mealName != null && !mealName.isEmpty()) {
                    for (int i = 0; i < mealName.length(); i++) {
                        if(!Character.isLetter(mealName.charAt(i)) && !Character.isWhitespace(mealName.charAt(i))){
                            JOptionPane.showMessageDialog(this, "Meal name can only contain letters.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    String priceInput = JOptionPane.showInputDialog(this, "Enter the meal price:",oldprice);
                    if (priceInput == null || priceInput.isEmpty()) return;
                    if (!priceInput.matches("\\d+")) {
                        if(Double.parseDouble(priceInput)<0){
                            JOptionPane.showMessageDialog(this, "Price can't be negative", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        JOptionPane.showMessageDialog(this, "Please enter number only", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    try {
                        double dprice = Double.parseDouble(priceInput);
                        long lprice = (long) dprice;
                        Meal nowmeal = new Meal();
                        for(Meal m:Meal.loadFromFile()){
                            if (m.name.equals(mealName)){
                                nowmeal = m;
                                break;
                            }
                        }
                        JFrame temp = (JFrame) SwingUtilities.getWindowAncestor(this);
                        CheckboxDialog selection = new CheckboxDialog(temp, Component.loadFromFile(),nowmeal.components);
                        selection.setVisible(true);
                        List<String> selectedItems = selection.getSelectedItems();
                        if (selectedItems.isEmpty()) {
                            return;
                        }
                        List<Component> mealComponents = new ArrayList<>();
                        for (String selectedItem : selectedItems) {
                            Component component = new Component(selectedItem);
                            mealComponents.add(component);
                        }
                        Meal meal = new Meal(mealName,lprice,mealComponents);
                        System.out.println("the old name:"+ oldname+"\n the new name :"+ mealName+"\ncheke:"+Meal.check(meal));
                        if(!oldname.equals(mealName)&&!Meal.check(meal)){
                            JOptionPane.showMessageDialog(this,
                                    "Meal already exists.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                            List<Meal> mealList1 = Meal.loadFromFile();
                            mealList1.set(selectedIndex, meal);
                            Meal.saveToFile(mealList1);
                            updateAll();


                    } catch (NumberFormatException | IIOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid price input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this,"Please select a meal to edit.","Error",JOptionPane.ERROR_MESSAGE);
            }
        });
        detailMealButton.addActionListener(e -> {
            int selectedIndex = mealList.getSelectedIndex();
            if (selectedIndex != -1) {
                try{
                    String selectedMealName = mealListModel.getElementAt(selectedIndex);
                    List<Meal> mealList1 = Meal.loadFromFile();
                    for (Meal meal : mealList1) {
                        if (meal.name.equals(mealList1.get(selectedIndex).name)) {
                            String details = meal.name + ": \n";
                            for (Component component : meal.components) {
                                details += component.name + "\n";
                            }
                            JOptionPane.showMessageDialog(this, details , "Details", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                    }
                }catch (IOException | ClassNotFoundException exception) {
                    throw new RuntimeException(exception);
                }
            } else {
                JOptionPane.showMessageDialog(this,"Please select a meal to show details.","Error",JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteMealButton.addActionListener(e -> {
            int selectedIndex = mealList.getSelectedIndex();
            if (selectedIndex != -1) {try {
                List<Meal> mealList1 = Meal.loadFromFile();
                for (Meal meal : mealList1) {
                    if (meal.name.equals(mealList1.get(selectedIndex).name)) {
                        List<Order> orderList = Order.loadFromFile();
                        for (Order order : orderList) {
                            if(order.getOrderStatus() == Order.OrderStatus.PENDING){
                                List<OrderDetail> detailList =order.getOrderDetails();
                                for (OrderDetail detail : detailList) {
                                    if (detail.meal.name.equals(meal.name)) {
                                        JOptionPane.showMessageDialog(this, "Cannot delete meal as it is used in an order.", "Error", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                }
                            }
                        }
                        int option = JOptionPane.showConfirmDialog(mealpanel.this, "Are you sure you want to delete" + meal.name+" ?", "Delete", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION)
                            mealListModel.remove(selectedIndex);
                            mealList1.remove(meal);
                        break;
                    }
                }
                Meal.saveToFile(mealList1);
                updateAll();
            }catch (Exception exception) {
                JOptionPane.showMessageDialog(this, "Error: " + exception.getClass().getName() + " - " + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a meal to delete.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        addcomponentbutton.addActionListener(e -> {
            try {
                List<Component> componentList1 = Component.loadFromFile();
                String componentName = JOptionPane.showInputDialog(mealpanel.this, "Enter the component name:");
                if (componentName != null && !componentName.isEmpty()) {
                    for (int i = 0; i < componentName.length(); i++) {
                        if(!Character.isLetter(componentName.charAt(i)) && !Character.isWhitespace(componentName.charAt(i))){
                            JOptionPane.showMessageDialog(mealpanel.this, "Component name can only contain letters.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    Component component = new Component(componentName);
                    if (Component.check(component, componentList1)) {
                        componentList1.add(component);
                        Component.saveToFile(componentList1);
                        updateAll();
                    } else {
                        JOptionPane.showMessageDialog(mealpanel.this,
                                "Component already exists.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(mealpanel.this,
                            "Please enter a component name.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, "Error: " + exception.getClass().getName() + " - " + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        editcomponentbutton.addActionListener(e ->{  try {
            List<Component> componentList1 = Component.loadFromFile();
            int selectedIndex = componentList.getSelectedIndex();
            if (selectedIndex != -1) {
               Component cur= componentList1.get(selectedIndex);
                String componentName = JOptionPane.showInputDialog(mealpanel.this, "Enter the component name:",cur.name);
                if (componentName != null && !componentName.isEmpty()) {
                    for (int i = 0; i < componentName.length(); i++) {
                        if(!Character.isLetter(componentName.charAt(i)) && !Character.isWhitespace(componentName.charAt(i))){
                            JOptionPane.showMessageDialog(this, "Component name can only contain letters.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    cur.name = componentName;
                    Component.saveToFile(componentList1);
                    updateAll();
                } else {
                    JOptionPane.showMessageDialog(mealpanel.this,
                            "Please enter a component name.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                Component.saveToFile(componentList1);
                updateAll();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a component to delete.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Error: " + exception.getClass().getName() + " - " + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        });

        deletecomponentbutton.addActionListener(e -> {
            try {
                List<Component> componentList1 = Component.loadFromFile();
                int selectedIndex = componentList.getSelectedIndex();
                if (selectedIndex != -1) {
                    componentList1.remove(selectedIndex);
                    Component.saveToFile(componentList1);
                    updateAll();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Please select a component to delete.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, "Error: " + exception.getClass().getName() + " - " + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel mealPanel = new JPanel();
        mealPanel.setLayout(new BoxLayout(mealPanel, BoxLayout.Y_AXIS));
        JLabel mealLabel = new JLabel("Meals", JLabel.CENTER);
        mealLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mealPanel.add(mealLabel);
        mealPanel.add(mealScrollPane);
        mealPanel.add(addMealButton);
        mealPanel.add(editMealButton);
        mealPanel.add(detailMealButton);
        mealPanel.add(deleteMealButton);


        JPanel componentpanel = new JPanel();
        componentpanel.setLayout(new BoxLayout(componentpanel, BoxLayout.Y_AXIS));
        JLabel componentlabel = new JLabel("Components", JLabel.CENTER);
        componentlabel.setFont(new Font("Arial", Font.BOLD, 14));
        componentpanel.add(componentlabel);
        componentpanel.add(componentscrollpane);
        componentpanel.add(addcomponentbutton);
        componentpanel.add(editcomponentbutton);
        componentpanel.add(deletecomponentbutton);


        JPanel listPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        listPanel.add(mealPanel);
        listPanel.add(componentpanel);

        add(listPanel, BorderLayout.CENTER);
        updateAll();
    }

    private void updateAll() {
        try {
            mealListModel.removeAllElements();
            componentlistmodel.removeAllElements();
            List<Meal> mealList1 = Meal.loadFromFile();
            List<Component> componentList1 = Component.loadFromFile();
            for (Meal meal : mealList1) {
                mealListModel.addElement(meal.name + " - $" + meal.Price);
            }
            for (Component component : componentList1) {
                componentlistmodel.addElement(component.name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getClass().getName() + " - " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
class CheckboxDialog extends JDialog {
    private JCheckBox[] checkBoxes;
    public List<String> selectedItems = new ArrayList<>();

    public List<String> getSelectedItems() {
        return selectedItems;
    }

    public CheckboxDialog(JFrame parentFrame, List<Component> items ) {
        super(parentFrame, "Select Items", true);
        setLayout(new BorderLayout());

        checkBoxes = new JCheckBox[items.size()];
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));

        for (Component item : items) {
            JCheckBox checkBox = new JCheckBox(item.name);
            checkBoxes[items.indexOf(item)] = checkBox;
            checkBoxPanel.add(checkBox);
        }
        JScrollPane scrollPane = new JScrollPane(checkBoxPanel);
        scrollPane.setPreferredSize(new Dimension(300, 200));  // ضبط حجم الـ JScrollPane

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numSelected = 0;
                for (JCheckBox box : checkBoxes) {
                    if(box.isSelected()){
                        selectedItems.add(box.getText());
                        numSelected++;
                    }
                }
                if (numSelected == 0) {
                    JOptionPane.showMessageDialog(CheckboxDialog.this, "Please select at least one item.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dispose();
            }
        });
        add(scrollPane, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);
        setSize(350, 300);
        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    public CheckboxDialog(JFrame parentFrame, List<Component> items, List<Component> originalComponents) {
        super(parentFrame, "Select Items", true);
        setLayout(new BorderLayout());

        checkBoxes = new JCheckBox[items.size()];
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));

        for (Component item : items) {
            JCheckBox checkBox = new JCheckBox(item.name);

            for (Component originalItem : originalComponents) {
                if (item.name.equals(originalItem.name)) {
                    checkBox.setSelected(true);
                    break;
                }
            }

            checkBoxes[items.indexOf(item)] = checkBox;
            checkBoxPanel.add(checkBox);
        }


        JScrollPane scrollPane = new JScrollPane(checkBoxPanel);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numSelected = 0;
                for (JCheckBox box : checkBoxes) {
                    if (box.isSelected()) {
                        selectedItems.add(box.getText());
                        numSelected++;
                    }
                }
                if (numSelected == 0) {
                    JOptionPane.showMessageDialog(CheckboxDialog.this, "Please select at least one item.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dispose();
            }
        });

        add(scrollPane, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);
        setSize(350, 300);
        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

}

