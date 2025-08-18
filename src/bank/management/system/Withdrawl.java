package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Withdrawl extends JFrame implements ActionListener {
    JTextField amount;
    JButton withdraw, back;
    String pin;
    
    Withdrawl(String pin){
        this.pin = pin;
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550,830,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,1550,830);
        add(image);
        
        JLabel text = new JLabel("Enter the amount you want to withdraw");
        text.setForeground(Color.WHITE);
        text.setFont(new Font("System", Font.BOLD, 16));
        text.setBounds(430,180,400,20);
        image.add(text);
        
        amount = new JTextField();
        amount.setFont(new Font("Raleway", Font.BOLD, 22));
        amount.setBounds(430,220,320,25);
        image.add(amount);
        
        withdraw = new JButton("Withdraw");
        withdraw.setBounds(700,362,150,35);
        withdraw.addActionListener(this);
        image.add(withdraw);
        
        back = new JButton("Back");
        back.setBounds(700,406,150,35);
        back.addActionListener(this);
        image.add(back);
        
        setLayout(null);
        setSize(1550,1080);
        setLocation(0,0);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == withdraw) {
            if (validateAmount()) {
                processWithdrawal();
            }
        } else if (e.getSource() == back) {
            setVisible(false);
            new main_Class(pin);
        }
    }
    
    private boolean validateAmount() {
        String amountText = amount.getText().trim();
        
        if (!ValidationUtils.isValidAmount(amountText)) {
            ValidationUtils.showError("Please enter a valid positive amount");
            amount.requestFocus();
            return false;
        }
        
        double withdrawalAmount = Double.parseDouble(amountText);
        if (withdrawalAmount > 10000) {
            ValidationUtils.showError("Maximum withdrawal amount is $10,000");
            amount.requestFocus();
            return false;
        }
        
        if (withdrawalAmount < 1) {
            ValidationUtils.showError("Minimum withdrawal amount is $1");
            amount.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void processWithdrawal() {
        Connn c = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        
        try {
            c = new Connn();
            double withdrawalAmount = Double.parseDouble(amount.getText().trim());
            
            // Compute current balance from ledger entries
            double currentBalance = 0.0;
            String sumQuery = "SELECT type, amount FROM bank WHERE pin = ?";
            pstmt = c.getConnection().prepareStatement(sumQuery);
            pstmt.setString(1, pin);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String t = resultSet.getString("type");
                double amt = Double.parseDouble(resultSet.getString("amount"));
                if ("Deposit".equalsIgnoreCase(t)) currentBalance += amt; else currentBalance -= amt;
            }
                
                if (withdrawalAmount > currentBalance) {
                    ValidationUtils.showError("Insufficient balance. Available: $" + ValidationUtils.formatAmount(currentBalance));
                    amount.requestFocus();
                    return;
                }
                
                double newBalance = currentBalance - withdrawalAmount;
                
            // Insert withdrawal row
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String now = LocalDateTime.now().format(fmt);
            String insertBank = "INSERT INTO bank (pin, date, type, amount) VALUES (?,?,?,?)";
            pstmt = c.getConnection().prepareStatement(insertBank);
            pstmt.setString(1, pin);
            pstmt.setString(2, now);
            pstmt.setString(3, "Withdrawal");
            pstmt.setString(4, String.valueOf(withdrawalAmount));
            pstmt.executeUpdate();

            // Also add to transactions
            String insertTxn = "INSERT INTO transactions (pin, date, type, amount) VALUES (?,?,?,?)";
            pstmt = c.getConnection().prepareStatement(insertTxn);
            pstmt.setString(1, pin);
            pstmt.setString(2, now);
            pstmt.setString(3, "Withdrawal");
            pstmt.setString(4, String.valueOf(withdrawalAmount));
            pstmt.executeUpdate();
                    
                    ValidationUtils.showSuccess("Withdrawal successful! Amount: $" + ValidationUtils.formatAmount(withdrawalAmount) + 
                                             "\nNew balance: $" + ValidationUtils.formatAmount(newBalance));
                    amount.setText("");
                    amount.requestFocus();
            
        } catch (Exception e) {
            ValidationUtils.showError("Error processing withdrawal: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (pstmt != null) pstmt.close();
                if (c != null) c.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void logTransaction(String type, double amount, double balanceAfter, String description) {
        Connn c = null;
        PreparedStatement pstmt = null;
        
        try {
            c = new Connn();
            
            // Get card number for transaction logging
            String cardQuery = "SELECT card_number FROM bank WHERE pin = ?";
            pstmt = c.getConnection().prepareStatement(cardQuery);
            pstmt.setString(1, pin);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String cardNumber = rs.getString("card_number");
                
                // Insert transaction record
                String insertQuery = "INSERT INTO transactions (card_number, transaction_type, amount, balance_after, description) VALUES (?, ?, ?, ?, ?)";
                pstmt = c.getConnection().prepareStatement(insertQuery);
                pstmt.setString(1, cardNumber);
                pstmt.setString(2, type);
                pstmt.setDouble(3, amount);
                pstmt.setDouble(4, balanceAfter);
                pstmt.setString(5, description);
                
                pstmt.executeUpdate();
            }
            
            rs.close();
            
        } catch (Exception e) {
            System.err.println("Error logging transaction: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (c != null) c.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        new Withdrawl("");
    }
}
