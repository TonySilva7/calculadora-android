package com.tony.calculadora;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String TAG = "myDebug";

    // Variáveis p/ cálculo e screens
    private String operator = "";
    private Integer num1 = 0;
    private Integer num2 = 0;
    private EditText secondaryDisplay;
    private TextView mainDisplay;

    // Método ON CREATE iniciando as views
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instancia elementos da view
        mainDisplay = (TextView) findViewById(R.id.mainDisplay);
        secondaryDisplay = (EditText) findViewById(R.id.secondaryDisplay);
    }

    // método para capturar conteúdo dos botões numéricos e escreve-los no screen
    public void getButton(View v) {
        // captura conteúdo do botão
        String n1 = (String) ((Button) v).getText();
        Log.d(TAG, "(String) ((Button) v).getText() = " + n1);
        // escreve na tela dados recebidos
        secondaryDisplay.append(n1);
        mainDisplay.append(n1);

    }

    // testa se o conteúdo da tela não está vazio e é maior que zero, então converte-o para inteiro.
    public Integer toNumber() {
        if ((secondaryDisplay.getText().length() > 0) || !secondaryDisplay.getText().toString().contains("")) {
            int num = Integer.parseInt(String.valueOf(secondaryDisplay.getText()));
            Log.d(TAG, "numero() retornou " + num);
            return num;
        } else {
            return 0;
        }
    }

    // Método que calcula a soma
    public Integer sum(Integer a, Integer b) {
        Log.d(TAG, "Soma = " + (a + b));
        return (a + b);
    }

    // Método para calcular a subtração
    public Integer subtraction(Integer a, Integer b) {
        return (a - b);
    }

    // Método para calcular a divisão
    public Integer division(Integer a, Integer b) {
        return (a / b);
    }

    // Método que calcula multiplicação
    public Integer multiply(Integer a, Integer b) {
        return (a * b);
    }

    // Método para armazenar os botões do tipo operadores
    public void getOperation(View v) {
        // Se o conteúdo obtido for "CE", Reseta os dados da tela
        if (((String) ((Button) v).getText().toString()).equals("CE")) {
            num1 = 0;
            num2 = 0;
            secondaryDisplay.setText("");
            mainDisplay.setText("");
        } else {
            // Escreve na tela o conteúdo capturado
            mainDisplay.append((String) ((Button) v).getText().toString());
            Log.d(TAG, "Display Principal = " + ((String) ((Button) v).getText().toString()));

            // Se num1 ainda não estiver setado, receberá o primeiro operando
            if (num1 == 0) {
                num1 = toNumber();
                // limpa o display secundário
                secondaryDisplay.setText("");
                // Captura o operador da operação
                operator = (String) ((Button) v).getText().toString();
            } else {
                /*
                 * O Switch abaixo é para operações com mais de dois operandos.
                 * Pra todos os casos abaixo, se num1 já tiver um valor, ele será resolvido.
                 */
                switch (operator) {
                    //
                    case "+":
                        if (num1 == 0) {
                            num1 = toNumber();
                        } else {
                            num1 = sum(num1, toNumber());
                        }
                        secondaryDisplay.setText("");
                        operator = (String) ((Button) v).getText().toString();
                        break;
                    case "-":
                        if (num1 == 0) {
                            num1 = toNumber();
                        } else {
                            num1 = subtraction(num1, toNumber());
                        }
                        secondaryDisplay.setText("");
                        operator = (String) ((Button) v).getText().toString();
                        break;
                    case "/":
                        if (num1 == 0) {
                            num1 = toNumber();
                        } else {
                            num1 = division(num1, toNumber());
                        }
                        secondaryDisplay.setText("");
                        operator = (String) ((Button) v).getText().toString();
                        break;
                    case "X":
                        if (num1 == 0) {
                            num1 = toNumber();
                        } else {
                            num1 = multiply(num1, toNumber());
                        }
                        secondaryDisplay.setText("");
                        operator = (String) ((Button) v).getText().toString();
                        break;
                }
            }
        }
    }

    // Método para obter o correspondente binário do conteúdo digitado.
    public void fazBinario(View v) {
        num1 = toNumber();
        StringBuffer resultado = new StringBuffer();
        int sobra = 0;
        // Usa o resto da divisão para fazer a conversão para binários
        do {
            sobra = num1 % 2;
            num1 = num1 / 2;
            resultado.append(sobra);
        } while (num1 != 0);

        secondaryDisplay.setText(resultado.reverse());
    }


    // Método para imprimir o resultado das operações no screen da calculadora
    public void resultado(View v) {
        // Recebe o segundo operando
        num2 = toNumber();

        Log.d(TAG, "Num2 = " + num2);
        mainDisplay.append("="); // imprime sinal de "igual"
        secondaryDisplay.setText(""); // limpa display secundário

        /*
         * Switch para efetivar o a operação e imprimir os valores resultantes dessa operação.
         * As operações binárias serão calculadas e impressas dentro de cada case.
         * num1 atuará como um acumulador para operações em cadeia.
         */
        switch (operator) {
            case "+":
                mainDisplay.append(sum(num1, num2).toString());
                Log.d(TAG, "Case soma (+) = " + sum(num1, num2).toString());
                num1 = sum(num1, num2);
                break;

            case "-":
                mainDisplay.append(subtraction(num1, num2).toString());
                num1 = subtraction(num1, num2);
                break;

            case "x":
                mainDisplay.append(multiply(num1, num2).toString());
                num1 = multiply(num1, num2);
                break;

            case "÷":
                mainDisplay.append(division(num1, num2).toString());
                num1 = division(num1, num2);
                break;
        }
    }
}