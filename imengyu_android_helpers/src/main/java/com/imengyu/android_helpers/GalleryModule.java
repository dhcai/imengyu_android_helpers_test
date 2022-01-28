package com.imengyu.android_helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.AlbumModel;
import com.huantansheng.easyphotos.models.album.entity.AlbumItem;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.imengyu.android_helpers.adapter.VideoListListAdapter;
import com.imengyu.android_helpers.model.VideoListItem;
import com.imengyu.android_helpers.model.VideoModel;
import com.imengyu.android_helpers.model.entity.Video;
import com.imengyu.android_helpers.utils.BitmapUtils;
import com.imengyu.android_helpers.utils.CacheUtils;
import com.imengyu.android_helpers.utils.GlideEngine;
import com.imengyu.android_helpers.utils.MD5Utils;
import com.imengyu.android_helpers.utils.StringUtils;
import com.imengyu.android_helpers.widget.RecyclerViewEmptySupport;
import com.imengyu.android_helpers.wxvideoeditt.EsayVideoEditActivity;
import com.kongzue.dialogx.dialogs.BottomMenu;
import com.kongzue.dialogx.dialogs.FullScreenDialog;
import com.kongzue.dialogx.dialogs.WaitDialog;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.utils.UniLogUtils;

@Keep
public class GalleryModule extends WXModule {
    String TAG = "GalleryModule";

    /**
     * 获取系统相册数据【异步】
     * @param options {}
     * @param callback  回调
     * 返回参数：
     *                  {
     *                      list: [
     *                          //相册列表：
     *                          {
     *                              name: string, //相册的名称
     *                              folderPath: string,
     *                              coverImagePath: string, //相册封面图片的路径
     *                              //相册图片列表：
     *                              photos: [
     *                                  name: string,//图片名称
     *                                  path: string,//图片全路径
     *                                  type: string,//图片类型
     *                                  width: number,//图片宽度
     *                                  height: number,//图片高度
     *                                  orientation: number,//图片旋转角度
     *                                  size: number,//图片文件大小，单位：Bytes
     *                                  duration: number,//视频时长，单位：毫秒
     *                                  time: number
     *                              ]
     *                          }
     *                      ]
     *                  }
     */
    @Keep
    @UniJSMethod
    public void getSystemAlbums(JSONObject options, final JSCallback callback) {
        final Context context = mUniSDKInstance.getContext();
        AlbumModel albumModel = AlbumModel.getInstance();
        albumModel.query(context, () -> {
            JSONObject jsonObject = new JSONObject();
            List<AlbumItem> albums = albumModel.getAlbumItems();
            jsonObject.put("list", albums);
            callback.invoke(jsonObject);
        });
    }

    /**
     * 获取系统相册视频数据 【异步】
     * @param options {}
     * @param callback  回调
     * 返回参数：
     *                  {
     *                      videos: [
     *                          name: string,//视频名称
     *                          path: string,//视频全路径
     *                          size: number,//视频文件大小，单位：Bytes
     *                          duration: number,//视频时长，单位：毫秒
     *                      ]
     *                  }
     */
    @Keep
    @UniJSMethod
    public void getSystemVideos(JSONObject options, final JSCallback callback) {
        final Context context = mUniSDKInstance.getContext();
        VideoModel videoModel = VideoModel.getInstance();
        videoModel.query(context, () -> {
            JSONObject jsonObject = new JSONObject();
            List<Video> videos = videoModel.getItems();
            jsonObject.put("videos", videos);
            callback.invoke(jsonObject);
        });
    }

