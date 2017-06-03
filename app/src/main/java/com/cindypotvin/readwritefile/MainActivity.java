package com.cindypotvin.readwritefile;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
   }

   /**
    * Write a single line to a file on the device when the write button is pressed.
    */
   public void onWriteToFileButtonClick(View view) {
      try {
         // Creates a file in the primary external storage space of the
         // current application.
         // If the file does not exists, it is created.
         File testFile = new File(this.getExternalFilesDir(null), "TestFile.txt");
         if (!testFile.exists())
            testFile.createNewFile();
         // Adds a line to the trace file
         BufferedWriter writer = new BufferedWriter(new FileWriter(testFile, true /*append*/));
         writer.write("This is a test file.");
         writer.close();
         // Refresh the data so it can seen when the device is plugged in a
         // computer. You may have to unplug and replug the device to see the
         // latest changes. This is not necessary if the user should not modify
         // the files.
         MediaScannerConnection.scanFile(this,
                 new String[]{testFile.toString()},
                 null,
                 null);
      } catch (IOException e) {
         Log.e("ReadWriteFile", "Unable to write to the TestFile.txt file.");
      }
      Log.v("ReadWriteFile", "Write to TestFile.txt file.");
   }

   /**
    * Read data from the file and insert in a textview when the refresh buttom is pressed.
    */
   public void onReadFromFileButtonClick(View view) {
      String textToDisplay = "";
      TextView FileContentTextView = (TextView) findViewById(R.id.tv_file_content);

      // Gets the file from the primary external storage space of the
      // current application.
      File testFile = new File(this.getExternalFilesDir(null), "TestFile.txt");
      if (testFile == null) {
         FileContentTextView.setText(textToDisplay);
         return;
      }

      StringBuilder stringBuilder = new StringBuilder();
      // Reads the data from the file
      BufferedReader reader = null;
      try {
         reader = new BufferedReader(new FileReader(testFile));
         String line;

         while ((line = reader.readLine()) != null) {
            textToDisplay += line.toString();
            textToDisplay += "\n";
         }
         reader.close();
      } catch (Exception e) {
         Log.e("ReadWriteFile", "Unable to read the TestFile.txt file.");
      }
      Log.v("ReadWriteFile", "Read from TestFile.txt file.");

      // Set the text read from the file to the textview
      FileContentTextView.setText(textToDisplay);
   }
}
