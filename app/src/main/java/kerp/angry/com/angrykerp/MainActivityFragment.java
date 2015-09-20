package kerp.angry.com.angrykerp;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment  implements SensorEventListener {

    private ImageButton imagebuttonImDeveloper, flybugs;
    private RelativeLayout MainPage;
    private int deadLine;
    private SensorManager sensorManager;
    private Sensor gyroSensor;
    private static final byte CHANGE = 0x1;

    private TextView tv_X;

    private TextView tv_Y;

    private TextView tv_Z;

    private TextView tv_info;

    private ObjectAnimator hasBugAnim;

    private TextView TextviewKillbugsScores;
    private int BugsScore = 0;

    public MainActivityFragment() {
    }

    public String SLogFunction(String BugKey, String BugValue){
        String a,b;
        a=BugKey;
        b=BugValue;
        return  String.format("%s:%s", a, b);

    }
    private void flyBugsAnimation() {
        Resources Res = this.getResources(); // 取得手機資源

        float deadLinePixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, //轉換dp值
                -12, //dp值
                Res.getDisplayMetrics());
        float FlytoHerePixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, //轉換dp值
                500, //dp值
                Res.getDisplayMetrics());

        hasBugAnim = ObjectAnimator.ofFloat(flybugs, "translationX", FlytoHerePixel, deadLinePixel);
        hasBugAnim.setDuration(6000);
        hasBugAnim.setRepeatCount(-1);

        hasBugAnim.start();

        flybugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                hasBugAnim.setDuration(0);
//                hasBugAnim.setRepeatCount(0);
//                hasBugAnim.removeAllListeners();
//                hasBugAnim.end();
//                hasBugAnim.cancel();

                BugGoToDie();
            }
        });
        hasBugAnim.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        imagebuttonImDeveloper = (ImageButton) view.findViewById(R.id.imagebuttonImDeveloper);
        flybugs = (ImageButton) view.findViewById(R.id.imageViewBugs);
        TextviewKillbugsScores = (TextView)view.findViewById(R.id.textviewKillbugsScores);
        MainPage = (RelativeLayout) view.findViewById(R.id.mainPage);
        tv_X=(TextView) view.findViewById(R.id.tvX);
        tv_Y=(TextView) view.findViewById(R.id.tvY);
        tv_Z=(TextView) view.findViewById(R.id.tvZ);
        tv_info=(TextView) view.findViewById(R.id.info);

        TextviewKillbugsScores.setText("Ymow kill " + "0000000000" + " bugs");
        gyroSensorFunction();
        BugAliveAgain();
        Resources Res = this.getResources(); // 取得手機資源
        android.view.ViewGroup.LayoutParams params = imagebuttonImDeveloper.getLayoutParams();
        float imageDevelopPX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, //轉換dp值
                72, //dp值
                Res.getDisplayMetrics());
        params.height = (int) imageDevelopPX;
        params.width = (int) imageDevelopPX;

        float imageDevelopLFMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, //轉換dp值
                16, //dp值
                Res.getDisplayMetrics());
        ((ViewGroup.MarginLayoutParams) imagebuttonImDeveloper.getLayoutParams()).leftMargin = (int) imageDevelopLFMargin;

        float imageDevelopLBMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, //轉換dp值
                44, //dp值
                Res.getDisplayMetrics());
        ((ViewGroup.MarginLayoutParams) imagebuttonImDeveloper.getLayoutParams()).bottomMargin = (int) imageDevelopLBMargin;


        imagebuttonImDeveloper.setLayoutParams(params);

        return view;
    }

    private void BugAliveAgain() {
        flybugs.setBackgroundResource(R.drawable.bug_alive);
        flyBugsAnimation();
    }

    private void BugGoToDie() {
        flybugs.setBackgroundResource(R.drawable.bug_die);
        BugsScore = BugsScore + 1;
        TextviewKillbugsScores.setText("Ymow kill 000000000" + String.valueOf(BugsScore) + " bugs");
        final Thread splashTread = new Thread() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int waited = 0;
                            while (waited < PrefData._splashTime) {
                                sleep(100);
                                waited += 100;
                                System.out.println("bugs die ");

                            }
                        } catch (Exception e) {
                            System.out.println("catch = ");
                        } finally {
                            BugAliveAgain();
                            Log.d("BugGoToDie", "to BugAliveAgain");
                        }

                    }

                });
            }
        };
        splashTread.start();
    }



    private void gyroSensorFunction() {
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (gyroSensor == null) {
            Toast.makeText(getActivity(), "您的設備不支援陀螺儀功能！", Toast.LENGTH_SHORT).show();
        } else {
            String hasGyroSensor = "\n名字：" + gyroSensor.getName() + "\n电池：" + gyroSensor.getPower() + "\n类型：" + gyroSensor.getType() + "\nVendor:" + gyroSensor.getVendor() + "\n版本：" + gyroSensor.getVersion() + "\n幅度：" + gyroSensor.getMaximumRange();
            Log.d("gyroSensor", hasGyroSensor);

            sensorManager.registerListener(sensoreventlistener, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

    };


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        sensorManager.unregisterListener(sensoreventlistener);

    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub

            switch (msg.what) {

                case CHANGE:

                    float[] valuse = (float[]) msg.obj;


                    tv_X.setText("沿X轴旋转的角度为：" + Float.toString(valuse[0]));
                    tv_Y.setText("沿Y轴旋转的角度为：" + Float.toString(valuse[1]));
                    tv_Z.setText("沿Z轴旋转的角度为：" + Float.toString(valuse[2]));
//                    Log.d("沿X轴旋转的角度为",Float.toString(valuse[0]));
                    break;
            }
            super.handleMessage(msg);
        }

    };

    private SensorEventListener sensoreventlistener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO Auto-generated method stub
            System.out.println("===========ll---");
            float[] valuse = event.values;
//        for (int i = 0; i < valuse.length; i++) {
//          System.out.println(valuse+"\n");
//        }
//        Message message=new Message();
//        message.obj=valuse;
//        message.what=CHANGE;
//        handler.sendMessage(message);
            tv_X.setText("沿X轴旋转的角度为：" + Float.toString(valuse[0]));
            tv_Y.setText("沿Y轴旋转的角度为：" + Float.toString(valuse[1]));
            tv_Z.setText("沿Z轴旋转的角度为：" + Float.toString(valuse[2]));

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}