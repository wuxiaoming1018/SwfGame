package qimonjy.cn.swfgame.dds;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qimonjy.cn.commonlibrary.FileIOandOperation;
import qimonjy.cn.commonlibrary.XmlUtils;
import qimonjy.cn.swfgame.dds.data.DDSData;
import qimonjy.cn.swfgame.dds.data.WordData;

public class DDSMainActivity extends AppCompatActivity {
    private int[][] voleIcon = new int[][]{{R.drawable.dds_vole_1_0, R.drawable.dds_vole_1_1},
            {R.drawable.dds_vole_2_0, R.drawable.dds_vole_2_1},
            {R.drawable.dds_vole_3_0, R.drawable.dds_vole_3_1},
            {R.drawable.dds_vole_4_0, R.drawable.dds_vole_4_1}};
    private static final String CONFIG_FILE_PATH = "/sdcard/Android/data/com.qimon.studentcircle/cache/config.xml";//"/sdcard/config.xml";//
    @BindView(R2.id.help)
    ImageView help;
    @BindView(R2.id.start)
    ImageView start;
    public static final String DATA_LIST = "DATA_LIST";
    private ArrayList<DDSData> dataList = new ArrayList<>();
    private String config;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dds_activity_main);
        ButterKnife.bind(this);
        buildData();
    }

    /**
     * 读取并构建数据对象列表。
     */
    private void buildData() {
        config = FileIOandOperation.readFile(CONFIG_FILE_PATH, "UTF-8").toString();
        if (!TextUtils.isEmpty(config)) {
            Document document = XmlUtils.getDocument(config);
            if (document != null) {
                Element root = document.getRootElement();
                dataList.clear();
//                int i = 1;
                for (Iterator it = root.elementIterator(); it.hasNext(); ) {
                    Element element = (Element) it.next();
//                    String chinese = element.attribute("chinese").getText();
                    String mp3Url = element.attribute("mp3Url").getText();
//                    String text = element.getText();
                    ArrayList<ArrayList<Integer>> position = new ArrayList<>();
                    for (int m = 0; m < voleIcon.length; m++) {
                        ArrayList<Integer> v = new ArrayList<>();
                        for (int n = 0; n < voleIcon[m].length; n++) {
                            v.add(voleIcon[m][n]);
                        }
                        position.add(v);
                    }
                    DDSData data = new DDSData();
                    List<Element> childrens = element.elements();
                    if (childrens != null) {
                        for (int j = 0; j < childrens.size(); j++) {
                            Element ele = childrens.get(j);
                            if (j == 0) {
                                data.setCorrect(ele.getText());
                            }
                            WordData wordData = new WordData();
                            wordData.setText(ele.getText());
                            int number = new Random().nextInt(4);
                            ArrayList<Integer> v = position.remove(number % position.size());
                            wordData.setVoleIcon(v.get(0));
                            wordData.setVoleIcon2(v.get(1));

                            data.addWordList(wordData);
                        }
                    }
//                    data.setChinese(chinese);
                    data.setMp3Url(mp3Url);
//                    data.setText(text);
//                    data.setIndex(i);
//                    i++;
//                    if (!TextUtils.isEmpty(text)) {
//                        char[] letter = text.toCharArray();
//                        if (letter != null && letter.length > 0) {
//                            ArrayList<Integer> position = new ArrayList<>();
//                            for (Integer p : bodyPositoinList) {
//                                position.add(p);
//                            }
//                            for (char l : letter) {
//                                LetterData letterData = new LetterData();
//                                letterData.setLetter(l);
//                                int number = new Random().nextInt(2);
//                                letterData.setBackgroundID(bodyBackground[number]);
//                                number = new Random().nextInt(17);
//                                letterData.setPositionID(position.remove(number % position.size()));
////                                System.out.println("build data : " + chinese + "  " + mp3Url + "  " + text + "  " + data.getBackgroundID() + "  " + data.getPositionID());
//                                data.addLetterData(letterData);
//                            }
//                            dataList.add(data);
//                        }
//                    }
                }
            }
        }
    }

    public void playGame() {
        Intent intent = new Intent(this, DDSGameActivity.class);
        intent.putExtra(DATA_LIST, dataList);
        startActivity(intent);
    }

    @OnClick(R2.id.help)
    public void onHelpClicked() {
        showDialog();
    }

    @OnClick(R2.id.start)
    public void onStartClicked() {
        playGame();
    }

    AlertDialog alertDialog;

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.dialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dds_activity_help, null);
        ImageView close = (ImageView) view.findViewById(R.id.close2);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        ImageView start = (ImageView) view.findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                playGame();
            }
        });
        builder.setView(view);
        alertDialog = builder.create();
//        Window window = alertDialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.alpha = 0.1f;    // 设置透明度为0.5
//        window.setAttributes(lp);
        alertDialog.show();
    }

}
