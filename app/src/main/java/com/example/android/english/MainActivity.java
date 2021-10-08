package com.example.android.english;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    TextView answCor, answWrg;
    Button btnEnter, btnReplace;
    RadioGroup radioGrEn, radioGrRus;
    RadioButton radioEn1, radioEn2, radioEn3, radioEn4, radioEn5;
    RadioButton radioRus1, radioRus2, radioRus3, radioRus4, radioRus5;

    // для чтения из файлов
    @Nullable
    InputStream rawis;
    @Nullable
    InputStreamReader isr;
    @Nullable
    BufferedReader br;

    // формируем массивы для англ. и русс. слов
    String [] word_engl;
    String [] word_rus;
    String strEn, strRu;
    int num; // количество строк
    int rn, rn0, rn1, rn2, rn3, rn4; // меняем вывод русских слов
    int[] randomNum; // пять случайно выбранных элемента

    int myCheckEn, myCheckRu;
    int answer_correct, answer_wrong;
    AnimationDrawable anim_wrong, anim_correct; // анимация btnEnter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnter = findViewById(R.id.ButtonEnter);
        btnReplace = findViewById(R.id.buttonReplace);
        answCor = findViewById(R.id.answerCorrect); // TextView правильных ответов
        answWrg = findViewById(R.id.answerWrong);  // TextView неправильных ответов

        radioGrEn = findViewById(R.id.rGrpEngl); // группа RadioButton на английском

        radioEn1 = findViewById(R.id.rBtnEngl1);
        radioEn2 = findViewById(R.id.rBtnEngl2);
        radioEn3 = findViewById(R.id.rBtnEngl3);
        radioEn4 = findViewById(R.id.rBtnEngl4);
        radioEn5 = findViewById(R.id.rBtnEngl5);
        radioEn1.setOnClickListener(radioButtonClickListener);
        radioEn2.setOnClickListener(radioButtonClickListener);
        radioEn3.setOnClickListener(radioButtonClickListener);
        radioEn4.setOnClickListener(radioButtonClickListener);
        radioEn5.setOnClickListener(radioButtonClickListener);
