package com.imengyu.android_helpers.wxvideoeditt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esay.ffmtool.FfmpegTool;
import com.imengyu.android_helpers.R;
import com.imengyu.android_helpers.utils.StatusBarUtils;
import com.imengyu.android_helpers.utils.StringUtils;
import com.kongzue.dialogx.dialogs.WaitDialog;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by ZBK on 2017/8/11.
 * Describe:仿微信10秒小视频编辑
 */

public class EsayVideoEditActivity extends AppCompatActivity implements RangeBar.OnRangeBarChangeListener {

    public static final String PATH = "path";
    public static final String TITLE = "title";
    public static final String DURATION = "duration";

    private final static int MSG_FINISH = 2;

    private final int IMAGE_NUM = 10;//每一屏图片的数量

    private RecyclerView recyclerview;
    private RangeBar rangeBar;
    private FrameLayout fram;
    private VideoView uVideoView;
    private TextView text_time;

    private String videoPath;
    private String parentPath;
    private long videoTime;
    private Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private FfmpegTool ffmpegTool;

    private Intent intent = null;
    private int firstItem = 0;//recycleView当前显示的第一项
    private int lastItem = 0;//recycleView当前显示的最后一项
    private int leftThumbIndex = 0;//滑动条的左端
    private int rightThumbIndex = IMAGE_NUM;//滑动条的右端
    private int startTime, endTime = IMAGE_NUM;//裁剪的开始、结束时间
    private int maxDuration = 60;//最长截取时间
    private String videoResultDir;//视频裁剪结果的存放目录
    private String videoResult;
    private boolean videoCorpResult;
    private ExecutorService executorService = Executors.newFixedThreadPool(3);

    private WaitDialog loadingDialog = null;

    private static class MainHandler extends Handler {
        private final WeakReference<EsayVideoEditActivity> mTarget;

