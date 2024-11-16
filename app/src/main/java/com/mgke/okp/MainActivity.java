package com.mgke.okp;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    // Объявляем элементы интерфейса
    private EditText inputD1, inputD2, inputGamma;
    private TextView resultText;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализируем элементы интерфейса
        inputD1 = findViewById(R.id.input_d1);
        inputD2 = findViewById(R.id.input_d2);
        inputGamma = findViewById(R.id.input_gamma);
        resultText = findViewById(R.id.result_text);
        calculateButton = findViewById(R.id.calculate_button);

        // Устанавливаем обработчик на кнопку
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем значения из полей ввода
                String d1Str = inputD1.getText().toString();
                String d2Str = inputD2.getText().toString();
                String gammaStr = inputGamma.getText().toString();

                // Проверка на пустые поля
                if (d1Str.isEmpty() || d2Str.isEmpty() || gammaStr.isEmpty()) {
                    showToast("Заполните все поля");
                    return;
                }

                // Проверяем, что введены только цифры
                if (!isValidInput(d1Str) || !isValidInput(d2Str) || !isValidInput(gammaStr)) {
                    showToast("Вводите только числа.");
                    return;
                }

                // Преобразуем введенные строки в числа
                double d1 = Double.parseDouble(d1Str);
                double d2 = Double.parseDouble(d2Str);
                double gamma = Double.parseDouble(gammaStr);

                // Проверка на отрицательные значения и угол больше 90°
                if (d1 <= 0 || d2 <= 0 || gamma <= 0 || gamma > 90) {
                    showSnackbar(v, "Значения не могут быть отрицательными или угол больше 90°");
                    return;
                }

                // Рассчитываем результат по формуле
                double result = 0.5 * d1 * d2 * Math.sin(Math.toRadians(gamma)); // Преобразуем угол в радианы

                // Форматируем результат с 2 знаками после запятой
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                resultText.setText("Результат: " + decimalFormat.format(result));
            }
        });
    }

    // Проверка на валидность ввода (только числа)
    private boolean isValidInput(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Метод для показа Toast
    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    // Метод для показа Snackbar
    private void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Можно добавить действия, если необходимо
                    }
                })
                .show();
    }
}
