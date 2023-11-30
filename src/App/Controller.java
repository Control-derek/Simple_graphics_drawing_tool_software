package App;

import File.FilePaste;
import Shape.*;
import UndoManager.RecordStack;
import Toolbar.ToolBarController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;
import javafx.scene.text.Font;

import java.util.ArrayList;

/**
 * @see Controller
 * 画板控制器
 * @version 6.2
 * @author derek
 */
public class Controller {
    public final int start_i = 1;
    public int i = start_i;
    /**
     * javafx group，用于存放图形
     */
    @FXML
    private Group group;
    /**
     * 界面右下方文本
     */
    @FXML
    private Label label;

    private App app;
    /**
     * 鼠标按下的坐标
     */
    private double startX, startY;
    /**
     * 鼠标松开的坐标
     */
    private double endX, endY;
    private boolean first = true;
    /**
     * 画布画图器
     */
    private GraphicsContext gc;
    /**
     * 当前画布
     */
    private MyCanvas canvas;
    /**
     * 当前自由画笔
     */
    private MyPath path;
    /**
     * 当前直线
     */
    private MyLine line;
    /**
     * 当前矩形
     */
    private MyRectangle rectangle;
    /**
     * 当前椭圆
     */
    private MyEllipse ellipse;
    /**
     * 当前曲线
     */
    private MyQuadCurve quadCurve;
    /**
     * 当前文本
     */
    private MyText text;
    /**
     * 右键唤出的菜单
     */
    ContextMenu contextMenu = new ContextMenu();
    /**
     * 右键点击处的坐标
     */
    double SecondaryX;
    double SecondaryY;
    public static ArrayList<Integer> dynamicArray = new ArrayList<>();
    public static double maxx=-9999;
    public static double minx=999;
    public static double maxy=-9999;
    public static double miny=999;
    public static void ADDindex(int index)
    {
        dynamicArray.add(index);
    }

    public Controller() {
    }

    public void setComponent(Node n) {
    }

    /**
     * 初始化画图器，设置背景画布
     */
    @FXML
    private void initialize() {
        if (canvas == null) {
            canvas = new MyCanvas(ShapeAttribute.CANVAS_WIDTH, ShapeAttribute.CANVAS_HEIGHT);
            group.getChildren().add(canvas);
        }
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, ShapeAttribute.CANVAS_WIDTH, ShapeAttribute.CANVAS_HEIGHT);
        gc.restore();
        canvas.setOnMouseClicked((e) -> {
            if (ShapeAttribute.getTool().equals("BARREL")) {
                MyCanvas c = (MyCanvas) e.getSource();
                RecordStack.nodeChange(c, c.clone());
                gc = (c.getGraphicsContext2D());
                gc.setFill(ShapeAttribute.getFillColor());
                gc.fillRect(0, 0, ShapeAttribute.CANVAS_WIDTH, ShapeAttribute.CANVAS_HEIGHT);
                gc.restore();
            }
        });
        label.setFont(Font.font(15));

