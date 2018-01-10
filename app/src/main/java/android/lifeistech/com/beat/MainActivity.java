package android.lifeistech.com.beat;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    int bpm;
    int bpm_a;
    int bpm_b;
    long bpm_c;
    int count;
    int cnt;
    Bitmap b1;
    Bitmap b2;
    Bitmap b3;
    Bitmap b4;
    Bitmap b5;
    ImageView visual;
    EditText bpm_e;
    SoundPool ms;

    int mySoundID;          //サウンド管理ID
    int oto;                //サウンド
    SoundPool soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bpm_e = (EditText)findViewById(R.id.bpm_e);
        visual = (ImageView)findViewById(R.id.visual);
        Resources r = getResources();
        bpm_c = 120;
        b1 = BitmapFactory.decodeResource(r, R.drawable.b1);
        b2 = BitmapFactory.decodeResource(r, R.drawable.b2);
        b3 = BitmapFactory.decodeResource(r, R.drawable.b3);
        b4 = BitmapFactory.decodeResource(r, R.drawable.b4);
        b5 = BitmapFactory.decodeResource(r, R.drawable.b5);

        //サウンドプールをクリア
        soundPool = null;

        //音を出すための手続き１　※音の出し方を設定している
        AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build();

        //音を出すための手続き２　※１の設定を利用してsoundPoolを設定
        soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(1).build();

        //鳴らしたい音を設定（rawフォルダにあるsound1という音）
        oto = getResources().getIdentifier("met", "raw", getPackageName());

        //あらかじめ音をロードする必要がある　※直前にロードしても間に合わないので早めに
        mySoundID = soundPool.load(getBaseContext(), oto, 1);

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                count++;
                cnt++;
                if (count == 5){
                    count = 1;
                }
                sncnt();
                    set();
                handler.postDelayed(this, bpm_c);
            }
        };
        handler.post(runnable);
    }
    public void set(){
        if(count == 1){
            visual.setImageBitmap(b2);
        }else if(count == 2){
            visual.setImageBitmap(b3);
        }else if(count == 3){
            visual.setImageBitmap(b4);
        }else if(count == 4){
            visual.setImageBitmap(b5);
        }else if(count == 5){
            visual.setImageBitmap(b1);
            if(cnt > 5){
            }
        }
    }
    public void restart(View v){
        String str1 = bpm_e.getText().toString();
        if(Pattern.matches("^-?[0-9]*.?[0-9]+$", str1)){
            bpm = Integer.parseInt(str1);
            bpm_a = 60000 / bpm;
            bpm_b = bpm_a / 5;
            bpm_c = bpm_b;
        }
    }
    public void sncnt(){
        if(cnt > 3){
            soundPool.play(mySoundID, 0.1f, 0.1f, 0, 0, 1);
            cnt = 0;
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ms.release();
    }
}
