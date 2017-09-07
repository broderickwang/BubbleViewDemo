package marc.com.bubbleviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import marc.com.bubbleview.BubbleView;

public class MainActivity extends AppCompatActivity implements BubbleView.BubbleDisappearListner {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		BubbleView.attach(findViewById(R.id.bubble_text),this);
		BubbleView.attach(findViewById(R.id.bubble_btn),this);
		BubbleView.attach(findViewById(R.id.bubble_img),this);
	}

	@Override
	public void disappear(View view) {
		Toast.makeText(this, view.getId() + " disappeared!", Toast.LENGTH_SHORT).show();
	}
}
