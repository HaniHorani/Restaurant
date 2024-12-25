package src;
import src.models.Component;
import src.models.Meal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class mealpanel extends JPanel {
    private DefaultListModel<String> mealListModel;
    private JList<String> mealList;
    private DefaultListModel<String> ingredientListModel;
    private JList<String> ingredientList;

    public mealpanel() {
        setLayout(new BorderLayout());

        mealListModel = new DefaultListModel<>();
        mealList = new JList<>(mealListModel);
        JScrollPane mealScrollPane = new JScrollPane(mealList);

        ingredientListModel = new DefaultListModel<>();
        ingredientList = new JList<>(ingredientListModel);
        JScrollPane ingredientScrollPane = new JScrollPane(ingredientList);

        JButton addMealButton = new JButton("Add meal");
        JButton deleteMealButton = new JButton("Delete meal");

        JButton addIngredientButton = new JButton("Add component");
        JButton deleteIngredientButton = new JButton("Delete component");

        // Set the maximum size for buttons based on the size of the lists
        Dimension buttonSize = new Dimension(mealList.getPreferredSize().width, 40);
        addMealButton.setPreferredSize(buttonSize);
        deleteMealButton.setPreferredSize(buttonSize);
        addIngredientButton.setPreferredSize(buttonSize);
        deleteIngredientButton.setPreferredSize(buttonSize);

        addMealButton.addActionListener(e -> {
            String mealName = JOptionPane.showInputDialog(this, "Enter the meal name:");
            if (mealName != null && !mealName.isEmpty()) {
                String priceInput = JOptionPane.showInputDialog(this, "Enter the meal price:");
                try {
                    double dprice = Double.parseDouble(priceInput);
                    long lprice = (long) dprice;
//                    Meal meal = new Meal(mealName, lprice);
//                    List<Meal> mealList1 = Meal.loadFromFile();
//                    mealList1.add(meal);
//                    Meal.saveToFile(mealList1);
                    updateAll();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid price input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteMealButton.addActionListener(e -> {
            int selectedIndex = mealList.getSelectedIndex();
            if (selectedIndex != -1) {
                mealListModel.remove(selectedIndex);
                updateAll();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a meal to delete.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        addIngredientButton.addActionListener(e -> {
            try {
                List<Component> componentList1 = Component.loadFromFile();
                String componentName = JOptionPane.showInputDialog(mealpanel.this, "Enter the component name:");
                if (componentName != null && !componentName.isEmpty()) {
                    Component component = new Component();
                    component.name = componentName;
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

        deleteIngredientButton.addActionListener(e -> {
            try {
                List<Component> componentList1 = Component.loadFromFile();
                int selectedIndex = ingredientList.getSelectedIndex();
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

        // Panel for meals with BoxLayout
        JPanel mealPanel = new JPanel();
        mealPanel.setLayout(new BoxLayout(mealPanel, BoxLayout.Y_AXIS)); // Vertical layout for buttons
        JLabel mealLabel = new JLabel("Meals", JLabel.CENTER);
        mealLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mealPanel.add(mealLabel);
        mealPanel.add(mealScrollPane);
        mealPanel.add(deleteMealButton);
        mealPanel.add(addMealButton);  // Add the add button after the delete button

        // Panel for components with BoxLayout
        JPanel ingredientPanel = new JPanel();
        ingredientPanel.setLayout(new BoxLayout(ingredientPanel, BoxLayout.Y_AXIS)); // Vertical layout for buttons
        JLabel ingredientLabel = new JLabel("Components", JLabel.CENTER);
        ingredientLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ingredientPanel.add(ingredientLabel);
        ingredientPanel.add(ingredientScrollPane);
        ingredientPanel.add(deleteIngredientButton);
        ingredientPanel.add(addIngredientButton); // Add the add button after the delete button

        JPanel listPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        listPanel.add(mealPanel);
        listPanel.add(ingredientPanel);

        add(listPanel, BorderLayout.CENTER);
        updateAll();
    }

    private void updateAll() {
        try {
            mealListModel.removeAllElements();
            ingredientListModel.removeAllElements();
            List<Meal> mealList1 = Meal.loadFromFile();
            List<Component> componentList1 = Component.loadFromFile();
            for (Meal meal : mealList1) {
                mealListModel.addElement(meal.name);
            }
            for (Component component : componentList1) {
                ingredientListModel.addElement(component.name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getClass().getName() + " - " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
