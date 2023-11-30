package File;

import javafx.scene.Node;
import java.io.*;

/**
 * @see FileCopy
 * 复制用文件
 * @version 2.317
 * @author derek
 */
public class FileCopy {
    /**
     * 要保存的图形节点
     */
    private Node node;


    public FileCopy() {
    }

    public FileCopy(Node nd) {
        node = nd;
    }

    /**
     * 设置节点
     * @param nd 设置{@link #node}
     */
    public void setGroup(Node nd) {
        node = nd;
    }

    /**
     * 保存可编辑文件edit
     */
    public void copy() {
        File f = new File(".\\tmp.copy");
        try {
            FileWriter fw = new FileWriter(f.getPath());
            fw.write(node.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
