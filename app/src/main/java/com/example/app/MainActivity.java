package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnTouchListener, View.OnHoverListener {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }

    private TextView m_LogText;
    private ImageView pink;
    private ImageView orange;
    private ImageView kb;

    private final FlickListener.Listener flickListener = new FlickListener.Listener() {
        @Override
        public void onFlickToLeft() {
            // doSomething();
        }

        @Override
        public void onFlickToRight() {
            // doSomething();
        }

        @Override
        public void onFlickToUp() {
            // doSomething();
        }

        @Override
        public void onFlickToDown() {
            // doSomething();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // タッチイベント設定
        LinearLayout layout = (LinearLayout)this.findViewById(R.id.main_layout);
        layout.setOnTouchListener(this);
        layout.setOnHoverListener(this);


        // ListViewに表示するリスト項目をArrayListで準備する
        ArrayList data = new ArrayList<>();
        data.add("国語");
        data.add("社会");
        data.add("算数");
        data.add("理科");
        data.add("生活");
        data.add("音楽");
        data.add("図画工作");
        data.add("家庭");
        data.add("体育");

//        // リスト項目とListViewを対応付けるArrayAdapterを用意する
//        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
//
//        // ListViewにArrayAdapterを設定する
//        ListView listView = (ListView)findViewById(R.id.listview);
//        listView.setAdapter(adapter);
//
////        ListView listView = this.findViewById(R.id.listview);
//        listView.setOnTouchListener(new FlickListener(flickListener));

        // テキストビューの取得
        m_LogText = (TextView)this.findViewById(R.id.log_text);

        pink = findViewById(R.id.pink);
        orange = findViewById(R.id.orange);
        kb = findViewById(R.id.kb);

        orange.setX(-80.0f);
        orange.setY(-80.0f);
        pink.setX(-80.0f);
        pink.setY(-80.0f);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        String log = "";



        // 種別の取得
        int type = event.getToolType(0);
        if (type == MotionEvent.TOOL_TYPE_FINGER) {
            log += "ToolType : Finger\n";
        } else if (type == MotionEvent.TOOL_TYPE_STYLUS) {
            log += "ToolType : Stylus\n";
        } else if (type == MotionEvent.TOOL_TYPE_ERASER) {
            log += "ToolType : Eraser\n";
        } else {
            log += "ToolType : Unknown\n";
        }

        // タッチ座標の取得
        float x = event.getX();
        float y = event.getY();
        log += "X : " + x + "\n";
        log += "Y : " + y + "\n";

        sons(x,y,kb);

        // 筆圧取得
        float pressure = event.getPressure();
        log += "Pressure : " + pressure + "\n";
        m_LogText.setText(log);


        // その場所にポインタを表示
        orange.setX(x-20);
        orange.setY(y-10);

        // 押されているときはpinkのポインタを消す
        if(pressure == 1) {
            pink.setVisibility(View.INVISIBLE);
            orange.setVisibility(View.VISIBLE);
        }

        return true;
    }

    private int sons(float x,float y,ImageView kb){
        int son=0;
        x+=1;
        y+=1;
        int xsize = kb.getWidth();
        int ysize = kb.getHeight();

        int a = (int) (xsize/x);
        int b = (int) (ysize/y);

        return son;
    }


    public boolean onHover(View v, MotionEvent event) {
        String log = "";

        // 種別の取得
        int type = event.getToolType(0);
        if (type == MotionEvent.TOOL_TYPE_FINGER) {
            log += "ToolType : Finger\n";
        } else if (type == MotionEvent.TOOL_TYPE_STYLUS) {
            log += "ToolType : Stylus\n";
        } else if (type == MotionEvent.TOOL_TYPE_ERASER) {
            log += "ToolType : Eraser\n";
        } else {
            log += "ToolType : Unknown\n";
        }

        // タッチ座標の取得
        float x = event.getX();
        float y = event.getY();
        log += "X : " + x + "\n";
        log += "Y : " + y + "\n";

        // 筆圧取得
        float pressure = event.getPressure();
        log += "Pressure : " + pressure + "\n";
        m_LogText.setText(log);

        // その場所にポインタを表示
        pink.setX(x-50);
        pink.setY(y-20);

        // Hoverの時はorangeのポインタを消す
        if(pressure == 0){
            orange.setVisibility(View.INVISIBLE);
            pink.setVisibility(View.VISIBLE);
        }

        return true;
    }

    // ログ出力
    public void outputMotionEvent(MotionEvent event) {
        String log = "";

        // 端末情報
        log += "--------------------------------------\n";
        InputDevice device = event.getDevice();
        log += "device name : " + device.getName() + "\n";
        log += "device descriptor : " + device.getDescriptor() + "\n";

        m_LogText.setText(log);
    }

}