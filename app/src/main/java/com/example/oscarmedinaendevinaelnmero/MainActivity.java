package com.example.oscarmedinaendevinaelnmero;

import android.content.DialogInterface;
import android.os.Bundle;
import java.util.Random;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

/*
    Ves al layout i crea la casella d'entrada per al número. Es tracta d'un objecte TextEdit,
    però volem restringir-ho perquè només ens deixi entrar nombres i no lletres. Entre els
    objectes disponibles al IDE hi ha un de predefinit que ja ens ho facilita.

    Crea un botó i un listener perquè l'usuari validi l'entrada del número.
    Prova que funciona amb un Toast, per exemple.

    Investiga com crear un nombre aleatori en Java. Crea un al mètode onCreate de l'Activity
    i emmagatzema-ho en una propietat de l'objecte per tenir-ho disponible quan calgui.

    Anem al codi principal del joc, que estarà principalment al onClick del listener. Quan l'usuari
    entri un número li mostrem un Toast dient-li si el nombre que busca és major o menor.

    Afegeix un AlertDialog per avisar l'usuari de quan acaba la partida i felicitar-lo.

    Quan s'acaba la partida, es regenera el número aleatori i es torna a jugar.

    Posa un TextView per anar indicant a l'usuari l'historial dels intents i resultats que ha obtingut.

    Per aconseguir un scroll en la pantalla de l'historial podem posar un ScrollView i a dins seu posar el TextView.

    Implementa un comptador d'intents que es visualitzi en algun racó de la pantalla. Ens servirà per després fer el ranking.

Podem millorar la jugabilitat amb alguns detalls més.

    Per facilitar el joc a l'usuari, esborrem el número del EditText quan l'usuari fa un intent d'endevinar
    (si no, l'usuari haurà d'esborrar-ho manualment).

    Es pot millorar la jugabilitat si implementem que el joc detecti la tecla Enter del teclat de pantalla.
    S'implementa millor amb un OnEditorActionListener ja que el OnClickListener amaga el teclat al prèmer ENTER.
    
    Encara que detecteu correctament el Enter, us passarà que trobareu un ACTION_UP i un ACTION_DOWN,
    pel que la callback es cridarà dos cops. Filtreu aquests dos esdeveniments i avalueu l'entrada de
    l'usuari només en un dels dos (preferentment ACTION_DOWN). També convé recuperar el focus dintre del
    EditText perquè l'usuari no hagi de clicar de nou el widget cada cop que fa un intent.

 */

public class MainActivity extends AppCompatActivity {

    int number = 0;
    int attempts = 0;
    TextView attemptsText;
    TextView historyText;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        number = new Random().nextInt(100)+1;

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        editText = findViewById(R.id.inputText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    CheckInput();
                    return true;
                }
                return false;
            }
        });
        attemptsText = findViewById(R.id.attempts);
        attemptsText.setText("Attempts: " + 0);
        historyText = findViewById(R.id.history);
        ClearHistory();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckInput();
            }
        });
        
    }

    public void CheckInput() {
        SetAttempts(attempts+1);
        String input = editText.getText().toString();
        TestInput(input);
        editText.setText("");
    }

    public void TestInput(String input) {
        int num = 0;
        try {
            num = Integer.parseInt(input);
        }
        catch (Exception e) {
            Toast.makeText(this, "Put a number between 1 and 100", Toast.LENGTH_SHORT).show();
            return;
        }
        if (num < 1 || num > 100) {
            Toast.makeText(this, "Put a number between 1 and 100", Toast.LENGTH_SHORT).show();
        }
        else if (num < number) {
            Toast.makeText(this, "The number is greater than " + num, Toast.LENGTH_SHORT).show();
            AddHistory("The number is greater than " + num);
        }
        else if (num > number) {
            Toast.makeText(this, "The number is less than " + num, Toast.LENGTH_SHORT).show();
            AddHistory("The number is less than " + num);
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("You win!");
            builder.setMessage("You won in " + attempts + " attempts");
            builder.setPositiveButton("New game", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    number = new Random().nextInt(100)+1;
                    SetAttempts(0);
                    ClearHistory();
                }
            });
            builder.show();
        }
    }

    public void SetAttempts(int num) {
        attempts = num;
        attemptsText.setText("Attempts: " + attempts);
    }

    public void AddHistory(String text) {
        historyText.append(text + "\n");
    }

    public void ClearHistory() {
        historyText.setText("history:\n");
    }
}