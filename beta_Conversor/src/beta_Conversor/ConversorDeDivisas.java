package beta_Conversor;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConversorDeDivisas extends JFrame {

    private JComboBox<String> comboBoxOrigen;
    private JComboBox<String> comboBoxDestino;
    private JTextField textFieldValor;
    private JLabel labelResultado;

    private String[] opcionesDivisas = {"USD", "EUR", "JPY", "GBP", "CAD", "COP"};
    private String apiKey = "TU_APP_ID"; // Reemplaza con tu App ID

    public ConversorDeDivisas() {
        setTitle("Conversor de Divisas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel labelTitulo = new JLabel("Conversor de Divisas");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        comboBoxOrigen = new JComboBox<>(opcionesDivisas);
        comboBoxDestino = new JComboBox<>(opcionesDivisas);

        textFieldValor = new JTextField(10);

        JButton botonConvertir = new JButton("Convertir");
        labelResultado = new JLabel("Resultado:");

        setLayout(new GridLayout(6, 1));
        add(labelTitulo);
        add(new JLabel("Seleccione la divisa de origen:"));
        add(comboBoxOrigen);
        add(new JLabel("Seleccione la divisa de destino:"));
        add(comboBoxDestino);
        add(textFieldValor);
        add(botonConvertir);
        add(labelResultado);

        botonConvertir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertirDivisas();
            }
        });
    }

    private void convertirDivisas() {
        String divisaOrigen = (String) comboBoxOrigen.getSelectedItem();
        String divisaDestino = (String) comboBoxDestino.getSelectedItem();
        double valorAConvertir = Double.parseDouble(textFieldValor.getText());

        try {
            URL url = new URL("https://openexchangerates.org/api/latest.json?app_id=" + apiKey);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONObject rates = jsonResponse.getJSONObject("rates");
            double tasaDeCambioOrigen = rates.getDouble(divisaOrigen);
            double tasaDeCambioDestino = rates.getDouble(divisaDestino);

            double valorConvertido = (valorAConvertir / tasaDeCambioOrigen) * tasaDeCambioDestino;

            labelResultado.setText("Resultado: " + valorConvertido + " " + divisaDestino);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ConversorDeDivisas ventana = new ConversorDeDivisas();
                ventana.setVisible(true);
            }
        });
    }
}
