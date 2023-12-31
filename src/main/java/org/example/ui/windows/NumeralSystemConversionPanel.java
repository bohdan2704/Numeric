package org.example.ui.windows;

import org.example.numeric.Number;
import org.example.numeric.NumeralSystem;
import org.example.ui.elements.HintTextField;

import javax.swing.*;
import java.awt.*;

public class NumeralSystemConversionPanel extends Panel implements ConversionPanel {
    private final JTextField textField;
    private final JComboBox<NumeralSystem> fromNumeralSystemJComboBox1;
    private final JComboBox<NumeralSystem> toNumeralSystemJComboBox1;
    private final JLabel resultLabel;
    private final JLabel labelComboBox1;
    private final JLabel labelComboBox2;
    private final JButton convertButton;

    public NumeralSystemConversionPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(207, 245, 207));

        GridBagConstraints gbc = new GridBagConstraints();

        textField = new HintTextField("Enter number");
        addComponent(textField, gbc, 0, 0);

        labelComboBox1 = new JLabel("From base: ");
        addComponent(labelComboBox1, gbc, 0, 2);

        fromNumeralSystemJComboBox1 = new JComboBox<>(NumeralSystem.values());
        addComponent(fromNumeralSystemJComboBox1, gbc, 0, 3);

        labelComboBox2 = new JLabel("To base: ");
        addComponent(labelComboBox2, gbc, 0, 4);

        toNumeralSystemJComboBox1 = new JComboBox<>(NumeralSystem.values());
        addComponent(toNumeralSystemJComboBox1, gbc, 0, 5);
        
        resultLabel = new JLabel("Number in different base: ");
        addComponent(resultLabel, gbc, 0, 6);

        convertButton = new JButton("Convert");
        addComponent(convertButton, gbc, 0, 7);

        setFontSize(textField, convertButton, resultLabel);
        convertButton.addActionListener(e -> performConversion());

        setPreferredSize(new Dimension(400, 300));
    }

    @Override
    public void performConversion() {
        String inputValueInSomeBase = textField.getText();
        NumeralSystem fromNumeralSystem = (NumeralSystem) fromNumeralSystemJComboBox1.getSelectedItem();
        Number number = new Number(fromNumeralSystem, inputValueInSomeBase);

        // Carry out converting in number system with diff base
        double inputValue = number.getDecimalNumber();

        NumeralSystem toNumeralSystem = (NumeralSystem) toNumeralSystemJComboBox1.getSelectedItem();
        Number numberFromInput = new Number(inputValue);
        String numberInDiffBase = numberFromInput.toSpecifiedNumeralSystem(toNumeralSystem);

        // Reverse convert to check if everything was converted properly
        assert toNumeralSystem != null; // Argument won't be null
        Number numberReverseConvert = new Number(toNumeralSystem, numberInDiffBase);
        double reverseConvertForNumInDiffBase = numberReverseConvert.getDecimalNumber();

        // Setting the result to our beautiful label
        if (toNumeralSystem.equals(NumeralSystem.DECIMAL)) {
            resultLabel.setText(formOutputLineIfDecimalIsResultingBase(toNumeralSystem.name(), reverseConvertForNumInDiffBase));
        } else {
            resultLabel.setText(formOutputLine(toNumeralSystem.name(), numberInDiffBase,reverseConvertForNumInDiffBase ));
        }
    }
}