//        Log.d("myLogs","" + radioEn1.getId());

        radioGrRus = findViewById(R.id.rGrpRus);  // группа RadioButton на русском

        radioRus1 = findViewById(R.id.rBtnRus1);
        radioRus2 = findViewById(R.id.rBtnRus2);
        radioRus3 = findViewById(R.id.rBtnRus3);
        radioRus4 = findViewById(R.id.rBtnRus4);
        radioRus5 = findViewById(R.id.rBtnRus5);
        radioRus1.setOnClickListener(radioButtonClickListener);
        radioRus2.setOnClickListener(radioButtonClickListener);
        radioRus3.setOnClickListener(radioButtonClickListener);
        radioRus4.setOnClickListener(radioButtonClickListener);
        radioRus5.setOnClickListener(radioButtonClickListener);

        // Заполняем слова
        goFillingWords();
    }

    private void goFillingWords() {

        // посчет строк
        rawis = null;
        rawis = getResources().openRawResource(R.raw.text_engl);
        isr = new InputStreamReader(rawis);
        br = new BufferedReader(isr);
        num = 0;
        randomNum = new int[5];

        try {
            while (br.readLine() != null) {
                num++;
            }
            rawis.close();    // закрыли
        } catch (IOException e) {
            e.printStackTrace();
        }
        word_engl = new String[num];
        word_rus = new String[num];

        Toast.makeText(this, "" + num,Toast.LENGTH_SHORT).show();

        // читаем английские слова
        // открываем канал, создаем переменную для работы с каналом
        // переменная читает НЕ побуквенно, а строку целиком,
        // закрываем канал
        rawis = null;
        try {
            rawis = getResources().openRawResource(R.raw.text_engl);
            isr = new InputStreamReader(rawis);
            br = new BufferedReader(isr);
            String temp;
            int i = 0;
            while ((temp = br.readLine()) != null) {
                word_engl[i] = temp;
                i++;
            }
            rawis.close();     // закрыли
        } catch (IOException e) {
            e.printStackTrace();
//            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        // читаем русские слова
        rawis = null;
        try {
            rawis = getResources().openRawResource(R.raw.text_rus);
            isr = new InputStreamReader(rawis);
            br = new BufferedReader(isr);
            String temp;
            int i = 0;
            while ((temp = br.readLine()) != null) {
                word_rus[i] = temp;
                i++;
            }
            rawis.close();    // закрыли
        } catch (IOException e) {
            e.printStackTrace();
        }

        // формируем случайный набор из 5 РАЗНЫХ слов
        for (int i = 0; i < 5; i++) {
            do {
                rn = 1 + (int) ((num - 1 + 1) * Math.random());
            } while (randomNum[0] == rn || randomNum[1] == rn ||randomNum[2] == rn ||
                    randomNum[3] == rn ||randomNum[4] == rn);
            randomNum[i] = rn;
        }

        // Выводим на английские кнопки
        radioEn1.setText(word_engl[randomNum[0]]);
        radioEn2.setText(word_engl[randomNum[1]]);
        radioEn3.setText(word_engl[randomNum[2]]);
        radioEn4.setText(word_engl[randomNum[3]]);
        radioEn5.setText(word_engl[randomNum[4]]);
        Log.d("myLogs","" + randomNum[0] + ", " + randomNum[1] + ", " +
                randomNum[2] + ", " + randomNum[3] + ", " + randomNum[4]);

        // случайное раставление руских слов
        rn0 = (int) ((5 - 1 + 1) * Math.random());
        do {
            rn1 = (int) ((5 - 1 + 1) * Math.random());
        } while (rn1==rn0);
        do {
            rn2 = (int) ((5 - 1 + 1) * Math.random());
        } while (rn2==rn0 | rn2==rn1);
        do {
            rn3 = (int) ((5 - 1 + 1) * Math.random());
        } while (rn3==rn0 | rn3==rn1 | rn3==rn2);
        do {
            rn4 = (int) ((5 - 1 + 1) * Math.random());
        } while (rn4==rn0 | rn4==rn1 | rn4==rn2 | rn4==rn3);


        // выводим русские слова
        radioRus1.setText(word_rus[randomNum[rn0]]);
        radioRus2.setText(word_rus[randomNum[rn1]]);
        radioRus3.setText(word_rus[randomNum[rn2]]);
        radioRus4.setText(word_rus[randomNum[rn3]]);
        radioRus5.setText(word_rus[randomNum[rn4]]);
        Log.d("myLogs","" + randomNum[rn0] + ", " + randomNum[rn1] + ", " +
                randomNum[rn2] + ", " + randomNum[rn3] + ", " + randomNum[rn4]);

        // Начальная надпись на кнопке
        String startButton = radioEn1.getText() + " = " + radioRus1.getText();
        btnEnter.setText(startButton);
        // chek на первых кнопках
        radioEn1.setChecked(true);
        radioRus1.setChecked(true);
    }

    final View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        // Узнаем идентификатор выбранного переключателя методом getCheckedRadioButtonId()
        public void   onClick(View v) {

            btnEnter.setEnabled(true);

            int checkRadioButIdEn = radioGrEn.getCheckedRadioButtonId();
            int checkRadioButIdRu = radioGrRus.getCheckedRadioButtonId();

//            radioGrEn.clearCheck(); // удалить все check в RadioGroup

            // Найдём переключатель по его id En
            RadioButton  myRadioCheckEn = findViewById(checkRadioButIdEn);
            myCheckEn = myRadioCheckEn.getId();
//            Log.d("myLogs","" + myCheckEn);

            // Проверяем видимость кнопки En
            if (myRadioCheckEn.getVisibility() == View.GONE) {
                myCheckEn = 0;
                strEn = "?";
            }else {
                strEn = "" + myRadioCheckEn.getText();
            }

            // Найдём переключатель по его id Ru
            RadioButton myRadioCheckRu = findViewById(checkRadioButIdRu);
            myCheckRu = myRadioCheckRu.getId();
//            Log.d("myLogs","" + myCheckRu);

//          Проверяем видимость кнопки Ru
            if (myRadioCheckRu.getVisibility() == View.GONE ) {
                myCheckRu = 0;
                strRu = "?";
            }else {
                strRu = "" + myRadioCheckRu.getText();
            }

            // Выведем текст на ImageButton
            String str = strEn + " = " + strRu;
            btnEnter.setText(str);
        }
    };

    public void onClickEnter(View view) {

        if (myCheckEn==0 || myCheckRu==0)
            return;

        int [] vectorEnglId = {radioEn1.getId(), radioEn2.getId(), radioEn3.getId(),
                radioEn4.getId(), radioEn5.getId()};
        int [] vectorRusId = {radioRus1.getId(), radioRus2.getId(), radioRus3.getId(),
                radioRus4.getId(), radioRus5.getId()};

        if (myCheckEn == vectorEnglId[rn0] && myCheckRu == vectorRusId[0]) {
            answer_correct++;
            btnEnter.setText(""); // убираем текст
            // Анимация правильного нажатия
            goAnimCorrect();

//            Toast.makeText(this, "Правильно",Toast.LENGTH_SHORT).show();

            radioRus1.setVisibility(View.GONE);

            switch (rn0) {
                case 0:radioEn1.setVisibility(View.GONE); break;
                case 1:radioEn2.setVisibility(View.GONE); break;
                case 2:radioEn3.setVisibility(View.GONE); break;
                case 3:radioEn4.setVisibility(View.GONE); break;
                case 4:radioEn5.setVisibility(View.GONE); break;
            }
        } else
        if (myCheckEn == vectorEnglId[rn1] && myCheckRu == vectorRusId[1]) {
            answer_correct++;
            btnEnter.setText(""); // убираем текст
            // Анимация правильного нажатия
            goAnimCorrect();
//            Toast.makeText(this, "Правильно",Toast.LENGTH_SHORT).show();

            radioRus2.setVisibility(View.GONE);

            switch (rn1) {
                case 0:radioEn1.setVisibility(View.GONE); break;
                case 1:radioEn2.setVisibility(View.GONE); break;
                case 2:radioEn3.setVisibility(View.GONE); break;
                case 3:radioEn4.setVisibility(View.GONE); break;
                case 4:radioEn5.setVisibility(View.GONE); break;
            }

        } else
        if (myCheckEn == vectorEnglId[rn2] && myCheckRu == vectorRusId[2]) {
            answer_correct++;
            btnEnter.setText(""); // убираем текст
            // Анимация правильного нажатия
            goAnimCorrect();
//            Toast.makeText(this, "Правильно",Toast.LENGTH_SHORT).show();

            radioRus3.setVisibility(View.GONE);
            switch (rn2) {
                case 0:radioEn1.setVisibility(View.GONE); break;
                case 1:radioEn2.setVisibility(View.GONE); break;
                case 2:radioEn3.setVisibility(View.GONE); break;
                case 3:radioEn4.setVisibility(View.GONE); break;
                case 4:radioEn5.setVisibility(View.GONE); break;
            }

        } else
        if (myCheckEn == vectorEnglId[rn3] && myCheckRu == vectorRusId[3]) {
            answer_correct++;
            btnEnter.setText(""); // убираем текст
            // Анимация правильного нажатия
            goAnimCorrect();
//            Toast.makeText(this, "Правильно",Toast.LENGTH_SHORT).show();
            radioRus4.setVisibility(View.GONE);
            switch (rn3) {
                case 0:radioEn1.setVisibility(View.GONE); break;
                case 1:radioEn2.setVisibility(View.GONE); break;
                case 2:radioEn3.setVisibility(View.GONE); break;
                case 3:radioEn4.setVisibility(View.GONE); break;
                case 4:radioEn5.setVisibility(View.GONE); break;
            }

        } else
        if (myCheckEn == vectorEnglId[rn4] && myCheckRu == vectorRusId[4]) {
            answer_correct++;
            btnEnter.setText(""); // убираем текст
            // Анимация правильного нажатия
            goAnimCorrect();
//            Toast.makeText(this, "Правильно",Toast.LENGTH_SHORT).show();
            radioRus5.setVisibility(View.GONE);

            switch (rn4) {
                case 0:radioEn1.setVisibility(View.GONE); break;
                case 1:radioEn2.setVisibility(View.GONE); break;
                case 2:radioEn3.setVisibility(View.GONE); break;
                case 3:radioEn4.setVisibility(View.GONE); break;
                case 4:radioEn5.setVisibility(View.GONE); break;
            }

        } else {
            answer_wrong++;
            // АНИМАЦИЯ НЕправильного нажатия
            goAnimWrong();
            Toast.makeText(this, "НЕ правильно",Toast.LENGTH_SHORT).show();}

        // Записываем тект
        String stranswok = "Правильных ответов: " + answer_correct;
        answCor.setText(stranswok);
        String stranswnot ="Не правильных ответов: " + answer_wrong;
        answWrg.setText(stranswnot);

        // проверка, если все кнопки нажаты, то btnEnter неактивен
        if (radioEn1.getVisibility() == View.GONE &&
                radioEn2.getVisibility() == View.GONE &&
                radioEn3.getVisibility() == View.GONE &&
                radioEn4.getVisibility() == View.GONE &&
                radioEn5.getVisibility() == View.GONE) {
//            btnEnter.setEnabled(false);
            btnReplace.performClick();

        }

        btnEnter.setEnabled(false);
    }

    private void goAnimWrong() {
        btnEnter.setBackgroundResource(R.drawable.anim_button_wrong);
        anim_wrong = (AnimationDrawable) btnEnter.getBackground();
        anim_wrong.setOneShot(true);
        anim_wrong.stop();
        anim_wrong.start();
    }

    private void goAnimCorrect() {
        btnEnter.setBackgroundResource(R.drawable.anim_button_correct);
        anim_correct = (AnimationDrawable) btnEnter.getBackground();
        anim_correct.setOneShot(true);
        anim_correct.stop();
        anim_correct.start();
    }

    public void onClicReplace(View view) {

        // Надо повключать все View-ы
        btnEnter.setEnabled(true);

        radioEn1.setVisibility(View.VISIBLE);
        radioEn2.setVisibility(View.VISIBLE);
        radioEn3.setVisibility(View.VISIBLE);
        radioEn4.setVisibility(View.VISIBLE);
        radioEn5.setVisibility(View.VISIBLE);
        radioRus1.setVisibility(View.VISIBLE);
        radioRus2.setVisibility(View.VISIBLE);
        radioRus3.setVisibility(View.VISIBLE);
        radioRus4.setVisibility(View.VISIBLE);
        radioRus5.setVisibility(View.VISIBLE);

        goFillingWords();
        Toast.makeText(this, "replace",Toast.LENGTH_SHORT).show();
    }
}