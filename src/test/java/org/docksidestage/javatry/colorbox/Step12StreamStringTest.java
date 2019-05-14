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

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.javatry.colorbox.base.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of String with color-box, using Stream API you can. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author your_name_here
 */
public class Step12StreamStringTest extends PlainTestCase {

    // ===================================================================================
    //                                                                            length()
    //                                                                            ========
    /**
     * What is color name length of first color-box? <br>
     * (最初のカラーボックスの色の名前の文字数は？)
     */
    public void test_length_basic() {
        // Listを同じように取得する。
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        // streamを作る
        String answer = colorBoxList.stream()
                // 最初の一つを返す
                .findFirst()
                // 全体をBoxColorにする
                .map(colorBox -> colorBox.getColor()) // consciously split as example
                // さらにそこからStringにする
                .map(boxColor -> boxColor.getColorName())
                // 長さを返す
                .map(colorName -> {
                    log(colorName); // for visual check
                    return String.valueOf(colorName.length());
                })
                .orElse("not found"); // basically no way because of not-empty list and not-null returns
        log(answer);
    }

    /**
     * Which string has max length in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長い文字列は？)
     */
    public void test_length_findMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String answer = colorBoxList.stream()
                .flatMap(spaceList -> spaceList.getSpaceList().stream())
                .filter(strContent -> strContent.getContent() instanceof String)
                .map(content -> String.valueOf(content.getContent()))
                // これってどっちがいいの？
//                .max((v1, v2) -> Integer.compare(v1.length(), v2.length()))
                .reduce((ans, target) -> {
                    return ans.length() < target.length() ? target : ans;
                })
                .orElse("not found");

