package br.com.maurosantos.android.exemplowebservice;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Runnable {

    private EditText edtValor1;
    private EditText edtValor2;
    private Button btnCalcular;
    private TextView txtResultado;
    private Handler handler = new Handler();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtValor1 = (EditText) findViewById(R.id.edtValor1);
        edtValor2 = (EditText) findViewById(R.id.edtValor2);
        btnCalcular = (Button) findViewById(R.id.btnCalcular);
        txtResultado = (TextView) findViewById(R.id.txtResultado);

        btnCalcular.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Exemplo Webservice");
        progressDialog.setMessage("Processando...");
        progressDialog.show();

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

        String valor1 = edtValor1.getText().toString().trim();
        String valor2 = edtValor2.getText().toString().trim();

        if (valor1.isEmpty() || valor2.isEmpty()) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Entradas Inválidas");
            dlg.setMessage("Informe valores válidos para Valor 1 e/ou Valor 2");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        } else {
            try {
                CalculatorWS calculatorWS = new CalculatorWS();
                final int resultado = calculatorWS.add(Integer.parseInt(valor1), Integer.parseInt(valor2));

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        txtResultado.setText("Resultado: " + resultado);
                    }
                });
            } catch (Exception e) {
                Log.e("MainActivity", "Erro: " + e.getMessage());
            } finally {
                progressDialog.dismiss();
            }
        }
    }
}
