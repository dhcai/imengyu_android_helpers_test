package com.imengyu.android_helpers;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.Keep;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.FileUtils;
import com.esay.ffmtool.FfmpegTool;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.ArrayList;
import java.util.List;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

@Keep
public class FFmpegModule extends WXModule {

    private JSONObject makeErrorJson(String err) {
        JSONObject o = new JSONObject();
        o.put("success", false);
        o.put("errMsg", err);
        return o;
    }
    private JSONObject makeSuccessJson() {
        JSONObject o = new JSONObject();
        o.put("success", true);
        o.put("errMsg", "ok");
        return o;
    }

    /**
     * 执行 FFmpeg 命令
     * @param options
     * {
     *     command?: string, //一条 ffmpeg 命令，会自动按空格分割
     *     commands?: string[], //一组 ffmpeg 命令，按空格，commands 和 command 二选一。
     * }
     * @param callback 回调数据：
     * {
     *     success: boolean, //是否成功
     *     errMsg: string, //错误信息
     *     result?: number, //如果成功，则返回FFmpeg执行的返回值
     * }
     */
    @Keep
    @UniJSMethod
    public void runCmd(JSONObject options, final JSCallback callback) {

        if(options.containsKey("commands")) {
            final List<String> commands = new ArrayList<>();
            JSONArray commandsArray = options.getJSONArray("commands");
            for (int i = 0; i < commandsArray.size(); i++)
                commands.add(commandsArray.getString(i));

            new Thread(() -> {
                final int result = FfmpegTool.cmdRun((String[])commands.toArray());
                ((Activity)mWXSDKInstance.getContext()).runOnUiThread(() -> {
                    JSONObject o = new JSONObject();
                    o.put("success", true);
                    o.put("result", result);
                    o.put("errMsg", "ok");
                    callback.invoke(o);
                });
            });

        } else if(options.containsKey("command")) {
            String command = options.getString("command");
            String regulation="[ \t]+";
            final String[] split = command.split(regulation);

            new Thread(() -> {
                final int result = FfmpegTool.cmdRun(split);
                ((Activity)mWXSDKInstance.getContext()).runOnUiThread(() -> {
                    JSONObject o = new JSONObject();
                    o.put("success", true);
                    o.put("result", result);
                    o.put("errMsg", "ok");
                    callback.invoke(o);
                });
            });
        } else {
            callback.invoke(makeErrorJson("commands or commands is required"));
        }
    }

    /**
     * 视频解码成一帧帧图片
     * @param options
     * {
     *     src: string, //视频源路径
     *     dir: string, //图片输出目录
     *     startTime?: number, //解码图片开始时间，单位s，默认0
     *     count?: number, //解码数量，默认1
     *     tag?: number, //标志位，默认0
     * }
     * @param callback 返回参数
     * {
     *     success: boolean, //是否成功
     *     errMsg: string, //错误信息
     *     tag: number, //输入的标志位
     *     src: string, //输入的视频源路径
     *     dest: string, //图片输出路径
     * }
     *
     */
    @Keep
    @UniJSMethod
    public void videoToImage(JSONObject options, final JSCallback callback) {
        String src = "";
        String dir = "";
        int startTime = 0;
        int count = 1;
        int tag = 0;

        if(!options.containsKey("src")) {
            callback.invoke(makeErrorJson("src is required"));
            return;
        } else {
            src = options.getString("src");
            if(!FileUtils.isFileExists(src))  {
                callback.invoke(makeErrorJson("File \"" + src + "\" not exist"));
                return;
            }
        }
        if(!options.containsKey("dir")) {
            callback.invoke(makeErrorJson("dir is required"));
            return;
        } else
            dir = options.getString("dir");

        if(options.containsKey("startTime"))
            startTime = options.getInteger("startTime");
        if(options.containsKey("count"))
            count = options.getInteger("count");
        if(options.containsKey("tag"))
            tag = options.getInteger("tag");

        FfmpegTool ffmpegTool = FfmpegTool.getInstance((Activity) mWXSDKInstance.getContext());
        ffmpegTool.videoToImage(src, dir, startTime, count, (i, s, s1, b, i1) -> {
            JSONObject o = new JSONObject();
            o.put("success", b);
            o.put("errMsg", b ? "ok" : "unknow");
            o.put("tag", i1);
            o.put("src", s);
            o.put("dest", s1);
            callback.invokeAndKeepAlive(o);
        }, tag);
    }

