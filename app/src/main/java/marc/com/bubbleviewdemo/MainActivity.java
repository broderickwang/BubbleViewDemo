package marc.com.bubbleviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import marc.com.bubbleview.BubbleView;

public class MainActivity extends AppCompatActivity implements BubbleView.BubbleDisappearListner {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		BubbleView.attach(findViewById(R.id.bubble_text),this);
	}

	@Override
	public void disappear(View view) {

	}
}
