package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
    private TextView m_IndText;
    private ImageView pink;
    private ImageView orange;
    private ImageView kb;
    private boolean Insele=false;

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
        m_IndText = (TextView)this.findViewById(R.id.ind_text);

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

        // 押されている場所の位置を取得(離されるまで帰ってこない)
        locas(x, y, kb,event);

        return true;
    }

    private void locas(float x,float y,ImageView kb,MotionEvent event){
        char son;
        x+=1;
        y+=1;
        // 押されている場所のキーの座標取得する
        int xind=0,yind=0;
        // キーボードの中の位置
        int[] location = new int[2];
        kb.getLocationOnScreen(location);
        // キーボードのサイズ
        int xsize = kb.getWidth();
        int ysize = kb.getHeight();
        // キーボード内で正規化されたタッチ位置の座標
        float xloc = x-location[0];
        float yloc = y-location[1]+85;

        for(int i=1;i<6;i++){
            if(xloc <= i*xsize/5){
                xind = i;
                break;
            }
        }

        for(int i=1;i<5;i++){
            if(yloc <= i*ysize/4){
                yind = i;
                break;
            }
        }

        tkch(xind,yind);

        String log = "";

        // タッチ座標の取得
        log += "X : " + xind + "\n";
        log += "Y : " + yind + "\n";

        // 筆圧取得
        m_IndText.setText(log);

//        while(event.getPressure()==1){
//
//            // タッチ座標の取得
//            float xx = event.getX();
//            float yy = event.getY();
//            log += "X : " + x + "\n";
//            log += "Y : " + y + "\n";
//            locas(xx, yy, kb,event);
//            m_LogText.setText(log);
//        }
    }

    private char tkch(int x,int y){
        char moji='n';
        Insele = true;

        if(moji!='n'){
            Insele = false;
        }
        return moji;
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