    /**
     * 异步任务模式，将视频解码成一帧帧图片。
     * 每完成一次图片的解码与保存会回调一次，并传回新保存图片的地址与当前是图片下标。
     * @param options
     * {
     *     src: string, //视频源路径
     *     dir: string, //图片输出目录
     *     startTime?: number, //解码图片开始时间，单位s，默认0
     *     count?: number, //解码数量，默认1
     * }
     * @param callback 返回参数
     * {
     *     success: boolean, //是否成功
     *     errMsg: string, //错误信息
     *     tag: number, //输入的标志位
     *     src: string, //输入的视频源路径
     *     dir: string, //图片输出目录
     * }
     * 解码完成的图片放在 dir 目录下，按 temp秒.jpg 名字排放,
     */
    @Keep
    @UniJSMethod
    public void decodeToImageWithCall(JSONObject options, final JSCallback callback) {
        String src = "";
        String dir = "";
        int startTime = 0;
        int count = 1;

        if(!options.containsKey("src")) {
            callback.invoke(makeErrorJson("src is required"));
            return;
        } else {
            src = options.getString("src");
            if(!FileUtils.isFileExists(src))  {
                callback.invoke(makeErrorJson("File \"" + src + "\" not exist"));
                return;
            }
        }
        if(!options.containsKey("dir")) {
            callback.invoke(makeErrorJson("dir is required"));
            return;
        } else
            dir = options.getString("dir");

        if(options.containsKey("startTime"))
            startTime = options.getInteger("startTime");
        if(options.containsKey("count"))
            count = options.getInteger("count");

        FfmpegTool ffmpegTool = FfmpegTool.getInstance((Activity) mWXSDKInstance.getContext());
        ffmpegTool.setImageDecodeing((path, i) -> {
            JSONObject o = new JSONObject();
            o.put("success", true);
            o.put("errMsg", "ok");
            o.put("path", path);
            o.put("index", i);
            callback.invokeAndKeepAlive(o);
        });
        ffmpegTool.decodToImageWithCall(src, dir, startTime, count);
    }

    /**
     * 执行指定帧视频帧图片任务。通常配合 decodeToImageWithCall 一起使用。
     * @param options
     * {
     *     src: string, //视频源路径
     *     index: number, //解码的秒数
     * }
     * @param callback 返回参数
     * {
     *     success: boolean, //是否成功
     *     errMsg: string, //错误信息
     * }
     */
    @Keep
    @UniJSMethod
    public void decodeToImageCall(JSONObject options, final JSCallback callback) {
        String src = "";
        int index = 0;

        if(!options.containsKey("src")) {
            callback.invoke(makeErrorJson("src is required"));
            return;
        } else
            src = options.getString("src");
        if(!options.containsKey("index")) {
            callback.invoke(makeErrorJson("index is required"));
            return;
        } else
            index = options.getInteger("index");

        FfmpegTool ffmpegTool = FfmpegTool.getInstance((Activity) mWXSDKInstance.getContext());
        ffmpegTool.decodToImageCall(src, index);
        callback.invoke(makeSuccessJson());
    }

