package Shape;

import App.ShapeAttribute;
import File.FileCopy;
import UndoManager.RecordStack;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

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
