package com.econnect.client;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.econnect.Utilities.PopupMessage;
import com.econnect.Utilities.SettingsFile;
import com.econnect.client.RegisterLogin.RegisterActivity;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;

public class StartupActivity extends AppCompatActivity {

    public static String CUSTOM_LANGUAGE_KEY = "CUSTOM_LANGUAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(this::exceptionHandler);

        attemptAutoSetLanguage();

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    void attemptAutoSetLanguage() {
        final SettingsFile file = new SettingsFile(this);
        final String language = file.getString(CUSTOM_LANGUAGE_KEY);
        // Language must be either null or an ISO 639 language code

        if (language == null) {
            // No custom language set, use device default
            return;
        }

        Locale locale = new Locale(language);

        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    private void exceptionHandler(Thread thread, Throwable throwable) {
        final String path = getFilesDir().getPath() + "/crash.txt";
        final File printFile = new File(path);
        final PrintStream output;
        try {
            printFile.createNewFile();
            output = new PrintStream(printFile);
        } catch (IOException e) {
            System.err.println("ERROR! Path was: " + path);
            e.printStackTrace();
            return;
        }

        printRow(output);
        throwable.printStackTrace(output);
        printRow(output);

        PopupMessage.showToast(this, "Stack trace written at: " + path);
        // Exit
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    private void printRow(PrintStream p) {
        final int ROW_SIZE = 50;

        p.println();
        for (int i = 0; i < ROW_SIZE; i++) {
            p.print('=');
        }
        p.println();
        p.println();
    }
}