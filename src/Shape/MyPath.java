package Shape;

import App.Controller;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;

import java.util.ArrayList;
import java.util.Optional;

import static javafx.scene.Cursor.HAND;
import static javafx.scene.Cursor.MOVE;

/**
 * @see MyPath
 * 自定义画笔类（可拖动、改变颜色、改变粗细）
 * @version 2.0
 * @author 眭永熙
 */
public class MyPath extends Path implements Cloneable {
    private double fromX, fromY, lastTranslateX, lastTranslateY;
    public  double Maxx=-100,Maxy=-100,Minx=999,Miny=999;
    public  int i;
    private String InitTool;
    private ArrayList TAGs = new ArrayList();

    //添加TAG
    public void setTAGs(String tag) {
        this.TAGs.add(tag);
    }
    //添加TAG
    public ArrayList getTAGs() {return TAGs;}
    //显示TAG框

    /**
     * 构造函数
     * 设置图形的事件响应器
     * @param x 初始位置x
     * @param y 初始位置y
     */
    public MyPath(double x, double y) {
        super();
        getElements().add(new MoveTo(x, y));

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
                FileCopy fc = new FileCopy(MyPath.this);
                fc.copy();
            }
        });

        //删除功能
        deleteMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("删除！！！！！！");
                RecordStack.nodeDelete(MyPath.this);
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
        setStroke(ShapeAttribute.getStrokeColor());
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
            if (ShapeAttribute.getTool().equals("BARREL")) {
                RecordStack.nodeChange(this, clone());
                setFill(ShapeAttribute.getFillColor());
            }
        });
    }

    public MyPath(double x, double y,int index) {
        super();
        if(x>Maxx)
            Maxx=x;
        if(x<Minx)
            Minx=x;
        if(y>Maxy)
            Maxy=y;
        if(y<Miny)
            Miny=y;
        getElements().add(new MoveTo(x, y));

        i=index;
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
                FileCopy fc = new FileCopy(MyPath.this);
                fc.copy();
            }
        });

        //删除功能
        deleteMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("删除！！！！！！");
                RecordStack.nodeDelete(MyPath.this);
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
//                    else if(){
//
//                    }
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
     * 画笔画图器
     * @param x 终点x
     * @param y 终点y
     */
    public void paint(double x, double y) {
        setStrokeWidth(ShapeAttribute.getSize());
        setStroke(ShapeAttribute.getStrokeColor());
        getElements().add(new LineTo(x, y));
        System.out.println(x+" "+y);
        if(x>Maxx)
            Maxx=x;

        if(x<Minx)
            Minx=x;
        if(y>Maxy)
            Maxy=y;
        if(y<Miny)
            Miny=y;
        System.out.println(Maxx+" "+Minx);
        getElements().add(new LineTo(x, y));
    }
    /**
     * 重载克隆函数
     * @return 当前Node的克隆
     */
    @Override
    public Node clone() {
        MyPath clone = new MyPath(0, 0);
        clone.getElements().clear();
        clone.getElements().addAll(getElements());
        clone.setTranslateX(lastTranslateX);
        clone.setTranslateY(lastTranslateY);
        clone.setStrokeWidth(getStrokeWidth());
        clone.setStroke(getStroke());
        clone.setFill(getFill());
        return clone;
    }
    /**
     * 重载toString函数，用于保存信息持久化
     * @return 属性信息
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (PathElement e : getElements()) {
            if (e.getClass().getName().equals(MoveTo.class.getName())) {
                MoveTo mt = (MoveTo) e;
                sb.append(mt.getX()).append(",").append(mt.getY()).append(" ");
            } else if (e.getClass().getName().equals(LineTo.class.getName())) {
                LineTo lt = (LineTo) e;
                sb.append(lt.getX()).append(",").append(lt.getY()).append(" ");
            }
        }
        sb.append("@");

        return new StringBuilder("Path")
                .append(" ").append(sb.toString())
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
    public MyPath creator(String[] myML) throws Exception {
        if (myML[0].equals("Path")) {
            int index = 1;
            String str = myML[index];
            boolean isFirst = true;
            while (!str.equals("@")) {
                index++;
                String[] pos = str.split(",");
                double x = Double.parseDouble(pos[0]), y = Double.parseDouble(pos[1]);
                if (isFirst) {
                    isFirst = false;
                    getElements().clear();
                    getElements().add(new MoveTo(x, y));
                } else {
                    getElements().add(new LineTo(x, y));
                }
                str = myML[index];
            }
            setTranslateX(Double.parseDouble(myML[index + 1]));
            setTranslateY(Double.parseDouble(myML[index + 2]));
            setStrokeWidth(Double.parseDouble(myML[index + 3]));
            setStroke(Color.web(myML[index + 4]));
            setFill(Color.web(myML[index + 5]));
            return this;
        } else {
            return null;
        }
    }

}
