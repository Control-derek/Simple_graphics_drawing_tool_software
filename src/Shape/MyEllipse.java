package Shape;
import App.App;
import App.Controller;
import App.ShapeAttribute;
import File.FileCopy;
import UndoManager.RecordStack;
import java.awt.Robot;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Optional;

import static javafx.scene.Cursor.HAND;
import static javafx.scene.Cursor.MOVE;

/**
 * @see MyEllipse
 * 自定义椭圆类（可拖动、改变颜色、改变粗细）
 * @version 3.0
 * @author 王培东
 */
public class MyEllipse extends Ellipse implements Cloneable {
    private double fromX, fromY, lastTranslateX, lastTranslateY;
    private String InitTool;
    public  double Maxx,Maxy,Minx,Miny;
    public  int i;
    private ArrayList TAGs = new ArrayList();
    //添加TAG
    public void setTAGs(String tag) {
        this.TAGs.add(tag);
    }
    //添加TAG
    public ArrayList getTAGs() {return TAGs;}
    //显示TAG框

    /**
     * 无参构造函数
     * 设置图形的事件响应器
     */
    public MyEllipse() {
        super();
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
        //TAGshow功能
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
        //TAGseek功能
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
                FileCopy fc = new FileCopy(MyEllipse.this);
                fc.copy();
            }
        });

        //删除功能
        deleteMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("删除！！！！！！");
                RecordStack.nodeDelete(MyEllipse.this);
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
//        copyMenu.setAccelerator(KeyCombination.keyCombination("Ctrl+c"));

        setCursor(HAND);
        setFill(ShapeAttribute.getFillColor());
        setStroke(ShapeAttribute.getStrokeColor());
        setOnMousePressed((MouseEvent e) -> {
            switch (e.getButton()) {
                case PRIMARY -> {  // 左键移动
                    InitTool = ShapeAttribute.getTool();
                    if(InitTool.equals("LEFT")||InitTool.equals("BOTTOM")||InitTool.equals("TOP")||InitTool.equals("CENVER")||InitTool.equals("RIGHT")||InitTool.equals("CENHOR"))
                    {
                        Controller.ADDindex(i);
                        if(Maxx>Controller.maxx)
                            Controller.maxx=Maxx;
                        if(Minx<Controller.minx)
                            Controller.minx=Minx;
                        if(Maxy>Controller.maxy)
                            Controller.maxy=Maxy;
                        if(Miny<Controller.miny)
                            Controller.miny=Miny;


                    }
                    if (ShapeAttribute.getTool().equals("MOVE")) {
                        setCursor(MOVE);
                        fromX = e.getSceneX();
                        fromY = e.getSceneY();
                        lastTranslateX = getTranslateX();
                        lastTranslateY = getTranslateY();
                        RecordStack.nodeChange(this, clone());
                    }
                    if (ShapeAttribute.getTool().equals("TAG")){
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
            if (e.getButton().equals(MouseButton.SECONDARY)) {
                System.out.println("MousePoint:" + " SECONDARY (" + e.getSceneX() + "," + e.getSceneY() + ") ");
                return;
            }
            if (ShapeAttribute.getTool().equals("MOVE")) {
                double deltaX = e.getSceneX() - fromX;
                double deltaY = e.getSceneY() - fromY;
                setTranslateX(deltaX + lastTranslateX);
                setTranslateY(deltaY + lastTranslateY);
            }


        });
        setOnMouseReleased((MouseEvent e) -> {
            setCursor(HAND);
            ShapeAttribute.setTool(InitTool);
        });
        setOnMouseClicked((MouseEvent e) -> {
            if (ShapeAttribute.getTool().equals("BARREL")) {
                RecordStack.nodeChange(this, clone());
                setFill(ShapeAttribute.getFillColor());
            }
        });
    }

    public MyEllipse(int index) {
        super();
        // 绑定一个菜单
        i=index;
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
        //TAGshow功能
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
        //TAGseek功能
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
                FileCopy fc = new FileCopy(MyEllipse.this);
                fc.copy();
            }
        });

        //删除功能
        deleteMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("删除！！！！！！");
                RecordStack.nodeDelete(MyEllipse.this);
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
//        copyMenu.setAccelerator(KeyCombination.keyCombination("Ctrl+c"));

        setCursor(HAND);
        setFill(ShapeAttribute.getFillColor());
        setStroke(ShapeAttribute.getStrokeColor());
        setOnMousePressed((MouseEvent e) -> {
            switch (e.getButton()) {
                case PRIMARY -> {  // 左键移动
                    setCursor(MOVE);
                    InitTool = ShapeAttribute.getTool();
                    if(InitTool.equals("LEFT")||InitTool.equals("BOTTOM")||InitTool.equals("TOP")||InitTool.equals("CENVER")||InitTool.equals("RIGHT")||InitTool.equals("CENHOR"))
                    {
                        Controller.ADDindex(i);
                        if(Maxx>Controller.maxx)
                            Controller.maxx=Maxx;
                        if(Minx<Controller.minx)
                            Controller.minx=Minx;
                        if(Maxy>Controller.maxy)
                            Controller.maxy=Maxy;
                        if(Miny<Controller.miny)
                            Controller.miny=Miny;


                    }
                    else if (ShapeAttribute.getTool().equals("TAG")){
                        System.out.println("MousePoint:" + " PRIMARY (" + e.getSceneX() + "," + e.getSceneY() + ") ");
                        TAGMenu.show(this.getScene().getWindow(), e.getSceneX()+450, e.getSceneY()+40);
                    }
                    else {

                        ShapeAttribute.setTool("MOVE");
                        fromX = e.getSceneX();
                        fromY = e.getSceneY();
                        lastTranslateX = getTranslateX();
                        lastTranslateY = getTranslateY();
                        RecordStack.nodeChange(this, clone());
                    }
                }
                case SECONDARY -> {  // 右键功能
                    System.out.println("MousePoint:" + " SECONDARY (" + e.getSceneX() + "," + e.getSceneY() + ") ");
                    contextMenu.show(this.getScene().getWindow(), e.getSceneX()+450, e.getSceneY()+40);
                }
            }
        });
        setOnMouseDragged((MouseEvent e) -> {
            if (e.getButton().equals(MouseButton.SECONDARY)) {
                System.out.println("MousePoint:" + " SECONDARY (" + e.getSceneX() + "," + e.getSceneY() + ") ");
                return;
            }
            if (ShapeAttribute.getTool().equals("MOVE")) {
                double deltaX = e.getSceneX() - fromX;
                double deltaY = e.getSceneY() - fromY;
                setTranslateX(deltaX + lastTranslateX);
                setTranslateY(deltaY + lastTranslateY);
            }

        });
        setOnMouseReleased((MouseEvent e) -> {
            setCursor(HAND);
            ShapeAttribute.setTool(InitTool);
        });
        setOnMouseClicked((MouseEvent e) -> {
            if (ShapeAttribute.getTool().equals("BARREL")) {
                RecordStack.nodeChange(this, clone());
                setFill(ShapeAttribute.getFillColor());
            }
        });
    }

    /**
     * 自定义椭圆画图函数
     * @param x1 起点x
     * @param y1 起点y
     * @param x2 终点x
     * @param y2 终点y
     * @param isCircle 是否是画圆
     */
    public void paint(double x1, double y1, double x2, double y2, boolean isCircle) {
        setStrokeWidth(ShapeAttribute.getSize());
        setStroke(ShapeAttribute.getStrokeColor());
        setFill(ShapeAttribute.getFillColor());
        if (isCircle) {
            if (Math.abs(y2 - y1) > Math.abs(x2 - x1))
                x2 = y2 + x1 - y1;
            else
                y2 = x2 + y1 - x1;
        }
        setCenterX((x1 + x2) / 2);
        setCenterY((y1 + y2) / 2);
        setRadiusX(Math.abs(x1 - x2) / 2);
        setRadiusY(Math.abs(y1 - y2) / 2);
        Minx=Math.min(x1,x2);
        Maxx=Math.max(x1,x2);
        Miny=Math.min(y1,y2);
        Maxy=Math.max(y1,y2);
    }

    /**
     * 重载克隆函数
     * @return 当前Node的克隆
     */
    @Override
    public Node clone() {
        MyEllipse myClone = new MyEllipse();
        myClone.setCenterX(getCenterX());
        myClone.setCenterY(getCenterY());
        myClone.setRadiusX(getRadiusX());
        myClone.setRadiusY(getRadiusY());
        myClone.setTranslateX(lastTranslateX);
        myClone.setTranslateY(lastTranslateY);
        myClone.setStrokeWidth(getStrokeWidth());
        myClone.setStroke(getStroke());
        myClone.setFill(getFill());
        return myClone;
    }

    /**
     * 重载toString函数，用于保存信息持久化
     * @return 属性信息
     */
    @Override
    public String toString() {
        return new StringBuilder("Ellipse")
                .append(" ").append(getCenterX())
                .append(" ").append(getCenterY())
                .append(" ").append(getRadiusX())
                .append(" ").append(getRadiusY())
                .append(" ").append(getTranslateX())
                .append(" ").append(getTranslateY())
                .append(" ").append(getStrokeWidth())
                .append(" ").append(getStroke())
                .append(" ").append(getFill())
                .toString();
    }

    /**
     * 创建器
     * @param myML Node信息数组
     * @return 创建的Node
     * @throws Exception 异常
     */
    public MyEllipse creator(String[] myML) throws Exception {
        if (myML[0].equals("Ellipse")) {
            setCenterX(Double.parseDouble(myML[1]));
            setCenterY(Double.parseDouble(myML[2]));
            setRadiusX(Double.parseDouble(myML[3]));
            setRadiusY(Double.parseDouble(myML[4]));
            setTranslateX(Double.parseDouble(myML[5]));
            setTranslateY(Double.parseDouble(myML[6]));
            setStrokeWidth(Double.parseDouble(myML[7]));
            setStroke(Color.web(myML[8]));
            setFill(Color.web(myML[9]));
            return this;
        } else return null;
    }
}
