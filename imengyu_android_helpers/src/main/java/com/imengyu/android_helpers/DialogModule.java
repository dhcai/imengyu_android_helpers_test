package com.imengyu.android_helpers;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hjq.toast.ToastUtils;
import com.imengyu.android_helpers.adapter.ShareListListAdapter;
import com.imengyu.android_helpers.dialog.ConfirmDialog;
import com.imengyu.android_helpers.model.ShareDialogItem;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.dialogs.BottomDialog;
import com.kongzue.dialogx.dialogs.BottomMenu;
import com.kongzue.dialogx.dialogs.InputDialog;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.kongzue.dialogx.dialogs.PopTip;
import com.kongzue.dialogx.dialogs.TipDialog;
import com.kongzue.dialogx.dialogs.WaitDialog;
import com.kongzue.dialogx.interfaces.BaseDialog;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.kongzue.dialogx.style.IOSStyle;
import com.kongzue.dialogx.style.KongzueStyle;
import com.kongzue.dialogx.style.MIUIStyle;
import com.kongzue.dialogx.style.MaterialStyle;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

@Keep
public class DialogModule extends WXModule {
    String TAG = "DialogModule";

    /**
     * 设置原生对话框的主题
     * @param options
     * {
     *     dialogStyle: 'Material'|'Kongzue'|'IOS'|'MIUI', //对话框样式
     *     dialogTheme: 'LIGHT'|'DARK', //对话框主题
     * }
     */
    @Keep
    @UniJSMethod
    public void setTheme(JSONObject options) {
        if(options.containsKey("dialogStyle")) {
            String theme = options.getString("dialogStyle");
            switch (theme) {
                case "Material":
                    DialogX.globalStyle = MaterialStyle.style();
                    break;
                case "Kongzue":
                    DialogX.globalStyle = KongzueStyle.style();
                    break;
                case "IOS":
                    DialogX.globalStyle = IOSStyle.style();
                    break;
                case "MIUI":
                    DialogX.globalStyle = MIUIStyle.style();
                    break;
            }
        }
        if(options.containsKey("dialogTheme")) {
            String theme = options.getString("dialogTheme");
            if(theme.equals("LIGHT"))
                DialogX.globalTheme =  DialogX.THEME.LIGHT;
            else if(theme.equals("DARK"))
                DialogX.globalTheme =  DialogX.THEME.DARK;
        }
    }

