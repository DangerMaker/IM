package com.ez08.im.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ez08.im.Config;
import com.ez08.im.R;
import com.ez08.im.model.Bimp;
import com.ez08.im.ui.view.Emotion_ViewPager;
import com.ez08.im.ui.view.SelectPopupWindow;
import com.ez08.im.util.FileUtils;
import com.ez08.im.util.RestAdapterUtils;
import com.ez08.im.util.SystemUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * 发表动态界面
 */
public class PublishedActivity extends BackBaseActivity implements View.OnClickListener {
    private static final int LOCATION_CODE = 4;
    @Bind(R.id.add_location)
    TextView location;
    private GridAdapter adapter;
    private int mPicWidth;
    @Bind(R.id.noScrollgridview)
    GridView mGridView;
    @Bind(R.id.add_group)
    LinearLayout mAddGroup;
    @Bind(R.id.add_more_grid)
    GridView mAddMoreGrid;
    @Bind(R.id.add_emoj_pager)
    Emotion_ViewPager mAddEmojPage;
    @Bind(R.id.root)
    LinearLayout root;
    @Bind(R.id.comment)
    EditText mComment;
    @Bind(R.id.activity_selectimg_send)
    TextView mSend;
    @Bind(R.id.username)
    TextView mUserName;
    @Bind(R.id.add_branch)
    TextView mBranch;


    private static final int TAKE_PICTURE = 2;
    private static final int ADD_MODLE = 0;
    private String path = "";
    private boolean isKeyShow = true;
    private SelectPopupWindow menuWindow;
    private String[] dialogString = {"无线事业部", "IOS", "ANDROID", "PHP", "J2EE", "HTML5"};

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    adapter.notifyDataSetChanged();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectimg);
        ButterKnife.bind(this);
        Init();
    }

    public void Init() {
        mAddEmojPage.setEditText(mComment);
        mSend.setClickable(false);
        //获取当前手机频幕高度
        WindowManager manager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        int screenWidth = manager.getDefaultDisplay().getWidth();
        //获取控件margin值
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mGridView.getLayoutParams();
        int rightMargin = params.rightMargin;
        int leftMargin = params.leftMargin;
        //计算图片排列总宽度
        mPicWidth = screenWidth - rightMargin - leftMargin;
        controlKeyboardLayout(root, mAddGroup);
        //发送按钮显示状态
        mComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    mSend.setTextColor(getResources().getColor(R.color.colorTextGray));
                    mSend.setBackgroundColor(getResources().getColor(R.color.gray_text));
                    if (mSend.isClickable()) mSend.setClickable(false);
                } else {
                    mSend.setTextColor(getResources().getColor(R.color.colorWhite));
                    mSend.setBackgroundColor(getResources().getColor(R.color.orange));
                    if (!mSend.isClickable()) mSend.setClickable(true);
                }
            }
        });
        mComment.setOnClickListener(this);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        adapter.update();
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == Bimp.bmp.size()) {
                    selectPic();
                } else {
                    Intent intent = new Intent(PublishedActivity.this,
                            PhotoActivity.class);
                    intent.putExtra("ID", position);
                    startActivity(intent);
                }
            }
        });