    /**
     * 裁剪视频
     * @param options
     * {
     *     src: string, //视频源路径
     *     dist: string, //视频输出路径
     *     startTime: number, //解码图片开始时间，单位ms
     *     duration: number, //解码图片开始时间，单位ms
     *     tag?: number, //标志位，默认0
     * }
     * @param callback
     * {
     *     success: boolean, //是否成功
     *     errMsg: string, //错误信息
     * }
     */
    @Keep
    @UniJSMethod
    public void clipVideo(JSONObject options, final JSCallback callback) {
        String src = "";
        String dist = "";
        int startTime = 0;
        int duration = 1;
        int tag = 0;

        if(!options.containsKey("src")) {
            callback.invoke(makeErrorJson("src is required"));
            return;
        } else {
            src = options.getString("src");
            if(!FileUtils.isFileExists(src))  {
                callback.invoke(makeErrorJson("File \"" + src + "\" not exist"));
                return;
            }
        }
        if(!options.containsKey("dist")) {
            callback.invoke(makeErrorJson("dist is required"));
            return;
        } else
            dist = options.getString("dist");

        if(options.containsKey("startTime"))
            startTime = options.getInteger("startTime");
        if(options.containsKey("duration"))
            duration = options.getInteger("duration");
        if(options.containsKey("tag"))
            tag = options.getInteger("tag");

        StringBuilder cmd = new StringBuilder();
        cmd.append("ffmpeg -y -ss ");
        cmd.append((startTime / 1000.0f));
        cmd.append(" -t ");
        cmd.append((duration / 1000.0f));
        cmd.append(" -i ");
        cmd.append(src);
        cmd.append(" -vcodec copy -acodec copy -strict -2 ");
        cmd.append(dist);

        String regulation = "[ \\t]+";
        Log.i("clipVideo", "cmd:" + cmd);
        String[] split = cmd.toString().split(regulation);

        final int result = FfmpegTool.cmdRun(split);
        final String finalSrc = src;
        final int finalTag = tag;
        final String finalDist = dist;
        ((Activity)mWXSDKInstance.getContext()).runOnUiThread(() -> {
            JSONObject o = new JSONObject();
            o.put("success", result == 0);
            o.put("errMsg", result == 0 ? "ok" : "unknow");
            o.put("tag", finalTag);
            o.put("src", finalSrc);
            o.put("dist", finalDist);
            callback.invoke(o);
        });
    }

    /**
     * 压缩视频
     * @param options
     * {
     *     src: string, //视频源路径
     *     dist: string, //压缩后的临时视频输出路径
     *     tag?: number, //标志位，默认0
     * }
     * @param callback
     * {
     *     success: boolean, //是否成功
     *     errMsg: string, //错误信息
     *     src: string, //视频源路径
     *     dist: string, //压缩后的临时视频路径
     *     tag?: number, //标志位，默认0
     * }
     */
    @Keep
    @UniJSMethod
    public void compressVideo(JSONObject options, final JSCallback callback) {
        String src = "";
        String dist = "";
        int tag = 0;

        if(!options.containsKey("src")) {
            callback.invoke(makeErrorJson("src is required"));
            return;
        } else {
            src = options.getString("src");
            if(!FileUtils.isFileExists(src))  {
                callback.invoke(makeErrorJson("File \"" + src + "\" not exist"));
                return;
            }
        }
        if(!options.containsKey("dir")) {
            callback.invoke(makeErrorJson("dir is required"));
            return;
        } else
            dist = options.getString("dist");

        if(options.containsKey("tag"))
            tag = options.getInteger("tag");

        FfmpegTool ffmpegTool = FfmpegTool.getInstance((Activity) mWXSDKInstance.getContext());
        ffmpegTool.compressVideo(src, dist, tag, (FfmpegTool.VideoResult) (i, s, s1, b, i1) -> {
            JSONObject o = new JSONObject();
            o.put("success", b);
            o.put("errMsg", b ? "ok" : "unknow");
            o.put("tag", i1);
            o.put("src", s);
            o.put("dist", s1);
            callback.invoke(o);
        });
    }
}

