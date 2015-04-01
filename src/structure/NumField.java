package structure;

//======================================================================
//PROJECT:            _programming.java_
//FILE:               Keypad.java
//PURPOSE:            Chapter 7 demo program, NumField class
//VERSION:            1.0
//TARGET:             Java v1.1
//UPDATE HISTORY:     1.0    11/2/98     
//======================================================================

//------------------------------ IMPORTS -------------------------------

import java.awt.*;

//========================== NumField CLASS ============================

/**
 * A NumField stores and displays the text representation of a floating-point
 * number. We've designed this class so that at any time, its display is either
 * empty or contains a legal representation of a floating-point number.
 */
public class NumField extends TextField {
	boolean hasDecimalPoint = false;

	/**
	 * Default constructor--build a NumField eight columns wide, initially
	 * empty.
	 */
	public NumField() {
		super(8); // Call the superclass constructor.
		super.setText("");
		setEditable(true); // Don't let the user in.
		hasDecimalPoint = true;
		setBackground(Color.white);
	}

	/**
	 * Returns the value represented by this field's text, or zero, if the text
	 * is empty or just contains a minus sign.
	 */
	public double getValue() {
		if ((getText().equals("")) || (getText().equals("-"))) {
			return 0.0;
		} else {
			Double d = new Double(getText());
			return d.doubleValue();
		}
	}

	/**
	 * Set the text to represent the double value of the argument.
	 */
	public void setValue(double d) {
		super.setText(String.valueOf(d));
	}

	/**
	 * Set the text to be the empty String.
	 */
	public void clear() {
		super.setText("");
		hasDecimalPoint = false;
	}

	/**
	 * Append the character c to the end of the text. We check that this method
	 * will result in a legal number representation, and do nothing if the char
	 * to be appended would result in an invalid number representation.
	 */
	public void append(char c) {
		if (isLegal(c)) {
			if (c == '.') {
				hasDecimalPoint = true;
			}

			if ((getText()).equals("0")) {
				super.setText("");
			}

			super.setText(getText() + c);
		}
	}

	/**
	 * Append the first character of s to the end of the text. This uses the
	 * other version of append() to ensure the representation is valid.
	 */
	public void append(String s) {
		char c = s.charAt(0);
		append(c);
	}

	/**
	 * Override of the TextComponent method. We don't want to allow the user to
	 * change the text arbitrarily.
	 */
	public void setText(String s) {
	}

	// -------------- Utility -------------

	/**
	 * Returns true if and only of c could legally be appended to the end of the
	 * text. In other words, return true if (1) c is a digit, or (2) if c would
	 * be the only decimal point in the text representation, or (3) if c is '-'
	 * and the current text is 0 or empty.
	 */
	private boolean isLegal(char c) {
		if (Character.isDigit(c)) {
			return true;
		} else if (c == '.') {
			return !hasDecimalPoint;
		} else if ((c == '-')
				&& ((getText().equals("")) || (getText().equals("0")))) {
			return true;
		} else {
			return false;
		}
	}
}
