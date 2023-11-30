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
import javafx.scene.shape.QuadCurve;

import java.util.ArrayList;
import java.util.Optional;

import static javafx.scene.Cursor.HAND;
import static javafx.scene.Cursor.MOVE;

/**
 * @see MyQuadCurve
 * 自定义曲线类（可拖动、改变颜色、改变粗细）
 * @version 2.0
 * @author derek
 */
public class MyQuadCurve extends QuadCurve implements Cloneable {
    private double fromX, fromY, lastTranslateX, lastTranslateY;
    public  double Maxx,Maxy,Minx,Miny;
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
     * {@link #isChangeControl()}是否正在改变曲线形状
     */
    private static boolean changeControl = false;
    /**
     * 无参构造函数
     * 设置图形的事件响应器
     */
    public MyQuadCurve() {
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
                FileCopy fc = new FileCopy(MyQuadCurve.this);
                fc.copy();
            }
        });

        //删除功能
        deleteMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("删除！！！！！！");
                RecordStack.nodeDelete(MyQuadCurve.this);
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
                    } else if (ShapeAttribute.getTool().equals("CURVE")) {
                        changeControl = true;
                        RecordStack.nodeChange(this, clone());
                    }else if (ShapeAttribute.getTool().equals("TAG")){
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
            } else if (ShapeAttribute.getTool().equals("CURVE")) {
                changeControl = true;
                setControlX(e.getX());
                setControlY(e.getY());
            }
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
                }
                case SECONDARY -> {  // 右键功能
                    System.out.println("MousePoint:" + " SECONDARY (" + e.getSceneX() + "," + e.getSceneY() + ") ");
                    contextMenu.show(this.getScene().getWindow(), e.getSceneX()+450, e.getSceneY()+40);
                }
            }
        });
        setOnMouseReleased((MouseEvent e) -> {
            setCursor(HAND);
            changeControl = false;
        });
        setOnMouseClicked((MouseEvent e) -> {
            if (ShapeAttribute.getTool().equals("BARREL")) {
                RecordStack.nodeChange(this, clone());
                setFill(ShapeAttribute.getFillColor());
            }
        });
    }
    public MyQuadCurve(int index) {
        super();
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
                FileCopy fc = new FileCopy(MyQuadCurve.this);
                fc.copy();
            }
        });

        //删除功能
        deleteMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("删除！！！！！！");
                RecordStack.nodeDelete(MyQuadCurve.this);
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
                    if(InitTool.equals("LEFT"))
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


                    } else if (ShapeAttribute.getTool().equals("CURVE")) {
                        changeControl = true;
                        RecordStack.nodeChange(this, clone());
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

            switch (e.getButton()) {
                case PRIMARY -> {  // 左键移动
                    if (ShapeAttribute.getTool().equals("MOVE")) {
                        double deltaX = e.getSceneX() - fromX;
                        double deltaY = e.getSceneY() - fromY;
                        setTranslateX(deltaX + lastTranslateX);
                        setTranslateY(deltaY + lastTranslateY);
                    } else if (ShapeAttribute.getTool().equals("CURVE")) {
                        changeControl = true;

                        setControlX(e.getX());
                        setControlY(e.getY());
                        if(getControlX()>Math.max(getStartX(),getEndX()))
                            Maxx=getControlX();
                        else
                            Maxx=Math.max(getStartX(),getEndX());
                        if(getControlX()<Math.min(getStartX(),getEndX()))
                            Minx=getControlX();
                        else
                            Minx=Math.min(getStartX(),getEndX());

                        if(getControlY()>Math.max(getStartY(),getEndY()))
                            Maxy=getControlY();
                        else
                            Maxy=Math.max(getStartY(),getEndY());
                        if(getControlY()<Math.min(getStartY(),getEndY()))
                            Miny=getControlY();
                        else
                            Miny=Math.min(getStartY(),getEndY());

                        System.out.println(getControlX()+" "+getControlY());
                        System.out.println(Minx+" "+Maxx+" "+Miny+" "+Maxy);
                    }
                }
                case SECONDARY -> {  // 右键功能
                    System.out.println("MousePoint:" + " SECONDARY (" + e.getSceneX() + "," + e.getSceneY() + ") ");
                    contextMenu.show(this.getScene().getWindow(), e.getSceneX()+450, e.getSceneY()+40);
                }
            }
        });
        setOnMouseReleased((MouseEvent e) -> {
            setCursor(HAND);
            changeControl = false;
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
     * 画图器
     * @param x1 起点x
     * @param y1 起点y
     * @param x2 终点x
     * @param y2 终点y
     */
    public void paint(double x1, double y1, double x2, double y2) {
        if (!changeControl) {
            setStrokeWidth(ShapeAttribute.getSize());
            setStroke(ShapeAttribute.getStrokeColor());
            setStartX(x1);
            setStartY(y1);
            setEndX(x2);
            setEndY(y2);
            setControlX((x1 + x2) / 2);
            setControlY((y1 + y2) / 2);
            Minx=Math.min(x1,x2);
            Maxx=Math.max(x1,x2);
            Miny=Math.min(y1,y2);
            Maxy=Math.max(y1,y2);
        }
    }

    /**
     * {@link #changeControl}
     * @return boolean
     */
    public static boolean isChangeControl() {
        return changeControl;
    }
    /**
     * 重载克隆函数
     * @return 当前Node的克隆
     */
    @Override
    public Node clone() {
        MyQuadCurve clone = new MyQuadCurve();
        clone.setStartX(getStartX());
        clone.setStartY(getStartY());
        clone.setEndX(getEndX());
        clone.setEndY(getEndY());
        clone.setControlX(getControlX());
        clone.setControlY(getControlY());
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
        return new StringBuilder("Curve")
                .append(" ").append(getStartX())
                .append(" ").append(getStartY())
                .append(" ").append(getEndX())
                .append(" ").append(getEndY())
                .append(" ").append(getControlX())
                .append(" ").append(getControlY())
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
    public MyQuadCurve creator(String[] myML) throws Exception {
        if (myML[0].equals("Curve")) {
            setStartX(Double.parseDouble(myML[1]));
            setStartY(Double.parseDouble(myML[2]));
            setEndX(Double.parseDouble(myML[3]));
            setEndY(Double.parseDouble(myML[4]));
            setControlX(Double.parseDouble(myML[5]));
            setControlY(Double.parseDouble(myML[6]));
            setTranslateX(Double.parseDouble(myML[7]));
            setTranslateY(Double.parseDouble(myML[8]));
            setStrokeWidth(Double.parseDouble(myML[9]));
            setStroke(Color.web(myML[10]));
            setFill(Color.web(myML[11]));
            return this;
        } else return null;
    }
}
