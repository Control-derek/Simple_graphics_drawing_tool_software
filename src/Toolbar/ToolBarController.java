package Toolbar;

import App.App;
import App.ShapeAttribute;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @see ToolBarController
 * 工具栏控制器
 * @version 1.0
 * @author 眭永熙
 */
public class ToolBarController {
    /**
     * 画笔按钮
     */
    @FXML
    private Button PenBtn;
    /**
     * 直线按钮
     */
    @FXML
    private Button LineBtn;
    /**
     * 椭圆按钮
     */
    @FXML
    private Button OvalBtn;
    /**
     * 矩形按钮
     */
    @FXML
    private Button RectBtn;
    /**
     * 移动按钮
     */
    @FXML
    private Button MoveBtn;
    /**
     * 橡皮按钮
     */
    @FXML
    private Button RubberBtn;
    /**
     * 油漆桶按钮
     */
    @FXML
    private Button BarrelBtn;
    /**
     * 曲线按钮
     */
    @FXML
    private Button CurveBtn;
    /**
     * 文本按钮
     */
    @FXML
    private Button TextBtn;
    /**
     * 拖动栏
     */
    @FXML
    private Slider SizeSlider;
    @FXML
    private Label label;
    /**
     * 画笔颜色选择器
     */
    @FXML
    private ColorPicker StrokeCP;
    /**
     * 填充颜色选择器
     */
    @FXML
    private ColorPicker FillCP;
    /**
     * 圆按钮
     */
    @FXML
    private Button CircleBtn;
    /**
     * 左对齐按钮
     */
    @FXML
    private Button leftAlignBtn;
    /**
     * 右对齐按钮
     */
    @FXML
    private Button rightAlignBtn;
    /**
     * 水平居中按钮
     */
    @FXML
    private Button centerHorBtn;
    /**
     * 顶端对齐按钮
     */
    @FXML
    private Button topAlignBtn;
    /**
     * 底端对齐按钮
     */
    @FXML
    private Button bottomAlignBtn;
    /**
     * 垂直居中对齐按钮
     */
    @FXML
    private Button centerVerBtn;
    /**
     * tag按钮
     */
    @FXML
    private Button tagBtn;

    private App app;
    public static boolean first=false;
    public ToolBarController() {
    }

