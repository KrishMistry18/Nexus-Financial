package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class mini extends JFrame implements ActionListener {
    String pin;
    JButton button;
    mini(String pin){
        this.pin = pin;
        getContentPane().setBackground(new Color(255,204,204));
        setSize(400,600);
        setLocation(20,20);
        setLayout(null);

        JLabel label1 = new JLabel();
        label1.setBounds(20,140,400,200);
        add(label1);

        JLabel label2 = new JLabel("TechCoder A.V");
        label2.setFont(new Font("System", Font.BOLD,15));
        label2.setBounds(150,20,200,20);
        add(label2);

        JLabel label3 = new JLabel();
        label3.setBounds(20,80,300,20);
        add(label3);

        JLabel label4 = new JLabel();
        label4.setBounds(20,400,300,20);
        add(label4);

        try{
            Connn c = new Connn();
            PreparedStatement ps = c.getConnection().prepareStatement("SELECT card_number FROM login WHERE pin = ?");
            ps.setString(1, pin);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                label3.setText("Card Number:  "+ resultSet.getString("card_number").substring(0,4) + "XXXXXXXX"+ resultSet.getString("card_number").substring(12));
            }
            resultSet.close();
            ps.close();
            c.closeConnection();
        }catch (Exception e ){
            e.printStackTrace();
        }

        try{
            // Show transactions from the transactions table in chronological order
            Connn c = new Connn();
            PreparedStatement psTx = c.getConnection().prepareStatement(
                    "SELECT date, type, amount FROM transactions WHERE pin = ? ORDER BY date ASC");
            psTx.setString(1, pin);
            ResultSet rsTx = psTx.executeQuery();
            StringBuilder html = new StringBuilder();
            html.append("<html>");
            while (rsTx.next()){
                html.append(rsTx.getString("date"))
                    .append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
                    .append(rsTx.getString("type"))
                    .append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
                    .append(rsTx.getString("amount")).append("<br><br>");
            }
            html.append("</html>");
            label1.setText(html.toString());
            rsTx.close();
            psTx.close();

            // Compute current balance from the bank ledger
            double balance = 0.0;
            PreparedStatement ps = c.getConnection().prepareStatement("SELECT type, amount FROM bank WHERE pin = ?");
            ps.setString(1, pin);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                String t = resultSet.getString("type");
                double amt = Double.parseDouble(resultSet.getString("amount").trim());
                if (t.equalsIgnoreCase("Deposit")) balance += amt; else balance -= amt;
            }
            label4.setText("Your Total Balance is Rs "+bank.management.system.ValidationUtils.formatAmount(balance));
            resultSet.close();
            ps.close();
            c.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        button = new JButton("Exit");
        button.setBounds(20,500,100,25);
        button.addActionListener(this);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        add(button);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
    }

    public static void main(String[] args) {
        new mini("");
    }
}
