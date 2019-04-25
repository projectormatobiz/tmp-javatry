/*
 * Copyright 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.javatry.colorbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import ch.qos.logback.core.joran.util.StringToObjectConverter;
import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.color.BoxColor;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.javatry.colorbox.base.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of String with color-box, not using Stream API. <br>
 * Show answer by log() for question of javadoc. <br>
 * <pre>
 * addition:
 * o e.g. "string in color-boxes" means String-type content in space of color-box
 * o don't fix the YourPrivateRoom class and color-box classes
 * </pre>
 * @author jflute
 * @author projectormatobiz
 */
public class Step11ClassicStringTest extends PlainTestCase {

    //カラーボックス：{{green}, {124, 52, 31}, spaces=3}　こんな感じ

    // ===================================================================================
    //                                                                            length()
    //                                                                            ========
    /**
     * How many lengths does color name of first color-boxes have? <br>
     * (最初のカラーボックスの色の名前の文字数は？)
     */
    public void test_length_basic() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        ColorBox colorBox = colorBoxList.get(0);
        BoxColor boxColor = colorBox.getColor();
        String colorName = boxColor.getColorName();
        int answer = colorName.length();
        log(answer, colorName); // also show name for visual check
    }

    /**
     * Which string has max length in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長い文字列は？)
     */
    public void test_length_findMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String answer = "";
        for (ColorBox box : colorBoxList) {
            List<BoxSpace> spaceList = box.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    log((String) content);
                    int currentLength = ((String) content).length();
                    if (answer.length() < currentLength)
                        answer = (String) content;
                }
            }
        }
        log(!answer.equals("") ? answer : "not found color-box");
    }

    /**
     * How many characters are difference between max and min length of string in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長いものと短いものの差は何文字？)
     */
    public void test_length_findMaxMinDiff() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String maxString = null;
        String minString = null;
        for (ColorBox box : colorBoxList) {
            List<BoxSpace> spaceList = box.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    String strContent = (String) content;
                    int currentLength = strContent.length();
                    if (maxString == null || maxString.length() < currentLength)
                        maxString = (String) content;
                    if (minString == null || currentLength < minString.length())
                        minString = (String) content;
                }
            }
        }
        int answer = maxString != null ? maxString.length() - minString.length() : -1;
        log(answer != -1 ? answer : "not found color-box.");
    }

    /**
     * Which value (toString() if non-string) has second-max legnth in color-boxes? (without sort)<br>
     * (カラーボックスに入ってる値 (文字列以外はtoString()) の中で、二番目に長い文字列は？ (ソートなしで))
     */
    public void test_length_findSecondMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String maxString = "";
        String secondString = "";
        for (ColorBox box : colorBoxList) {
            List<BoxSpace> spaceList = box.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                int currentLength = 0;
                if (content != null)
                    currentLength = content.toString().length();
                //                log(content);
                //                log(currentLength);
                if (maxString.length() < currentLength) {
                    maxString = content.toString();
                } else if (secondString.length() < currentLength) {
                    secondString = content.toString();
                }
            }
        }
        log(secondString, secondString.length());
    }

    /**
     * How many total lengths of strings in color-boxes? <br>
     * (カラーボックスに入ってる文字列の長さの合計は？)
     */
    public void test_length_calculateLengthSum() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int answer = 0;
        for (ColorBox box : colorBoxList) {
            List<BoxSpace> spaceList = box.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    answer += ((String) content).length();
                }
            }
        }
        log(answer != 0 ? answer : "not found color-box");

    }

    /**
     * Which color name has max length in color-boxes? <br>
     * (カラーボックスの中で、色の名前が一番長いものは？)
     */
    public void test_length_findMaxColorSize() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String answer = null;
        for (ColorBox colorBox : colorBoxList) {
            int currentNameLength = colorBox.getColor().getColorName().length();
            if (answer == null || answer.length() < currentNameLength) {
                answer = colorBox.getColor().getColorName();
            }
        }
        log(answer);
    }

    // ===================================================================================
    //                                                            startsWith(), endsWith()
    //                                                            ========================
    /**
     * What is color in the color-box that has string starting with "Water"? <br>
     * ("Water" で始まる文字列をしまっているカラーボックスの色は？)
     */
    public void test_startsWith_findFirstWord() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String answer = "";
        for (ColorBox box : colorBoxList) {
            List<BoxSpace> spaceList = box.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    if (((String) content).startsWith("Water")) {
                        answer = box.getColor().getColorName();
                    }
                }
            }
        }
        log(!answer.equals("") ? answer : "not found color-box");
    }

    /**
     * What is color in the color-box that has string ending with "front"? <br>
     * ("front" で終わる文字列をしまっているカラーボックスの色は？)
     */
    public void test_endsWith_findLastWord() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String answer = "";
        for (ColorBox box : colorBoxList) {
            List<BoxSpace> spaceList = box.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    if (((String) content).endsWith("front")) {
                        answer = box.getColor().getColorName();
                    }
                }
            }
        }
        log(!answer.equals("") ? answer : "not found color-box");

    }

    // ===================================================================================
    //                                                            indexOf(), lastIndexOf()
    //                                                            ========================
    /**
     * What number character is starting with "front" of string ending with "front" in color-boxes? <br>
     * (あなたのカラーボックスに入ってる "front" で終わる文字列で、"front" は何文字目から始まる？)
     */
    public void test_indexOf_findIndex() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int answer = 0;
        for (ColorBox box : colorBoxList) {
            List<BoxSpace> spaceList = box.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    if (((String) content).endsWith("front")) {
                        answer = ((String) content).indexOf("front");
                    }
                }
            }
        }
        log(answer != 0 ? answer : "not found color-box");

    }

    /**
     * What number character is starting with the late "ど" of string containing plural "ど"s in color-boxes? (e.g. "どんどん" => 3) <br>
     * (あなたのカラーボックスに入ってる「ど」を二つ以上含む文字列で、最後の「ど」は何文字目から始まる？ (e.g. "どんどん" => 3))
     */
    public void test_lastIndexOf_findIndex() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int answer = 0;
        for (ColorBox box : colorBoxList) {
            List<BoxSpace> spaceList = box.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    if (((String) content).indexOf("ど", ((String) content).indexOf("ど")) != -1) {
                        answer = ((String) content).lastIndexOf("ど");
                    }
                }
            }
        }
        log(answer != 0 ? answer : "not found color-box");

    }

    // ===================================================================================
    //                                                                         substring()
    //                                                                         ===========
    /**
     * What character is first of string ending with "front" in color-boxes? <br>
     * (カラーボックスに入ってる "front" で終わる文字列の最初の一文字は？)
     */
    public void test_substring_findFirstChar() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String answer = "";
        for (ColorBox box : colorBoxList) {
            List<BoxSpace> spaceList = box.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    if (((String) content).endsWith("front")) {
                        answer = ((String) content).substring(0, 1);
                    }
                }
            }
        }
        log(!answer.equals("") ? answer : "not found color-box");
    }

    /**
     * What character is last of string starting with "Water" in color-boxes? <br>
     * (カラーボックスに入ってる "Water" で始まる文字列の最後の一文字は？)
     */
    public void test_substring_findLastChar() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String answer = "";
        for (ColorBox box : colorBoxList) {
            List<BoxSpace> spaceList = box.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    if (((String) content).startsWith("Water")) {
                        answer = ((String) content).substring(((String) content).length() - 1, ((String) content).length());
                    }
                }
            }
        }
        log(!answer.equals("") ? answer : "not found color-box");

    }

    // ===================================================================================
    //                                                                           replace()
    //                                                                           =========
    /**
     * How many characters does string that contains "o" in color-boxes and removing "o" have? <br>
     * (カラーボックスに入ってる "o" (おー) を含んだ文字列から "o" を全て除去したら何文字？)
     */
    public void test_replace_remove_o() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int answer = 0;
        for (ColorBox box : colorBoxList) {
            List<BoxSpace> spaceList = box.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    answer += ((String) content).replaceAll("o", "").length();
                }
            }
        }
        log(answer != 0 ? answer : "not found color-box");

    }

    /**
     * What string is path string of java.io.File in color-boxes, which is replaced with "/" to Windows file separator? <br>
     * カラーボックスに入ってる java.io.File のパス文字列のファイルセパレーターの "/" を、Windowsのファイルセパレーターに置き換えた文字列は？
     */
    public void test_replace_fileseparator() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String answer = "";
        for (ColorBox box : colorBoxList) {
            List<BoxSpace> spaceList = box.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                if (content instanceof File) {
                    answer = content.toString().replaceFirst("/", "C:");
                }
            }
        }
        log(!answer.equals("") ? answer : "not found color-box");

    }

    // ===================================================================================
    //                                                                    Welcome to Devil
    //                                                                    ================
    /**
     * What is total length of text of DevilBox class in color-boxes? <br>
     * (カラーボックスの中に入っているDevilBoxクラスのtextの長さの合計は？)
     */
    public void test_welcomeToDevil() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int answer = 0;
        for (ColorBox box : colorBoxList) {
            List<BoxSpace> spaceList = box.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                if (content instanceof YourPrivateRoom.DevilBox) {
                    answer += content.toString().length();

                }
            }
        }
        log(answer != 0 ? answer : "not found color-box");
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What string is converted to style "map:{ key = value ; key = value ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = value ; ... }" という形式で表示すると？)
     */
    public void test_showMap() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Map<String, Integer> answer = new HashMap<String, Integer>();
        for (ColorBox box : colorBoxList) {
            List<BoxSpace> spaceList = box.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                if (content instanceof Map) {
                    log("map:" + content);
                    answer.putAll((Map) content);
                }
            }
        }
        log(answer);
    }

    /**
     *
     * @param string
     * @return Map<String, String>
     * @throws IOException
     */
    public Map StringToMap(String string) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(string));
        Map<String, String> map = new HashMap<String, String>();
        String line;
        while ((line = reader.readLine()) != null) {
            int index = line.indexOf("=");
            if (index == -1) {
                throw new IllegalArgumentException();
            }
            String key = line.substring(0, index);
            String value = line.substring(index + 1);
            map.put(key, value);
        }
        return map;
    }
    public void test_showMap_flat() {
    }

    /**
     * What string is converted to style "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" という形式で表示すると？)
     */
    public void test_showMap_nested() {
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * What string of toString() is converted from text of SecretBox class in upper space on the "white" color-box to java.util.Map? <br>
     * (whiteのカラーボックスのupperスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
     */
    public void test_parseMap_basic() throws IOException {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Map<String, String> answer = new HashMap<String, String>();
        for (ColorBox box : colorBoxList) {
            if (box.getColor().getColorName().equals("white")) {
                List<BoxSpace> spaceList = box.getSpaceList();
                Object content = spaceList.get(0).getContent(); //upperスペース?
                if (content instanceof YourPrivateRoom.SecretBox) {
                    String currnet = ((YourPrivateRoom.SecretBox) content).getText();
                    //正規表現で{}消す
                    currnet = currnet.replaceFirst("map:", "");
                    answer = StringToMap(currnet);
                    log(answer);

                }
            }
        }
        //        log(answer);
    }
    public void test_parseMap_flat() {
    }

    // String -> Mapのメソッドを作成しましょう

    /**
     * What string of toString() is converted from text of SecretBox class in both middle and lower spaces on the "white" color-box to java.util.Map? <br>
     * (whiteのカラーボックスのmiddleおよびlowerスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
     */
    public void test_parseMap_deep() throws IOException {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Map<String, Integer> answer = new HashMap<String, Integer>();
        for (ColorBox box : colorBoxList) {
            if (box.getColor().getColorName().equals("white")) {
                List<BoxSpace> spaceList = box.getSpaceList();
                Object content = spaceList.get(1).getContent();
                if (content instanceof YourPrivateRoom.SecretBox) {
                    log(((YourPrivateRoom.SecretBox) content).getText());
                    String currnet = ((YourPrivateRoom.SecretBox) content).getText();
                    currnet = currnet.replaceFirst("map:", "");
                    log(currnet);
                    answer = StringToMap(currnet);
                    log(answer);
                }
                content = spaceList.get(2).getContent();
                if (content instanceof YourPrivateRoom.SecretBox) {
                    log(((YourPrivateRoom.SecretBox) content).getText());
                }
            }
        }
    }
    public void test_parseMap_nested() {
    }
}
