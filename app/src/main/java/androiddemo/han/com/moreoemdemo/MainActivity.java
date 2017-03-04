package androiddemo.han.com.moreoemdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView text;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.tvtext);
        img = (ImageView) findViewById(R.id.img);
        img.setImageResource(Util.getMetaDataInt(this,"OEM_LOGO"));

        text.setText(R.string.dynamic_text);

    }
}