//        mComment.setFocusable(true);
//        InputMethodManager imm = (InputMethodManager)getSystemService(Service.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (Config.user != null) mUserName.setText(Config.user.getName());
    }

    //打开相机
    private void fromCamera() {
        String sdStatus = Environment.getExternalStorageState();
        if (!TextUtils.equals(sdStatus, Environment.MEDIA_MOUNTED)) {
            SystemUtils.show_msg(PublishedActivity.this, "未检测到SD卡");
            return;
        }
        File file = new File(FileUtils.SDPATH, String.valueOf(System.currentTimeMillis()) + ".png");
        path = file.getPath();
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }
    //打开图库
    private void fromPicture() {
        Intent intent = new Intent(this,
                ImageGridActivity.class);
        intent.putExtra("type",1);
        startActivity(intent);
    }

    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            return Bimp.bmp == null ? 0 : Bimp.bmp.size() + 1;
        }

        public Object getItem(int arg0) {

            return null;
        }

        public long getItemId(int arg0) {

            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            final int coord = position;
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = new ImageView(PublishedActivity.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mPicWidth / 4, mPicWidth / 4);
                convertView.setLayoutParams(params);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView;
                holder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position == Bimp.bmp.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_unfocused));
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.bmp.get(position));
            }
            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if(Bimp.drr != null){
                            if (Bimp.max == Bimp.drr.size()) {
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                                break;
                            } else {
                                String path = Bimp.drr.get(Bimp.max);
                                System.out.println(path);
                                //将对应路径的图片压缩
                                Bitmap bm = Bimp.revitionImageSize(path);
                                //压缩后添加到集合
                                Bimp.bmp.add(bm);
                                //截取图片名称
                                String newStr = path.substring(
                                        path.lastIndexOf("/") + 1,
                                        path.lastIndexOf("."));
                                //保存图片
                                FileUtils.saveBitmap(bm, "" + newStr);
                                Bimp.max += 1;
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                            }
                        }
                    }
                }
            }).start();
        }
    }


    protected void onRestart() {
        adapter.update();
        checkGridView();
        mAddEmojPage.setVisibility(View.GONE);
        mAddMoreGrid.setVisibility(View.GONE);
        super.onRestart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (path != null) {
                    if (Bimp.drr.size() < 9) {
                        Bimp.drr.add(path);
                    }
                }
                break;
            case ADD_MODLE:

                break;

            case LOCATION_CODE :
               if(resultCode == 3){
                   String title = data.getStringExtra("title");
                   location.setText(title);
               }
                break;
        }
    }

    @Override
    protected void onStart() {
        checkGridView();
        super.onStart();
    }

    public void checkGridView() {
        if (Bimp.drr == null || Bimp.drr.size() == 0) {
            mGridView.setVisibility(View.GONE);
        } else {
            mGridView.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.add_picature)
    public void addPicature() {
        mAddEmojPage.setVisibility(View.GONE);
        mAddMoreGrid.setVisibility(View.GONE);
        selectPic();
    }

    //强制隐藏键盘
    public void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mComment.getWindowToken(), 0);
    }

    private void selectPic() {
        View v = this.getLayoutInflater().inflate(R.layout.item_user_center_edit_avatar, null);
        menuWindow = new SelectPopupWindow(root, this, v);
        v.findViewById(R.id.btn_take_photo).setOnClickListener(this);
        v.findViewById(R.id.btn_pick_photo).setOnClickListener(this);
    }


    @OnClick(R.id.add_more)
    public void addMore() {
        hideInput();
        if (mAddMoreGrid.getVisibility() == View.GONE) {
            if (mAddEmojPage.getVisibility() == View.VISIBLE) mAddEmojPage.setVisibility(View.GONE);
            mAddMoreGrid.setVisibility(View.VISIBLE);
        } else if (mAddMoreGrid.getVisibility() == View.VISIBLE) {
            mAddMoreGrid.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.add_emoj)
    public void addEmoj() {
        hideInput();
        if (mAddEmojPage.getVisibility() == View.GONE) {
            if (mAddMoreGrid.getVisibility() == View.VISIBLE) mAddMoreGrid.setVisibility(View.GONE);
            mAddEmojPage.setVisibility(View.VISIBLE);
        } else if (mAddEmojPage.getVisibility() == View.VISIBLE) {
            mAddEmojPage.setVisibility(View.GONE);
        }
    }



    @OnClick(R.id.add_location)
    public void addLocation() {
        //启动定位
        startActivityForResult(new Intent(PublishedActivity.this,LocationActivity.class),LOCATION_CODE);
    }



    @OnClick(R.id.add_branch)
    public void addBranch() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ListView listView = new ListView(this);
        listView.setBackgroundResource(R.drawable.toast_background_white);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dialogString));
        final AlertDialog dialog = builder.setView(listView).show();
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mBranch.setText(dialogString[position]);
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.add_modle)
    public void addModle() {
        startActivityForResult(new Intent(PublishedActivity.this, ModleActivity.class), ADD_MODLE);
    }

    @OnClick(R.id.activity_selectimg_send)
    public void send() {
        hideInput();
        if (Config.user == null) {
            startActivity(new Intent(PublishedActivity.this, LoginActivity.class));
            return;
        }
        if(Bimp.drr.size() != 0 && Bimp.drr != null){
            List<String> list = new ArrayList<>();
            for (int i = 0; i < Bimp.drr.size(); i++) {
                String Str = Bimp.drr.get(i).substring(
                        Bimp.drr.get(i).lastIndexOf("/") + 1,
                        Bimp.drr.get(i).lastIndexOf("."));
                list.add(FileUtils.SDPATH + Str + ".JPEG");
            }
            String comment = mComment.getText().toString();
            // 高清的压缩图片路径全部就在  list 里面
            // 高清的压缩过的 bmp 对象  都在 Bimp.bmp里面
            File file = new File(list.get(0));
            TypedFile typedFile = new TypedFile("application/octet-stream", file);
            RestAdapterUtils.getUserApi().upFile(Config.token, Config.cookie, file.getName(), typedFile, new Callback<String>() {
                @Override
                public void success(String s, Response response) {
                    Bimp.bmp = null;
                    Bimp.drr = null;
                    Bimp.max = 0;
                    FileUtils.deleteDir();
                    SystemUtils.show_msg(PublishedActivity.this, s);
                }

                @Override
                public void failure(RetrofitError error) {
                    SystemUtils.show_msg(PublishedActivity.this, error.toString());
                }
            });
        }
        SystemUtils.show_msg(this, "发送动态");
    }

    @OnClick(R.id.cancle)
    public void cancle() {
//        if(TextUtils.isEmpty(mComment.getText().toString()) && Bimp.bmp.size() == 0  ){
        finish();
//        }else {
//            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            View view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_save_comment,null);
//            builder.setView(view);
//            Button noSave = (Button) view.findViewById(R.id.no_save);
//            Button save = (Button) view.findViewById(R.id.save);
//            noSave.setOnClickListener(this);
//            save.setOnClickListener(this);
//            builder.show();
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_take_photo:
                fromCamera();
                menuWindow.dismiss();
                break;
            case R.id.btn_pick_photo:
                fromPicture();
                menuWindow.dismiss();
                break;
            case R.id.comment:
                mAddEmojPage.setVisibility(View.GONE);
                mAddMoreGrid.setVisibility(View.GONE);
                break;

//            case R.id.no_save:
//                finish();
//                break;
//            case R.id.save:
//                finish();
//                break;
        }
    }

    /**
     * 根据键盘状态设置布局位置
     */
    private void controlKeyboardLayout(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                //获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                //若不可视区域高度大于100，则键盘显示
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) scrollToView.getLayoutParams();
                if (rootInvisibleHeight > 100 && isKeyShow) {
                    int[] location = new int[2];
                    //获取scrollToView在窗体的坐标
                    scrollToView.getLocationInWindow(location);
                    //被遮盖的高度
                    int srollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                    params.bottomMargin = srollHeight;
                    scrollToView.setLayoutParams(params);
                    isKeyShow = false;
                } else if (rootInvisibleHeight <= 100 && !isKeyShow) {
                    //键盘隐藏
                    params.bottomMargin = 0;
                    scrollToView.setLayoutParams(params);
                    isKeyShow = true;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bimp.drr.clear();
        Bimp.bmp.clear();
        Bimp.max = 0;
    }
}
