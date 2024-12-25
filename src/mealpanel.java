package src;
import src.models.Component;
import src.models.Meal;

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
        JButton deleteMealButton = new JButton("Delete meal");

        JButton addcomponentbutton = new JButton("Add component");
        JButton deletecomponentbutton = new JButton("Delete component");

        // Set the maximum size for buttons based on the size of the lists
        Dimension buttonSize = new Dimension(mealList.getPreferredSize().width, 40);
        addMealButton.setPreferredSize(buttonSize);
        deleteMealButton.setPreferredSize(buttonSize);
        addcomponentbutton.setPreferredSize(buttonSize);
        deletecomponentbutton.setPreferredSize(buttonSize);

        addMealButton.addActionListener(e -> {
            String mealName = JOptionPane.showInputDialog(this, "Enter the meal name:");
            if (mealName != null && !mealName.isEmpty()) {
                String priceInput = JOptionPane.showInputDialog(this, "Enter the meal price:");
                try {
                    double dprice = Double.parseDouble(priceInput);
                    long lprice = (long) dprice;
                    JFrame temp = (JFrame) SwingUtilities.getWindowAncestor(this);
                    CheckboxDialog selection = new CheckboxDialog(temp, Component.loadFromFile());
                    selection.setVisible(true);
                    List<String> selectedItems = selection.getSelectedItems();
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

        deleteMealButton.addActionListener(e -> {
            int selectedIndex = mealList.getSelectedIndex();
            if (selectedIndex != -1) {try {

                List<Meal> mealList1 = Meal.loadFromFile();
                mealListModel.remove(selectedIndex);
                for (Meal meal : mealList1) {
                    if (meal.name.equals(mealList1.get(selectedIndex).name)) {
                        int option = JOptionPane.showConfirmDialog(mealpanel.this, "Are you sure you want to delete" + meal.name+" ?", "Delete", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION)
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
        mealPanel.add(deleteMealButton);
        mealPanel.add(addMealButton);

        JPanel componentpanel = new JPanel();
        componentpanel.setLayout(new BoxLayout(componentpanel, BoxLayout.Y_AXIS));
        JLabel componentlabel = new JLabel("Components", JLabel.CENTER);
        componentlabel.setFont(new Font("Arial", Font.BOLD, 14));
        componentpanel.add(componentlabel);
        componentpanel.add(componentscrollpane);
        componentpanel.add(deletecomponentbutton);
        componentpanel.add(addcomponentbutton);

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

    public CheckboxDialog(JFrame parentFrame, List<Component> items) {
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
                for (JCheckBox box : checkBoxes) {
                    if(box.isSelected()){
                        selectedItems.add(box.getText());
                    }
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

