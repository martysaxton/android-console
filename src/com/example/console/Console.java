package com.example.console;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

public class Console {

	private final TextView consoleTextView;
	private final Handler handler;

	public Console(TextView textView) {
		consoleTextView = textView;
		handler = new Handler(Looper.getMainLooper()) {
			public void handleMessage(android.os.Message msg) {
				appendLineFromUIThread((String) msg.obj);
			};
		};
	}

	public void appendLine(String line) {
		Message message = handler.obtainMessage();
		message.obj = line;
		message.sendToTarget();
	}

	private void appendLineFromUIThread(String line) {
		consoleTextView.append(line + "\n");
		final int scrollAmount = consoleTextView.getLayout().getLineTop(consoleTextView.getLineCount()) - consoleTextView.getHeight();
		if (scrollAmount > 0) {
			consoleTextView.scrollTo(0, scrollAmount);
		} else {
			consoleTextView.scrollTo(0, 0);
		}
	}
	
}
