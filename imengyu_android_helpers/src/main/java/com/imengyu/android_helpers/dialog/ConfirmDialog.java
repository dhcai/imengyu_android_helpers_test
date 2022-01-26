package com.imengyu.android_helpers.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.imengyu.android_helpers.R;

public class ConfirmDialog  extends Dialog {
    private final String TITLE;
    private final String MESSAGE;
    private final String CONFIRMTEXT;
    private final String CANCELTEXT;
    private final onConfirmClickListener ONCONFIRMCLICKLISTENER;
    private final onCancelClickListener ONCANCELCLICKLISTENER;

    public interface onConfirmClickListener {
        void onClick(Dialog dialog, View view);
    }

    public interface onCancelClickListener {
        void onClick(Dialog dialog, View view);
    }

    private ConfirmDialog(@NonNull Context context, String title, String message, String confirmText, String cancelText, boolean cancelable,
                           onConfirmClickListener onConfirmClickListener, onCancelClickListener onCancelClickListener) {
        super(context, R.style.MyUsualDialog);
        setCancelable(cancelable);
        setCanceledOnTouchOutside(cancelable);
        this.TITLE = title;
        this.MESSAGE = message;
        this.CONFIRMTEXT = confirmText;
        this.CANCELTEXT = cancelText;
        this.ONCONFIRMCLICKLISTENER = onConfirmClickListener;
        this.ONCANCELCLICKLISTENER = onCancelClickListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);
        initView();
    }

    public static Builder Builder(Context context) {
        return new Builder(context);
    }

    private void initView() {
        Button btnConfirm = findViewById(R.id.btn_confirm);
        Button btnCancel = findViewById(R.id.btn_cancel);
        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvMessage = findViewById(R.id.tv_message);
        View vSepator = findViewById(R.id.view_button_sepator);

        if (!TITLE.isEmpty()) {
            tvTitle.setText(TITLE);
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        if (!MESSAGE.isEmpty()) {
            tvMessage.setText(MESSAGE);
            tvMessage.setVisibility(View.VISIBLE);
        } else {
            tvMessage.setVisibility(View.GONE);
        }
        if (!CONFIRMTEXT.isEmpty()) {
            btnConfirm.setText(CONFIRMTEXT);
        }
        if (!CANCELTEXT.isEmpty()) {
            btnCancel.setText(CANCELTEXT);
            btnCancel.setVisibility(View.VISIBLE);
            vSepator.setVisibility(View.VISIBLE);
            btnConfirm.setBackgroundResource(R.drawable.btn_transparent_round_br);
        } else {
            btnConfirm.setBackgroundResource(R.drawable.btn_transparent_round_bottom);
            btnCancel.setVisibility(View.GONE);
            vSepator.setVisibility(View.GONE);
        }

        btnConfirm.setOnClickListener(view -> {
            if (ONCONFIRMCLICKLISTENER == null) {
                throw new NullPointerException("clicklistener is not null");
            } else {
                ONCONFIRMCLICKLISTENER.onClick(this, view);
            }
        });
        btnCancel.setOnClickListener(view -> {
            if (ONCANCELCLICKLISTENER == null) {
                throw new NullPointerException("clicklistener is not null");
            } else {
                ONCANCELCLICKLISTENER.onClick(this, view);
            }
        });
    }

    public ConfirmDialog shown() {
        show();
        return this;
    }

    public static class Builder {
        private boolean mCancelable;
        private String mTitle;
        private String mMessage;
        private String mConfirmText = "";
        private String mCancelText = "";
        private onConfirmClickListener mOnConfirmClickListener;
        private onCancelClickListener mOnCcancelClickListener;
        private final Context mContext;

        private Builder(Context context) {
            this.mContext = context;
        }

        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return this;
        }

        public Builder setMessage(String message) {
            this.mMessage = message;
            return this;
        }

        public Builder setOnConfirmClickListener(String confirmText, onConfirmClickListener confirmclickListener) {
            this.mConfirmText = confirmText;
            this.mOnConfirmClickListener = confirmclickListener;
            return this;
        }

        public Builder setOnCancelClickListener(String cancelText, onCancelClickListener onCancelclickListener) {
            this.mCancelText = cancelText;
            this.mOnCcancelClickListener = onCancelclickListener;
            return this;
        }

        public ConfirmDialog build() {
            return new ConfirmDialog(mContext, mTitle, mMessage, mConfirmText, mCancelText, mCancelable,
                    mOnConfirmClickListener, mOnCcancelClickListener);
        }
    }
}
