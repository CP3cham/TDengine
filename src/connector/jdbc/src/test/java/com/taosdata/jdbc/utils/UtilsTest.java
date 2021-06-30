package com.taosdata.jdbc.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;

public class UtilsTest {

    @Test
    public void escapeSingleQuota() {
        // given
        String s = "'''''a\\'";
        // when
        String news = Utils.escapeSingleQuota(s);
        // then
        Assert.assertEquals("\\'\\'\\'\\'\\'a\\'", news);

        // given
        s = "\'''''a\\'";
        // when
        news = Utils.escapeSingleQuota(s);
        // then
        Assert.assertEquals("\\'\\'\\'\\'\\'a\\'", news);

        // given
        s = "\'\'\'\''a\\'";
        // when
        news = Utils.escapeSingleQuota(s);
        // then
        Assert.assertEquals("\\'\\'\\'\\'\\'a\\'", news);
    }

    @Test
    public void getNativeSqlReplaceQuestionMarks() {
        // given
        String nativeSql = "insert into ?.? (ts, temperature, humidity) using ?.? tags(?,?) values(now, ?, ?)";
        Object[] parameters = Stream.of("test", "t1", "test", "weather", "beijing", 1, 12.2, 4).toArray();

        // when
        String actual = Utils.getNativeSql(nativeSql, parameters);

        // then
        String expected = "insert into test.t1 (ts, temperature, humidity) using test.weather tags('beijing',1) values(now, 12.2, 4)";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getNativeSqlReplaceQuestionMarks2() {
        // given
        String nativeSql = "INSERT INTO ? (TS,CURRENT,VOLTAGE,PHASE) USING METERS TAGS (?)  VALUES (?,?,?,?)";
        Object[] parameters = Stream.of("d1", 1, 123, 3.14, 220, 4).toArray();

        // when
        String actual = Utils.getNativeSql(nativeSql, parameters);

        // then
        String expected = "INSERT INTO d1 (TS,CURRENT,VOLTAGE,PHASE) USING METERS TAGS (1)  VALUES (123,3.14,220,4)";
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void getNativeSqlReplaceNothing() {
        // given
        String nativeSql = "insert into test.t1 (ts, temperature, humidity) using test.weather tags('beijing',1) values(now, 12.2, 4)";

        // when
        String actual = Utils.getNativeSql(nativeSql, null);

        // then
        Assert.assertEquals(nativeSql, actual);
    }

    @Test
    public void getNativeSqlReplaceNothing2() {
        // given
        String nativeSql = "insert into test.t1 (ts, temperature, humidity) using test.weather tags('beijing',1) values(now, 12.2, 4)";
        Object[] parameters = Stream.of("test", "t1", "test", "weather", "beijing", 1, 12.2, 4).toArray();

        // when
        String actual = Utils.getNativeSql(nativeSql, parameters);

        // then
        Assert.assertEquals(nativeSql, actual);
    }

    @Test
    public void getNativeSqlReplaceNothing3() {
        // given
        String nativeSql = "insert into ?.? (ts, temperature, humidity) using ?.? tags(?,?) values(now, ?, ?)";

        // when
        String actual = Utils.getNativeSql(nativeSql, null);

        // then
        Assert.assertEquals(nativeSql, actual);

    }
}