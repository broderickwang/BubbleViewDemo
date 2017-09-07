package marc.com.bubbleviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import marc.com.bubbleview.BubbleView;

public class MainActivity extends AppCompatActivity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		BubbleView.attach(findViewById(R.id.bubble_text), new BubbleView.BubbleDisappearListner() {
			@Override
			public void disappear(View view) {
				Toast.makeText(MainActivity.this, view.getId() +" disappeared!", Toast.LENGTH_SHORT).show();
			}
		});
		BubbleView.attach(findViewById(R.id.bubble_btn),new BubbleView.BubbleDisappearListner() {
			@Override
			public void disappear(View view) {

			}
		});
		BubbleView.attach(findViewById(R.id.bubble_img),new BubbleView.BubbleDisappearListner() {
			@Override
			public void disappear(View view) {

			}
		});
	}

}
