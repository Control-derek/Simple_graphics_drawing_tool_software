package File;

import App.ShapeAttribute;
import Shape.*;
import UndoManager.RecordStack;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Objects;

/**
 * @see FilePaste
 * 复制用文件
 * @version 2.317
 * @author derek
 */
public class FilePaste {
    /**
     * 画图板的图形组
     */
    private Group openingGroup;

    public FilePaste() {
    }

    public FilePaste(Group gp) {
        openingGroup = gp;
    }

    /**
     * 设置组
     * @param gp 设置{@link #openingGroup}
     */
    public void setGroup(Group gp) {
        openingGroup = gp;
    }

    /**
     * 打开可编辑文件edit
     */
    public void paste(double x, double y) {
        File file = new File(".\\tmp.copy");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
//            openingGroup.getChildren().clear();
//            while (RecordStack.undo()) ;
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.equals("")) continue;
                String[] attr = line.split("\\s+");
                if (Objects.equals(attr[0], "Path")) {
                    int index = 1;
                    String str = attr[index];
                    boolean isFirst = true;
                    double px0 = 0.0, py0 = 0.0;
                    while (!str.equals("@")) {
                        String[] pos = str.split(",");
                        double px = Double.parseDouble(pos[0]), py = Double.parseDouble(pos[1]);
                        StringBuilder sb = new StringBuilder();
                        if (isFirst) {
                            isFirst = false;
                            px0 = px; py0 = py;
                        }
                        double dx = px - px0, dy = py - py0;
                        sb.append(x+dx).append(",").append(y+dy).append(" ");
                        attr[index] = new String(sb);
                        index++;
                        str = attr[index];
                    }
                }
                else {  // 除了笔画
                    if (Objects.equals(attr[0], "Line") || Objects.equals(attr[0], "Curve")) {
                        double deltaX = Double.parseDouble(attr[3]) - Double.parseDouble(attr[1]);
                        double deltaY = Double.parseDouble(attr[4]) - Double.parseDouble(attr[2]);
                        attr[3] = String.valueOf(x + deltaX);
                        attr[4] = String.valueOf(y + deltaY);
                    }
                    if (Objects.equals(attr[0], "Curve")) {
                        double deltaX = Double.parseDouble(attr[5]) - Double.parseDouble(attr[1]);
                        double deltaY = Double.parseDouble(attr[6]) - Double.parseDouble(attr[2]);
                        attr[5] = String.valueOf(x + deltaX);
                        attr[6] = String.valueOf(y + deltaY);
                        attr[7] = String.valueOf(0.0);
                        attr[8] = String.valueOf(0.0);
                    } else {
                        attr[5] = String.valueOf(0.0);
                        attr[6] = String.valueOf(0.0);
                    }
                    attr[1] = String.valueOf(x);
                    attr[2] = String.valueOf(y);
                }
                Node newNode = createNode(attr);
                openingGroup.getChildren().addAll(newNode);
            }
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    /**
     * 创建Node对象
     * @param arr 字符串数组保存node信息
     * @return 返回创建的Node地址
     * @throws Exception 异常
     */
    protected Node createNode(String[] arr) throws Exception {
        return switch (arr[0]) {
            case "Ellipse" -> new MyEllipse().creator(arr);
            case "Line" -> new MyLine().creator(arr);
            case "Path" -> new MyPath(0, 0).creator(arr);
            case "Curve" -> new MyQuadCurve().creator(arr);
            case "Rectangle" -> new MyRectangle().creator(arr);
            case "Text" -> new MyText(0, 0, "").creator(arr);
            case "Canvas" -> new MyCanvas(ShapeAttribute.CANVAS_WIDTH, ShapeAttribute.CANVAS_HEIGHT).creator(arr);
            default -> null;
        };
    }
}