        log(answer);
    }

    /**
     * How many characters are difference between max and min length of string in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長いものと短いものの差は何文字？)
     */
    public void test_length_findMaxMinDiff() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        // TODO:もっと良い処理を知りたい
        // Streamは使い回し出来ないみたいなので、中間処置してから、一度普通のオブジェクトに落とし込んで、もっかいStreamし始めるとか
        List<String> strAll = colorBoxList.stream()
                .flatMap(spaceList -> spaceList.getSpaceList().stream())
                .filter(strContent -> strContent.getContent() instanceof String)
                .map(content -> String.valueOf(content.getContent()))
                .collect(Collectors.toList());
        String longest = strAll.stream()
                .reduce((ans, target) -> {
                    return ans.length() > target.length() ? ans : target;
                })
                .orElse("not found");
        String shortest = strAll.stream()
                .reduce((ans, target) -> {
                    return ans.length() < target.length() ? ans : target;
                })
                .orElse("not found");
        log(longest.length() - shortest.length());
    }

    /**
     * Which value (toString() if non-string) has second-max legnth in color-boxes? (without sort)<br>
     * (カラーボックスに入ってる値 (文字列以外はtoString()) の中で、二番目に長い文字列は？ (ソートなしで))
     */
    public void test_length_findSecondMax() {
        // よく分からなくなったので、一旦飛ばします
        String max = "";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String secondLengthValue = colorBoxList.stream()
                .flatMap(spaceList -> spaceList.getSpaceList().stream())
                .map(content -> content.getContent().toString())
                .reduce((ans, target) -> {
                    // 本当にこれでいいんだろうか
//                    if (target.length() >= max.length()) {
//                        max = target;
//                    }else {
//
//                    }
                    return ans;
                })
                .orElse("not found second length value.");

    }

    /**
     * How many total lengths of strings in color-boxes? <br>
     * (カラーボックスに入ってる文字列の長さの合計は？)
     */
    public void test_length_calculateLengthSum() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int ans = colorBoxList.stream()
                .flatMap(spaceList -> spaceList.getSpaceList().stream())
                .filter(strContent -> strContent.getContent() instanceof String)
                .map(content -> content.getContent().toString().length())
                .reduce((sum, value) -> {
                    return sum += value;
                })
                .orElse(-1);
        log(ans != -1 ? ans : "not found color box.");

    }

    /**
     * Which color name has max length in color-boxes? <br>
     * (カラーボックスの中で、色の名前が一番長いものは？)
     */
    public void test_length_findMaxColorSize() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String ans = colorBoxList.stream()
                .map(colorList -> colorList.getColor().getColorName())
                .reduce((max, value) -> {
                    return max.length() >= value.length() ? max : value;
                })
                .orElse("not found color box.");
        log(ans);
    }

    // ===================================================================================
    //                                                            startsWith(), endsWith()
    //                                                            ========================
    /**
     * What is color in the color-box that has string starting with "Water"? <br>
     * ("Water" で始まる文字列をしまっているカラーボックスの色は？)
     */
    public void test_startsWith_findFirstWord() {
        //mapすると情報が失われる気がするのでfilterで取得する
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
//        String ans = colorBoxList.stream()
//                .filter(strContent -> strContent. instanceof String)
//                .filter(startWithWaterContent -> startWithWaterContent.toString().startsWith("Water"))
//                .map(ansStr -> ansStr.getC)
//        log(ans);
    }

    /**
     * What is color in the color-box that has string ending with "front"? <br>
     * ("front" で終わる文字列をしまっているカラーボックスの色は？)
     */
    public void test_endsWith_findLastWord() {
    }

    // ===================================================================================
    //                                                            indexOf(), lastIndexOf()
    //                                                            ========================
    /**
     * What number character is starting with "front" of string ending with "front" in color-boxes? <br>
     * (あなたのカラーボックスに入ってる "front" で終わる文字列で、"front" は何文字目から始まる？)
     */
    public void test_indexOf_findIndex() {
    }

    /**
     * What number character is starting with the late "ど" of string containing plural "ど"s in color-boxes? (e.g. "どんどん" => 3) <br>
     * (あなたのカラーボックスに入ってる「ど」を二つ以上含む文字列で、最後の「ど」は何文字目から始まる？ (e.g. "どんどん" => 3))
     */
    public void test_lastIndexOf_findIndex() {
    }

    // ===================================================================================
    //                                                                         substring()
    //                                                                         ===========
    /**
     * What character is first of string ending with "front" in color-boxes? <br>
     * (カラーボックスに入ってる "front" で終わる文字列の最初の一文字は？)
     */
    public void test_substring_findFirstChar() {
    }

    /**
     * What character is last of string starting with "Water" in color-boxes? <br>
     * (カラーボックスに入ってる "Water" で始まる文字列の最後の一文字は？)
     */
    public void test_substring_findLastChar() {
    }

    // ===================================================================================
    //                                                                           replace()
    //                                                                           =========
    /**
     * How many characters does string that contains "o" in color-boxes and removing "o" have? <br>
     * (カラーボックスに入ってる "o" (おー) を含んだ文字列から "o" を全て除去したら何文字？)
     */
    public void test_replace_remove_o() {
    }

    /**
     * What string is path string of java.io.File in color-boxes, which is replaced with "/" to Windows file separator? <br>
     * カラーボックスに入ってる java.io.File のパス文字列のファイルセパレーターの "/" を、Windowsのファイルセパレーターに置き換えた文字列は？
     */
    public void test_replace_fileseparator() {
    }

    // ===================================================================================
    //                                                                    Welcome to Devil
    //                                                                    ================
    /**
     * What is total length of text of DevilBox class in color-boxes? <br>
     * (カラーボックスの中に入っているDevilBoxクラスのtextの長さの合計は？)
     */
    public void test_welcomeToDevil() {
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What string is converted to style "map:{ key = value ; key = value ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = value ; ... }" という形式で表示すると？)
     */
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
    // too difficult to be stream?
    ///**
    // * What string of toString() is converted from text of SecretBox class in upper space on the "white" color-box to java.util.Map? <br>
    // * (whiteのカラーボックスのupperスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
    // */
    //public void test_parseMap_flat() {
    //}
    //
    ///**
    // * What string of toString() is converted from text of SecretBox class in both middle and lower spaces on the "white" color-box to java.util.Map? <br>
    // * (whiteのカラーボックスのmiddleおよびlowerスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
    // */
    //public void test_parseMap_nested() {
    //}
}