    /**
     * 显示原生底部菜单
     * @param options
     * {
     *     title: string, //对话框标题
     *     choices: string[], //条目文字
     *     dialogStyle?: 'Material'|'Kongzue'|'IOS'|'MIUI', //对话框样式
     *     dialogTheme?: 'LIGHT'|'DARK', //对话框主题
     * }
     * @param callback 回调
     * {
     *     chooseIndex: number, //选中的条目索引
     *     chooseText: string, //选中的条目文字
     * }
     */
    @Keep
    @UniJSMethod
    public void showBottomMenu(JSONObject options, final JSCallback callback) {
        try {
            List<CharSequence> choices = new ArrayList<>();
            String title = "请选择";
            if (options.containsKey("choices")) {
                JSONArray jchoices = options.getJSONArray("choices");
                for (int i = 0; i < jchoices.size(); i++)
                    choices.add(jchoices.getString(i));
            }
            if (options.containsKey("title")) {
                title = options.getString("title");
            }

            BottomMenu dialogInstance = BottomMenu.build();

            if(options.containsKey("dialogStyle")) {
                String theme = options.getString("dialogStyle");
                switch (theme) {
                    case "Material":
                        dialogInstance.setStyle(MaterialStyle.style());
                        break;
                    case "Kongzue":
                        dialogInstance.setStyle(KongzueStyle.style());
                        break;
                    case "IOS":
                        dialogInstance.setStyle(IOSStyle.style());
                        break;
                    case "MIUI":
                        dialogInstance.setStyle(MIUIStyle.style());
                        break;
                }
            }
            if(options.containsKey("dialogTheme")) {
                String theme = options.getString("dialogTheme");
                if(theme.equals("LIGHT"))
                    dialogInstance.setTheme(DialogX.THEME.LIGHT);
                else if(theme.equals("DARK"))
                    dialogInstance.setTheme(DialogX.THEME.DARK);
            }

            dialogInstance
                    .setMenuList(choices)
                    .setTitle(title)
                    .setOnMenuItemClickListener((dialog, text, index) -> {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("chooseIndex", index);
                        jsonObject.put("chooseText", text);
                        jsonObject.put("success", true);
                        jsonObject.put("errMsg", "ok");
                        callback.invoke(jsonObject);
                        return false;
                    })
                    .show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private final HashMap<Integer, WaitDialog> waitDialogHashMap = new HashMap<>();
    private int waitDialogId = 0;
    private final HashMap<Integer, WaitDialog> tipDialogHashMap = new HashMap<>();
    private int tipDialogId = 0;

    /**
     * 显示原生加载中对话框
     *  @param options
     * {
     *     title: string, //标题
     * }
     * @param callback 回调返回数据
     * {
     *     dialogId: number, //当前对话框ID，可以使用此ID进行后续操作
     *     success: boolean,
     *     errMsg: string
     * }
     */
    @Keep
    @UniJSMethod
    public void showLoading(JSONObject options, final JSCallback callback) {
        try {
            String title = "请稍候...";
            if (options.containsKey("title"))
                title = options.getString("title");

            waitDialogHashMap.put(++waitDialogId, WaitDialog.show(title));

            if(callback != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("dialogId", waitDialogId);
                jsonObject.put("success", true);
                jsonObject.put("errMsg", "ok");
                callback.invoke(jsonObject);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * 隐藏原生加载中对话框
     * @param options
     * {
     *     id: number, //showLoading 返回的ID
     * }
     */
    @Keep
    @UniJSMethod
    public void hideLoading(JSONObject options) {
        try {
            if (options != null && options.containsKey("id")) {
                int id = options.getInteger("id");
                WaitDialog dialog = waitDialogHashMap.get(id);
                if (dialog != null)
                    dialog.doDismiss();
                waitDialogHashMap.remove(id);
            } else {
                WaitDialog.dismiss();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 更新原生加载中对话框的百分比
     * @param options
     * {
     *     id: number, //showLoading 返回的ID
     *     progress: number, //百分比，0-1
     * }
     */
    @Keep
    @UniJSMethod
    public void setLoadingProgress(JSONObject options) {
        if(options.containsKey("id") && options.containsKey("progress")) {
            int id = options.getInteger("id");
            WaitDialog dialog = waitDialogHashMap.get(id);
            if(dialog != null) {
                dialog.setProgress(options.getFloatValue("progress"));
            }
        }
    }

    /**
     * 显示原生提示对话框
     *  @param options
     * {
     *     title: string, //标题
     *     type: 'success'|'warn'|'error'|'none', 提示类型
     *     duration: number, //显示时长，毫秒
     * }
     * @param callback 回调返回数据
      * {
      *     dialogId: number, //当前对话框ID，可以使用此ID进行后续操作
      *     success: boolean,
      *     errMsg: string
      * }
     */
    @Keep
    @UniJSMethod
    public void showTipDialog(JSONObject options, final JSCallback callback) {
        String title = "请稍候...";
        String type = "none";
        int duration = 2000;
        if(options.containsKey("title"))
            title = options.getString("title");
        if(options.containsKey("type"))
            type = options.getString("type");
        if(options.containsKey("duration"))
            duration = options.getInteger("duration");

        TipDialog.TYPE type1 = TipDialog.TYPE.NONE;
        switch (type) {
            case "success": type1 = TipDialog.TYPE.SUCCESS; break;
            case "warn": type1 = TipDialog.TYPE.WARNING; break;
            case "error": type1 = TipDialog.TYPE.ERROR; break;
        }

        tipDialogHashMap.put(++tipDialogId, TipDialog.show(title, type1, duration));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dialogId", tipDialogId);
        jsonObject.put("success", true);
        jsonObject.put("errMsg", "ok");
        callback.invoke(jsonObject);
    }
    /**
     * 隐藏原生提示对话框
     * @param options
     * {
     *     id: number, //showLoading 返回的ID
     * }
     */
    @Keep
    @UniJSMethod
    public void hideTipDialog(JSONObject options) {
        if(options.containsKey("id")) {
            int id = options.getInteger("id");
            WaitDialog dialog = tipDialogHashMap.get(id);
            if(dialog != null)
                dialog.doDismiss();
            tipDialogHashMap.remove(id);
        } else {
            TipDialog.dismiss();
        }
    }

    /**
     * 显示原生土司
     *  @param options
     * {
     *     title: string, //标题
     * }
     */
    @Keep
    @UniJSMethod
    public void showToast(JSONObject options) {
        String title = "请稍候...";
        if(options.containsKey("title"))
            title = options.getString("title");

        ToastUtils.show(title);
    }

    /**
     * 显示系统原生土司
     *  @param options
     * {
     *     title: string, //标题
     *     length: 'shot'|'long', //Toast.LENGTH_SHORT / Toast.LENGTH_LONG
     * }
     */
    @Keep
    @UniJSMethod
    public void showSystemToast(JSONObject options) {
        String title = "请稍候...";
        int length = Toast.LENGTH_SHORT;
        if(options.containsKey("title"))
            title = options.getString("title");
        if(options.containsKey("length")) {
            String slength = options.getString("length");
            if(slength.equalsIgnoreCase("long"))
                length = Toast.LENGTH_LONG;
        }

        Toast.makeText(mWXSDKInstance.getContext(), title, length).show();
    }

    /**
     * 显示原生输入对话框
     * @param options
     * {
     *     title: string, //对话框标题
     *     message: string, //对话框文字
     *     okText: string, //确定按钮文字，默认“确定”
     *     cancelText: string, //取消按钮文字，默认“取消”
     *     hintText: string, //输入框提示文字
     *     initialText: string, //输入框开始时的文字
     *     dialogStyle?: 'Material'|'Kongzue'|'IOS'|'MIUI', //对话框样式
     *     dialogTheme?: 'LIGHT'|'DARK', //对话框主题
     * }
     * @param callback 回调
     * {
     *     confirm: boolean, //是选中确定按钮还是取消按钮
     *     inputStr: string, //文本框输入的文字
     * }
     */
    @Keep
    @UniJSMethod
    public void showInputDialog(JSONObject options, final JSCallback callback) {
        String title = "";
        String message = "";
        String okText = "确定";
        String cancelText = "取消";
        String hintText = "请输入";
        String initialText = "";
        if(options.containsKey("title"))
            title = options.getString("title");
        if(options.containsKey("okText"))
            okText = options.getString("okText");
        if(options.containsKey("cancelText"))
            cancelText = options.getString("cancelText");
        if(options.containsKey("message"))
            message = options.getString("message");
        if(options.containsKey("hintText"))
            hintText = options.getString("hintText");
        if(options.containsKey("initialText"))
            initialText = options.getString("initialText");

        InputDialog dialogInstance = InputDialog.build();

        if(options.containsKey("dialogStyle")) {
            String theme = options.getString("dialogStyle");
            switch (theme) {
                case "Material":
                    dialogInstance.setStyle(MaterialStyle.style());
                    break;
                case "Kongzue":
                    dialogInstance.setStyle(KongzueStyle.style());
                    break;
                case "IOS":
                    dialogInstance.setStyle(IOSStyle.style());
                    break;
                case "MIUI":
                    dialogInstance.setStyle(MIUIStyle.style());
                    break;
            }
        }
        if(options.containsKey("dialogTheme")) {
            String theme = options.getString("dialogTheme");
            if(theme.equals("LIGHT"))
                dialogInstance.setTheme(DialogX.THEME.LIGHT);
            else if(theme.equals("DARK"))
                dialogInstance.setTheme(DialogX.THEME.DARK);
        }

        dialogInstance
                .setTitle(title)
                .setMessage(message)
                .setInputText(initialText)
                .setInputHintText(hintText)
                .setOkButton(okText, (baseDialog, v, inputStr) -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("inputStr", inputStr);
                    jsonObject.put("success", true);
                    jsonObject.put("confirm", true);
                    jsonObject.put("errMsg", "ok");
                    callback.invoke(jsonObject);
                    return false;
                })
                .setCancelButton(cancelText, (baseDialog, v, inputStr) -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("inputStr", inputStr);
                    jsonObject.put("confirm", false);
                    jsonObject.put("success", true);
                    jsonObject.put("errMsg", "ok");
                    callback.invoke(jsonObject);
                    return false;
                })
                .show();
    }
    /**
     * 显示原生信息对话框
     * @param options
     * {
     *     title: string, //对话框标题
     *     message: string, //对话框文字
     *     okText: string, //确定按钮文字，默认“确定”
     *     cancelText: string, //取消按钮文字，默认“取消”
     *     dialogStyle?: 'Material'|'Kongzue'|'IOS'|'MIUI', //对话框样式
     *     dialogTheme?: 'LIGHT'|'DARK', //对话框主题
     * }
     * @param callback 回调
     * {
     *     clicked: 'ok'|'cancel'|'other', //是选中确定按钮
     * }
     */
    @Keep
    @UniJSMethod
    public void showMessageDialog(JSONObject options, final JSCallback callback) {
        String title = "";
        String message = "";
        String okText = "确定";
        String cancelText = "";
        String thirdText = "";
        if(options.containsKey("title"))
            title = options.getString("title");
        if(options.containsKey("okText"))
            okText = options.getString("okText");
        if(options.containsKey("thirdText"))
            thirdText = options.getString("thirdText");
        if(options.containsKey("cancelText"))
            cancelText = options.getString("cancelText");
        if(options.containsKey("message"))
            message = options.getString("message");

        MessageDialog messageDialog = MessageDialog.build();

        if(options.containsKey("dialogStyle")) {
            String theme = options.getString("dialogStyle");
            switch (theme) {
                case "Material":
                    messageDialog.setStyle(MaterialStyle.style());
                    break;
                case "Kongzue":
                    messageDialog.setStyle(KongzueStyle.style());
                    break;
                case "IOS":
                    messageDialog.setStyle(IOSStyle.style());
                    break;
                case "MIUI":
                    messageDialog.setStyle(MIUIStyle.style());
                    break;
            }
        }
        if(options.containsKey("dialogTheme")) {
            String theme = options.getString("dialogTheme");
            if(theme.equals("LIGHT"))
                messageDialog.setTheme(DialogX.THEME.LIGHT);
            else if(theme.equals("DARK"))
                messageDialog.setTheme(DialogX.THEME.DARK);
        }

        messageDialog.setTitle(title);
        messageDialog.setMessage(message);

        if(!okText.equals(""))
            messageDialog.setOkButton(okText, (baseDialog, v) -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("success", true);
                jsonObject.put("confirm", "ok");
                jsonObject.put("errMsg", "ok");
                callback.invoke(jsonObject);
                return false;
            });
        if(!cancelText.equals(""))
            messageDialog.setCancelButton(cancelText, (baseDialog, v) -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("confirm", "cancel");
                jsonObject.put("success", true);
                jsonObject.put("errMsg", "ok");
                callback.invoke(jsonObject);
                return false;
            });
        if(!thirdText.equals(""))
            messageDialog.setOtherButton(thirdText, (baseDialog, v) -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("confirm", "other");
                jsonObject.put("success", true);
                jsonObject.put("errMsg", "ok");
                callback.invoke(jsonObject);
                return false;
            });
        messageDialog.show();
    }

    /**
     * 显示原生分享对话框
     * @param options
     * {
     *     choices: string[], //可选分享类型 wechat-moments，qq，wechart，sina，link，poster，weapp-qrcode，qrcode
     *     dialogStyle?: 'Material'|'Kongzue'|'IOS'|'MIUI', //对话框样式
     *     dialogTheme?: 'LIGHT'|'DARK', //对话框主题
     * }
     * @param callback 回调
     * {
     *     confirm: boolean, //是选中确定按钮还是取消按钮
     * }
     */
    @Keep
    @UniJSMethod
    public void showShareDialog(JSONObject options, final JSCallback callback) {
        try {
            List<ShareDialogItem> itemList = new ArrayList<>();
            List<String> typeList = new ArrayList<>();
            Activity activity = (Activity) mWXSDKInstance.getContext();

            if (options.containsKey("choices")) {
                JSONArray jchoices = options.getJSONArray("choices");
                for (int i = 0; i < jchoices.size(); i++) {
                    String t = jchoices.getString(i);
                    switch (t) {
                        case "qq":
                            itemList.add(new ShareDialogItem(activity, R.drawable.share_qq, "QQ"));
                            typeList.add(t);
                            break;
                        case "wechat-moments":
                            itemList.add(new ShareDialogItem(activity, R.drawable.share_sheet_wechat_moments, "朋友圈"));
                            typeList.add(t);
                            break;
                        case "wechart":
                            itemList.add(new ShareDialogItem(activity, R.drawable.share_wechart, "微信"));
                            typeList.add(t);
                            break;
                        case "sina":
                            itemList.add(new ShareDialogItem(activity, R.drawable.share_sina, "微博"));
                            typeList.add(t);
                            break;
                        case "link":
                            itemList.add(new ShareDialogItem(activity, R.drawable.share_sheet_link, "复制链接"));
                            typeList.add(t);
                            break;
                        case "poster":
                            itemList.add(new ShareDialogItem(activity, R.drawable.share_sheet_poster, "生成海报"));
                            typeList.add(t);
                            break;
                        case "weapp-qrcode":
                            itemList.add(new ShareDialogItem(activity, R.drawable.share_sheet_weapp_qrcode, "小程序码"));
                            typeList.add(t);
                            break;
                        case "qrcode":
                            itemList.add(new ShareDialogItem(activity, R.drawable.share_sheet_qrcode, "二维码"));
                            typeList.add(t);
                            break;
                    }
                }
            }
            BottomDialog dialog = BottomDialog.build().setStyle(MaterialStyle.style());

            if(options.containsKey("dialogStyle")) {
                String theme = options.getString("dialogStyle");
                switch (theme) {
                    case "Material":
                        dialog.setStyle(MaterialStyle.style());
                        break;
                    case "Kongzue":
                        dialog.setStyle(KongzueStyle.style());
                        break;
                    case "IOS":
                        dialog.setStyle(IOSStyle.style());
                        break;
                    case "MIUI":
                        dialog.setStyle(MIUIStyle.style());
                        break;
                }
            }
            if(options.containsKey("dialogTheme")) {
                String theme = options.getString("dialogTheme");
                if(theme.equals("LIGHT"))
                    dialog.setTheme(DialogX.THEME.LIGHT);
                else if(theme.equals("DARK"))
                    dialog.setTheme(DialogX.THEME.DARK);
            }

            dialog.setCustomView(new OnBindView<BottomDialog>(R.layout.layout_share_dialog) {
                @Override
                public void onBind(BottomDialog dialog, View v) {
                    RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
                    v.findViewById(R.id.btn_cancel).setOnClickListener((v1) -> {
                        dialog.dismiss();
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("success", false);
                        jsonObject.put("errMsg", "cancel");
                        callback.invoke(jsonObject);
                    });
                    if (itemList.size() > 0) {
                        ShareListListAdapter adapter = new ShareListListAdapter(R.layout.item_share_list_item, itemList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
                        recyclerView.setAdapter(adapter);
                        adapter.setListOnItemClickListener((parent, view, position, id) -> {
                            dialog.dismiss();
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("index", position);
                            jsonObject.put("type", typeList.get(position));
                            jsonObject.put("success", true);
                            jsonObject.put("errMsg", "ok");
                            callback.invoke(jsonObject);
                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示PopTip
     * @param options
     * {
     *     title: string, //文字
     *     icon: ''|'success'|'error'|'warning'|'build'|'check'|'bug_report', //图标
     *     buttonText: string, //按钮文字
     *     duration: number, //显示算出，毫秒
     *     dialogStyle?: 'Material'|'Kongzue'|'IOS'|'MIUI', //对话框样式
     *     dialogTheme?: 'LIGHT'|'DARK', //对话框主题
     * }
     * @param callback
     * {
     *     click: false|'button'|'tip' //返回点击了什么，false没有，button点击了按钮，tip点击了PopTip
     * }
     */
    @Keep
    @UniJSMethod
    public void showPopTip(JSONObject options, final JSCallback callback) {
        try {

            PopTip popTip = null;

            if(options.containsKey("title")) {
                String icon = "", title = "";
                if(options.containsKey("icon"))
                    icon = options.getString("icon");
                title = options.getString("title");

                switch (icon) {
                    case "":
                        popTip = PopTip.show(title);
                        break;
                    case "success":
                        popTip = PopTip.show(R.mipmap.ic_done_black, title);
                        break;
                    case "error":
                        popTip = PopTip.show(R.mipmap.ic_error_black, title);
                        break;
                    case "warning":
                        popTip = PopTip.show(R.mipmap.ic_warning_black, title);
                        break;
                    case "build":
                        popTip = PopTip.show(R.mipmap.ic_build_black, title);
                        break;
                    case "check":
                        popTip = PopTip.show(R.mipmap.ic_check_circle_black, title);
                        break;
                    case "bug_report":
                        popTip = PopTip.show(R.mipmap.ic_bug_report_black, title);
                        break;
                }
            }
            if(popTip == null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("success", false);
                jsonObject.put("click", false);
                jsonObject.put("errMsg", "no title");
                callback.invoke(jsonObject);
                return;
            }

            if(options.containsKey("dialogStyle")) {
                String theme = options.getString("dialogStyle");
                switch (theme) {
                    case "Material":
                        popTip.setStyle(MaterialStyle.style());
                        break;
                    case "Kongzue":
                        popTip.setStyle(KongzueStyle.style());
                        break;
                    case "IOS":
                        popTip.setStyle(IOSStyle.style());
                        break;
                    case "MIUI":
                        popTip.setStyle(MIUIStyle.style());
                        break;
                }
            }
            if(options.containsKey("dialogTheme")) {
                String theme = options.getString("dialogTheme");
                if(theme.equals("LIGHT"))
                    popTip.setTheme(DialogX.THEME.LIGHT);
                else if(theme.equals("DARK"))
                    popTip.setTheme(DialogX.THEME.DARK);
            }

            if(options.containsKey("buttonText"))
                popTip.setButton(options.getString("buttonText"), (baseDialog, v) -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("success", true);
                    jsonObject.put("click", "button");
                    jsonObject.put("errMsg", "ok");
                    callback.invoke(jsonObject);
                    return false;
                });
            if(options.containsKey("duration"))
                popTip.autoDismiss(options.getInteger("duration"));

            popTip.setOnPopTipClickListener((baseDialog, v) -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("success", true);
                jsonObject.put("click", "tip");
                jsonObject.put("errMsg", "ok");
                callback.invoke(jsonObject);
                return false;
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示原生确认对话框
     *  @param options
     * {
     *     title: string, //对话框标题
     *     message: string, //对话框文字
     *     okText: string, //确定按钮文字，默认“确定”
     *     cancelText: string, //取消按钮文字，默认“取消”
     *     cancelable: boolean, //是否可以点击外围区域关闭对话框
     * }
     * @param callback 回调返回数据
     * {
     *     confirm: boolean, //是否点击了确定，否则为取消
     *     success: boolean,
     *     errMsg: string
     * }
     */
    @Keep
    @UniJSMethod
    public void showConfirmDialog(JSONObject options, final JSCallback callback) {
        try {
            String title = "";
            String message = "";
            String okText = "确定";
            String cancelText = "取消";
            boolean cancelable = false;
            if(options.containsKey("title"))
                title = options.getString("title");
            if(options.containsKey("okText"))
                okText = options.getString("okText");
            if(options.containsKey("cancelText"))
                cancelText = options.getString("cancelText");
            if(options.containsKey("message"))
                message = options.getString("message");
            if(options.containsKey("cancelable"))
                cancelable = options.getBoolean("cancelable");

            ConfirmDialog.Builder builder = ConfirmDialog.Builder(mWXSDKInstance.getContext());
            builder.setMessage(message);
            builder.setTitle(title);
            builder.setCancelable(cancelable);
            builder.setOnConfirmClickListener(okText, (dialog, view) -> {
                dialog.dismiss();
                if(callback != null) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("confirm", true);
                    jsonObject.put("success", true);
                    jsonObject.put("errMsg", "ok");
                    callback.invoke(jsonObject);
                }
            });
            if(!cancelText.equals("")) {
                builder.setOnCancelClickListener(cancelText, (dialog, view) -> {
                    dialog.dismiss();
                    if(callback != null) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("confirm", false);
                        jsonObject.put("success", true);
                        jsonObject.put("errMsg", "ok");
                        callback.invoke(jsonObject);
                    }
                });
            }

            builder.build().show();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