    /**
     * 获取本地视频缩略图 【异步】
     * @param options
     *                  {
     *                      path: string //视频的路径
     *                  }
     * @param callback  回调
     * 返回参数：
     *                  {
     *                      videoThumbnail: string //视频的缩略图临时文件
     *                  }
     */
    @Keep
    @UniJSMethod
    public void getVideoThumbnail(JSONObject options, final JSCallback callback) {
        String path = options.getString("path");
        String name = MD5Utils.md5(path);
        String cachePath = BitmapUtils.checkBitmapTempExists(mWXSDKInstance.getContext(), name);
        if(cachePath != null) {
            //已存在cache，则直接返回
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("videoThumbnail", cachePath);
            callback.invoke(jsonObject);
        }
        else {
            new Thread(() -> {
                //生成缩略图并保存在临时目录，以供Uniapp使用
                Bitmap videoThumbnail = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MINI_KIND);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("videoThumbnail", BitmapUtils.saveBitmapToTemp(mWXSDKInstance.getContext(), videoThumbnail, name));
                callback.invoke(jsonObject);

            }).start();
        }
    }

    /**
     * 获取本地图片的Exif旋转信息
     * @param path 本地文件路径
     * @return 返回旋转度数
     */
    @Keep
    @UniJSMethod
    public int getImageExifOrientation(String path) {
       return BitmapUtils.getImageExifOrientation(path);
    }

    /**
     * 获取本地视频宽高 【异步】
     * @param options
     *                  {
     *                      path: string //视频的路径
     *                  }
     * @param callback  回调
     * 返回参数：
     *                  {
     *                      width: number,
     *                      height: number,
     *                      size: number,//大小，字节
     *                      rotation: number,//视频的方向角度
     *                  }
     */
    @Keep
    @UniJSMethod
    public void getVideoSize(JSONObject options, final JSCallback callback) {
        String path = options.getString("path");
        new Thread(() -> {

            if(!new File(path).exists()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("errMsg", "Not exists");
                callback.invoke(jsonObject);
                return;
            }

            try {
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(path);
                String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH); //宽
                String height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT); //高
                String rotation = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);//视频的方向角度
                long duration = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) * 1000;//视频的长度
                retriever.release();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("width", width);
                jsonObject.put("height", height);
                jsonObject.put("rotation", rotation);
                jsonObject.put("duration", duration);
                callback.invoke(jsonObject);
            }
            catch (Exception e) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("errMsg", e.getLocalizedMessage());
                callback.invoke(jsonObject);
            }

        }).start();
    }

    private final int REQUEST_CODE_VIDEO_EDIT = 10;
    private final int REQUEST_CODE_CHOOSE_IMAGE = 11;

    private JSCallback callbackVideoEditActivity = null;
    private JSCallback callbackChooseImage = null;
    private JSCallback callbackCropImage = null;

    /**
     * 开启视频剪裁窗口
     * @param options
     *                  {
     *                      path: string, //视频的路径
     *                      title: string, //剪裁窗口顶部显示的文字
     *                      maxDuration: number, //最大可剪裁时长，单位秒，默认60秒
     *                  }
     * @param callback
     *                  {
     *                      tempVideoPath: string, //剪裁完成后的视频的路径。使用完之后请使用deleteTempVideo删除
     *                      success: boolean //获取是否成功
     *                  }
     */
    @Keep
    @UniJSMethod
    public void startVideoEditActivity(JSONObject options, final JSCallback callback){
        String path = options.getString("path");
        String title = options.getString("title");
        Integer maxDuration = options.getInteger("maxDuration");

        Intent intent = new Intent();
        intent.putExtra(EsayVideoEditActivity.PATH, path);
        intent.putExtra(EsayVideoEditActivity.TITLE, title);
        intent.putExtra(EsayVideoEditActivity.DURATION, maxDuration);
        intent.setClass(mUniSDKInstance.getContext(), EsayVideoEditActivity.class);

        callbackVideoEditActivity = callback;
        ((Activity) mWXSDKInstance.getContext()).startActivityForResult(intent, REQUEST_CODE_VIDEO_EDIT);
    }

    /**
     * 删除上一步剪裁的临时视频 【异步】
     * @param options
     *                  {
     *                      path: string //视频的路径
     *                  }
     * @param callback  回调
     *                  {
     *                      success: boolean, //是否成功
     *                      errMsg: string //如果失败，则返回异常信息
     *                  }
     */
    @Keep
    @UniJSMethod
    public void deleteTempVideo(JSONObject options, final JSCallback callback) {
        String path = options.getString("path");

        new Thread(() -> {

            boolean deleteSuccess = false;
            String err = "";
            try {
                File file = new File(path);
                if (file.exists())
                    deleteSuccess = file.delete();
                else
                    err = "File not exists";
            } catch (SecurityException e) {
                err = e.getLocalizedMessage();
                e.printStackTrace();
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", deleteSuccess);
            jsonObject.put("errMsg", err);
            callback.invoke(jsonObject);

        }).start();
    }

    /**
     * 打开裁剪图片窗口 (UCorp)
     * @param options
     * {
     *      sourceFilePath: string, //源文件路径
     *      useSourceImageAspectRatio?: boolean, //是否使用源图像比例剪裁
     *      aspectRatio?: number[], //是否使用指定比例剪裁，当useSourceImageAspectRatio为true时此值无效
     *      maxResultSize?: number[], //最大可以允许的图片的长宽
     *      activeControlsWidgetColor?: string, //控件激活状态颜色
     *      cropFrameColor?: string, //剪裁框颜色
     *      cropGridColor?: string, //剪裁框网格颜色
     *      dimmedLayerColor?: string, //裁剪边界周围暗显区域的所需颜色
     *      logoColor?: string, //徽标填充所需的解析颜色（默认为深灰色）
     *      rootViewBackgroundColor?: string, //根视图的背景色
     *      statusBarColor?: string, //状态栏颜色
     *      toolbarColor?: string, //工具栏颜色
     *      toolbarWidgetColor?: string, //工具栏文本和按钮所需的解析颜色（默认为深橙色）
     *      toolbarTitle?: string, //工具栏标题
     *      compressionQuality?: number, //[0 - 100] 剪裁图片压缩质量，png图片无此参数
     *      compressionFormat?: 'jpeg'|'png'|'webp', //设置压缩图片格式
     *      circleDimmedLayer?: boolean, //设置是否是圆形剪裁框
     *      showCropFrame?: boolean, //设置是否显示剪裁框
     *      showCropGrid?: boolean, //设置是否显示剪裁框网格
     *      freeStyleCropEnabled?: boolean, //设置是否可以自由调整剪裁框
     *      hideBottomControls?: boolean, //设置是否显隐藏底部控件
     *      maxScaleMultiplier?: integer, //此方法设置用于从最小图像比例计算最大图像比例的乘数。
     *      maxBitmapSize?: integer, //用于设置从输入Uri解码并在视图中使用的位图宽度和高度的最大大小。
     *      imageToCropBoundsAnimDuration?: integer, //此方法设置图像的动画持续时间，以包裹裁剪边界
     *      cropFrameStrokeWidth?: integer, //裁剪帧线的宽度（以像素为单位）
     *      cropGridRowCount?: integer, //裁剪网格的行数。
     *      cropGridColumnCount?: integer, //裁剪网格的列数。
     * }
     * @param callback
     * {
     *      success: boolean,
     *      errMsg: string,
     *      tempFilePath: string, //剪裁后的临时图片路径
     * }
     */
    @Keep
    @UniJSMethod
    public void startCropImageActivity(JSONObject options, final JSCallback callback) {
        Activity context = (Activity)mWXSDKInstance.getContext();
        callbackCropImage = callback;

        //参数
        if(!options.containsKey("sourceFilePath")) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", false);
            jsonObject.put("errMsg", "No sourceFilePath");
            callback.invoke(jsonObject);
            return;
        }

        //文件是否存在
        File srcFile = new File(options.getString("sourceFilePath"));
        if(!srcFile.exists()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", false);
            jsonObject.put("errMsg", "Source file does not exist");
            callback.invoke(jsonObject);
            return;
        }

        UCrop.Options uCropOptions1 = new UCrop.Options();
        if(options.containsKey("activeControlsWidgetColor"))
            uCropOptions1.setActiveControlsWidgetColor(Color.parseColor(options.getString("activeControlsWidgetColor")));
        if(options.containsKey("cropFrameColor"))
            uCropOptions1.setCropFrameColor(Color.parseColor(options.getString("cropFrameColor")));
        if(options.containsKey("cropGridColor"))
            uCropOptions1.setCropGridColor(Color.parseColor(options.getString("cropGridColor")));
        if(options.containsKey("dimmedLayerColor"))
            uCropOptions1.setDimmedLayerColor(Color.parseColor(options.getString("dimmedLayerColor")));
        if(options.containsKey("logoColor"))
            uCropOptions1.setLogoColor(Color.parseColor(options.getString("logoColor")));
        if(options.containsKey("rootViewBackgroundColor"))
            uCropOptions1.setRootViewBackgroundColor(Color.parseColor(options.getString("rootViewBackgroundColor")));
        if(options.containsKey("statusBarColor"))
            uCropOptions1.setStatusBarColor(Color.parseColor(options.getString("statusBarColor")));
        if(options.containsKey("toolbarColor"))
            uCropOptions1.setToolbarColor(Color.parseColor(options.getString("toolbarColor")));
        if(options.containsKey("toolbarWidgetColor"))
            uCropOptions1.setToolbarWidgetColor(Color.parseColor(options.getString("toolbarWidgetColor")));
        if(options.containsKey("circleDimmedLayer"))
            uCropOptions1.setCircleDimmedLayer(options.getBoolean("circleDimmedLayer"));
        if(options.containsKey("showCropFrame"))
            uCropOptions1.setShowCropFrame(options.getBoolean("showCropFrame"));
        if(options.containsKey("showCropGrid"))
            uCropOptions1.setShowCropGrid(options.getBoolean("showCropGrid"));
        if(options.containsKey("hideBottomControls"))
            uCropOptions1.setHideBottomControls(options.getBoolean("hideBottomControls"));
        if(options.containsKey("freeStyleCropEnabled"))
            uCropOptions1.setFreeStyleCropEnabled(options.getBoolean("freeStyleCropEnabled"));
        if(options.containsKey("compressionFormat")) {
            String compressionFormat = options.getString("compressionFormat");
            switch (compressionFormat) {
                case "jpeg": uCropOptions1.setCompressionFormat(Bitmap.CompressFormat.JPEG); break;
                case "png": uCropOptions1.setCompressionFormat(Bitmap.CompressFormat.PNG); break;
                case "webp": uCropOptions1.setCompressionFormat(Bitmap.CompressFormat.WEBP); break;
            }
        }

        if(options.containsKey("cropFrameStrokeWidth"))
            uCropOptions1.setCropFrameStrokeWidth(options.getInteger("cropFrameStrokeWidth"));
        if(options.containsKey("cropGridColumnCount"))
            uCropOptions1.setCropGridColumnCount(options.getInteger("cropGridColumnCount"));
        if(options.containsKey("cropGridRowCount"))
            uCropOptions1.setCropGridRowCount(options.getInteger("cropGridRowCount"));
        if(options.containsKey("imageToCropBoundsAnimDuration"))
            uCropOptions1.setImageToCropBoundsAnimDuration(options.getInteger("imageToCropBoundsAnimDuration"));
        if(options.containsKey("maxBitmapSize"))
            uCropOptions1.setMaxBitmapSize(options.getInteger("maxBitmapSize"));
        if(options.containsKey("maxScaleMultiplier"))
            uCropOptions1.setMaxScaleMultiplier(options.getInteger("maxScaleMultiplier"));
        if(options.containsKey("toolbarTitle"))
            uCropOptions1.setToolbarTitle(options.getString("toolbarTitle"));

        //cache路径
        File cacheFile = CacheUtils.getCachePath(context, "/crop-image-cache/", srcFile.getName());
        UCrop uCrop = UCrop.of(Uri.fromFile(srcFile), Uri.fromFile(cacheFile)).withOptions(uCropOptions1);

        if(options.containsKey("useSourceImageAspectRatio") && options.getBoolean("useSourceImageAspectRatio")) {
            uCrop.useSourceImageAspectRatio();
        } else {
            if(options.containsKey("aspectRatio")) {
                JSONArray array = options.getJSONArray("aspectRatio");
                if(array.size() >= 2)
                    uCrop.withAspectRatio(array.getInteger(0), array.getInteger(1));
            }
        }
        if(options.containsKey("maxResultSize")) {
            JSONArray array = options.getJSONArray("maxResultSize");
            if(array.size() >= 2)
                uCrop.withMaxResultSize(array.getInteger(0), array.getInteger(1));
        }

        uCrop.start(context);
    }

    private int compressQuality = 80;
    private boolean compressImage = false;
    private int compressImageWidth = 0;
    private int compressImageHeight = 0;
    private int chooseImageImageDirectionCorrection = 0;

    /**
     * 选择图片
     * @param options
     * {
     *     sourceType: [ 'album', 'camera' ], //同 uni.chooseImage sourceType
     *     count: number, //可选最大数量
     *     hasGif: boolean, //是否显示动图，默认true
     *     hasVideo: boolean, //是否显示视频，默认false
     *     needSize: boolean, //是否需要获取图像宽高，默认false,
     *     compressQuality: number,	//压缩图片的质量，取值范围为1-100，数值越小，质量越低。默认值为80。
     *     compress: { //如果指定此参数，返回的图片均会进行强制压缩，并且调整到您设置的大小.
     *         //如果您至指定了长宽其中某个值，则会按比例为你缩小图片。
     *         //如果您至指定了长宽其两个值，则会按您设置的长宽直接拉伸图片。
     *         width: Number, //目标压缩宽度，单位为px，用于计算裁剪宽高比。
     *         height: Number, //目标压缩高度，单位为px，用于计算裁剪宽高比。
     *     },
     *     imageDirectionCorrection: number, //纠正图片的旋转方向，默认旋转方向是依照照片的拍摄方向，你可以通过设置这个值，让图片在默认旋转方向上再旋转多少度。
     * }
     * @param callback 回调，大致与 uni.chooseImage 相同
     * {
     *      success: boolean,
     *      errMsg: string,
     *      isOriginal: boolean,  //是否选中了原图
     *      tempFilePaths: string[], //选中图像信息路径数组
     *      tempFiles: {
     *                 name: string, //文件名
     *                 width: number, //宽，必须指定needSize为true才有效
     *                 height: number, //高，必须指定needSize为true才有效
     *                 type: string, //图片类型
     *                 duration: number, //视频的时长
     *                 orientation: number, //图片的旋转方向
     *                 time: number, //图片创建时间戳
     *                 path: string //图片完整路径
     *      }[], //选中图像信息
     * }
     */
    @Keep
    @UniJSMethod
    public void chooseImage(JSONObject options, final JSCallback callback) {
        try {
            boolean hasAlbum = false, hasCamera = false;
            int selCount = 9;
            boolean hasGif = true;
            boolean hasVideo = false;
            boolean needSize = false;

            callbackChooseImage = callback;

            final Activity activity = (Activity) mWXSDKInstance.getContext();

            if (options.containsKey("sourceType")) {
                JSONArray sourceType = options.getJSONArray("sourceType");
                for (int i = 0; i < sourceType.size(); i++)
                    if ("album".equals(sourceType.get(i)))
                        hasAlbum = true;
                    else if ("camera".equals(sourceType.get(i)))
                        hasCamera = true;
            } else {
                hasAlbum = true;
                hasCamera = true;
            }
            if (options.containsKey("count"))
                selCount = options.getInteger("count");
            if (options.containsKey("hasGif"))
                hasGif = options.getBoolean("hasGif");
            if (options.containsKey("hasVideo"))
                hasVideo = options.getBoolean("hasVideo");
            if (options.containsKey("needSize"))
                needSize = options.getBoolean("needSize");
            if (options.containsKey("compressQuality"))
                compressQuality = options.getInteger("compressQuality");
            else
                compressQuality = 80;
            if (options.containsKey("imageDirectionCorrection"))
                chooseImageImageDirectionCorrection = options.getInteger("imageDirectionCorrection");
            else
                chooseImageImageDirectionCorrection = 0;
            if (options.containsKey("compress")) {
                compressImage = true;

                JSONObject compress = options.getJSONObject("compress");
                if (compress.containsKey("width"))
                    compressImageWidth = compress.getInteger("width");
                else
                    compressImageWidth = 0;
                if (compress.containsKey("height"))
                    compressImageHeight = compress.getInteger("height");
                else
                    compressImageHeight = 0;
                if (compressImageHeight == compressImageWidth && compressImageWidth == 0)
                    compressImage = false;
            } else
                compressImage = false;


            if (hasAlbum && hasCamera) {
                int finalSelCount = selCount;
                boolean finalHasVideo = hasVideo;
                boolean finalHasGif = hasGif;
                boolean finalNeedSize = needSize;
                BottomMenu.show(new String[]{
                        "使用相机拍摄照片", "从相册中选择照片"
                })
                .setCancelButton("取消")
                .setOnMenuItemClickListener((dialog, text, index) -> {
                    if (index == 0) {
                        EasyPhotos.createCamera(activity, finalNeedSize)
                                .setFileProviderAuthority(mWXSDKInstance.getContext().getPackageName() + ".android_helpers.fileprovider")
                                .start(REQUEST_CODE_CHOOSE_IMAGE);
                    } else {
                        EasyPhotos.createAlbum(activity, false, finalNeedSize, GlideEngine.getInstance())
                                .setFileProviderAuthority(mWXSDKInstance.getContext().getPackageName() + ".android_helpers.fileprovider")
                                .setGif(finalHasGif)
                                .setVideo(finalHasVideo)
                                .setCount(finalSelCount)
                                .start(REQUEST_CODE_CHOOSE_IMAGE);
                    }
                    return false;
                }).setCancelButtonClickListener((baseDialog, v) -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("success", false);
                    jsonObject.put("errMsg", "cancel");
                    callback.invoke(jsonObject);
                    return false;
                }).setOnBackPressedListener(() -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("success", false);
                    jsonObject.put("errMsg", "cancel");
                    callback.invoke(jsonObject);
                    return false;
                });
            } else if (hasAlbum) {
                EasyPhotos.createAlbum(activity, false, needSize, GlideEngine.getInstance())
                        .setFileProviderAuthority(mWXSDKInstance.getContext().getPackageName() + ".android_helpers.fileprovider")
                        .setCount(selCount)
                        .setGif(hasGif)
                        .setVideo(hasVideo)
                        .start(REQUEST_CODE_CHOOSE_IMAGE);
            } else if (hasCamera) {
                EasyPhotos.createCamera(activity, needSize)
                        .setFileProviderAuthority(mWXSDKInstance.getContext().getPackageName() + ".android_helpers.fileprovider")
                        .start(REQUEST_CODE_CHOOSE_IMAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", false);
            jsonObject.put("errMsg", e.getMessage());
            jsonObject.put("stackTrace", e.getStackTrace());
            callbackCropImage.invoke(jsonObject);
        }
    }

    /**
     * 选择本地视频
     * @param options
     * {
     *     title: string, //对话框标题
     *     showCameraButton: boolean, //是否显示相机按钮, 默认显示
     * }
     * @param callback 回调
     * {
     *     chooseVideo: {
     *        duration: number, //时长ms
     *        path: string,  //路径
     *        name: string,  //名称
     *        size: number,  //大小字节
     *     },
     *     chooseVideoPath: string,  //选中的视频路径，同 chooseVideo.path，如果选中的是相机按钮，则为 "camera"
     *     success: boolean, //是否成功
     *     errMsg: string
     * }
     */
    @Keep
    @UniJSMethod
    public void chooseVideo(JSONObject options, final JSCallback callback) {

        Activity activity = (Activity) mWXSDKInstance.getContext();
        String title = "";
        boolean showCameraButton = true;
        if(options.containsKey("title")) title = options.getString("title");
        if(options.containsKey("showCameraButton")) showCameraButton = options.getBoolean("showCameraButton");

        final WaitDialog tipDialog = WaitDialog.show("载入中，请稍等");
        final String finalTitle = title;
        boolean finalShowCameraButton = showCameraButton;
        VideoModel videoModel = VideoModel.getInstance();
        videoModel.query(activity, () -> {
            List<Video> videos = videoModel.getItems();

            tipDialog.doDismiss();
            FullScreenDialog.show(new OnBindView<FullScreenDialog>(R.layout.layout_choose_video) {
                @Override
                public void onBind(FullScreenDialog dialog, View rootView) {
                    RecyclerViewEmptySupport recyclerView = rootView.findViewById(R.id.recycler_view);

                    View layout_empty = rootView.findViewById(R.id.layout_empty);
                    recyclerView.setEmptyView(layout_empty);

                    if(!finalTitle.equals("")) {
                        TextView text_title = rootView.findViewById(R.id.text_title);
                        text_title.setText(finalTitle);
                    }

                    rootView.findViewById(R.id.btn_close).setOnClickListener((v) -> {
                        dialog.dismiss();
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("success", false);
                        jsonObject.put("errMsg", "cancel");
                        callback.invoke(jsonObject);
                    });

                    //Load list
                    List<VideoListItem> videoList = new ArrayList<>();
                    if(finalShowCameraButton) {
                        VideoListItem item = new VideoListItem();
                        item.durationString = "";
                        item.path = "camera";
                        item.isCameraButton = true;
                        videoList.add(item);
                    }
                    for (Video v : videos) {
                        VideoListItem item = new VideoListItem();
                        item.durationString = StringUtils.getTimeString(v.duration);
                        item.path = v.path;
                        item.videoId = v.videoId;
                        videoList.add(item);
                    }

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 4);
                    VideoListListAdapter adapter = new VideoListListAdapter(activity, R.layout.item_video_list_item, videoList);
                    adapter.setListOnItemClickListener((parent, view, position, id) -> {
                        dialog.dismiss();

                        if(finalShowCameraButton && position == 0) {
                            //返回数据
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("chooseVideoPath", "camera");
                            jsonObject.put("success", true);
                            jsonObject.put("errMsg", "ok");
                            callback.invoke(jsonObject);
                            return;
                        }

                        Video video = videos.get(finalShowCameraButton ? (position - 1) : position);
                        //返回数据
                        JSONObject jsonObject = new JSONObject();
                        JSONObject jvideo = new JSONObject();
                        jvideo.put("duration", video.duration);
                        jvideo.put("path", video.path);
                        jvideo.put("name", video.name);
                        jvideo.put("size", video.size);
                        jsonObject.put("chooseVideo", jvideo);
                        jsonObject.put("chooseVideoPath", video.path);
                        jsonObject.put("success", true);
                        jsonObject.put("errMsg", "ok");
                        callback.invoke(jsonObject);
                    });

                    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                                List<Integer> changedList = adapter.getChangedItems();
                                for (Integer i : changedList)
                                    adapter.notifyItemChanged(i);
                                changedList.clear();
                            }
                        }
                    });
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.postDelayed(() -> {
                        List<Integer> changedList = adapter.getChangedItems();
                        for (Integer i : changedList)
                            adapter.notifyItemChanged(i);
                        changedList.clear();
                    }, 1000);
                }
            });
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Context context = mWXSDKInstance.getContext();
        if (requestCode == REQUEST_CODE_VIDEO_EDIT && callbackVideoEditActivity != null) {
            if (resultCode == Activity.RESULT_OK) {
                //VideoEditActivity 返回参数
                JSONObject result = new JSONObject();
                result.put("success", data.getExtras().getBoolean("success", false));
                result.put("cancel", false);
                result.put("tempVideoPath", data.getExtras().getString("path"));
                callbackVideoEditActivity.invoke(result);
            } else {
                JSONObject result = new JSONObject();
                result.put("cancel", true);
                callbackVideoEditActivity.invoke(result);
            }
            callbackVideoEditActivity = null;
        }
        else if (requestCode == REQUEST_CODE_CHOOSE_IMAGE && callbackChooseImage != null) {
            if(resultCode == Activity.RESULT_OK) {
                //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
                ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                boolean resultOriginal = data.getBooleanExtra(EasyPhotos.RESULT_SELECTED_ORIGINAL, false);
                if(resultPhotos == null) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("success", false);
                    jsonObject.put("errMsg", "error");
                    callbackChooseImage.invoke(jsonObject);
                    return;
                }

                JSONArray tempFilePaths = new JSONArray();
                JSONArray tempFiles = new JSONArray();

                for (Photo p : resultPhotos) {
                    String path = p.path;
                    int orientation = BitmapUtils.getImageExifOrientation(path);

                    //非原图，或者指定了压缩，则进行压缩
                    if(!resultOriginal || compressImage) {
                        if(!path.endsWith(".gif")) {
                            String name = MD5Utils.md5(path);
                            Bitmap b = BitmapFactory.decodeFile(path);
                            File cacheFile = CacheUtils.getCachePath(context, "/choose-image-cache/", name);
                            if (b != null && cacheFile != null) {
                                try {
                                    if(compressImageWidth == 0 && compressImageHeight == 0) {
                                        compressImageWidth = p.width;
                                        compressImageHeight = p.height;
                                    }
                                    if(compressImageWidth > 0 && compressImageHeight > 0) {
                                        BitmapUtils.imageFixCompress(b,
                                                compressQuality,
                                                compressImageWidth,
                                                compressImageHeight,
                                                orientation + chooseImageImageDirectionCorrection,
                                                cacheFile);
                                        path = cacheFile.getAbsolutePath();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    UniLogUtils.w("Failed to compress image \"" + path + "\"");
                                }
                            }
                            if (b != null)
                                b.recycle();
                        }
                    }

                    tempFilePaths.add(path);
                    JSONObject file = new JSONObject();
                    file.put("name", p.name);
                    file.put("size", p.size);
                    file.put("width", p.width);
                    file.put("height", p.height);
                    file.put("type", p.type);
                    file.put("duration", p.duration);
                    file.put("orientation", orientation);
                    file.put("time", p.time);
                    file.put("path", path);
                    tempFiles.add(file);
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("success", true);
                jsonObject.put("isOriginal", resultOriginal);
                jsonObject.put("tempFilePaths", tempFilePaths);
                jsonObject.put("tempFiles", tempFiles);
                jsonObject.put("errMsg", "ok");
                callbackChooseImage.invoke(jsonObject);
            } else {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("success", false);
                jsonObject.put("errMsg", "cancel");
                callbackChooseImage.invoke(jsonObject);
            }
            callbackChooseImage = null;
        }
        else if (requestCode == UCrop.REQUEST_CROP) {
            if(resultCode == Activity.RESULT_OK) {
                final Uri resultUri = UCrop.getOutput(data);
                if(resultUri != null) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("tempFilePath", resultUri.getPath());
                    jsonObject.put("success", true);
                    jsonObject.put("errMsg", "ok");
                    callbackCropImage.invoke(jsonObject);
                }
            } else if (resultCode == UCrop.RESULT_ERROR) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("success", false);
                final Throwable cropError = UCrop.getError(data);
                if(cropError != null)
                    jsonObject.put("errMsg", "error:" + cropError.getLocalizedMessage());
                else
                    jsonObject.put("errMsg", "error:unknow");
                callbackCropImage.invoke(jsonObject);
            }
        }
    }
}