    /**
     * 初始化各按钮功能
     */
    @FXML
    private void initialize() {
        first = false;
        SizeSlider.setValue(ShapeAttribute.getSize());
        label.setText(String.valueOf(ShapeAttribute.getSize()));
        StrokeCP.setValue(ShapeAttribute.getStrokeColor());
        FillCP.setValue(ShapeAttribute.getFillColor());
        setIcon("/pen.png",PenBtn);
        PenBtn.setOnMouseClicked((e) -> {
            ShapeAttribute.setTool("PEN");
            System.out.println(ShapeAttribute.getTool());
        });
        setIcon("/linear.png",LineBtn);
        LineBtn.setOnMouseClicked((e) -> {
            ShapeAttribute.setTool("LINE");
            System.out.println(ShapeAttribute.getTool());
        });
        setIcon("/elliptic.png",OvalBtn);
        OvalBtn.setOnMouseClicked((e) -> {
            ShapeAttribute.setTool("OVAL");
            System.out.println(ShapeAttribute.getTool());
        });
        setIcon("/rectangular.png",RectBtn);
        RectBtn.setOnMouseClicked((e) -> {
            ShapeAttribute.setTool("RECT");
            System.out.println(ShapeAttribute.getTool());
        });
        setIcon("/move.png",MoveBtn);
        MoveBtn.setOnMouseClicked((e) -> {
            ShapeAttribute.setTool("MOVE");
            System.out.println(ShapeAttribute.getTool());
        });
        setIcon("/eraser.png",RubberBtn);
        RubberBtn.setOnMouseClicked((e) -> {
            ShapeAttribute.setTool("RUBBER");
            System.out.println(ShapeAttribute.getTool());
        });
        setIcon("/filling.png",BarrelBtn);
        BarrelBtn.setOnMouseClicked((e) -> {
            ShapeAttribute.setTool("BARREL");
            System.out.println(ShapeAttribute.getTool());
        });
        setIcon("/curve.png",CurveBtn);
        CurveBtn.setOnMouseClicked((e) -> {
            ShapeAttribute.setTool("CURVE");
            System.out.println(ShapeAttribute.getTool());
        });
        setIcon("/text.png",TextBtn);
        TextBtn.setOnMouseClicked((e) -> {
            ShapeAttribute.setTool("TEXT");
            System.out.println(ShapeAttribute.getTool());
        });
        setIcon("/left_align.png",leftAlignBtn);
        leftAlignBtn.setOnMouseClicked((e) -> {

            ShapeAttribute.setTool("LEFT");
            if(!first)
            {System.out.println("开始选择要操作的对象！");
                first = true;
            }
            else
            {
                System.out.println("选择好了，请再按一次白板开始对齐，注意不要按对象！");
                first=false;
            }
            System.out.println(ShapeAttribute.getTool());
        });
        setIcon("/right_align.png",rightAlignBtn);
        rightAlignBtn.setOnMouseClicked((e) -> {
            ShapeAttribute.setTool("RIGHT");
            if(!first)
            {System.out.println("开始选择要操作的对象！");
                first = true;
            }
            else
            {
                System.out.println("选择好了，请再按一次白板开始对齐，注意不要按对象！");
                first=false;
            }
            System.out.println(ShapeAttribute.getTool());
        });
        setIcon("/center_hor.png",centerHorBtn);
        centerHorBtn.setOnMouseClicked((e) -> {
            ShapeAttribute.setTool("CENHOR");
            if(!first)
            {System.out.println("水平居中开始选择要操作的对象！");
                first = true;
            }
            else
            {
                System.out.println("选择好了，请再按一次白板开始对齐，注意不要按对象！");
                first=false;
            }
            System.out.println(ShapeAttribute.getTool());
        });
        setIcon("/top_align.png",topAlignBtn);
        topAlignBtn.setOnMouseClicked((e) -> {
            ShapeAttribute.setTool("TOP");
            if(!first)
            {System.out.println("开始选择要操作的对象！");
                first = true;
            }
            else
            {
                System.out.println("选择好了，请再按一次白板开始对齐，注意不要按对象！");
                first=false;
            }
            System.out.println(ShapeAttribute.getTool());
        });
        setIcon("/bottom_align.png",bottomAlignBtn);
        bottomAlignBtn.setOnMouseClicked((e) -> {
            ShapeAttribute.setTool("BOTTOM");
            if(!first)
            {System.out.println("开始选择要操作的对象！");
                first = true;
            }
            else
            {
                System.out.println("选择好了，请再按一次白板开始对齐，注意不要按对象！");
                first=false;
            }
            System.out.println(ShapeAttribute.getTool());
        });
        setIcon("/center_ver.png",centerVerBtn);
        centerVerBtn.setOnMouseClicked((e) -> {
            ShapeAttribute.setTool("CENVER");
            if(!first)
            {System.out.println("开始选择要操作的对象！");
                first = true;
            }
            else
            {
                System.out.println("选择好了，请再按一次白板开始对齐，注意不要按对象！");
                first=false;
            }
            System.out.println(ShapeAttribute.getTool());
        });
        setIcon("/tag.png",tagBtn);
        tagBtn.setOnMouseClicked((e) -> {
            ShapeAttribute.setTool("TAG");
            System.out.println(ShapeAttribute.getTool());
        });
        setIcon("/circular.png",CircleBtn);
        CircleBtn.setOnMouseClicked((e) -> {
            ShapeAttribute.setTool("CIRCLE");
            System.out.println(ShapeAttribute.getTool());
        });
        SizeSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            ShapeAttribute.setSize(SizeSlider.getValue());
            label.setText(String.valueOf(SizeSlider.getValue()));
        });
        StrokeCP.setOnAction((ActionEvent e) -> ShapeAttribute.setStrokeColor(StrokeCP.getValue()));
        FillCP.setOnAction((ActionEvent e) -> ShapeAttribute.setFillColor(FillCP.getValue()));
    }

    public void setMainApp(App appApp) {
        this.app = appApp;
    }

    /**
     * 设置按钮图标
     * @param path 图标路径
     * @param btn 按钮
     */
    private void setIcon(String path,Button btn) {
        Image image = new Image(path);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(44);
        imageView.setFitHeight(47);
        btn.setGraphic(imageView);
    }

}