        MainHandler(EsayVideoEditActivity target) {
            super(Looper.myLooper());
            mTarget = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == EsayVideoEditActivity.MSG_FINISH) {
                EsayVideoEditActivity activity = mTarget.get();

                activity.loadingDialog.doDismiss();
                Toast.makeText(activity, activity.videoCorpResult ? "裁剪视频完成" : "裁剪视频失败", Toast.LENGTH_LONG).show();

                Intent data = new Intent();
                data.putExtra("success", activity.videoCorpResult );
                data.putExtra("path", activity.videoResult);
                activity.setResult(Activity.RESULT_OK, data);
            }
        }
    }
    private final MainHandler handler = new MainHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_video);

        StatusBarUtils.setDarkMode(this);

        intent = getIntent();
        videoPath = intent.getStringExtra(PATH);
        maxDuration = intent.getIntExtra(DURATION, 60);

        text_time = findViewById(R.id.text_time);
        recyclerview = findViewById(R.id.recyclerview);
        rangeBar = findViewById(R.id.rangeBar);
        fram = findViewById(R.id.fram);
        uVideoView = findViewById(R.id.uVideoView);

        Log.i("onCreate", "videoPath:" + videoPath);
        if (!new File(videoPath).exists()) {
            Toast.makeText(this, "视频文件不存在", Toast.LENGTH_LONG).show();
            finish();
        }

        String str = "temp" + System.currentTimeMillis() / 1000;
        parentPath = getCacheDir() + File.separator + "video-editor" + File.separator + str + File.separator;
        videoResultDir = getCacheDir() + File.separator + "video-editor" + File.separator + "clicp";

        File file1 = new File(parentPath);
        File file2 = new File(videoResultDir);
        if (!file1.exists()) file1.mkdirs();
        if (!file2.exists()) file2.mkdirs();
        file1.deleteOnExit();
        file2.deleteOnExit();

        rangeBar.setmTickCount(maxDuration + 1);
        videoTime = UIUtil.getVideoDuration(videoPath);

        Log.i("onCreate", "videoTime:" + videoTime);

        ffmpegTool = FfmpegTool.getInstance(this);
        ffmpegTool.setImageDecodeing((s, i) -> adapter.notifyItemRangeChanged(i, 1));

        initView();
        initData();
    }

    private void initView() {

        findViewById(R.id.btn_ok).setOnClickListener(this::onOkClick);
        findViewById(R.id.btn_cancel).setOnClickListener(this::onCancelClick);
        ((TextView)findViewById(R.id.text_top)).setText(intent.getStringExtra(TITLE));

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        adapter = new Adapter(this, getDataList(videoTime));
        adapter.setParentPath(parentPath);
        adapter.setRotation(UIUtil.strToFloat(UIUtil.getVideoInf(videoPath)));
        recyclerview.setAdapter(adapter);
        recyclerview.addOnScrollListener(onScrollListener);
        rangeBar.setOnRangeBarChangeListener(this);//设置滑动条的监听
        uVideoView.setVideoPath(videoPath);
        uVideoView.start();

        updateSelectTimeString();
    }

    /**
     * 第一次解码，先解码两屏的图片
     */
    private void initData() {
        //Toast.makeText(this, "第一次解码中，先解码两屏的图片", Toast.LENGTH_SHORT).show();
        runImageDecodTask(0, 2 * IMAGE_NUM);
    }

    /**
     * 取消
     * @param view view
     */
    public void onCancelClick(View view) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    /**
     * 确定并裁剪视频
     * @param view view
     */
    public void onOkClick(View view) {
        uVideoView.stopPlayback();
        loadingDialog = WaitDialog.show("正在处理视频中，请稍后");

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String video = videoResultDir + File.separator + "clip" + System.currentTimeMillis() / 1000 + ".mp4";
                ffmpegTool.clipVideo(videoPath, video, startTime, endTime - startTime, 2, (i, s, s1, b, i1) -> {
                    Log.i("clipResult", "clipResult:" + s1);
                    videoCorpResult = b;
                    videoResult = s1;
                });
            }
        });
    }

    /**
     * 根据视频的时长，按秒分割成多个data先占一个位置
     * @return
     */
    public List<Data> getDataList(long videoTime) {
        List<Data> dataList = new ArrayList<>();
        int seconds = (int) (videoTime / 1000);
        //整个视频要解码图片的总数量
        for (int imageCount = 0; imageCount < seconds; imageCount++) {
            dataList.add(new Data(imageCount, "temp" + imageCount + ".jpg"));
        }
        return dataList;
    }

    /**
     * rangeBar 滑动改变时监听，重新计算时间
     *
     * @param rangeBar
     * @param leftThumbIndex
     * @param rightThumbIndex
     */
    @Override
    public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
        //Log.i("onIndexChange", "leftThumbIndex:" + leftThumbIndex + "___rightThumbIndex:" + rightThumbIndex);
        this.leftThumbIndex = leftThumbIndex;
        this.rightThumbIndex = rightThumbIndex;
        this.calStartEndTime();
        this.updateSelectTimeString();
    }

    private void updateSelectTimeString() {
        String sb =
                StringUtils.getTimeString(startTime * 1000L) +
                "-" +
                StringUtils.getTimeString(endTime * 1000L) +
                " (" +
                StringUtils.getTimeString((endTime - startTime) * 1000L) +
                ")";
        text_time.setText(sb);
    }

    /**
     * 计算开始结束时间
     */
    private void calStartEndTime() {
        int duration = rightThumbIndex - leftThumbIndex;
        startTime = firstItem + leftThumbIndex;
        endTime = startTime + duration;
        //此时可能视频已经结束，若已结束重新start
        if (!uVideoView.isPlaying()) uVideoView.start();
        //把视频跳转到新选择的开始时间
        uVideoView.seekTo(startTime * 1000);
    }

    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.i("onScrollStateChanged", "onScrollStateChanged :" + newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                firstItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastItem = linearLayoutManager.findLastVisibleItemPosition();
                List<Data> dataList = adapter.getDataList();
                for (int i = firstItem; i <= lastItem; i++) {
                    if (!UIUtil.isFileExist(parentPath + dataList.get(i).getImageName())) {
                        Log.i("onScrollStateChanged", "not exist :" + i);
                        runImageDecodTask(i, lastItem - i + 1);
                        break;
                    }
                }
            }
            calStartEndTime();
            updateSelectTimeString();
        }
    };

    /**
     * 运行一个图片的解码任务
     *
     * @param start 解码开始的视频时间 秒
     * @param count 一共解析多少张
     */
    private void runImageDecodTask(final int start, final int count) {
        executorService.execute(() -> ffmpegTool.decodToImageWithCall(videoPath, parentPath, start, count));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {//获取到图片总的显示范围的大小后，设置每一个图片所占有的宽度
            adapter.setImagWidth(rangeBar.getMeasuredWidth() / IMAGE_NUM);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        uVideoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uVideoView.stopPlayback();
    }
}
