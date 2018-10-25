package com.lnjecit.thumbnail;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author lnj
 * createTime 2018-10-19 15:39
 **/
public class ImageUtilTest {

    /**
     * 测试在指定目录下生成缩略图
     */
    @Test
    public void testGenerateThumbnail2Directory() throws IOException {
        String path = "D:\\workspace\\idea\\individual\\springboot-learn\\springboot-thumbnail\\image";
        String[] files = new String[]{
                "image/1.jpg",
                "image/2.jpg"
        };

        List<String> list = ImageUtil.generateThumbnail2Directory(path, files);
        System.out.println(list);
    }

    /**
     * 将指定目录下的图片生成缩略图
     */
    @Test
    public void testGenerateDirectoryThumbnail() throws IOException {
        String path = "D:\\workspace\\idea\\individual\\springboot-learn\\springboot-thumbnail\\image";
        ImageUtil.generateDirectoryThumbnail(path);
    }
}
