package com.ez08.im.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.EditText;

import com.ez08.im.R;

import java.util.HashMap;

/**
 * Created by shand on 2016/4/21.
 */
public class EditTextUtils {

    public static void setEditTextImage(final EditText text, final View view){
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    view.setVisibility(View.GONE);
                } else {
                    if (isHasFocus) {
                        view.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                view.setVisibility(hasFocus && (text.length() > 0) ? View.VISIBLE
                        : View.GONE);
                isHasFocus = hasFocus;
            }
        });
    }
    private static boolean isHasFocus = false;

    public static HashMap<String, Integer> emo_map = new HashMap<>();

    public static final String[] EMOS = new String[] { "微笑", "呲牙", "色", "发呆",
            "得意", "大哭", "害羞", "闭嘴", "睡", "崇拜", "亲亲", "发怒", "调皮", "大笑", "惊讶",
            "委屈", "冷汗", "抓狂", "吐", "阴险", "傲慢", "困", "憨笑", "敲打", "奋斗", "拥护",
            "晕", "鄙视", "强", "菜刀", "再见", "玫瑰", "嘴唇", "爱心", "咖啡", "月亮", "礼物",
            "握手", "鼓掌", "啤酒", "OK" };
    public static SpannableStringBuilder getEmotion(Context context, String text) {
        if (emo_map.size() == 0) {
            for (int i = 0; i < 41; i++) {
                emo_map.put("[" + EMOS[i] + "]", R.drawable.emoji_01 + i);
            }
        }
        int index1 = text.indexOf("[");
        SpannableStringBuilder sb;
        if (index1 >= 0) {
            sb = new SpannableStringBuilder("");
            //特殊符号前面加一个空格，不会出现不正常的折行现象
            text=text.replace(" [", "[");
            text=text.replace("[", " [");
            replaceString(context, sb, text);
        } else {
            sb = new SpannableStringBuilder(text);
        }

        return sb;
    }
    public static void replaceString(Context context,
                                     SpannableStringBuilder sb, String text) {

        int index1 = text.indexOf("[");
        int index2 = text.indexOf("]");
        String remindedString = "";
        String emString = "";
        if (index2 > 0&&index1<index2) {
            // sb.append(text.substring(0, index1));
            remindedString = text.substring(index2 + 1);
            emString = text.substring(index1, index2 + 1);
            if (emString == null) {
                return;
            }
            String cc = emString.substring(1, emString.length() - 1);
            if (!TextUtils.isEmpty(cc) && emo_map.containsKey("[" + cc + "]")) {

                int id = emo_map.get("[" + cc + "]");
                Drawable d = context.getResources().getDrawable(id);
                //
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                //
                ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);

                SpannableString ss = new SpannableString(text.substring(0,
                        index2 + 1));
                ss.setSpan(span, index1, index2 + 1,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                sb.append(ss);
                replaceString(context, sb, remindedString);

            }else {
                sb.append(text.substring(0, index2+1));
                replaceString(context, sb, remindedString);
            }

        } else {
            sb.append(text);
        }
    }

}
