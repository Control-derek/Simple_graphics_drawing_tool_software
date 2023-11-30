package Shape;

import App.ShapeAttribute;
import File.FileCopy;
import UndoManager.RecordStack;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Optional;

import static javafx.scene.Cursor.*;

/**
 * @see MyText
 * 自定义文本类（可输入、拖动、旋转、改变颜色、调节字号）
 * @version 5.0
 * @author 眭永熙
 */
public class MyText extends Text implements Cloneable {
    private double fromX, fromY, lastTranslateX, lastTranslateY;

    private ArrayList TAGs = new ArrayList();

    //添加TAG
    public void setTAGs(String tag) {
        this.TAGs.add(tag);
    }
    //添加TAG
    public ArrayList getTAGs() {return TAGs;}
    //显示TAG框

    /**
     * {@link #isTexting()}
     */
    private static boolean texting = false;
    private static final double OFFSET = 15;
    /**
     * 构造函数
     * 设置图形的事件响应器
     * @param s 文本
     * @param x 初始位置x
     * @param y 初始位置y
     */
    public MyText(double x, double y, String s) {
        super(x, y, s);
        // 绑定一个菜单
        ContextMenu contextMenu = new ContextMenu();
        MenuItem copyMenu = new MenuItem("复制");
        MenuItem deleteMenu = new MenuItem("删除(不可撤回)");

        //绑定TAG菜单
        ContextMenu TAGMenu = new ContextMenu();
        MenuItem tagaddMenu = new MenuItem("添加标签");
        MenuItem tagshowMenu = new MenuItem("显示标签");
        MenuItem tagseekMenu = new MenuItem("查找标签");
        TAGMenu.getItems().addAll(tagaddMenu,tagshowMenu,tagseekMenu);

        //TAGadd功能
        tagaddMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("添加标签!!!!!");

                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("添加一个标签");
                dialog.setHeaderText("比如：椭圆、直线");
                dialog.setContentText("在这里添加你的标签");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
                    System.out.println("您输入的标签是: " + result.get());
                }
                String tag;
                tag = result.get();
                setTAGs(tag);
                System.out.println(TAGs.size());
            }
        });
        //TAGseek功能
        tagshowMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("显示标签!!!!!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("显示标签");
                alert.setHeaderText(null);
                String alltags = String.join(" ", TAGs);
                alert.setContentText(alltags);
                alert.showAndWait();
            }
        });
        //TAGshow功能
        tagseekMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("标签查询图形!!!!!");
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("输入您要查询的标签");
                dialog.setHeaderText("比如：椭圆、直线");
                dialog.setContentText("在这里输入您要查询的标签");
                Optional<String> result = dialog.showAndWait();
                String target_tag;
                target_tag = result.get();
                Group globalGroup = RecordStack.getGroup();
                for (Node obj: globalGroup.getChildren()) {
                    try {
                        if (obj instanceof MyEllipse) {
                            MyEllipse shape = (MyEllipse)obj;
                            ArrayList TAGs = shape.getTAGs();
                            if (TAGs.contains(target_tag)) {
                                System.out.println("找到了目标图形：" + TAGs.get(TAGs.indexOf(target_tag)));
//                                targetGroup.getChildren().addAll(shape);
                                shape.setFill(Color.BLUE);
                            }else{
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("错误");
                                alert.setHeaderText(null);
                                alert.setContentText("没有此标签");
                                alert.showAndWait();
                            }
                        } else if (obj instanceof MyLine) {
                            MyLine shape = (MyLine)obj;
                            ArrayList TAGs = shape.getTAGs();
                            if (TAGs.contains(target_tag)) {
                                System.out.println("找到了目标图形：" + TAGs.get(TAGs.indexOf(target_tag)));
//                                targetGroup.getChildren().addAll(shape);
                                shape.setStroke(Color.BLUE);
                            }else{
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("错误");
                                alert.setHeaderText(null);
                                alert.setContentText("没有此标签");
                                alert.showAndWait();
                            }
                        } else if (obj instanceof MyPath) {
                            MyPath shape = (MyPath)obj;
                            ArrayList TAGs = shape.getTAGs();
                            if (TAGs.contains(target_tag)) {
                                System.out.println("找到了目标图形：" + TAGs.get(TAGs.indexOf(target_tag)));
//                                targetGroup.getChildren().addAll(shape);
                                shape.setStroke(Color.BLUE);
                            }else{
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("错误");
                                alert.setHeaderText(null);
                                alert.setContentText("没有此标签");
                                alert.showAndWait();
                            }
                        }else if (obj instanceof MyQuadCurve) {
                            MyQuadCurve shape = (MyQuadCurve)obj;
                            ArrayList TAGs = shape.getTAGs();
                            if (TAGs.contains(target_tag)) {
                                System.out.println("找到了目标图形：" + TAGs.get(TAGs.indexOf(target_tag)));
//                                targetGroup.getChildren().addAll(shape);
                                shape.setStroke(Color.BLUE);
                            }else{
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("错误");
                                alert.setHeaderText(null);
                                alert.setContentText("没有此标签");
                                alert.showAndWait();
                            }
                        }else if (obj instanceof MyRectangle) {
                            MyRectangle shape = (MyRectangle)obj;
                            ArrayList TAGs = shape.getTAGs();
                            if (TAGs.contains(target_tag)) {
                                System.out.println("找到了目标图形：" + TAGs.get(TAGs.indexOf(target_tag)));
//                                targetGroup.getChildren().addAll(shape);
                                shape.setFill(Color.BLUE);
                            }else{
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("错误");
                                alert.setHeaderText(null);
                                alert.setContentText("没有此标签");
                                alert.showAndWait();
                            }
                        }else if (obj instanceof MyText) {
                            MyText shape = (MyText)obj;
                            ArrayList TAGs = shape.getTAGs();
                            if (TAGs.contains(target_tag)) {
                                System.out.println("找到了目标图形：" + TAGs.get(TAGs.indexOf(target_tag)));
//                                targetGroup.getChildren().addAll(shape);
                                shape.setFill(Color.BLUE);
                            }else{
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("错误");
                                alert.setHeaderText(null);
                                alert.setContentText("没有此标签");
                                alert.showAndWait();
                            }
                        }
                    } catch(Exception e) {
                        // 处理异常语句
                    }
                }
            }
        });

        //复制功能
        copyMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("复制！！！！！！");
                FileCopy fc = new FileCopy(MyText.this);
                fc.copy();
            }
        });

        //删除功能
        deleteMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("删除！！！！！！");
                RecordStack.nodeDelete(MyText.this);
            }
        });

        contextMenu.getItems().addAll(copyMenu, deleteMenu);

        //ContextMenu弹出
        this.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                System.out.println("图形的小菜单弹出！！！！");
            }
        });

        setCursor(HAND);
        setFill(ShapeAttribute.getFillColor());
        setFont(Font.font("Times New Roman", ShapeAttribute.getSize() + OFFSET));
        setPickOnBounds(true);
        setOnMousePressed((MouseEvent e) -> {
            switch (e.getButton()) {
                case PRIMARY -> {  // 左键移动
                    if (ShapeAttribute.getTool().equals("MOVE")) {
                        setCursor(MOVE);
                        fromX = e.getSceneX();
                        fromY = e.getSceneY();
                        lastTranslateX = getTranslateX();
                        lastTranslateY = getTranslateY();
                        RecordStack.nodeChange(this, clone());
                    } else if (ShapeAttribute.getTool().equals("TEXT")) {
                        texting = true;
                        //System.out.println("Texting");
                        setUnderline(true);
                        setFill(ShapeAttribute.getFillColor());
                        setFont(Font.font("Times New Roman", ShapeAttribute.getSize() + OFFSET));
                        requestFocus();
                        RecordStack.nodeChange(this, clone());
                    } else if (ShapeAttribute.getTool().equals("TAG")){
                        System.out.println("MousePoint:" + " PRIMARY (" + e.getSceneX() + "," + e.getSceneY() + ") ");
                        TAGMenu.show(this.getScene().getWindow(), e.getSceneX()+450, e.getSceneY()+40);
                    }
                }
                case SECONDARY -> {  // 右键功能
                    System.out.println("MousePoint:" + " SECONDARY (" + e.getSceneX() + "," + e.getSceneY() + ") ");
                    contextMenu.show(this.getScene().getWindow(), e.getSceneX()+450, e.getSceneY()+40);
                }
            }
        });
        setOnMouseDragged((MouseEvent e) -> {
            if (ShapeAttribute.getTool().equals("MOVE")) {
                double deltaX = e.getSceneX() - fromX;
                double deltaY = e.getSceneY() - fromY;
                setTranslateX(deltaX + lastTranslateX);
                setTranslateY(deltaY + lastTranslateY);
            }
        });
        setOnMouseReleased((MouseEvent e) -> {
            setCursor(HAND);
        });
        setOnMouseClicked((MouseEvent e) -> {
            if (ShapeAttribute.getTool().equals("TEXT") && e.getClickCount() == 2) {
                texting = true;
                //System.out.println("Texting");
                requestFocus();
                setCursor(TEXT);
//                setText("");
            }
        });
        setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.BACK_SPACE) {
                String content = getText();
                if (!content.equals(""))
                    setText(content.substring(0, content.length() - 1));
            } else if (e.getCode() != KeyCode.ENTER) {
                setText(getText() + e.getText());
            } else {
                setCursor(HAND);
                setUnderline(false);
                setFocused(false);
                getParent().requestFocus();
                texting = false;
            }
        });
        setOnKeyTyped((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER) {
                texting = false;
                setFocused(false);
                getParent().requestFocus();
            }
        });
    }

    /**
     * 是否正在输入
     * @return boolena
     */
    public static boolean isTexting() {
        return texting;
    }

    /**
     * 画图器
     * @param x 终点x
     * @param y 终点y
     * @param isRotate 是否旋转
     */
    public void paint(double x, double y, boolean isRotate) {
        double deltaX, deltaY;
        deltaX = x - getX();
        deltaY = y - getY();
        if (isRotate) {
            deltaX = deltaX - getWrappingWidth() / 2;
            double angle = Math.atan(deltaY / deltaX) * (180 / Math.PI);
            if (deltaX < 0) angle = angle + 180;
            setRotate(angle);
        } else {
            setWrappingWidth(deltaX);
        }
    }
    /**
     * 重载克隆函数
     * @return 当前Node的克隆
     */
    @Override
    public Node clone() {
        MyText mt = new MyText(getX(), getY(), getText());
        mt.setWrappingWidth(getWrappingWidth());
        mt.setTranslateX(lastTranslateX);
        mt.setTranslateY(lastTranslateY);
        mt.setRotate(getRotate());
        mt.setFont(Font.font("Times New Roman", getFont().getSize()));
        mt.setFill(getFill());
        return mt;
    }
    /**
     * 重载toString函数，用于保存信息持久化
     * @return 属性信息
     */
    @Override
    public String toString() {
        return new StringBuilder("Text")
                .append(" ").append(getX())
                .append(" ").append(getY())
                .append(" ").append(getWrappingWidth())
                .append(" ").append(getTranslateX())
                .append(" ").append(getTranslateY())
                .append(" ").append(getRotate())
                .append(" ").append(getFont().getSize())
                .append(" ").append(getFill())
                .append(" ").append(getText().strip().replace(' ', '|'))
                .toString();
    }

    /**
     * 创建器
     * @param myML Node信息数组
     * @return 创建的Node
     * @throws Exception 异常
     */
    public MyText creator(String[] myML) throws Exception {
        if (myML[0].equals("Text")) {
            setX(Double.parseDouble(myML[1]));
            setY(Double.parseDouble(myML[2]));
            setWrappingWidth(Double.parseDouble(myML[3]));
            setTranslateX(Double.parseDouble(myML[4]));
            setTranslateY(Double.parseDouble(myML[5]));
            setRotate(Double.parseDouble(myML[6]));
            setFont(Font.font("Times New Roman", Double.parseDouble(myML[7])));
            setFill(Color.web(myML[8]));
            setText(myML[9].replace('|', ' '));
            return this;
        } else return null;
    }

}
