package bank.management.system;

import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class ValidationUtils {
    
    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    // PIN validation pattern (4-6 digits)
    private static final Pattern PIN_PATTERN = Pattern.compile("^\\d{4,6}$");
    
    // Card number validation pattern (16 digits)
    private static final Pattern CARD_PATTERN = Pattern.compile("^\\d{16}$");
    
    // Pincode validation pattern (6 digits)
    private static final Pattern PINCODE_PATTERN = Pattern.compile("^\\d{6}$");
    
    /**
     * Validates if a string is not null or empty
     */
    public static boolean isValidString(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    /**
     * Validates email format
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Validates PIN format
     */
    public static boolean isValidPIN(String pin) {
        return pin != null && PIN_PATTERN.matcher(pin).matches();
    }
    
    /**
     * Validates card number format
     */
    public static boolean isValidCardNumber(String cardNumber) {
        return cardNumber != null && CARD_PATTERN.matcher(cardNumber).matches();
    }
    
    /**
     * Validates pincode format
     */
    public static boolean isValidPincode(String pincode) {
        return pincode != null && PINCODE_PATTERN.matcher(pincode).matches();
    }
    
    /**
     * Validates amount (positive number)
     */
    public static boolean isValidAmount(String amount) {
        try {
            double amt = Double.parseDouble(amount);
            return amt > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Shows error message dialog
     */
    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Shows success message dialog
     */
    public static void showSuccess(String message) {
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Shows confirmation dialog
     */
    public static boolean showConfirmation(String message) {
        int result = JOptionPane.showConfirmDialog(null, message, "Confirm", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
    
    /**
     * Sanitizes SQL input to prevent SQL injection
     */
    public static String sanitizeSQL(String input) {
        if (input == null) return null;
        return input.replace("'", "''").replace(";", "").replace("--", "");
    }
    
    /**
     * Formats amount with proper decimal places
     */
    public static String formatAmount(double amount) {
        return String.format("%.2f", amount);
    }
    
    /**
     * Generates a random card number
     */
    public static String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append((int) (Math.random() * 10));
        }
        return cardNumber.toString();
    }
    
    /**
     * Generates a random PIN
     */
    public static String generatePIN() {
        StringBuilder pin = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            pin.append((int) (Math.random() * 10));
        }
        return pin.toString();
    }
}
