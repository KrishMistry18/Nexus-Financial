package bank.management.system;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.util.Random;

public class Signup extends JFrame implements ActionListener {
    JRadioButton r1,r2,m1,m2,m3;
    JButton next, clear;

    JTextField textName, textFname, textEmail, textAdd, textcity, textState, textPin;
    JDateChooser dateChooser;
    Random ran = new Random();
    long first4 = (ran.nextLong() % 9000L) + 1000L;
    String first = " " + Math.abs(first4);
    
    Signup(){
        super("APPLICATION FORM");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image i2 = i1.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(25,10,100,100);
        add(image);

        JLabel label1 = new JLabel("APPLICATION FORM NO."+ first);
        label1.setBounds(160,20,600,40);
        label1.setFont(new Font("Raleway",Font.BOLD,38));
        add(label1);

        JLabel label2 = new JLabel("Page 1");
        label2.setFont(new Font("Ralway",Font.BOLD, 22));
        label2.setBounds(330,70,600,30);
        add(label2);

        JLabel label3 = new JLabel("Personal Details");
        label3.setFont(new Font("Ralway", Font.BOLD,22));
        label3.setBounds(290,90,600,30);
        add(label3);

        JLabel labelName = new JLabel("Name :");
        labelName.setFont(new Font("Ralway", Font.BOLD, 20));
        labelName.setBounds(100,190,100,30);
        add(labelName);

        textName = new JTextField();
        textName.setFont(new Font("Ralway",Font.BOLD, 14));
        textName.setBounds(300,190,400,30);
        add(textName);

        JLabel labelfName = new JLabel("Father's Name :");
        labelfName.setFont(new Font("Ralway", Font.BOLD, 20));
        labelfName.setBounds(100,240,200,30);
        add(labelfName);

        textFname = new JTextField();
        textFname.setFont(new Font("Ralway",Font.BOLD, 14));
        textFname.setBounds(300,240,400,30);
        add(textFname);

        JLabel DOB = new JLabel("Date of Birth");
        DOB.setFont(new Font("Ralway", Font.BOLD, 20));
        DOB.setBounds(100,340,200,30);
        add(DOB);

        dateChooser = new JDateChooser();
        dateChooser.setForeground(new Color(105,105,105));
        dateChooser.setBounds(300,340,400,30);
        add(dateChooser);

        JLabel labelG = new JLabel("Gender");
        labelG.setFont(new Font("Ralway", Font.BOLD, 20));
        labelG.setBounds(100,290,200,30);
        add(labelG);

        r1 = new JRadioButton("Male");
        r1.setFont(new Font("Ralway", Font.BOLD,14));
        r1.setBackground(new Color(222,255,228));
        r1.setBounds(300,290,60,30);
        add(r1);

        r2 = new JRadioButton("Female");
        r2.setBackground(new Color(222,255,228));
        r2.setFont(new Font("Ralway", Font.BOLD,14));
        r2.setBounds(450,290,90,30);
        add(r2);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(r1);
        buttonGroup.add(r2);

        JLabel labelEmail = new JLabel("Email address :");
        labelEmail.setFont(new Font("Ralway", Font.BOLD, 20));
        labelEmail.setBounds(100,390,200,30);
        add(labelEmail);

        textEmail = new JTextField();
        textEmail.setFont(new Font("Ralway",Font.BOLD, 14));
        textEmail.setBounds(300,390,400,30);
        add(textEmail);

        JLabel labelMs = new JLabel("Marital Status :");
        labelMs.setFont(new Font("Ralway", Font.BOLD, 20));
        labelMs.setBounds(100,440,200,30);
        add(labelMs);

        m1 = new JRadioButton("Married");
        m1.setBounds(300,440,100,30);
        m1.setBackground(new Color(222,255,228));
        m1.setFont(new Font("Ralway", Font.BOLD,14));
        add(m1);

        m2 = new JRadioButton("Unmarried");
        m2.setBackground(new Color(222,255,228));
        m2.setBounds(450,440,100,30);
        m2.setFont(new Font("Ralway", Font.BOLD,14));
        add(m2);

        m3 = new JRadioButton("Other");
        m3.setBackground(new Color(222,255,228));
        m3.setBounds(635,440,100,30);
        m3.setFont(new Font("Ralway", Font.BOLD,14));
        add(m3);

        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(m1);
        buttonGroup1.add(m2);
        buttonGroup1.add(m3);

        JLabel labelAdd = new JLabel("Address :");
        labelAdd.setFont(new Font("Ralway", Font.BOLD, 20));
        labelAdd.setBounds(100,490,200,30);
        add(labelAdd);

        textAdd = new JTextField();
        textAdd.setFont(new Font("Ralway",Font.BOLD, 14));
        textAdd.setBounds(300,490,400,30);
        add(textAdd);

        JLabel labelCity = new JLabel("City :");
        labelCity.setFont(new Font("Ralway", Font.BOLD, 20));
        labelCity.setBounds(100,540,200,30);
        add(labelCity);

        textcity = new JTextField();
        textcity.setFont(new Font("Ralway",Font.BOLD, 14));
        textcity.setBounds(300,540,400,30);
        add(textcity);

        JLabel labelPin = new JLabel("Pin Code :");
        labelPin.setFont(new Font("Ralway", Font.BOLD, 20));
        labelPin.setBounds(100,590,200,30);
        add(labelPin);

        textPin = new JTextField();
        textPin.setFont(new Font("Ralway",Font.BOLD, 14));
        textPin.setBounds(300,590,400,30);
        add(textPin);

        JLabel labelstate = new JLabel("State :");
        labelstate.setFont(new Font("Ralway", Font.BOLD, 20));
        labelstate.setBounds(100,640,200,30);
        add(labelstate);

        textState = new JTextField();
        textState.setFont(new Font("Ralway",Font.BOLD, 14));
        textState.setBounds(300,640,400,30);
        add(textState);

        // Add Clear button
        clear = new JButton("Clear");
        clear.setFont(new Font("Ralway",Font.BOLD, 14));
        clear.setBackground(Color.RED);
        clear.setForeground(Color.WHITE);
        clear.setBounds(520,710,80,30);
        clear.addActionListener(this);
        add(clear);

        next = new JButton("Next");
        next.setFont(new Font("Ralway",Font.BOLD, 14));
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.setBounds(620,710,80,30);
        next.addActionListener(this);
        add(next);

        getContentPane().setBackground(new Color(222,255,228));
        setLayout(null);
        setSize(850,800);
        setLocation(360,40);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clear) {
            clearFields();
        } else if (e.getSource() == next) {
            if (validateForm()) {
                processSignup();
            }
        }
    }
    
    private void clearFields() {
        textName.setText("");
        textFname.setText("");
        textEmail.setText("");
        textAdd.setText("");
        textcity.setText("");
        textState.setText("");
        textPin.setText("");
        dateChooser.setDate(null);
        r1.setSelected(false);
        r2.setSelected(false);
        m1.setSelected(false);
        m2.setSelected(false);
        m3.setSelected(false);
    }
    
    private boolean validateForm() {
        // Validate required fields
        if (!ValidationUtils.isValidString(textName.getText())) {
            ValidationUtils.showError("Please enter your name");
            textName.requestFocus();
            return false;
        }
        
        if (!ValidationUtils.isValidString(textFname.getText())) {
            ValidationUtils.showError("Please enter your father's name");
            textFname.requestFocus();
            return false;
        }
        
        if (dateChooser.getDate() == null) {
            ValidationUtils.showError("Please select your date of birth");
            return false;
        }
        
        if (!r1.isSelected() && !r2.isSelected()) {
            ValidationUtils.showError("Please select your gender");
            return false;
        }
        
        if (!ValidationUtils.isValidEmail(textEmail.getText())) {
            ValidationUtils.showError("Please enter a valid email address");
            textEmail.requestFocus();
            return false;
        }
        
        if (!m1.isSelected() && !m2.isSelected() && !m3.isSelected()) {
            ValidationUtils.showError("Please select your marital status");
            return false;
        }
        
        if (!ValidationUtils.isValidString(textAdd.getText())) {
            ValidationUtils.showError("Please enter your address");
            textAdd.requestFocus();
            return false;
        }
        
        if (!ValidationUtils.isValidString(textcity.getText())) {
            ValidationUtils.showError("Please enter your city");
            textcity.requestFocus();
            return false;
        }
        
        if (!ValidationUtils.isValidPincode(textPin.getText())) {
            ValidationUtils.showError("Please enter a valid 6-digit pincode");
            textPin.requestFocus();
            return false;
        }
        
        if (!ValidationUtils.isValidString(textState.getText())) {
            ValidationUtils.showError("Please enter your state");
            textState.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void processSignup() {
        try {
            String formno = first;
            String name = ValidationUtils.sanitizeSQL(textName.getText());
            String fname = ValidationUtils.sanitizeSQL(textFname.getText());
            String dob = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
            String gender = r1.isSelected() ? "Male" : "Female";
            String email = ValidationUtils.sanitizeSQL(textEmail.getText());
            String marital = m1.isSelected() ? "Married" : (m2.isSelected() ? "Unmarried" : "Other");
            String address = ValidationUtils.sanitizeSQL(textAdd.getText());
            String city = ValidationUtils.sanitizeSQL(textcity.getText());
            String pincode = textPin.getText();
            String state = ValidationUtils.sanitizeSQL(textState.getText());

            Connn c = new Connn();
            String query = "INSERT INTO signup (form_no, name, father_name, dob, gender, email, marital_status, address, city, state, pin_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement pstmt = c.getConnection().prepareStatement(query);
            pstmt.setString(1, formno);
            pstmt.setString(2, name);
            pstmt.setString(3, fname);
            pstmt.setString(4, dob);
            pstmt.setString(5, gender);
            pstmt.setString(6, email);
            pstmt.setString(7, marital);
            pstmt.setString(8, address);
            pstmt.setString(9, city);
            pstmt.setString(10, state);
            pstmt.setString(11, pincode);
            
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                ValidationUtils.showSuccess("Registration successful! Proceeding to next step...");
                new Signup2(formno);
                setVisible(false);
            } else {
                ValidationUtils.showError("Registration failed. Please try again.");
            }
            
            pstmt.close();
            c.closeConnection();
            
        } catch (Exception e) {
            ValidationUtils.showError("Error during registration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}
