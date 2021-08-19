package com.example.framedemo.util;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.framedemo.R;
import com.example.framedemo.api.Config;

/**
 * 用户隐私和协议工具类
 */
public class UserPrivacyUtils {

    public static SpannableString setUserPrivacy(Context context) {
        String string = context.getResources().getString(R.string.agree_privacy_policy_user_agreement);
        SpannableString spannableString = new SpannableString(string);
        int indexOf = string.indexOf(context.getResources().getString(R.string.privacy_policy));
        if (indexOf == -1) {
            indexOf = 0;
        }
        int length = context.getResources().getString(R.string.privacy_policy).length() + indexOf;
        spannableString.setSpan(new UnderlineSpan(), indexOf, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                PromotionManager.openUri(context, Config.URL_PRIVACY_POLICY);
            }
        }, indexOf, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.privacy_policy_text_color)), indexOf, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        indexOf = string.indexOf(context.getResources().getString(R.string.user_agreement));
        if (indexOf == -1) {
            indexOf = 0;
        }
        int length2 = context.getResources().getString(R.string.user_agreement).length() + indexOf;
        spannableString.setSpan(new UnderlineSpan(), indexOf, length2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                PromotionManager.openUri(context, Config.URL_USER_AGREEMENT);
            }
        }, indexOf, length2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.privacy_policy_text_color)), indexOf, length2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

       return spannableString;

    }

}
