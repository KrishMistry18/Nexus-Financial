package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BalanceEnquriy extends JFrame implements ActionListener {
    JButton back;
    String pin;
    
    BalanceEnquriy(String pin){
        this.pin = pin;
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550,830,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,1550,830);
        add(image);
        
        back = new JButton("Back");
        back.setBounds(700,406,150,35);
        back.addActionListener(this);
        image.add(back);
        
        // Display balance
        displayBalance(image);
        
        setLayout(null);
        setSize(1550,1080);
        setLocation(0,0);
        setVisible(true);
    }
    
    private void displayBalance(JLabel image) {
        Connn c = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        
        try {
            c = new Connn();
            
            // Compute balance from ledger and fetch card holder name via login/signup tables
            // Get card by pin
            String cardQuery = "SELECT card_number FROM login WHERE pin = ?";
            pstmt = c.getConnection().prepareStatement(cardQuery);
            pstmt.setString(1, pin);
            resultSet = pstmt.executeQuery();
            String cardNumber = null;
            if (resultSet.next()) {
                cardNumber = resultSet.getString("card_number");
            }
            if (resultSet != null) { resultSet.close(); }

            double balance = 0.0;
            String sumQuery = "SELECT type, amount FROM bank WHERE pin = ?";
            pstmt = c.getConnection().prepareStatement(sumQuery);
            pstmt.setString(1, pin);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String t = resultSet.getString("type");
                double amt = Double.parseDouble(resultSet.getString("amount"));
                if ("Deposit".equalsIgnoreCase(t)) balance += amt; else balance -= amt;
            }
            if (resultSet != null) { resultSet.close(); }

            String name = "";
            if (cardNumber != null) {
                String nameQuery = "SELECT s.name FROM signup s JOIN signup3 s3 ON s.form_no = s3.form_no WHERE s3.pin = ?";
                pstmt = c.getConnection().prepareStatement(nameQuery);
                pstmt.setString(1, pin);
                resultSet = pstmt.executeQuery();
                if (resultSet.next()) name = resultSet.getString("name");
            }
            
            if (cardNumber != null) {
                
                // Display account holder name
                JLabel nameLabel = new JLabel("Account Holder: " + name);
                nameLabel.setForeground(Color.WHITE);
                nameLabel.setFont(new Font("System", Font.BOLD, 20));
                nameLabel.setBounds(430,180,400,30);
                image.add(nameLabel);
                
                // Display card number (masked)
                String maskedCard = "****-****-****-" + cardNumber.substring(12);
                JLabel cardLabel = new JLabel("Card: " + maskedCard);
                cardLabel.setForeground(Color.WHITE);
                cardLabel.setFont(new Font("System", Font.BOLD, 16));
                cardLabel.setBounds(430,220,400,25);
                image.add(cardLabel);
                
                // Display balance
                JLabel balanceLabel = new JLabel("Your Current Balance is");
                balanceLabel.setForeground(Color.WHITE);
                balanceLabel.setFont(new Font("System", Font.BOLD, 20));
                balanceLabel.setBounds(430,270,400,30);
                image.add(balanceLabel);
                
                JLabel amountLabel = new JLabel("$ " + ValidationUtils.formatAmount(balance));
                amountLabel.setForeground(Color.WHITE);
                amountLabel.setFont(new Font("System", Font.BOLD, 30));
                amountLabel.setBounds(430,310,400,35);
                image.add(amountLabel);
                
                // Display last transaction date
                displayLastTransaction(image, pin);
                
            } else {
                JLabel errorLabel = new JLabel("Account not found");
                errorLabel.setForeground(Color.RED);
                errorLabel.setFont(new Font("System", Font.BOLD, 20));
                errorLabel.setBounds(430,250,400,30);
                image.add(errorLabel);
            }
            
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Error retrieving balance: " + e.getMessage());
            errorLabel.setForeground(Color.RED);
            errorLabel.setFont(new Font("System", Font.BOLD, 16));
            errorLabel.setBounds(430,250,400,30);
            image.add(errorLabel);
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
    
    private void displayLastTransaction(JLabel image, String pinParam) {
        Connn c = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        
        try {
            c = new Connn();
            
            String query = "SELECT type, amount, date FROM transactions WHERE pin = ? ORDER BY date DESC LIMIT 1";
            
            pstmt = c.getConnection().prepareStatement(query);
            pstmt.setString(1, pinParam);
            resultSet = pstmt.executeQuery();
            
            if (resultSet.next()) {
                String type = resultSet.getString("type");
                double amount = resultSet.getDouble("amount");
                String date = resultSet.getString("date");
                
                JLabel lastTransLabel = new JLabel("Last Transaction: " + type + " $" + ValidationUtils.formatAmount(amount));
                lastTransLabel.setForeground(Color.WHITE);
                lastTransLabel.setFont(new Font("System", Font.BOLD, 14));
                lastTransLabel.setBounds(430,360,400,25);
                image.add(lastTransLabel);
                
                JLabel dateLabel = new JLabel("Date: " + date);
                dateLabel.setForeground(Color.WHITE);
                dateLabel.setFont(new Font("System", Font.BOLD, 14));
                dateLabel.setBounds(430,385,400,25);
                image.add(dateLabel);
            }
            
        } catch (Exception e) {
            System.err.println("Error retrieving last transaction: " + e.getMessage());
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
    
    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        new main_Class(pin);
    }
    
    public static void main(String[] args) {
        new BalanceEnquriy("");
    }
}