        /*下面初始化右键菜单*/
        MenuItem pasteMenu = new MenuItem("粘贴");
        //MenuItem点击
        pasteMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("！！粘贴！！");
                FilePaste fp = new FilePaste(app.getGroup());
                fp.paste(SecondaryX-185, SecondaryY-68);
//                fp.paste(SecondaryX, SecondaryY);
            }
        });
        contextMenu.getItems().addAll(pasteMenu);
    }


    public void setMainApp(App appApp) {
        this.app = appApp;
    }

    /**
     * 鼠标点击响应器
     * 点击后在当前点击位置画图
     * @param event 鼠标事件
     */
    @FXML
    private void canvasOnMousePressed(MouseEvent event) {
        if (first && event.getButton().equals(MouseButton.SECONDARY) ) {  // 鼠标右键 展开菜单
            for (Node node : group.getChildren()) {  // 遍历所有子节点 跟节点的菜单不同时出现
                if (node instanceof MyCanvas) continue;
                // 对每个节点执行操作
                if (!node.isCache() && node.contains(event.getX()-node.getTranslateX(), event.getY()-node.getTranslateY())) {
                    return;
                }
            }
            SecondaryX  = event.getSceneX();
            SecondaryY = event.getSceneY();
            contextMenu.show(canvas, SecondaryX+420, SecondaryY+40);
        }

        if (first && event.getButton().equals(MouseButton.PRIMARY)) {  // 第一次点击且是鼠标左键
            startX = event.getX();
            startY = event.getY();
            String content = ShapeAttribute.getTool();
            contextMenu.hide();  // 单机别的地方 菜单消失
            switch (content) {
                case "LINE":
                    //path = new Path();
                    line = new MyLine(i++);
//                    line.setPasteMenu(contextMenu);
                    line.paint(startX, startY, startX, startY);
                    // 打个标找着容易
                    line.setId("line");
                    group.getChildren().addAll(line);
                    RecordStack.nodeCreate(line);
                    break;
                case "CURVE":
                    if (!MyQuadCurve.isChangeControl()) {
//                        System.out.println("Group1");
                        quadCurve = new MyQuadCurve(i++);
                        quadCurve.paint(startX, startY, startX, startY);
                        group.getChildren().addAll(quadCurve);
                        RecordStack.nodeCreate(quadCurve);
                    }
                    break;
                case "RECT":
                    //path = new Path();
                    rectangle = new MyRectangle(i++);
                    rectangle.paint(startX, startY, startX, startY);
                    group.getChildren().addAll(rectangle);
                    RecordStack.nodeCreate(rectangle);
                    break;
                case "OVAL":
                case "CIRCLE":
                    ellipse = new MyEllipse(i++);
                    ellipse.paint(startX, startY, startX, startY, false);
                    group.getChildren().addAll(ellipse);
                    RecordStack.nodeCreate(ellipse);
                    break;
                case "PEN":
                    path = new MyPath(startX, startY, i++);
                    group.getChildren().addAll(path);
                    RecordStack.nodeCreate(path);
                    break;
                case "TEXT":
                    if (!MyText.isTexting()) {
                        text = new MyText(startX, startY, "Text");
                        group.getChildren().addAll(text);
                        RecordStack.nodeCreate(text);
                    }/*else if(event.getButton().equals(MouseButton.SECONDARY)){
                        try {
                            cloneText=(MyText) text.clone();
                        } catch (CloneNotSupportedException cloneNotSupportedException) {
                            cloneNotSupportedException.printStackTrace();
                        }
                    }*/
                    break;
                case "RUBBER":
                    canvas = new MyCanvas(ShapeAttribute.CANVAS_WIDTH, ShapeAttribute.CANVAS_HEIGHT);
                    canvas.setMouseTransparent(true);
                    group.getChildren().addAll(canvas);
                    gc = canvas.getGraphicsContext2D();
                    gc.setStroke(Color.WHITE);
                    gc.setLineWidth(ShapeAttribute.getSize());
                    RecordStack.nodeCreate(canvas);
                    break;
                case"LEFT" :
                    if(ToolBarController.first)
                    {



                    }
                    else {

                        for(int i = 0; i < dynamicArray.size(); i++)
                        {
                            Object o=group.getChildren().get(dynamicArray.get(i));
                            System.out.println("Controller.dynamicArray.size()" + Controller.dynamicArray.size());
//                            System.out.println("Object" + o.toString());
                            if(o instanceof MyLine)
                            {
                                System.out.println("Minx" + ((MyLine) o).Minx);
                                System.out.println("minx" + minx);
                                double trans=((MyLine) o).Minx-minx;
                                System.out.println("trans" + trans);
                                RecordStack.nodeChange((MyLine)o, ((MyLine)o).clone());
                                ((MyLine) o).paint(((MyLine) o).getStartX()-trans,((MyLine) o).getStartY(),((MyLine) o).getEndX()-trans,((MyLine) o).getEndY());
                            }
                            if(o instanceof MyRectangle)
                            {
                                double trans=((MyRectangle) o).Minx-minx;
                                RecordStack.nodeChange((MyRectangle)o,((MyRectangle)o).clone());
                                ((MyRectangle)o).paint(((MyRectangle)o).getX()-trans,((MyRectangle)o).getY(),((MyRectangle)o).getWidth()+((MyRectangle)o).getX()-trans,((MyRectangle)o).getHeight()+((MyRectangle)o).getY());
                            }
                            if(o instanceof MyEllipse)
                            {
                                double trans=((MyEllipse) o).Minx-minx;
                                RecordStack.nodeChange((MyEllipse)o,((MyEllipse)o).clone());
                                ((MyEllipse) o).paint(((MyEllipse) o).getCenterX()-((MyEllipse) o).getRadiusX()-trans,((MyEllipse) o).getCenterY()-((MyEllipse) o).getRadiusY(),((MyEllipse) o).getCenterX()+((MyEllipse) o).getRadiusX()-trans,((MyEllipse) o).getCenterY()+((MyEllipse) o).getRadiusY(),false);
                            }
                            if(o instanceof MyQuadCurve)
                            {
                                double trans=((MyQuadCurve) o).Minx-minx;
                                RecordStack.nodeChange((MyQuadCurve)o,((MyQuadCurve)o).clone());
                                ((MyQuadCurve) o).setControlX(((MyQuadCurve) o).getControlX()-trans);
                                ((MyQuadCurve) o).setStartX(((MyQuadCurve) o).getStartX()-trans);
                                ((MyQuadCurve) o).setEndX(((MyQuadCurve) o).getEndX()-trans);
                            }
                            if(o instanceof MyPath)
                            {
                                System.out.println(((MyPath) o).Maxx+" "+((MyPath) o).Minx);
                                double trans=((MyPath) o).Minx-minx;
                                RecordStack.nodeChange((MyPath)o,((MyPath)o).clone());

                                ((MoveTo)((MyPath) o).getElements().get(0)).setX(((MoveTo)((MyPath) o).getElements().get(0)).getX()-trans);
                                for(int j=1;j< ((MyPath) o).getElements().size();j++)
                                {
                                    ((LineTo)((MyPath) o).getElements().get(j)).setX(((LineTo)((MyPath) o).getElements().get(j)).getX()-trans);
                                    ((MyPath) o).Minx=((MyPath) o).Minx-trans;
                                    ((MyPath) o).Minx=((MyPath) o).Maxx-trans;
                                }
                            }
                        }
                        dynamicArray.clear();//最好设一个集合，去重
                        maxx=maxy=-999;
                        minx=miny=999;
                    }
                    break;
                case"RIGHT":
                    if(ToolBarController.first)
                    {

                        System.out.println("heelo");

                    }
                    else {
                        System.out.println("helloright");
                        for(int i = 0; i < dynamicArray.size(); i++)
                        {
                            Object o=group.getChildren().get(dynamicArray.get(i));
                            System.out.println("Controller.dynamicArray.size()" + Controller.dynamicArray.size());
//                            System.out.println("Object" + o.toString());
                            if(o instanceof MyLine)
                            {
                                double trans=maxx-((MyLine) o).Maxx;
                                System.out.println("trans" + trans);
                                RecordStack.nodeChange((MyLine)o, ((MyLine)o).clone());
                                ((MyLine) o).paint(((MyLine) o).getStartX()+trans,((MyLine) o).getStartY(),((MyLine) o).getEndX()+trans,((MyLine) o).getEndY());
                            }
                            if(o instanceof MyRectangle)
                            {
                                double trans=maxx-((MyRectangle) o).Maxx;
                                RecordStack.nodeChange((MyRectangle)o,((MyRectangle)o).clone());
                                ((MyRectangle)o).paint(((MyRectangle)o).getX()+trans,((MyRectangle)o).getY(),((MyRectangle)o).getWidth()+((MyRectangle)o).getX()+trans,((MyRectangle)o).getHeight()+((MyRectangle)o).getY());
                            }
                            if(o instanceof MyEllipse)
                            {
                                double trans=maxx-((MyEllipse) o).Maxx;
                                RecordStack.nodeChange((MyEllipse)o,((MyEllipse)o).clone());
                                ((MyEllipse) o).paint(((MyEllipse) o).getCenterX()-((MyEllipse) o).getRadiusX()+trans,((MyEllipse) o).getCenterY()-((MyEllipse) o).getRadiusY(),((MyEllipse) o).getCenterX()+((MyEllipse) o).getRadiusX()+trans,((MyEllipse) o).getCenterY()+((MyEllipse) o).getRadiusY(),false);
                            }
                            if(o instanceof MyQuadCurve)
                            {
                                double trans=maxx-((MyQuadCurve) o).Maxx;
                                RecordStack.nodeChange((MyQuadCurve)o,((MyQuadCurve)o).clone());
                                ((MyQuadCurve) o).setControlX(((MyQuadCurve) o).getControlX()+trans);
                                ((MyQuadCurve) o).setStartX(((MyQuadCurve) o).getStartX()+trans);
                                ((MyQuadCurve) o).setEndX(((MyQuadCurve) o).getEndX()+trans);
                            }
                            if(o instanceof MyPath)
                            {
                                double trans=maxx-((MyPath) o).Maxx;
                                RecordStack.nodeChange((MyPath)o,((MyPath)o).clone());
                                ((MoveTo)((MyPath) o).getElements().get(0)).setX(((MoveTo)((MyPath) o).getElements().get(0)).getX()+trans);
                                for(int j=1;j< ((MyPath) o).getElements().size();j++)
                                {
                                    ((LineTo)((MyPath) o).getElements().get(j)).setX(((LineTo)((MyPath) o).getElements().get(j)).getX()+trans);
                                    ((MyPath) o).Maxx=((MyPath) o).Maxx+trans;
                                    ((MyPath) o).Minx=((MyPath) o).Minx+trans;
                                }

                            }
                        }
                        dynamicArray.clear();//最好设一个集合，去重
                        maxx=maxy=-999;
                        minx=miny=999;
                    }
                    break;
                case"CENHOR":
                    if(ToolBarController.first)
                    {



                    }
                    else {
                        double mid=(maxx+minx)/2;
                        for(int i = 0; i < dynamicArray.size(); i++)
                        {
                            Object o=group.getChildren().get(dynamicArray.get(i));
                            System.out.println("Controller.dynamicArray.size()" + Controller.dynamicArray.size());
//                            System.out.println("Object" + o.toString());
                            if(o instanceof MyLine)
                            {
                                double trans=(((MyLine) o).Minx+((MyLine) o).Maxx)/2-mid;
                                System.out.println("trans" + trans);
                                RecordStack.nodeChange((MyLine)o, ((MyLine)o).clone());
                                ((MyLine) o).paint(((MyLine) o).getStartX()-trans,((MyLine) o).getStartY(),((MyLine) o).getEndX()-trans,((MyLine) o).getEndY());
                            }
                            if(o instanceof MyRectangle)
                            {
                                double trans=(((MyRectangle) o).Minx+((MyRectangle) o).Maxx)/2-mid;
                                RecordStack.nodeChange((MyRectangle)o,((MyRectangle)o).clone());
                                ((MyRectangle)o).paint(((MyRectangle)o).getX()-trans,((MyRectangle)o).getY(),((MyRectangle)o).getWidth()+((MyRectangle)o).getX()-trans,((MyRectangle)o).getHeight()+((MyRectangle)o).getY());
                            }
                            if(o instanceof MyEllipse)
                            {
                                double trans=(((MyEllipse) o).Minx+((MyEllipse) o).Maxx)/2-mid;
                                RecordStack.nodeChange((MyEllipse)o,((MyEllipse)o).clone());
                                ((MyEllipse) o).paint(((MyEllipse) o).getCenterX()-((MyEllipse) o).getRadiusX()-trans,((MyEllipse) o).getCenterY()-((MyEllipse) o).getRadiusY(),((MyEllipse) o).getCenterX()+((MyEllipse) o).getRadiusX()-trans,((MyEllipse) o).getCenterY()+((MyEllipse) o).getRadiusY(),false);
                            }
                            if(o instanceof MyQuadCurve)
                            {
                                double trans=(((MyPath) o).Minx+((MyPath) o).Maxx)/2-mid;
                                RecordStack.nodeChange((MyQuadCurve)o,((MyQuadCurve)o).clone());
                                ((MyQuadCurve) o).setControlX(((MyQuadCurve) o).getControlX()-trans);
                                ((MyQuadCurve) o).setStartX(((MyQuadCurve) o).getStartX()-trans);
                                ((MyQuadCurve) o).setEndX(((MyQuadCurve) o).getEndX()-trans);
                            }
                            if(o instanceof MyPath)
                            {
                                double trans=(((MyPath) o).Minx+((MyPath) o).Maxx)/2-mid;
                                RecordStack.nodeChange((MyPath)o,((MyPath)o).clone());
                                ((MoveTo)((MyPath) o).getElements().get(0)).setX(((MoveTo)((MyPath) o).getElements().get(0)).getX()-trans);
                                for(int j=1;j< ((MyPath) o).getElements().size();j++)
                                {
                                    ((LineTo)((MyPath) o).getElements().get(j)).setX(((LineTo)((MyPath) o).getElements().get(j)).getX()-trans);
                                    ((MyPath) o).Minx=((MyPath) o).Minx-trans;
                                    ((MyPath) o).Minx=((MyPath) o).Maxx-trans;
                                }

                            }
                        }
                        maxx=maxy=-999;
                        minx=miny=999;
                        dynamicArray.clear();//最好设一个集合，去重

                    }
                    break;
                case"TOP":
                    if(ToolBarController.first)
                    {



                    }
                    else {

                        for(int i = 0; i < dynamicArray.size(); i++)
                        {
                            Object o=group.getChildren().get(dynamicArray.get(i));
                            System.out.println("Controller.dynamicArray.size()" + Controller.dynamicArray.size());
//                            System.out.println("Object" + o.toString());
                            if(o instanceof MyLine)
                            {
                                double trans=((MyLine) o).Miny-miny;
                                System.out.println("trans" + trans);
                                RecordStack.nodeChange((MyLine)o, ((MyLine)o).clone());
                                ((MyLine) o).paint(((MyLine) o).getStartX(),((MyLine) o).getStartY()-trans,((MyLine) o).getEndX(),((MyLine) o).getEndY()-trans);
                            }
                            if(o instanceof MyRectangle)
                            {
                                double trans=((MyRectangle) o).Miny-miny;
                                RecordStack.nodeChange((MyRectangle)o,((MyRectangle)o).clone());
                                ((MyRectangle)o).paint(((MyRectangle)o).getX(),((MyRectangle)o).getY()-trans,((MyRectangle)o).getWidth()+((MyRectangle)o).getX(),((MyRectangle)o).getHeight()+((MyRectangle)o).getY()-trans);
                            }
                            if(o instanceof MyEllipse)
                            {
                                double trans=((MyEllipse) o).Miny-miny;
                                RecordStack.nodeChange((MyEllipse)o,((MyEllipse)o).clone());
                                ((MyEllipse) o).paint(((MyEllipse) o).getCenterX()-((MyEllipse) o).getRadiusX(),((MyEllipse) o).getCenterY()-((MyEllipse) o).getRadiusY()-trans,((MyEllipse) o).getCenterX()+((MyEllipse) o).getRadiusX(),((MyEllipse) o).getCenterY()+((MyEllipse) o).getRadiusY()-trans,false);
                            }
                            if(o instanceof MyQuadCurve)
                            {
                                double trans=((MyQuadCurve) o).Miny-miny;
                                RecordStack.nodeChange((MyQuadCurve)o,((MyQuadCurve)o).clone());
                                ((MyQuadCurve) o).setControlX(((MyQuadCurve) o).getControlX()-trans);
                                ((MyQuadCurve) o).setStartX(((MyQuadCurve) o).getStartX()-trans);
                                ((MyQuadCurve) o).setEndX(((MyQuadCurve) o).getEndX()-trans);
                            }
                            if(o instanceof MyPath)
                            {
                                double trans=((MyPath) o).Miny-miny;
                                RecordStack.nodeChange((MyPath)o,((MyPath)o).clone());
                                ((MoveTo)((MyPath) o).getElements().get(0)).setY(((MoveTo)((MyPath) o).getElements().get(0)).getY()-trans);
                                for(int j=1;j< ((MyPath) o).getElements().size();j++)
                                {
                                    ((LineTo)((MyPath) o).getElements().get(j)).setY(((LineTo)((MyPath) o).getElements().get(j)).getY()-trans);
                                    ((MyPath) o).Miny=((MyPath) o).Miny-trans;
                                    ((MyPath) o).Miny=((MyPath) o).Maxy-trans;
                                }

                            }
                        }
                        maxx=maxy=-999;
                        minx=miny=999;
                        dynamicArray.clear();//最好设一个集合，去重

                    }
                    break;
                case"BOTTOM":
                    if(ToolBarController.first)
                    {



                    }
                    else {

                        for(int i = 0; i < dynamicArray.size(); i++)
                        {
                            System.out.println(dynamicArray.get(i));
                            System.out.println(maxy);
                            Object o=group.getChildren().get(dynamicArray.get(i));
                            System.out.println("Controller.dynamicArray.size()" + Controller.dynamicArray.size());
//                            System.out.println("Object" + o.toString());
                            if(o instanceof MyLine)
                            {
                                double trans=maxy-((MyLine) o).Maxy;
                                System.out.println("trans" + trans);
                                RecordStack.nodeChange((MyLine)o, ((MyLine)o).clone());
                                ((MyLine) o).paint(((MyLine) o).getStartX(),((MyLine) o).getStartY()+trans,((MyLine) o).getEndX(),((MyLine) o).getEndY()+trans);
                            }
                            if(o instanceof MyRectangle)
                            {
                                double trans=maxy-((MyRectangle) o).Maxy;
                                RecordStack.nodeChange((MyRectangle)o,((MyRectangle)o).clone());
                                ((MyRectangle)o).paint(((MyRectangle)o).getX(),((MyRectangle)o).getY()+trans,((MyRectangle)o).getWidth()+((MyRectangle)o).getX(),((MyRectangle)o).getHeight()+((MyRectangle)o).getY()+trans);
                            }
                            if(o instanceof MyEllipse)
                            {
                                double trans=maxy-((MyEllipse) o).Maxy;
                                RecordStack.nodeChange((MyEllipse)o,((MyEllipse)o).clone());
                                ((MyEllipse) o).paint(((MyEllipse) o).getCenterX()-((MyEllipse) o).getRadiusX(),((MyEllipse) o).getCenterY()-((MyEllipse) o).getRadiusY()+trans,((MyEllipse) o).getCenterX()+((MyEllipse) o).getRadiusX(),((MyEllipse) o).getCenterY()+((MyEllipse) o).getRadiusY()+trans,false);
                            }
                            if(o instanceof MyQuadCurve)
                            {
                                double trans=((MyQuadCurve) o).Minx-minx;
                                RecordStack.nodeChange((MyQuadCurve)o,((MyQuadCurve)o).clone());
                                ((MyQuadCurve) o).setControlX(((MyQuadCurve) o).getControlX()-trans);
                                ((MyQuadCurve) o).setStartX(((MyQuadCurve) o).getStartX()-trans);
                                ((MyQuadCurve) o).setEndX(((MyQuadCurve) o).getEndX()-trans);
                            }
                            if(o instanceof MyPath)
                            {
                                double trans=((MyPath) o).Maxy-maxy;
                                RecordStack.nodeChange((MyPath)o,((MyPath)o).clone());
                                ((MoveTo)((MyPath) o).getElements().get(0)).setY(((MoveTo)((MyPath) o).getElements().get(0)).getY()-trans);
                                for(int j=1;j< ((MyPath) o).getElements().size();j++)
                                {
                                    ((LineTo)((MyPath) o).getElements().get(j)).setY(((LineTo)((MyPath) o).getElements().get(j)).getY()-trans);
                                    ((MyPath) o).Miny=((MyPath) o).Miny-trans;
                                    ((MyPath) o).Miny=((MyPath) o).Maxy-trans;
                                }

                            }
                        }
                        dynamicArray.clear();//最好设一个集合，去重
                        maxx=maxy=-999;
                        minx=miny=999;
                    }
                    break;
                case"CENVER":
                    if(ToolBarController.first)
                    {



                    }
                    else {
                        double mid=(maxy+miny)/2;
                        for(int i = 0; i < dynamicArray.size(); i++)
                        {
                            Object o=group.getChildren().get(dynamicArray.get(i));
                            System.out.println("Controller.dynamicArray.size()" + Controller.dynamicArray.size());
//                            System.out.println("Object" + o.toString());
                            if(o instanceof MyLine)
                            {
                                double trans=(((MyLine) o).Miny+((MyLine) o).Maxy)/2-mid;
                                System.out.println("trans" + trans);
                                RecordStack.nodeChange((MyLine)o, ((MyLine)o).clone());
                                ((MyLine) o).paint(((MyLine) o).getStartX(),((MyLine) o).getStartY()-trans,((MyLine) o).getEndX(),((MyLine) o).getEndY()-trans);
                            }
                            if(o instanceof MyRectangle)
                            {
                                double trans=(((MyRectangle) o).Miny+((MyRectangle) o).Maxy)/2-mid;
                                RecordStack.nodeChange((MyRectangle)o,((MyRectangle)o).clone());
                                ((MyRectangle)o).paint(((MyRectangle)o).getX(),((MyRectangle)o).getY()-trans,((MyRectangle)o).getWidth()+((MyRectangle)o).getX(),((MyRectangle)o).getHeight()+((MyRectangle)o).getY()-trans);
                            }
                            if(o instanceof MyEllipse)
                            {
                                double trans=(((MyEllipse) o).Miny+((MyEllipse) o).Maxy)/2-mid;
                                RecordStack.nodeChange((MyEllipse)o,((MyEllipse)o).clone());
                                ((MyEllipse) o).paint(((MyEllipse) o).getCenterX()-((MyEllipse) o).getRadiusX(),((MyEllipse) o).getCenterY()-((MyEllipse) o).getRadiusY()-trans,((MyEllipse) o).getCenterX()+((MyEllipse) o).getRadiusX(),((MyEllipse) o).getCenterY()+((MyEllipse) o).getRadiusY()-trans,false);
                            }
                            if(o instanceof MyQuadCurve)
                            {
                                double trans=((MyQuadCurve) o).Miny-miny;
                                RecordStack.nodeChange((MyQuadCurve)o,((MyQuadCurve)o).clone());
                                ((MyQuadCurve) o).setControlX(((MyQuadCurve) o).getControlX()-trans);
                                ((MyQuadCurve) o).setStartX(((MyQuadCurve) o).getStartX()-trans);
                                ((MyQuadCurve) o).setEndX(((MyQuadCurve) o).getEndX()-trans);
                            }
                            if(o instanceof MyPath)
                            {
                                double trans=(((MyPath) o).Miny+((MyPath) o).Maxy)/2-mid;
                                RecordStack.nodeChange((MyPath)o,((MyPath)o).clone());
                                ((MoveTo)((MyPath) o).getElements().get(0)).setY(((MoveTo)((MyPath) o).getElements().get(0)).getY()-trans);
                                for(int j=1;j< ((MyPath) o).getElements().size();j++)
                                {
                                    ((LineTo)((MyPath) o).getElements().get(j)).setY(((LineTo)((MyPath) o).getElements().get(j)).getY()-trans);
                                    ((MyPath) o).Miny=((MyPath) o).Miny-trans;
                                    ((MyPath) o).Miny=((MyPath) o).Maxy-trans;
                                }


                            }
                        }
                        maxx=maxy=-999;
                        minx=miny=999;
                        dynamicArray.clear();//最好设一个集合，去重

                    }
                    break;
                case "BARREL":
                    break;
            }
            //path.getElements().add(new MoveTo(startX, startY));
            first = false;
        }
    }

    /**
     * 鼠标拖曳响应器
     * @param event 鼠标事件
     */
    @FXML
    private void canvasOnMouseDragged(MouseEvent event) {
        //path.getElements().add(new LineTo(event.getX(), event.getY()));
        // !first确保鼠标松开后就不能拖动重绘了  防止左键松开后右键拖拽还能继续画图形
        if (!first && group.getChildren().get(0).contains(event.getX(), event.getY())) {
            switch (ShapeAttribute.getTool()) {
                case "LINE":
                    line.paint(startX, startY, event.getX(), event.getY());
                    break;
                case "CURVE":
                    quadCurve.paint(startX, startY, event.getX(), event.getY());
                    break;
                case "RECT":
                    rectangle.paint(startX, startY, event.getX(), event.getY());
                    break;
                case "OVAL":
                    ellipse.paint(startX, startY, event.getX(), event.getY(), false);
                    break;
                case "CIRCLE":
                    ellipse.paint(startX, startY, event.getX(), event.getY(), true);
                    break;
                case "PEN":
                    path.paint(event.getX(), event.getY());
                    break;
                case "TEXT":
                    boolean rotate = false;
                    if (event.getButton().equals(MouseButton.SECONDARY)) {
                        rotate = true;
                    }
//                    if (!group.getChildren().contains(text))
                        for (Node e : group.getChildren()) {
                            if (e.getClass().equals(MyText.class) && e.isFocused())
                                text = (MyText) e;
                        }
                    text.paint(event.getX(), event.getY(), rotate);
                    break;
                case "RUBBER":
                    gc.lineTo(event.getX(), event.getY());
                    canvas.addPath(event.getX() + "," + event.getY());
                    gc.stroke();
                    break;
            }

        }
    }

    /**
     * 鼠标释放响应器
     * @param event 鼠标事件
     */
    @FXML
    private void canvasOnMouseReleased(MouseEvent event) {
        if (!first) {
            endX = event.getX();
            endY = event.getY();
            first = true;
        }
/*        if(ShapeAttribute.getTool().equals("TEXT")&&event.getButton().equals(MouseButton.SECONDARY)){
            Record rd=new Record(text, Record.Operation.CHANGE.setOldNode(cloneText));
            RecordStack.push(rd);
        }*/
    }

    /**
     * 鼠标移动响应器
     * @param event 鼠标事件
     */
    @FXML
    private void onMouseMoved(MouseEvent event) {
        label.setText(String.format("鼠标坐标：\n %.1f, %.1fpx ", event.getX(), event.getY()));
    }

    /**
     * 获取组
     * @return 获取{@link #group}
     */
    public Group getGroup() {
        return group;
    }

}
