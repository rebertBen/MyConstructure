package com.robert.tool.view.qrcode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.robert.tool.R;
import com.robert.tool.base.BaseActivity;
import com.robert.tool.constant.ArouterPathConstant;
import com.robert.tool.custom.ClearEditText;
import com.robert.tool.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bertsir.zbar.Qr.ScanResult;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
import cn.bertsir.zbar.utils.QRUtils;

/**
 * @author bobo
 * <p>
 * function：二维码
 * <p>
 * create_time：2018/9/15 18:34
 * update_by：
 * update_time:
 */
@Route(path = ArouterPathConstant.PATH_TO_QR_CODE)
public class QRCodeActivity extends BaseActivity {
    @BindView(R.id.et_clear)
    ClearEditText etClear;
    @BindView(R.id.zxing_btn)
    Button zxingBtn;
    @BindView(R.id.scan_btn)
    Button scanBtn;
    @BindView(R.id.iv_img)
    ImageView ivImg;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_qr_code;
    }

    @Override
    protected void setViews() {
        setTitle(getResources().getString(R.string.qr_code));
        ivImg = findViewById(R.id.iv_img);
        ivImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try{
                    ToastUtil.showShort(QRCodeActivity.this, QRUtils.getInstance().decodeQRcode(ivImg));
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    @OnClick({R.id.zxing_btn, R.id.scan_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zxing_btn:
                if (etClear.getText().toString().trim().equals("")){
                    ToastUtil.showShort(this, "请输入二维码信息");
                    return;
                }
                bitmap = createQRCode(etClear.getText().toString().trim(), 0);
                if (null == bitmap){
                    ToastUtil.showShort(this, "二维码生成失败");
                }
                ivImg.setImageBitmap(bitmap);
                break;
            case R.id.scan_btn:
                scanQRCode();
                break;
        }
    }

    /**
     * 生成二维码
     *
     * @param msg        二维码信息
     * @param resourceId 资源文件logo
     */
    private Bitmap createQRCode(String msg, int resourceId) {
        if (null == msg || msg.trim().equals("")) {
            ToastUtil.showShort(this, "生成二维码信息不能为空！");
            return null;
        }

        Bitmap qrCode = null;
        if (0 == resourceId) {
            qrCode = QRUtils.getInstance().createQRCode(msg);
        } else {
            qrCode = QRUtils.getInstance().createQRCodeAddLogo(msg, BitmapFactory.decodeResource(getResources(), resourceId));
        }

        return qrCode;
    }

    /**
     * 二维码扫描识别
     */
    private void scanQRCode(){
        QrConfig qrConfig = new QrConfig.Builder()
                .setDesText("(识别二维码)")//扫描框下文字
                .setShowDes(false)//是否显示扫描框下面文字
                .setShowLight(true)//显示手电筒按钮
                .setShowTitle(true)//显示Title
                .setShowAlbum(true)//显示从相册选择按钮
                .setCornerColor(Color.WHITE)//设置扫描框颜色
                .setLineColor(Color.WHITE)//设置扫描线颜色
                .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
                .setScanType(QrConfig.TYPE_ALL)//设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE)//设置扫描框类型（二维码还是条形码，默认为二维码）
                .setCustombarcodeformat(QrConfig.BARCODE_I25)//此项只有在扫码类型为TYPE_CUSTOM时才有效
                .setPlaySound(true)//是否扫描成功后bi~的声音
//                .setDingPath(R.raw.test)//设置提示音(不设置为默认的Ding~)
                .setIsOnlyCenter(true)//是否只识别框中内容(默认为全屏识别)
                .setTitleText("扫描二维码")//设置Tilte文字
                .setTitleBackgroudColor(Color.BLUE)//设置状态栏颜色
                .setTitleTextColor(Color.WHITE)//设置Title文字颜色
                .create();
        QrManager.getInstance().init(qrConfig).startScan(QRCodeActivity.this, new QrManager.OnScanResultCallback() {
            @Override
            public void onScanSuccess(ScanResult result) {
                ToastUtil.showShort(QRCodeActivity.this, result.getContent());
            }

        });
    }

}
